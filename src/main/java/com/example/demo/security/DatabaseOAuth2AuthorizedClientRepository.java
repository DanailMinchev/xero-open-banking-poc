package com.example.demo.security;

import com.example.demo.xero.security.domain.XeroOAuth2AuthorizedClientEntity;
import com.example.demo.xero.security.repository.XeroOAuth2AuthorizedClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseOAuth2AuthorizedClientRepository implements OAuth2AuthorizedClientRepository {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final XeroOAuth2AuthorizedClientRepository xeroOAuth2AuthorizedClientRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
                                                                     Authentication principal,
                                                                     HttpServletRequest request) {
        Optional<XeroOAuth2AuthorizedClientEntity> entityOptional =
                xeroOAuth2AuthorizedClientRepository.findByRegistrationIdAndPrincipalName(
                        clientRegistrationId,
                        principal.getName()
                );

        if (entityOptional.isEmpty()) {
            return null;
        }

        XeroOAuth2AuthorizedClientEntity entity = entityOptional.get();

        // Reconstruct the ClientRegistration using the existing repository
        ClientRegistration clientRegistration =
                clientRegistrationRepository.findByRegistrationId(entity.getRegistrationId());
        if (clientRegistration == null) {
            return null;
        }

        // Build an OAuth2AccessToken
        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                entity.getAccessToken(),
                entity.getAccessTokenIssuedAt(),
                entity.getAccessTokenExpiresAt()
        );

        // Build a RefreshToken
        OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                entity.getRefreshToken(),
                entity.getRefreshTokenIssuedAt()
        );

        OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(
                clientRegistration,
                principal.getName(),
                accessToken,
                refreshToken
        );

        @SuppressWarnings("unchecked")
        T castedClient = (T) client;
        return castedClient;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient,
                                     Authentication principal,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        // Find existing or create a new entity
        Optional<XeroOAuth2AuthorizedClientEntity> entityOptional =
                xeroOAuth2AuthorizedClientRepository.findByRegistrationIdAndPrincipalName(
                        authorizedClient.getClientRegistration().getRegistrationId(),
                        principal.getName()
                );
        XeroOAuth2AuthorizedClientEntity entity = entityOptional.orElseGet(XeroOAuth2AuthorizedClientEntity::new);

        entity.setRegistrationId(authorizedClient.getClientRegistration().getRegistrationId());
        entity.setPrincipalName(principal.getName());

        // Access Token
        entity.setAccessToken(authorizedClient.getAccessToken().getTokenValue());
        entity.setAccessTokenIssuedAt(authorizedClient.getAccessToken().getIssuedAt());
        entity.setAccessTokenExpiresAt(authorizedClient.getAccessToken().getExpiresAt());

        // Refresh Token
        entity.setRefreshToken(authorizedClient.getRefreshToken().getTokenValue());
        entity.setRefreshTokenIssuedAt(authorizedClient.getRefreshToken().getIssuedAt());

        xeroOAuth2AuthorizedClientRepository.save(entity);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId,
                                       Authentication principal,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            xeroOAuth2AuthorizedClientRepository.deleteByRegistrationIdAndPrincipalName(clientRegistrationId, principal.getName());
        } catch (EmptyResultDataAccessException ignored) {
            // No record to remove
        }
    }
}
