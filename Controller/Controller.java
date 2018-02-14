package Controller;

import Database.Implementation;
import Model.User;


public class Controller {

    private static Controller controller;
    private final Implementation impl;
    
    private Controller() {
        this.impl = new Implementation();
        this.impl.openConnection();
    }

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public boolean signup(User user) {
        return this.impl.insert(user);
    }

    public User login(String username, String password) {
        return this.impl.getUser(username, password);
    }
}
