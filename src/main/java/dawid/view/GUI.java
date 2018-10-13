package dawid.view;

import dawid.model.User;
import dawid.repository.UserRepo;

import java.util.Scanner;

public class GUI {
    public static void helloScreen() {
        System.out.println("1. Zaloguj");
        System.out.println("2. Zarejestruj");

        String choose;
        Scanner scanner = new Scanner(System.in);

        choose = scanner.nextLine();

        if(choose.equals("1")) {
            loginScreen();
        } else if(choose.equals("2")) {
            registerScreen();
        } else {
            System.out.println("Niepoprawny wybor");
            helloScreen();
        }
    }

    public static void loginScreen() {
        User user = new User();

        System.out.println("Podaj login:");
        Scanner scanner = new Scanner(System.in);
        user.setLogin(scanner.nextLine());

        System.out.println("Podaj haslo:");
        scanner = new Scanner(System.in);
        user.setPass(scanner.nextLine());

        if(UserRepo.authenticate(user)) {
            System.out.println("Jest git");
        } else {
            System.out.println("Jest nie git");
        }
    }

    public static void registerScreen() {
        System.out.println("Podaj login:");

        String login;
        Scanner scanner = new Scanner(System.in);

        login = scanner.nextLine();

        System.out.println("Podaj haslo:");

        String pass;
        scanner = new Scanner(System.in);

        pass = scanner.nextLine();

        System.out.println("Powtorz haslo:");

        String pass2;
        scanner = new Scanner(System.in);

        pass2 = scanner.nextLine();

        if(pass.equals(pass2)) {
            User user = new User();
            user.setLogin(login);
            user.setPass(pass);
            UserRepo.addUser(user);

            System.out.println("Zarejestrowano");

            helloScreen();
        } else {
            registerScreen();
        }
    }
}
