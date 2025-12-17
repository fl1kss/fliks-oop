import java.time.LocalDateTime;
import java.util.*;

public class Main {

    static void main(String[] args) {

        System.out.println("Args count: " + args.length);

        SocialMedia platform = new SocialMedia("MiniSocial");

        User nurakhmet = new User("nurakhmet", "Nurakhmet", "nurakhmet@mail.com");
        User princess  = new User("princess",  "Princess",  "princess@mail.com");
        User erlen     = new User("erlen",     "Erlen",     "erlen@mail.com");
        User adil      = new User("adil",      "Adil",      "adil@mail.com");


        adil.setUsername(adil.getUsername());


        System.out.println("Nurakhmet email: " + nurakhmet.getEmail());
        nurakhmet.setEmail(nurakhmet.getEmail());

        platform.registerUser(nurakhmet);
        platform.registerUser(princess);
        platform.registerUser(erlen);
        platform.registerUser(adil);

        nurakhmet.follow(princess);
        princess.follow(nurakhmet);


        System.out.println("Princess followers: " + princess.getFollowers());

        Post p1 = nurakhmet.createPost(platform, "Hello from Nurakhmet!");
        Post p2 = princess.createPost(platform, "Hi! I'm Princess 👑");
        Post p3 = erlen.createPost(platform, "Erlen here. Java OOP is cool.");
        Post p4 = adil.createPost(platform, "Adil posting on MiniSocial.");

        p2.like(nurakhmet);
        p2.addComment(nurakhmet, "Welcome!");
        p1.like(princess);

        System.out.println("\n=== PLATFORM ===");
        System.out.println(platform);

        System.out.println("\n=== USERS ===");
        System.out.println(nurakhmet);
        System.out.println(princess);
        System.out.println(erlen);
        System.out.println(adil);

        System.out.println("\n=== POSTS ===");
        for (Post p : platform.getAllPosts()) {
            System.out.println(p);
            System.out.println("  Comments: " + p.getComments());
        }


        User nurakhmetClone = new User("nurakhmet", "Nurakhmet (Clone)", "clone@mail.com");

        System.out.println("\n=== COMPARISONS ===");
        System.out.println("nurakhmet.equals(nurakhmetClone) ? " + nurakhmet.equals(nurakhmetClone));
        System.out.println("Objects.equals(nurakhmet, nurakhmetClone) ? " + Objects.equals(nurakhmet, nurakhmetClone));
        System.out.println("nurakhmet.compareTo(princess) -> " + nurakhmet.compareTo(princess));

        System.out.println("\nPost p2 equals p3 ? " + p2.equals(p3));
        System.out.println("p2.compareTo(p3) -> " + p2.compareTo(p3));
        System.out.println("p4 content: " + p4.getContent());
    }
}

class SocialMedia {
    private String name;
    private final Map<String, User> usersByUsername = new HashMap<>();
    private final List<Post> posts = new ArrayList<>();
    private long nextPostId = 1;

    public SocialMedia(String name) { this.name = name; }

    public boolean registerUser(User user) {
        if (user == null || user.getUsername() == null) return false;
        String key = user.getUsername().toLowerCase(Locale.ROOT);
        if (usersByUsername.containsKey(key)) return false;
        usersByUsername.put(key, user);
        return true;
    }

    public Post createPost(User author, String content) {
        Post post = new Post(nextPostId++, author, content);
        posts.add(post);
        return post;
    }

    public List<Post> getAllPosts() {
        return Collections.unmodifiableList(posts);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getUserCount() { return usersByUsername.size(); }
    public int getPostCount() { return posts.size(); }

    @Override
    public String toString() {
        return "SocialMedia{name='" + name + "', users=" + getUserCount() + ", posts=" + getPostCount() + "}";
    }
}

class User implements Comparable<User> {
    private String username;
    private String displayName;
    private String email;

    private final Set<User> followers = new HashSet<>();
    private final Set<User> following = new HashSet<>();

    public User(String username, String displayName, String email) {
        this.username = username;
        this.displayName = displayName;
        this.email = email;
    }

    public boolean follow(User other) {
        if (other == null || other.equals(this)) return false;
        boolean added = following.add(other);
        if (added) other.followers.add(this);
        return added;
    }

    public Post createPost(SocialMedia platform, String content) {
        if (platform == null) throw new IllegalArgumentException("platform is null");
        return platform.createPost(this, content);
    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<User> getFollowers() { return Collections.unmodifiableSet(followers); }
    public Set<User> getFollowing() { return Collections.unmodifiableSet(following); }

    @Override
    public int compareTo(User other) {
        if (other == null) return 1;
        return this.username.toLowerCase(Locale.ROOT)
                .compareTo(other.username.toLowerCase(Locale.ROOT));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return username != null && user.username != null
                && username.equalsIgnoreCase(user.username);
    }

    @Override
    public int hashCode() {
        return username == null ? 0 : username.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return "User{@" + username +
                ", displayName='" + displayName + '\'' +
                ", followers=" + followers.size() +
                ", following=" + following.size() +
                '}';
    }
}

class Post implements Comparable<Post> {
    private final long postId;
    private final User author;
    private String content;
    private final LocalDateTime createdAt;

    private final Set<User> likedBy = new HashSet<>();
    private final List<String> comments = new ArrayList<>();

    public Post(long postId, User author, String content) {
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public boolean like(User user) {
        if (user == null) return false;
        return likedBy.add(user);
    }

    public void addComment(User user, String text) {
        String who = (user == null) ? "unknown" : "@" + user.getUsername();
        comments.add(who + ": " + (text == null ? "" : text));
    }

    public long getPostId() { return postId; }
    public User getAuthor() { return author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public int getLikeCount() { return likedBy.size(); }
    public Set<User> getLikedBy() { return Collections.unmodifiableSet(likedBy); }

    public List<String> getComments() { return Collections.unmodifiableList(comments); }

    @Override
    public int compareTo(Post other) {
        if (other == null) return 1;
        int t = this.createdAt.compareTo(other.createdAt);
        if (t != 0) return t;
        return Long.compare(this.postId, other.postId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return postId == post.postId;
    }

    @Override
    public int hashCode() { return Long.hashCode(postId); }

    @Override
    public String toString() {
        return "Post{id=" + postId +
                ", author=@" + (author == null ? "unknown" : author.getUsername()) +
                ", likes=" + getLikeCount() +
                ", createdAt=" + createdAt +
                ", content='" + content + '\'' +
                '}';
    }
}
