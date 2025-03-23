package com.example.demo.xero.scheduling.tokenrefresh;

import com.example.demo.xero.security.domain.XeroOAuth2AuthorizedClientEntity;
import com.example.demo.xero.security.repository.XeroOAuth2AuthorizedClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Service responsible for refreshing OAuth2 tokens for Xero integrations.
 *
 * @see <a href="https://developer.xero.com/documentation/guides/oauth2/auth-flow/#refreshing-access-and-refresh-tokens">Refreshing access and refresh tokens</a>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenRefreshService {

    private final XeroOAuth2AuthorizedClientRepository xeroOAuth2AuthorizedClientRepository;

    @Value("${app.xero.client-id}")
    private String clientId;

    @Value("${app.xero.client-secret}")
    private String clientSecret;

    // TODO: adjust according the volume of clients and API rate limits
    // https://developer.xero.com/documentation/guides/oauth2/limits/#api-rate-limits
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void refreshToken() {
        log.info("Started Xero token refresh scheduled task");

        Page<XeroOAuth2AuthorizedClientEntity> page = xeroOAuth2AuthorizedClientRepository.findAll(PageRequest.of(
                0,
                1,
                Sort.by(Sort.Direction.ASC, "refreshTokenIssuedAt")
        ));

        log.info("Found {} Xero authorized clients to refresh", page.getTotalElements());

        for (XeroOAuth2AuthorizedClientEntity entity : page.getContent()) {
            log.info("Refreshing client id {}", entity.getId());

            TokenResponseDto tokenResponseDto = RestClient.create()
                    .post()
                    .uri("https://identity.xero.com/connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(String.format(
                            "grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s",
                            entity.getRefreshToken(),
                            clientId,
                            clientSecret
                    ))
                    .retrieve()
                    .body(TokenResponseDto.class);

            entity.setAccessToken(tokenResponseDto.getAccessToken());
            entity.setAccessTokenIssuedAt(Instant.now());
            entity.setAccessTokenExpiresAt(Instant.now().plusSeconds(tokenResponseDto.getExpiresIn()));
            entity.setRefreshToken(tokenResponseDto.getRefreshToken());
            entity.setRefreshTokenIssuedAt(Instant.now());

            xeroOAuth2AuthorizedClientRepository.save(entity);
        }

        log.info("Finished Xero token refresh scheduled task");
    }

}
