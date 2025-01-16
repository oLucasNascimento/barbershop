CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL
)