package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.Project;

@Repository
public class ProjectDao {

    private final JdbcTemplate jdbcTemplate;

    public ProjectDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(Project project) {

        String sql = """
                INSERT INTO projects
                (title, domain, project_abstract, description,
                 status, created_by, progress)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                project.getTitle(),
                project.getDomain(),
                project.getProjectAbstract(),
                project.getDescription(),
                project.getStatus(),
                project.getCreatedBy(),
                project.getProgress()
        );
    }


    public List<Project> findAll() {

        String sql = """
                SELECT id, title, domain, project_abstract,
                       description, status, created_by, progress
                FROM projects
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Project project = new Project();

            project.setId(rs.getInt("id"));
            project.setTitle(rs.getString("title"));
            project.setDomain(rs.getString("domain"));
            project.setProjectAbstract(rs.getString("project_abstract"));
            project.setDescription(rs.getString("description"));
            project.setStatus(rs.getString("status"));
            project.setCreatedBy(rs.getInt("created_by"));
            project.setProgress(rs.getInt("progress"));

            return project;
        });
    }


    public Project findById(int id) {

        String sql = """
                SELECT id, title, domain, project_abstract,
                       description, status, created_by, progress
                FROM projects
                WHERE id = ?
                """;

        List<Project> projects = jdbcTemplate.query(sql, (rs, rowNum) -> {

            Project project = new Project();

            project.setId(rs.getInt("id"));
            project.setTitle(rs.getString("title"));
            project.setDomain(rs.getString("domain"));
            project.setProjectAbstract(rs.getString("project_abstract"));
            project.setDescription(rs.getString("description"));
            project.setStatus(rs.getString("status"));
            project.setCreatedBy(rs.getInt("created_by"));
            project.setProgress(rs.getInt("progress"));

            return project;
        }, id);

        return projects.isEmpty() ? null : projects.get(0);
    }


    public List<Project> findByCreatedBy(int createdBy) {

        String sql = """
                SELECT id, title, domain, project_abstract,
                       description, status, created_by, progress
                FROM projects
                WHERE created_by = ?
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Project project = new Project();
            project.setId(rs.getInt("id"));
            project.setTitle(rs.getString("title"));
            project.setDomain(rs.getString("domain"));
            project.setProjectAbstract(rs.getString("project_abstract"));
            project.setDescription(rs.getString("description"));
            project.setStatus(rs.getString("status"));
            project.setCreatedBy(rs.getInt("created_by"));
            project.setProgress(rs.getInt("progress"));
            return project;
        }, createdBy);
    }

    public void updateStatus(int projectId, String status) {

        String sql = """
                UPDATE projects
                SET status = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                status,
                projectId
        );
    }


    public void updateProgress(int projectId, int progress) {

        String sql = """
                UPDATE projects
                SET progress = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                progress,
                projectId
        );
    }
}