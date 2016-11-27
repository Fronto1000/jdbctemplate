package dao;

import model.Auto;

import java.util.List;

public interface AutoDao {

    List<Auto> findAll();
    void update(Auto auto);
    void save(Auto auto);
    Auto find(int id);
    void delete(int id);
}
