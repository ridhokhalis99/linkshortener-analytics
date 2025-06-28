CREATE TABLE IF NOT EXISTS analytics_events (
    id SERIAL PRIMARY KEY,
    short_code VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    ip_address VARCHAR(255),
    user_agent TEXT,
    referrer TEXT,
    country VARCHAR(100)
);
