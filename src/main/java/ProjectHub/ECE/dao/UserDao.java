package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.User;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {

        String sql = """
                INSERT INTO users (name, email, password, role)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public User findByEmail(String email) {

        String sql = """
                SELECT id, name, email, password, role
                FROM users
                WHERE email = ?
                """;

        List<User> users = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    User user = new User();

                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));

                    return user;
                },
                email
        );

        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> findAllStudents() {

        String sql = """
                SELECT id, name, email, password, role
                FROM users
                WHERE role = 'STUDENT'
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    User user = new User();

                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));

                    return user;
                }
        );
    }
}