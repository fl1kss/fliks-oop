import java.sql.*;

public class Db {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/social_media_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "12345678"; // поменяй

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

