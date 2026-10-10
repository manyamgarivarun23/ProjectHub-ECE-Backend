package ProjectHub.ECE.model;

public class Feedback {

    private int id;
    private int projectId;
    private int facultyId;
    private String comments;
    private String status;

    public Feedback() {
    }

    public Feedback(int id, int projectId, int facultyId, String comments, String status) {
        this.id = id;
        this.projectId = projectId;
        this.facultyId = facultyId;
        this.comments = comments;
        this.status = status;
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

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}