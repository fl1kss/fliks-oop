-- Optional sample data
INSERT INTO app_user (username, email)
VALUES ('alice', 'alice@mail.com'),
       ('bob', 'bob@mail.com')
ON CONFLICT DO NOTHING;

INSERT INTO post (user_id, content, likes_count)
SELECT id, 'Hello from Alice!', 1 FROM app_user WHERE username='alice';

INSERT INTO post (user_id, content, likes_count)
SELECT id, 'Bob first post', 3 FROM app_user WHERE username='bob';
