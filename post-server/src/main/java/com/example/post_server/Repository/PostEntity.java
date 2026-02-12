package com.example.post_server.Repository;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class PostEntity {
	@Id
	Integer id;

	Integer seller_id;	//foreign key, references another data in another service.

	String title;
	String description;
	double price;

	//post status enum

	Date created_at;
	Date last_edited;
	String media_Encoded;
	String[] tags;


}
/*
CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    seller_id INT REFERENCES users(id) ON DELETE CASCADE,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status post_status_enum DEFAULT 'unlisted',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_edit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS post_media (
    id SERIAL PRIMARY KEY,
    post_id INT REFERENCES post(id) ON DELETE CASCADE,
    media_encoded TEXT NOT NULL,
    media_type media_type_enum DEFAULT 'image'
    );


CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    tag_name VARCHAR(50) UNIQUE NOT NULL
    );


CREATE TABLE IF NOT EXISTS post_tags (
    post_id INT REFERENCES post(id) ON DELETE CASCADE,
    tag_id INT REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
    );
 */