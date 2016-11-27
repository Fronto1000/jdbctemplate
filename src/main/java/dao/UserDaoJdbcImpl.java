package dao;

import model.Auto;
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
    private static final String SQL_INSERT_USER ="INSERT INTO group_member (id, name, age, city) VALUES(?,?, ?, ?)";
    // language=SQL
    private static final String SQL_SELECT_USER_BY_CITY ="SELECT * FROM group_member WHERE city =?";
    // language=SQL
    private static final String SQL_DELETE_USER ="DELETE FROM group_member WHERE id = ?";

    private NamedParameterJdbcTemplate template;

    private Map<Long, User> userMap;

    public UserDaoJdbcImpl(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
        userMap = new HashMap<Long, User>();
    }

    RowMapper<User> userRowMapper = new RowMapper<User>() {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(resultSet.getLong("id"), resultSet.getString("name"), new ArrayList<Auto>());
            userMap.put(user.getId(), user);
            return user;
        }
    };

    public ArrayList<User> findAll() {
        template.query(SQL_SELECT_USER, userRowMapper);
        return new ArrayList<User>(userMap.values());
    }

    public void update(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getCity());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public ArrayList<User> findByCity(String city) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_CITY);
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<User> resultList = new ArrayList<User>();
            while (resultSet.next()){
                User user = new User(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("age"),resultSet.getString("city"));
                resultList.add(user);
            }
            return resultList;
        }catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
