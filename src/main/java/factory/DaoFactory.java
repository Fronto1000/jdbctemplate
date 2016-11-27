package factory;

import dao.AutoDao;
import dao.UsersDao;
import model.User;
import org.postgresql.core.ConnectionFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.activation.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

public class DaoFactory {
    private static DaoFactory instance;
    private UsersDao usersDao;
    private AutoDao autoDao;
    private Properties properties;

    static {
        instance = new DaoFactory();
    }
    private DaoFactory() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\HomeWork\\JDBC\\src\\main\\resources\\application.properties"));
            String daoUserClassName = properties.getProperty("users.dao.class");
            String daoAutoClassName = properties.getProperty("auto.dao.class");
            Constructor constructorUser = Class.forName(daoUserClassName).getConstructor(DataSource.class);
            Constructor constructorAuto = Class.forName(daoAutoClassName).getConstructor(DataSource.class);
            usersDao = (UsersDao) constructorUser.newInstance(DataSourceFactory.getDataSourceFactory().getDataSource());
            autoDao = (AutoDao) constructorAuto.newInstance(DataSourceFactory.getDataSourceFactory().getDataSource());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public UsersDao getUsersDao() {
        return usersDao;
    }
    public AutoDao getAutoDao() {
        return autoDao;
    }
    public static DaoFactory getInstance() {
        return instance;
    }

}
