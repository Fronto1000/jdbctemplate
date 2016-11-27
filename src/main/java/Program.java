
import dao.AutoDao;
import dao.UsersDao;
import factory.DaoFactory;
import factory.ServiceFactory;
import model.Auto;
import model.User;
import service.UserService;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args){
        UserService userService = ServiceFactory.getInstance().getUserService();
        UsersDao usersDao = DaoFactory.getInstance().getUsersDao();
        //usersDao.delete(5);
        //usersDao.update(new User(6,"Yura",26,"Moscow"));
//        AutoDao autoDao = DaoFactory.getInstance().getAutoDao();
//        Auto aut = autoDao.find(0);
//        System.out.println(aut);
        //autoDao.save(new Auto(5,"toyota",12122,2));
//        ArrayList<User> users =  userService.getUsersByCity("Moscow");
//        for (User user:users) {
//            System.out.println(user);
//        }
        ArrayList<Auto> autos =  userService.getAutoByCity("Moscow");
        for (Auto car:autos) {
            System.out.println(car);
        }
        boolean isRegistered = userService.isRegistered("Alex");
        System.out.println(isRegistered);
    }
}