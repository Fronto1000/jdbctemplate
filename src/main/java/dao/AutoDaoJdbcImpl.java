package dao;

import model.Auto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoDaoJdbcImpl implements AutoDao {

    // language=SQL
    private static final String SQL_SELECT_AUTO = "SELECT * FROM auto";
    // language=SQL
    private final String SQL_UPDATE_AUTO = "UPDATE auto SET memberid = :memberid; WHERE id = :id;";
    // language=SQL
    static final String SQL_INSERT_AUTO = "INSERT INTO auto(id, model, mileage, memberid) VALUES(:id;, :model;, :mileage;, :memberid;)";
    // language=SQL
    static final String SQL_DELETE_AUTO = "DELETE FROM auto WHERE id=:id;";
    // language=SQL
    static final String SQL_SELECT_AUTO_BY_MEMBER = "SELECT * FROM auto WHERE memberid = :memberid;";

    private NamedParameterJdbcTemplate template;

    private Map<Integer, Auto> autoMap;

    public AutoDaoJdbcImpl(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
        autoMap = new HashMap<Integer, Auto>();
    }

    RowMapper<Auto> autoRowMapper = new RowMapper<Auto>() {
        public Auto mapRow(ResultSet resultSet, int i) throws SQLException {
            Auto auto = new Auto(resultSet.getInt("id"), resultSet.getString("model"), resultSet.getInt("mileage"), resultSet.getInt("memberid"));
            autoMap.put(auto.getId(), auto);
            return auto;
        }
    };

    public List<Auto> findAll() {
        template.query(SQL_SELECT_AUTO, autoRowMapper);
        return new ArrayList<Auto>(autoMap.values());
    }

    public void update(Auto auto) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberid", auto.getMemberId());
        params.put("id", auto.getId());
        template.update(SQL_UPDATE_AUTO,params);
    }

    public void save(Auto auto) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", auto.getId());
        params.put("model", auto.getModel());
        params.put("mileage", auto.getMileage());
        params.put("memberid", auto.getMemberId());
        template.update(SQL_INSERT_AUTO,params);
    }

    public Auto find(int memberid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberid", memberid);
        return template.queryForObject(SQL_SELECT_AUTO_BY_MEMBER, params, autoRowMapper);
    }

    public void delete(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        template.update(SQL_DELETE_AUTO,params);
    }
}

