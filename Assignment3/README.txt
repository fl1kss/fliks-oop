Assignment 3 – Milestone 1 (Social Media Platform + PostgreSQL)

What this project shows (requirements):
1) PostgreSQL: 2 tables (app_user, post) like entity classes (User, Post).
2) Java JDBC connection to Postgres.
3) CRUD actions: Create / Read / Update / Delete.

HOW TO RUN
A) Database
1. Create database: social_media_db
2. Run db/schema.sql
(Optional) Run db/sample_data.sql

B) Java
1. Open project folder in IntelliJ (the folder "Assignment3")
2. In Main.java change DB_URL / DB_USER / DB_PASSWORD
. Add PostgreSQL JDBC driver:
   - Easiest: use Maven/Gradle OR add postgresql-*.jar to libraries.
4. Run Main.java

Expected output:
- Connected ✅
- Users list + Feed list
- After UPDATE: changed email, edited post, +1 like
- After DELETE: post and user removed
