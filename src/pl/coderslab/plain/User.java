package pl.coderslab.plain;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class User {

    private int id;
    private String name;
    private String password; //zahashowane
    private String email; //unikatowy
    private int userGroupId;
    private static final String FIND_ALL_USER_BY_GROUP_ID_QUERY =
            "SELECT * FROM user WHERE user_group_id = ?";

    public User() {
    }

    public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User(String name, String email, String password, int userGroupId) {
        this.name = name;
        this.hashPassword(password);
        this.email = email;
        this.userGroupId = userGroupId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }
      private User[] addToArray(User user, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = user;
        return tmpUsers;

    }
       public User[] findAll(int groupId) {
           try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
               User[] users = new User[0];
               PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_BY_GROUP_ID_QUERY);
               statement.setInt(1, groupId);
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

}
