import java.time.OffsetDateTime;

public class User {
    private final long id;
    private final String username;
    private final String email;
    private final OffsetDateTime createdAt;

    public User(long id, String username, String email, OffsetDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt + "}";
    }
}
