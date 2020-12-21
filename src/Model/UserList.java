package Model;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private final List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }

    public boolean verifyUsername(String username) {
        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

    public void addToUserList(User user) {
        userList.add(user);
    }

    public void removeFromUserList(String addresseMAC) {
        for (User user : userList) {
            if (addresseMAC.equals(user.getAddressMAC())) {
                userList.remove(user);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return userList.toString();
    }
}
