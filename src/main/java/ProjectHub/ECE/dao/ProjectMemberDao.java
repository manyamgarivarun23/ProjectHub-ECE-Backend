package ProjectHub.ECE.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ProjectHub.ECE.model.ProjectMember;

@Repository
public class ProjectMemberDao {

    private final JdbcTemplate jdbcTemplate;

    public ProjectMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProjectMember member) {

        String sql = """
                INSERT INTO project_members (project_id, student_id)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                sql,
                member.getProjectId(),
                member.getStudentId()
        );
    }

    public List<ProjectMember> findByProjectId(int projectId) {

        String sql = """
                SELECT id, project_id, student_id
                FROM project_members
                WHERE project_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    ProjectMember member = new ProjectMember();

                    member.setId(rs.getInt("id"));
                    member.setProjectId(rs.getInt("project_id"));
                    member.setStudentId(rs.getInt("student_id"));

                    return member;
                },
                projectId
        );
    }

    public void deleteByProjectId(int projectId) {

        String sql = """
                DELETE FROM project_members
                WHERE project_id = ?
                """;

        jdbcTemplate.update(sql, projectId);
    }
    public void deleteById(int id) {
    String sql = """
            DELETE FROM project_members
            WHERE id = ?
            """;

    jdbcTemplate.update(sql, id);
}
}