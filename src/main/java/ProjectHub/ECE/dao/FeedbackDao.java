package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.Feedback;

@Repository
public class FeedbackDao {

    private final JdbcTemplate jdbcTemplate;

    public FeedbackDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Feedback feedback) {

        String sql = """
                INSERT INTO feedback
                (project_id, faculty_id, comments, status)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                feedback.getProjectId(),
                feedback.getFacultyId(),
                feedback.getComments(),
                feedback.getStatus()
        );
    }


    public List<Feedback> findByProjectId(int projectId) {

        String sql = """
                SELECT id, project_id, faculty_id, comments, status
                FROM feedback
                WHERE project_id = ?
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    Feedback feedback = new Feedback();

                    feedback.setId(rs.getInt("id"));
                    feedback.setProjectId(rs.getInt("project_id"));
                    feedback.setFacultyId(rs.getInt("faculty_id"));
                    feedback.setComments(rs.getString("comments"));
                    feedback.setStatus(rs.getString("status"));

                    return feedback;
                },
                projectId
        );
    }


    public List<Feedback> findAll() {

        String sql = """
                SELECT id, project_id, faculty_id, comments, status
                FROM feedback
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    Feedback feedback = new Feedback();

                    feedback.setId(rs.getInt("id"));
                    feedback.setProjectId(rs.getInt("project_id"));
                    feedback.setFacultyId(rs.getInt("faculty_id"));
                    feedback.setComments(rs.getString("comments"));
                    feedback.setStatus(rs.getString("status"));

                    return feedback;
                }
        );
    }


    public void updateStatus(int feedbackId, String status) {

        String sql = """
                UPDATE feedback
                SET status = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                status,
                feedbackId
        );
    }
}