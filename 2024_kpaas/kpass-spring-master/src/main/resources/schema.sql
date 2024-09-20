create table users(
                      user_id varchar(50) not null PRIMARY KEY,
                      user_name varchar(255) not null,
                      user_email varchar(255) not null,
                      user_login_type varchar(10) not null,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      user_role varchar(10) not null default 'ROLE_USER'
);

CREATE TABLE members
(
    member_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_UUID  BINARY(16) NOT NULL,
    member_name  VARCHAR(50) NOT NULL,
    member_email VARCHAR(50) NOT NULL,
    created_at   datetime(6) NOT NULL,
    updated_at   datetime(6) NOT NULL
);

CREATE TABLE posts
(
    post_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_UUID  BINARY(16) NOT NULL,
    member_id  BIGINT NOT NULL,
    title      VARCHAR(50)  NOT NULL,
    content    VARCHAR(500) NOT NULL,
    like_count INT,
    region     VARCHAR(50),
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) NOT NULL,
    CONSTRAINT fk_posts_to_members FOREIGN KEY (member_id) REFERENCES members (member_id) ON DELETE CASCADE
);

CREATE TABLE comments
(
    comment_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment_UUID BINARY(16) NOT NULL,
    post_id      BIGINT NOT NULL,
    content      VARCHAR(500) NOT NULL,
    like_count   INT,
    created_at   datetime(6) NOT NULL,
    updated_at   datetime(6) NOT NULL,
    CONSTRAINT fk_comments_to_posts FOREIGN KEY (post_id) REFERENCES posts (post_id) ON DELETE CASCADE
);