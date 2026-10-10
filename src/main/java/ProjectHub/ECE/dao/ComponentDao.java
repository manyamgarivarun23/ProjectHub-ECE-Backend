package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.Component;

@Repository
public class ComponentDao {

    private final JdbcTemplate jdbcTemplate;

    public ComponentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Component component) {

        String sql = """
                INSERT INTO components (name, category, description)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                component.getName(),
                component.getCategory(),
                component.getDescription()
        );
    }

    public List<Component> findAll() {

        String sql = """
                SELECT id, name, category, description
                FROM components
                ORDER BY name
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Component component = new Component();

                    component.setId(rs.getInt("id"));
                    component.setName(rs.getString("name"));
                    component.setCategory(rs.getString("category"));
                    component.setDescription(rs.getString("description"));

                    return component;
                }
        );
    }

    public Component findById(int id) {

        String sql = """
                SELECT id, name, category, description
                FROM components
                WHERE id = ?
                """;

        List<Component> components = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Component component = new Component();

                    component.setId(rs.getInt("id"));
                    component.setName(rs.getString("name"));
                    component.setCategory(rs.getString("category"));
                    component.setDescription(rs.getString("description"));

                    return component;
                },
                id
        );

        return components.isEmpty() ? null : components.get(0);
    }
}