-- Active: 1765210607825@@127.0.0.1@5432@postgres
CREATE TYPE report_status_enum AS ENUM ('OPEN', 'RESOLVED');
CREATE TABLE IF NOT EXISTS report (
    id SERIAL PRIMARY KEY,
    reporter_id INT,
    reported_id INT,
    post_id INT,
    message_id INT,
    reason TEXT,
    status report_status_enum DEFAULT 'OPEN'
    );

drop type report_status_enum;

drop table report;