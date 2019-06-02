package pl.coderslab.dao;

import pl.coderslab.plain.Solution;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;
import java.util.Arrays;

public class SolutionDao {

        private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solution(created, updated, description, exercise_id, user_id) VALUES (?, ?, ? ,?, ?)";
        private static final String READ_SOLUTION_QUERY =
            "SELECT * FROM user where id = ?";
        private static final String UPDATE_SOLUTION_QUERY =
            "UPDATE solution SET created = ?, updated = ?, description = ?, exercise_id = ?, user_id = ?, where id = ?";
        private static final String DELETE_SOLUTION_QUERY =
            "DELETE FROM solution WHERE id = ?";
        private static final String FIND_ALL_SOLUTION_BY_USER_ID_QUERY =
            "SELECT * FROM solution";

       public Solution create(Solution solution) {
           try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
               PreparedStatement statement =
                       conn.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
               statement.setString(1, solution.getCreated());
               statement.setString(2, solution.getUpdated());
               statement.setString(3, solution.getDescription());
               statement.setInt(4, solution.getExerciseId());
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

         public Solution read(int solutionId) {
            try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(READ_SOLUTION_QUERY);
            statement.setInt(1, solutionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getString("created"));
                solution.setUpdated(resultSet.getString("updated"));
                solution.setDescription(resultSet.getString("descrption"));
                solution.setExerciseId(resultSet.getInt("exercise_id"));
                solution.setUserId(resultSet.getInt("user_id"));
                return solution;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
       }

        public void update(Solution solution) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_SOLUTION_QUERY);
            statement.setString(1, solution.getCreated());
            statement.setString(2, solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExerciseId());
            statement.setInt(5, solution.getUserId());
            statement.setInt(6, solution.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public void delete(int solutionId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.setInt(1, solutionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

      private Solution [] addToArray(Solution solution, Solution[] solutions) {
        Solution[] tmpSolution = Arrays.copyOf(solutions, solutions.length + 1);
        tmpSolution[solutions.length] = solution;
        return tmpSolution;

    }

       public Solution[] findAllBySolutionId (int solutionId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_SOLUTION_BY_USER_ID_QUERY);
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
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
       }


}
