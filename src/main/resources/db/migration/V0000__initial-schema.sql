CREATE TABLE IF NOT EXISTS xero_oauth2_authorized_clients
(
    id                      BIGSERIAL PRIMARY KEY,
    registration_id         VARCHAR(255)  NOT NULL,
    principal_name          VARCHAR(255)  NOT NULL,
    access_token            VARCHAR(3000) NOT NULL,
    access_token_issued_at  TIMESTAMP     NOT NULL,
    access_token_expires_at TIMESTAMP     NOT NULL,
    refresh_token           VARCHAR(3000) NOT NULL,
    refresh_token_issued_at TIMESTAMP     NOT NULL
);
