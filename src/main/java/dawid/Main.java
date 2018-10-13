package dawid;

import dawid.repository.UserRepo;
import dawid.view.GUI;

public class Main {
    public static void main(String[] args) {
        UserRepo.connect();
        GUI.helloScreen();
    }
}
