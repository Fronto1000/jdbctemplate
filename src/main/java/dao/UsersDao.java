package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

public interface UsersDao {

    ArrayList<User> findAll();
    void update(User user);
    void save(User user);
    ArrayList<User> findByCity(String city);
    void delete(int id);
}