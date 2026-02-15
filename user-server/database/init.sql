CREATE TYPE user_role_enum AS ENUM ('user', 'admin', 'super_user');

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    role user_role_enum NOT NULL DEFAULT 'user',
    verified_seller BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS user_profile (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    pfp_encoded TEXT,
    bio TEXT,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    address VARCHAR(255)
    );