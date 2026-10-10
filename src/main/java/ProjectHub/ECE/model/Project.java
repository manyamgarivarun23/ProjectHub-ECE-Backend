package ProjectHub.ECE.model;

public class Project {

    private int id;
    private String title;
    private String domain;
    private String projectAbstract;
    private String description;
    private String status;
    private int createdBy;
    private int progress;

    public Project() {
    }

    public Project(int id, String title, String domain, String projectAbstract,
                   String description, String status, int createdBy, int progress) {

        this.id = id;
        this.title = title;
        this.domain = domain;
        this.projectAbstract = projectAbstract;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProjectAbstract() {
        return projectAbstract;
    }

    public void setProjectAbstract(String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}