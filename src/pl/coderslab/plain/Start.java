package pl.coderslab.plain;

import java.util.Scanner;

import static pl.coderslab.dao.ExerciseDao.exerciseStart;
import static pl.coderslab.dao.SolutionDao.solutionStart;
import static pl.coderslab.dao.UserDao.userStart;
import static pl.coderslab.dao.UserGroupDao.userGroupStart;

public class Start {

    public static void startProgram() {
        while (true) {

            System.out.println("Dzień dobry, czym chcesz zarządząć? Wybierz:\n 1 - użytkownicy \n 2 - grupy \n 3 - zadania \n 4 - przypisanie zadania \n 5 - aby zakończyć ");//napisać program do wyboru
            Scanner scanner = new Scanner(System.in);
            int input = 0;
            input = scanner.nextInt();
            if (input == 1) {
                userStart();
            } else if (input == 2) {
                userGroupStart();
            } else if (input == 3) {
                exerciseStart();
            } else if (input == 4) {
                solutionStart();
            } else if (input == 5) {
                break;
            } else {
                System.out.println("Niepoprawny wybór");
            }
        }

    }
}
