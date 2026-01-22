import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final Db db = new Db();
    private static final SocialMedia app = new SocialMedia();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conn = db.connect()) {
            status("Connected to PostgreSQL ✓");

            while (true) {
                printMenu();

                System.out.print("\nВыберите пункт: ");
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1" -> showUsers(conn);
                    case "2" -> showFeed(conn);
                    case "3" -> addUser(conn);
                    case "4" -> addPost(conn);
                    case "5" -> updateUserEmail(conn);
                    case "6" -> editPost(conn);
                    case "7" -> likePost(conn);
                    case "8" -> deletePost(conn);
                    case "9" -> deleteUser(conn);
                    case "0" -> {
                        status("Выход. Пока!");
                        return;
                    }
                    default -> status("Неверный пункт. Попробуйте снова.");
                }
            }

        } catch (SQLException e) {
            status("DB error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---------- UI ----------
    private static void printMenu() {
        System.out.println("\n=== SOCIAL MEDIA MANAGEMENT ===");
        System.out.println("1. Показать пользователей");
        System.out.println("2. Показать ленту (посты + авторы)");
        System.out.println("3. Добавить пользователя");
        System.out.println("4. Добавить пост");
        System.out.println("5. Обновить email пользователя");
        System.out.println("6. Обновить текст поста");
        System.out.println("7. Поставить лайк посту");
        System.out.println("8. Удалить пост");
        System.out.println("9. Удалить пользователя");
        System.out.println("0. Выход");
    }

    private static void status(String message) {
        System.out.println("\nНовый статус: ✓ " + message);
    }

    // ---------- Actions ----------
    private static void showUsers(Connection conn) {
        try {
            System.out.println("\n--- USERS ---");
            for (User u : app.getAllUsers(conn)) System.out.println(u);
            status("Пользователи показаны.");
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void showFeed(Connection conn) {
        try {
            System.out.println("\n--- FEED ---");
            for (String row : app.getFeed(conn)) System.out.println(row);
            status("Лента показана.");
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void addUser(Connection conn) {
        try {
            System.out.print("Username: ");
            String username = sc.nextLine().trim();
            System.out.print("Email: ");
            String email = sc.nextLine().trim();

            long id = app.createUser(conn, username, email);
            status("Пользователь добавлен! id=" + id);
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void addPost(Connection conn) {
        try {
            System.out.print("User ID: ");
            long userId = Long.parseLong(sc.nextLine().trim());
            System.out.print("Content: ");
            String content = sc.nextLine().trim();

            long id = app.createPost(conn, userId, content);
            status("Пост добавлен! id=" + id);
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void updateUserEmail(Connection conn) {
        try {
            System.out.print("User ID: ");
            long userId = Long.parseLong(sc.nextLine().trim());
            System.out.print("New email: ");
            String email = sc.nextLine().trim();

            app.changeEmail(conn, userId, email);
            status("Email обновлен!");
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void editPost(Connection conn) {
        try {
            System.out.print("Post ID: ");
            long postId = Long.parseLong(sc.nextLine().trim());
            System.out.print("New content: ");
            String content = sc.nextLine().trim();

            app.editPost(conn, postId, content);
            status("Пост обновлен!");
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void likePost(Connection conn) {
        try {
            System.out.print("Post ID: ");
            long postId = Long.parseLong(sc.nextLine().trim());

            app.likePost(conn, postId);
            status("Лайк добавлен!");
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void deletePost(Connection conn) {
        try {
            System.out.print("Post ID: ");
            long postId = Long.parseLong(sc.nextLine().trim());

            app.removePost(conn, postId);
            status("Пост удален!");
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }

    private static void deleteUser(Connection conn) {
        try {
            System.out.print("User ID: ");
            long userId = Long.parseLong(sc.nextLine().trim());

            app.removeUser(conn, userId);
            status("Пользователь удален!");
        } catch (Exception e) {
            status("Ошибка: " + e.getMessage());
        }
    }
}
