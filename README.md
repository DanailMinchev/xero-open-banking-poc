# Xero Open Banking proof of concept

This PoC demonstrates an integration between Xero and Open Baking provider (currently GoCardless).

## Setup and run locally

### .env

Copy [.env.example](.env.example) into [.env](.env) file and import it into the run configuration of the application.

### Xero

Follow [Getting started guide](https://developer.xero.com/documentation/getting-started-guide/).

Register in [https://developer.xero.com/](https://developer.xero.com/) and create a new application.

Use `http://localhost:8080/` for `Redirect URI` of the new application for the local testing.

Change following `.env` variables:

- XERO_CLIENT_ID
  
- XERO_CLIENT_SECRET

Navigate to [http://localhost:8080/oauth2/authorization/xero-client](http://localhost:8080/oauth2/authorization/xero-client)
and select `Demo Company (Global)`

## Xero useful information

According to [The standard authorization code flow](https://developer.xero.com/documentation/guides/oauth2/auth-flow) there is token expiry.

Token expiry times:
- id_token: 5 minutes
- access_token: 30 minutes
- refresh_token: 60 days

## TODOs

- Create a `User` registration and onboarding flow, so that the user can register / login and connect Xero and GoCardless accounts.
   User will be associated with `com.example.demo.xero.security.domain.XeroOAuth2AuthorizedClientEntity`.
- Change the schedulers to be lockable (using database)
- Check OAuth scopes for Xero, remove not needed ones
- Add unit and integration tests
- Check `TODO` in the code
