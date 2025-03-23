package com.example.demo.xero.security.repository;

import com.example.demo.xero.security.domain.XeroOAuth2AuthorizedClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface XeroOAuth2AuthorizedClientRepository extends JpaRepository<XeroOAuth2AuthorizedClientEntity, Long> {

    Optional<XeroOAuth2AuthorizedClientEntity> findByRegistrationIdAndPrincipalName(String registrationId, String principalName);

    void deleteByRegistrationIdAndPrincipalName(String registrationId, String principalName);

}
