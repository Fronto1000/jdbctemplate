package dao;

import model.Auto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoDaoJdbcImpl implements AutoDao{

    // language=SQL
    private static final String SQL_SELECT_AUTO = "SELECT * FROM auto";
    // language=SQL
    private final String SQL_UPDATE_AUTO = "UPDATE auto SET memberid = ? WHERE id = ?";
    // language=SQL
    static final String SQL_INSERT_AUTO = "INSERT INTO auto(id, model, mileage, memberid) VALUES(?, ?, ?,?)";
    // language=SQL
    static final String SQL_DELETE_AUTO = "DELETE FROM auto WHERE id=?";
    // language=SQL
    static final String SQL_SELECT_AUTO_BY_MEMBER = "SELECT * FROM auto WHERE memberid =?";

    private Connection connection;

    public AutoDaoJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Auto> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_AUTO);
            ArrayList<Auto> resultList = new ArrayList<Auto>();
            while (resultSet.next()){
                Auto auto = new Auto(resultSet.getInt("id"),resultSet.getString("model"),resultSet.getInt("mileage"),resultSet.getInt("memberid"));
                resultList.add(auto);
            }
            return resultList;
        }catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void update(Auto auto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_AUTO);
            preparedStatement.setInt(1, auto.getMemberId());
            preparedStatement.setInt(2, auto.getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save(Auto auto) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_AUTO);
            preparedStatement.setInt(1, auto.getId());
            preparedStatement.setString(2, auto.getModel());
            preparedStatement.setInt(3, auto.getMileage());
            preparedStatement.setInt(4,auto.getMemberId());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Auto find(int memberid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_AUTO_BY_MEMBER);
            preparedStatement.setInt(1, memberid);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return  new Auto(resultSet.getInt("id"),resultSet.getString("model"),resultSet.getInt("mileage"),resultSet.getInt("memberid"));
        }catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_AUTO);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
