import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class SocialMedia {

    public List<User> getAllUsers(Connection conn) throws SQLException {
        String sql = "SELECT id, username, email, created_at FROM app_user ORDER BY id";
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getObject("created_at", OffsetDateTime.class)
                ));
            }
        }
        return users;
    }

    public List<String> getFeed(Connection conn) throws SQLException {
        String sql =
                "SELECT p.id, u.username, p.content, p.likes_count, p.created_at " +
                        "FROM post p " +
                        "JOIN app_user u ON u.id = p.user_id " +
                        "ORDER BY p.created_at DESC";
        List<String> rows = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rows.add("Post{id=" + rs.getLong("id") +
                        ", author='" + rs.getString("username") + "'" +
                        ", likes=" + rs.getInt("likes_count") +
                        ", createdAt=" + rs.getObject("created_at", OffsetDateTime.class) +
                        ", content='" + rs.getString("content") + "'}");
            }
        }
        return rows;
    }

    public long createUser(Connection conn, String username, String email) throws SQLException {
        String sql = "INSERT INTO app_user (username, email) VALUES (?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    public long createPost(Connection conn, long userId, String content) throws SQLException {
        String sql = "INSERT INTO post (user_id, content) VALUES (?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setString(2, content);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    public void changeEmail(Connection conn, long userId, String newEmail) throws SQLException {
        String sql = "UPDATE app_user SET email=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newEmail);
            ps.setLong(2, userId);
            ps.executeUpdate();
        }
    }

    public void editPost(Connection conn, long postId, String newContent) throws SQLException {
        String sql = "UPDATE post SET content=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newContent);
            ps.setLong(2, postId);
            ps.executeUpdate();
        }
    }

    public void likePost(Connection conn, long postId) throws SQLException {
        String sql = "UPDATE post SET likes_count = likes_count + 1 WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, postId);
            ps.executeUpdate();
        }
    }

    public void removePost(Connection conn, long postId) throws SQLException {
        String sql = "DELETE FROM post WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, postId);
            ps.executeUpdate();
        }
    }

    public void removeUser(Connection conn, long userId) throws SQLException {
        String sql = "DELETE FROM app_user WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        }
    }
}
