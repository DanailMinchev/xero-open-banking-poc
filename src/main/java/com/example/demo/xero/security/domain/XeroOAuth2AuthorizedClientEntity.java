package com.example.demo.xero.security.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "xero_oauth2_authorized_clients")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class XeroOAuth2AuthorizedClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "registration_id", nullable = false)
    private String registrationId;

    @NotBlank
    @Column(name = "principal_name", nullable = false)
    private String principalName;

    @NotBlank
    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @NotNull
    @Column(name = "access_token_issued_at", nullable = false)
    private Instant accessTokenIssuedAt;

    @NotNull
    @Column(name = "access_token_expires_at", nullable = false)
    private Instant accessTokenExpiresAt;

    @NotBlank
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @NotNull
    @Column(name = "refresh_token_issued_at", nullable = false)
    private Instant refreshTokenIssuedAt;

}
