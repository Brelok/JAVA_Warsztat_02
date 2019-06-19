package pl.coderslab.dao;

import pl.coderslab.plain.User;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO user(name, email, password, user_group_id) VALUES (?, ?, ? ,?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM user where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE user SET name = ?, email = ?, password = ?, user_group_id = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM user WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM user";



    public User create(User user) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserGroupId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserGroupId());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User[] addToArray(User user, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = user;
        return tmpUsers;

    }

    public User[] findAll() {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_group_id"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
         public static void userStart () {
           try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
               String input = null;
               while (true) {
                   PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
                   ResultSet resultSet = statement.executeQuery();

                   System.out.println("Oto lista wszystkich użytkowników");
                   DatabaseUtils.print(resultSet, "id", "name", "email", "password", "user_group_id");
                   System.out.println("Wybierz jedną z opcji: \n" +
                           "\"add\" - dodanie użytkownika \n" +
                           "\"edit\" - edycja użytkownika \n" +
                           "\"delete\" - usunięcie użytkownika\n" +
                           "\"esc\" - wróć do menu głównego");
                   Scanner scanner = new Scanner(System.in);
                   input = scanner.nextLine();
                   if (input.equals("add")) {
                       User user = new User();
                       System.out.print("Podaj imię: ");
                       user.setName(scanner.nextLine());
                       System.out.print("Podaj email: ");
                       user.setEmail(scanner.nextLine());
                       System.out.print("Podaj hasło: ");
                       user.setPassword(scanner.nextLine());
                       System.out.print("Podaj ID groupy: ");
                       user.setUserGroupId(scanner.nextInt());

                       UserDao userDao = new UserDao();
                       userDao.create(user);

                   } else if (input.equals("edit")) {
                       User user = new User();
                       System.out.print("Podaj id użytkownika do zmiany: ");
                       user.setId(scanner.nextInt());
                       scanner.nextLine();
                       System.out.print("Zmień imię: ");
                       user.setName(scanner.nextLine());
                       System.out.print("Zmień email: ");
                       user.setEmail(scanner.nextLine());
                       System.out.print("Zmień hasło: ");
                       user.setPassword(scanner.nextLine());
                       System.out.print("Zmień ID groupy: ");
                       user.setUserGroupId(scanner.nextInt());

                       UserDao userDao = new UserDao();
                       userDao.update(user);

                   } else if (input.equals("delete")) {
                       User user = new User();
                       System.out.print("Podaj ID użytkownika, którego chcesz usunąć: ");
                       user.setId(scanner.nextInt());
                       UserDao userDao = new UserDao();
                       userDao.delete(user.getId());
                   } else if (input.equals("esc")) {
                       break;
                    }else {
                       System.out.println("Niepoprawne polecenie\n");
                   }
               }

           }catch (SQLException e ) {
               e.printStackTrace();
         }


    }
}
