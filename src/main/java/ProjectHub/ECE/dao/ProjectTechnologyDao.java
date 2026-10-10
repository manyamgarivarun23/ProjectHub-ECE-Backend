package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.ProjectTechnology;

@Repository
public class ProjectTechnologyDao {

    private final JdbcTemplate jdbcTemplate;

    public ProjectTechnologyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProjectTechnology projectTechnology) {

        String sql = """
                INSERT INTO project_technologies
                (project_id, technology_id)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                sql,
                projectTechnology.getProjectId(),
                projectTechnology.getTechnologyId()
        );
    }

    public List<ProjectTechnology> findByProjectId(int projectId) {

        String sql = """
                SELECT pt.id,
                       pt.project_id,
                       pt.technology_id,
                       t.name,
                       t.category
                FROM project_technologies pt
                JOIN technologies t
                  ON pt.technology_id = t.id
                WHERE pt.project_id = ?
                ORDER BY t.name
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            ProjectTechnology pt = new ProjectTechnology();

            pt.setId(rs.getInt("id"));
            pt.setProjectId(rs.getInt("project_id"));
            pt.setTechnologyId(rs.getInt("technology_id"));
            pt.setTechnologyName(rs.getString("name"));
            pt.setCategory(rs.getString("category"));

            return pt;

        }, projectId);
    }

    public void deleteByProjectId(int projectId) {

        String sql = """
                DELETE FROM project_technologies
                WHERE project_id = ?
                """;

        jdbcTemplate.update(sql, projectId);
    }
    public boolean exists(int projectId, int technologyId) {

    String sql = """
            SELECT COUNT(*)
            FROM project_technologies
            WHERE project_id = ?
              AND technology_id = ?
            """;

    Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            projectId,
            technologyId
    );

    return count != null && count > 0;
}
}