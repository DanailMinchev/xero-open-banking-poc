# Xero Open Banking proof of concept

This PoC demonstrates an integration between Xero and Open Baking provider (currently GoCardless).

## Setup and run locally

Copy [.env.example](.env.example) into [.env](.env) file and import it into the run configuration of the application.

## Xero useful information

According to [The standard authorization code flow](https://developer.xero.com/documentation/guides/oauth2/auth-flow) there is token expiry.

Token expiry times:
- id_token: 5 minutes
- access_token: 30 minutes
- refresh_token: 60 days

## TODOs

1. Create a `User` registration and onboarding flow, so that the user can register / login and connect Xero and GoCardless accounts.
   User will be associated with `com.example.demo.xero.security.domain.XeroOAuth2AuthorizedClientEntity`.
2. Change the schedulers to be lockable (using database)
3. Check `TODO` in the code
