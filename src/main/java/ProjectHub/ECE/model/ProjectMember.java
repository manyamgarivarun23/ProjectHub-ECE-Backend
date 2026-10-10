package ProjectHub.ECE.model;

public class ProjectMember {

    private int id;
    private int projectId;
    private int studentId;

    public ProjectMember() {
    }

    public ProjectMember(int id, int projectId, int studentId) {
        this.id = id;
        this.projectId = projectId;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}