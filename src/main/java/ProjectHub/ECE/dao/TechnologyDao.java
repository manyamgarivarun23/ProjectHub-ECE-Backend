package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.Technology;

@Repository
public class TechnologyDao {

    private final JdbcTemplate jdbcTemplate;

    public TechnologyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Technology technology) {

        String sql = """
                INSERT INTO technologies (name, category)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                sql,
                technology.getName(),
                technology.getCategory()
        );
    }

    public List<Technology> getAllTechnologies() {

        String sql = """
                SELECT id, name, category
                FROM technologies
                ORDER BY name
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Technology technology = new Technology();

            technology.setId(rs.getInt("id"));
            technology.setName(rs.getString("name"));
            technology.setCategory(rs.getString("category"));

            return technology;
        });
    }
}