package DZ2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "qwerty007";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db_oris";

    public static void main(String[] args) throws SQLException {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            UserRepository userRepository = new UserRepositoryJdbcImpl(connection);
            UserFilter filter = new UserFilterImpl();
            List<User> usersDop = new ArrayList<>();
            usersDop.add(new User(null, "Кол",   "Налыч",   67));
            usersDop.add(new User(null, "Аки",   "Младший",   25));
            usersDop.add(new User(null, "Анна",   "Тбанковская", 30));
            usersDop.add(new User(null, "Ольга",   "Кузнецова", 22));
            usersDop.add(new User(null, "Серега", "Попов",    28));
            usersDop.add(new User(null, "Анастасья",  "Ждуль", 51));

            List<User> usersAll = userRepository.findAll();
            userRepository.saveAll(usersDop);
            List<User> usersByFilter = userRepository.findAllByFilter(filter);


            for (User user: usersByFilter){
                System.out.println(user.toString());
            }
        }

    }
}