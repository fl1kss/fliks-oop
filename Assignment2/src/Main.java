import java.util.*;

/*
 Assignment 2 — Social Media Platform
 All classes are in ONE file for simplicity
*/

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SocialMedia platform = new SocialMedia("MiniSocial");

        // demo users
        platform.addUser(new User("alice", "alice@mail.com"));
        platform.addUser(new User("bob", "bob@mail.com"));

        // demo posts
        platform.addPost(new TextPost("alice", "Hello world"));
        platform.addPost(new ImagePost("bob", "My photo", "image.png"));

        while (true) {
            System.out.println("\n--- Social Media Platform ---");
            System.out.println("1. Show users");
            System.out.println("2. Show posts");
            System.out.println("3. Add post");
            System.out.println("4. Search posts");
            System.out.println("5. Sort posts by author");
            System.out.println("0. Exit");

            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> platform.printUsers();
                case 2 -> platform.printPosts();
                case 3 -> {
                    System.out.print("Username: ");
                    String u = sc.nextLine();
                    System.out.print("Text: ");
                    String t = sc.nextLine();
                    platform.addPost(new TextPost(u, t));
                }
                case 4 -> {
                    System.out.print("Keyword: ");
                    String k = sc.nextLine();
                    platform.searchPosts(k);
                }
                case 5 -> platform.sortPostsByUser();
                case 0 -> {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }
}

/* ===================== CLASSES ===================== */

// DATA ABSTRACTION + DATA POOL
class SocialMedia {
    private String name;
    private List<User> users = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    public SocialMedia(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void printUsers() {
        users.forEach(System.out::println);
    }

    public void printPosts() {
        posts.forEach(System.out::println);
    }

    // SEARCH
    public void searchPosts(String keyword) {
        posts.stream()
                .filter(p -> p.getText().contains(keyword))
                .forEach(System.out::println);
    }

    // SORT
    public void sortPostsByUser() {
        posts.sort(Comparator.comparing(Post::getUsername));
        printPosts();
    }
}

// INCAPSULATION
class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User: " + username + " | " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

// INHERITANCE + POLYMORPHISM
abstract class Post {
    private String username;
    private String text;

    public Post(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Post post)) return false;
        return text.equals(post.text) && username.equals(post.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, text);
    }
}

class TextPost extends Post {
    public TextPost(String username, String text) {
        super(username, text);
    }

    @Override
    public String toString() {
        return "[TEXT] " + getUsername() + ": " + getText();
    }
}

class ImagePost extends Post {
    private String imageUrl;

    public ImagePost(String username, String text, String imageUrl) {
        super(username, text);
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "[IMAGE] " + getUsername() + ": " + getText() + " (" + imageUrl + ")";
    }
}
