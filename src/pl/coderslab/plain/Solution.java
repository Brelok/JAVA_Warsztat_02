package pl.coderslab.plain;

import pl.coderslab.utlis.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private int exerciseId;
    private int userId;
        private static final String FIND_ALL_SOLUTION_BY_USER_ID_QUERY =
            "SELECT * FROM solution WHERE user_id = ?";
        private static final String FIND_ALL_SOLUTION_BY_EXERCISE_ID_QUERY =
            "SELECT * FROM solution WHERE exercise_id = ? ORDER BY created DESC";

    public Solution () {
    }

    public Solution(String created, String updated, String description, int excersiseId, int userId) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exerciseId = excersiseId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", excersiseId=" + exerciseId +
                ", userId=" + userId +
                '}';
    }
        private Solution [] addToArray(Solution solution, Solution[] solutions) {
        Solution[] tmpSolution = Arrays.copyOf(solutions, solutions.length + 1);
        tmpSolution[solutions.length] = solution;
        return tmpSolution;

    }
           public Solution [] findAllByUserId (int userId) {
           try (Connection connection = DatabaseUtils.getConnection("java_warsztat_2")) {
               Solution[] solutions = new Solution[0];
               PreparedStatement statement = connection.prepareStatement(FIND_ALL_SOLUTION_BY_USER_ID_QUERY);
               statement.setInt(1, userId);
               ResultSet resultSet = statement.executeQuery();
               while (resultSet.next()) {
                   Solution solution = new Solution();
                   solution.setId(resultSet.getInt("id"));
                   solution.setCreated(resultSet.getString("created"));
                   solution.setUpdated(resultSet.getString("updated"));
                   solution.setDescription(resultSet.getString("description"));
                   solution.setExerciseId(resultSet.getInt("exercise_id"));
                   solution.setUserId(resultSet.getInt("user_id"));
                   solutions = addToArray(solution, solutions);
               }
               return solutions;
           }catch (SQLException e ) {
               e.printStackTrace(); return  null;
           }
       }

           public Solution [] findAllByExerciseId (int exerciseId) {
           try (Connection connection = DatabaseUtils.getConnection("java_warsztat_2")) {
               Solution[] solutions = new Solution[0];
               PreparedStatement statement = connection.prepareStatement(FIND_ALL_SOLUTION_BY_EXERCISE_ID_QUERY);
               statement.setInt(1, exerciseId);
               ResultSet resultSet = statement.executeQuery();
               while (resultSet.next()) {
                   Solution solution = new Solution();
                   solution.setId(resultSet.getInt("id"));
                   solution.setCreated(resultSet.getString("created"));
                   solution.setUpdated(resultSet.getString("updated"));
                   solution.setDescription(resultSet.getString("description"));
                   solution.setExerciseId(resultSet.getInt("exercise_id"));
                   solution.setUserId(resultSet.getInt("user_id"));
                   solutions = addToArray(solution, solutions);
               }
               return solutions;
           }catch (SQLException e ) {
               e.printStackTrace(); return  null;
           }
       }
}
