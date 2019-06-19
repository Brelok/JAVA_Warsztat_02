package pl.coderslab.dao;

import pl.coderslab.plain.UserGroup;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class UserGroupDao {

        private static final String CREATE_USER_GROUP_QUERY =
            "INSERT INTO user_group(name) VALUES (?)";
        private static final String READ_USER_GROUP_QUERY =
            "SELECT * FROM user_group where id = ?";
        private static final String UPDATE_USER_GROUP_QUERY =
            "UPDATE user_group SET name = ? where id = ?";
        private static final String DELETE_USER_GROUP_QUERY =
            "DELETE FROM user_group WHERE id = ?";
        private static final String FIND_ALL_USER_GROUP_BY_USER_GROUP_ID_QUERY =
            "SELECT * FROM user_group";

         public UserGroup create(UserGroup userGroup) {
           try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
               PreparedStatement statement =
                       conn.prepareStatement(CREATE_USER_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS);
               statement.setString(1, userGroup.getName());
               statement.executeUpdate();
               ResultSet resultSet = statement.getGeneratedKeys();
               if (resultSet.next()) {
                   userGroup.setId(resultSet.getInt(1));
               }
               return userGroup;
           } catch (SQLException e) {
               e.printStackTrace();
               return null;
           }
       }

       public UserGroup read(int userGroupId) {
            try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_GROUP_QUERY);
            statement.setInt(1, userGroupId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setId(resultSet.getInt("id"));
                userGroup.setName(resultSet.getString("name"));
                return userGroup;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
       }

    public void update(UserGroup userGroup) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_GROUP_QUERY);
            statement.setString(1, userGroup.getName());
            statement.setInt(2, userGroup.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      public void delete(int userGroupId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_GROUP_QUERY);
            statement.setInt(1, userGroupId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserGroup [] addToArray(UserGroup userGroup, UserGroup [] userGroups) {
        UserGroup[] tmpUserGroup = Arrays.copyOf(userGroups, userGroups.length + 1);
        tmpUserGroup[userGroups.length] = userGroup;
        return tmpUserGroup;

    }

    public UserGroup[] findAllByUserGroupId (int userGroupId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            UserGroup [] userGroups = new UserGroup[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_GROUP_BY_USER_GROUP_ID_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setId(resultSet.getInt("id"));
                userGroup.setName(resultSet.getString("name"));
                userGroups = addToArray(userGroup, userGroups);
            }
            return userGroups;
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }

    public static void userGroupStart () {
           try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
               String input = null;
               while (true) {
               PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_GROUP_BY_USER_GROUP_ID_QUERY);
               ResultSet resultSet = statement.executeQuery();

                   System.out.println("Oto lista wszystkich grup");
                   DatabaseUtils.print(resultSet, "id", "name");
                   System.out.println("Wybierz jedną z opcji: \n" +
                           "\"add\" - dodanie nowej grupy \n" +
                           "\"edit\" - edycja grupy \n" +
                           "\"delete\" - usunięcie grupy\n" +
                           "\"esc\" - wróć do menu głównego");
                   Scanner scanner = new Scanner(System.in);
                   input = scanner.nextLine();
                   if (input.equals("add")){
                       UserGroup userGroup = new UserGroup();
                       System.out.print("Podaj nazwę: ");
                       userGroup.setName(scanner.nextLine());

                       UserGroupDao userGroupDao = new UserGroupDao();
                       userGroupDao.create(userGroup);

                   } else if (input.equals("edit")){
                       UserGroup userGroup = new UserGroup();
                       System.out.print("Podaj id grupy do zmiany: ");
                       userGroup.setId(scanner.nextInt());
                       scanner.nextLine();
                       System.out.print("Podaj nowy opis grupy: ");
                       userGroup.setName(scanner.nextLine());

                       UserGroupDao userGroupDao = new UserGroupDao();
                       userGroupDao.update(userGroup);

                   } else if (input.equals("delete")){
                       UserGroup userGroup = new UserGroup();
                       System.out.print("Podaj ID grupy, którą chcesz usunąć: ");
                       userGroup.setId(scanner.nextInt());

                       UserGroupDao userGroupDao = new UserGroupDao();
                       userGroupDao.delete(userGroup.getId());
                   } else if (input.equals("esc")) {
                       break;
                   } else {
                       System.out.println("Niepoprawne polecenie\n");
                   }
               }

           }catch (SQLException e ) {
               e.printStackTrace();
         }


    }

}
