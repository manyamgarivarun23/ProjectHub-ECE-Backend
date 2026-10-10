package ProjectHub.ECE.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    public CustomUserDetailsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        String sql = "SELECT email, password, role FROM users WHERE email = ?";

        try {

            return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> User.withUsername(rs.getString("email"))
                        .password(rs.getString("password"))
                        .roles(rs.getString("role"))
                        .build(),
                email
            );

        } catch (Exception e) {

            throw new UsernameNotFoundException(
                "User not found: " + email
            );
        }
    }
}
