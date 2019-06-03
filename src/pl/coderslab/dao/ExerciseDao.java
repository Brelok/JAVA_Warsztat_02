package pl.coderslab.dao;

import pl.coderslab.plain.Exercise;
import pl.coderslab.plain.User;
import pl.coderslab.utlis.DatabaseUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class ExerciseDao {

        private static final String CREATE_EXERCISE_QUERY =
            "INSERT INTO exercise(title, description) VALUES (?, ?)";
        private static final String READ_EXERCISE_QUERY =
            "SELECT * FROM exercise where id = ?";
        private static final String UPDATE_EXERCISE_QUERY =
            "UPDATE exercise SET title = ?, description = ? where id = ?";
        private static final String DELETE_EXERCISE_QUERY =
            "DELETE FROM exercise WHERE id = ?";
        private static final String FIND_ALL_EXERCISE_QUERY =
            "SELECT * FROM exercise";

        public Exercise create(Exercise exercise) {
           try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
               PreparedStatement statement =
                       conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
               statement.setString(1, exercise.getTitle());
               statement.setString(2, exercise.getDescription());
               statement.executeUpdate();
               ResultSet resultSet = statement.getGeneratedKeys();
               if (resultSet.next()) {
                   exercise.setId(resultSet.getInt(1));
               }
               return exercise;
           } catch (SQLException e) {
               e.printStackTrace();
               return null;
           }
       }

        public Exercise read(int exerciseId) {
            try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
       }

       public void update(Exercise exercise) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            }
        }

        public void delete(int exerciseId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
      }

      private Exercise [] addToArray(Exercise exercise, Exercise[] exercises) {
        Exercise[] tmpExercise = Arrays.copyOf(exercises, exercises.length + 1);
        tmpExercise[exercises.length] = exercise;
        return tmpExercise;

      }

      public Exercise[] findAllByExerciseId (int exerciseId) {
        try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
            Exercise [] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISE_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
      }

       public static void exerciseStart () {
           try (Connection conn = DatabaseUtils.getConnection("java_warsztat_2")) {
               String input = null;
               while (true) {
               PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISE_QUERY);
               ResultSet resultSet = statement.executeQuery();

                   System.out.println("Oto lista wszystkich zadań");
                   DatabaseUtils.print(resultSet, "id", "title", "description");
                   System.out.println("Wybierz jedną z opcji: \n" +
                           "\"add\" - dodanie zadania \n" +
                           "\"edit\" - edycja zadania \n" +
                           "\"delete\" - usunięcie zadania\n" +
                           "\"quit\" - zakończenie programu");
                   Scanner scanner = new Scanner(System.in);
                   input = scanner.nextLine();
                   if (input.equals("add")){
                       Exercise exercise = new Exercise();
                       System.out.print("Podaj tytuł: ");
                       exercise.setTitle(scanner.nextLine());
                       System.out.print("Podaj opis: ");
                       exercise.setDescription(scanner.nextLine());

                       ExerciseDao exerciseDao = new ExerciseDao();
                       exerciseDao.create(exercise);

                   } else if (input.equals("edit")){
                       Exercise exercise = new Exercise();
                       System.out.print("Podaj id zadania do zmiany: ");
                       exercise.setId(scanner.nextInt());
                       scanner.nextLine();
                       System.out.print("Zmień tytuł: ");
                       exercise.setTitle(scanner.nextLine());
                       System.out.print("Zmień opis: ");
                       exercise.setDescription(scanner.nextLine());

                       ExerciseDao exerciseDao = new ExerciseDao();
                       exerciseDao.update(exercise);

                   } else if (input.equals("delete")){
                       Exercise exercise = new Exercise();
                       System.out.print("Podaj ID zadania, które chcesz usunąć: ");
                       exercise.setId(scanner.nextInt());

                       ExerciseDao exerciseDao = new ExerciseDao();
                       exerciseDao.delete(exercise.getId());
                   } else if (input.equals("quit")) {
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
