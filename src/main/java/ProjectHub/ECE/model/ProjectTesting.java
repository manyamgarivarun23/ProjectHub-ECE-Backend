package ProjectHub.ECE.model;

import java.time.LocalDate;

public class ProjectTesting {

    private int id;
    private int projectId;
    private String testName;
    private LocalDate testDate;
    private String result;
    private String remarks;

    public ProjectTesting() {
    }

    public ProjectTesting(
            int id,
            int projectId,
            String testName,
            LocalDate testDate,
            String result,
            String remarks) {

        this.id = id;
        this.projectId = projectId;
        this.testName = testName;
        this.testDate = testDate;
        this.result = result;
        this.remarks = remarks;
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

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}