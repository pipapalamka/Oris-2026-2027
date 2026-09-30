package DZ2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_FROM_DRIVERS = "select id, first_name, last_name, age from drivers";

    public UserRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVERS);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age")
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public List<User> findAllByFilter(UserFilter filters) throws SQLException {
        List<User> usersByFilter = new ArrayList<>();
        for (User user : this.findAll()) {
            if (filters.filter(user)) {
                usersByFilter.add(user);
            }
        }
        return usersByFilter;
    }

    @Override
    public void saveAll(List<User> users) throws SQLException {
        StringBuilder sql = new StringBuilder("insert into drivers (first_name, last_name, age) values ");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            sql.append("('" + user.getName() + "', '" + user.getSurname() + "', " + user.getAge() + ")");
            if (i != users.size() - 1) {
                sql.append(",");
            }
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
        }


    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String sql = "select id, first_name, last_name, age from drivers where id = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"));
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }


    @Override
    public void save(User entity) throws SQLException {
        StringBuilder sql = new StringBuilder("insert into drivers (first_name, last_name, age) values ");
        sql.append("('" + entity.getName() + "', '" + entity.getSurname() + "', " + entity.getAge()+ ")");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        StringBuilder sql = new StringBuilder("update drivers set ");
        sql.append("first_name = '" + entity.getName() + "', "
                + "last_name = '" + entity.getSurname() + "', "
                + "age = " + entity.getAge()
                + " where id = " + entity.getId());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
        }
    }

    @Override
    public void remove(User entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        String sql = new String("delete from drivers where id = " + id);
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        List<User> users = new ArrayList<>();
        for(User user: this.findAll()){
            if(user.getAge().equals(age)){
                users.add(user);
            }
        }
        return users;
    }

}