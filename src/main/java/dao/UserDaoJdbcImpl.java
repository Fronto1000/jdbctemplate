package dao;

import model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDaoJdbcImpl implements UsersDao {

    // language=SQL
    private static final String SQL_SELECT_USER ="SELECT * FROM group_member";
    // language=SQL
    private static final String SQL_UPDATE_USER ="UPDATE group_member SET name = ?, age = ?, city = ? WHERE id =?";
    // language=SQL
    private static final String SQL_INSERT_USER ="INSERT INTO group_member (id, name, age, city) VALUES(:id;, :name;, :age;, :city;)";
    // language=SQL
    private static final String SQL_SELECT_USER_BY_CITY ="SELECT * FROM group_member WHERE city = :city;";
    // language=SQL
    private static final String SQL_DELETE_USER ="DELETE FROM group_member WHERE id = ?";

    private NamedParameterJdbcTemplate template;

    private Map<Integer, User> userMap;

    public UserDaoJdbcImpl(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
        userMap = new HashMap<Integer, User>();
    }

    RowMapper<User> userRowMapper = new RowMapper<User>() {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"),resultSet.getString("city"));
            userMap.put(user.getId(), user);
            return user;
        }
    };

    public ArrayList<User> findAll() {
        template.query(SQL_SELECT_USER, userRowMapper);
        return new ArrayList<User>(userMap.values());
    }

    public void update(User user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", user.getName());
        params.put("age", user.getAge());
        params.put("city", user.getCity());
        params.put("id", user.getId());
        template.update(SQL_UPDATE_USER,params);
    }

    public void save(User user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", user.getId());
        params.put("name", user.getName());
        params.put("age", user.getAge());
        params.put("city", user.getCity());
        template.update(SQL_INSERT_USER,params);
    }

    public ArrayList<User> findByCity(String city) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("city", city);
        template.query(SQL_SELECT_USER_BY_CITY,params,userRowMapper);
        return new ArrayList<User>(userMap.values());
    }

    public void delete(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        template.update(SQL_DELETE_USER,params);
    }
}
