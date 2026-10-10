package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.ProjectComponent;

@Repository
public class ProjectComponentDao {

    private final JdbcTemplate jdbcTemplate;

    public ProjectComponentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProjectComponent projectComponent) {

        String sql = """
                INSERT INTO project_components
                (project_id, component_id, quantity)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                projectComponent.getProjectId(),
                projectComponent.getComponentId(),
                projectComponent.getQuantity()
        );
    }

    public List<ProjectComponent> findByProjectId(int projectId) {

        String sql = """
                SELECT pc.id,
                       pc.project_id,
                       pc.component_id,
                       pc.quantity,
                       c.name,
                       c.category
                FROM project_components pc
                JOIN components c
                  ON pc.component_id = c.id
                WHERE pc.project_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            ProjectComponent pc = new ProjectComponent();

            pc.setId(rs.getInt("id"));
            pc.setProjectId(rs.getInt("project_id"));
            pc.setComponentId(rs.getInt("component_id"));
            pc.setQuantity(rs.getInt("quantity"));

            pc.setComponentName(rs.getString("name"));
            pc.setCategory(rs.getString("category"));

            return pc;

        }, projectId);
    }

    public void deleteByProjectId(int projectId) {

        String sql = """
                DELETE FROM project_components
                WHERE project_id = ?
                """;

        jdbcTemplate.update(sql, projectId);
    }
}