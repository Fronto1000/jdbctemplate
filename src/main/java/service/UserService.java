package service;

import model.Auto;
import model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    boolean isRegistered(String name);
    ArrayList<User> getUsersByCity(String city);
    ArrayList<Auto> getAutoByCity(String city);
}
