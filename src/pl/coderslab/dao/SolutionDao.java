package pl.coderslab.dao;

import pl.coderslab.plain.Solution;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;

public class SolutionDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO user(created, updated, description, exercise_id, user_id) VALUES (?, ?, ? ,?, ?)";

       public Solution create(Solution solution) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, solution.getCreated());
            statement.setString(2, solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExcersiseId());
            statement.setInt(5, solution.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
//       public Solution[] findAllByUserId (int userId) {//copy z userDAO = coś zmoenić
//        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
//            User[] users = new User[0];
//            PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTION_BY_USER_ID_QUERY);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getInt("id"));
//                user.setName(resultSet.getString("name"));
//                user.setEmail(resultSet.getString("email"));
//                user.setPassword(resultSet.getString("password"));
//                user.setUserGroupId(resultSet.getInt("user_group_id"));
//                users = addToArray(user, users);
//            }
//            return users;
//        } catch (SQLException e) {
//            e.printStackTrace(); return null;
//        }}

}
