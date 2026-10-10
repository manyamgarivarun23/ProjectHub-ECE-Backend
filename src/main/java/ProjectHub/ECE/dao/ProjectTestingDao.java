package ProjectHub.ECE.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.ProjectTesting;

@Repository
public class ProjectTestingDao {

    private final JdbcTemplate jdbcTemplate;

    public ProjectTestingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(ProjectTesting testing) {

        String sql = """
                INSERT INTO project_testing
                (project_id, test_name, test_date, result, remarks)
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                testing.getProjectId(),
                testing.getTestName(),
                testing.getTestDate(),
                testing.getResult(),
                testing.getRemarks()
        );
    }


    public List<ProjectTesting> findByProjectId(int projectId) {

        String sql = """
                SELECT id,
                       project_id,
                       test_name,
                       test_date,
                       result,
                       remarks
                FROM project_testing
                WHERE project_id = ?
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            ProjectTesting testing = new ProjectTesting();

            testing.setId(rs.getInt("id"));
            testing.setProjectId(rs.getInt("project_id"));
            testing.setTestName(rs.getString("test_name"));

            Date date = rs.getDate("test_date");

            if (date != null) {
                testing.setTestDate(date.toLocalDate());
            }

            testing.setResult(rs.getString("result"));
            testing.setRemarks(rs.getString("remarks"));

            return testing;

        }, projectId);
    }


    public void deleteById(int id) {

        String sql = """
                DELETE FROM project_testing
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}