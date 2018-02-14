package Database;

import Model.User;


public interface Interface {
    
    boolean insert(User user);
    
    User getUser(String userName, String password);
}
