package service;

import dao.AutoDao;
import dao.UsersDao;
import model.Auto;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UsersDao usersDao;
    private AutoDao autoDao;

    public UserServiceImpl(UsersDao usersDao,AutoDao autoDao) {
        this.usersDao = usersDao;
        this.autoDao = autoDao;
    }
    public boolean isRegistered(String name) {
        List<User> users = usersDao.findAll();

        for (User currentUser : users) {
            if (currentUser.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<User> getUsersByCity(String cityName) {
        return null;
    }

    public ArrayList<Auto> getAutoByCity(String city) {
        return null;
    }
}