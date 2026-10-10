package ProjectHub.ECE.model;

public class ProjectTechnology {

    private int id;
    private int projectId;
    private int technologyId;
    private String technologyName;
    private String category;

    public ProjectTechnology() {
    }

    public ProjectTechnology(int id, int projectId, int technologyId,
                             String technologyName, String category) {
        this.id = id;
        this.projectId = projectId;
        this.technologyId = technologyId;
        this.technologyName = technologyName;
        this.category = category;
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

    public int getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(int technologyId) {
        this.technologyId = technologyId;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}