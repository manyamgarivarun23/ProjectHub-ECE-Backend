package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.ProjectDao;
import ProjectHub.ECE.model.Project;

@Service
public class ProjectService {

    private final ProjectDao projectDao;

    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public void createProject(Project project) {
        projectDao.save(project);
    }

    public List<Project> getAllProjects() {
        return projectDao.findAll();
    }

    public Project getProjectById(int id) {
        return projectDao.findById(id);
    }

    public List<Project> getProjectsByCreator(int createdBy) {
        return projectDao.findByCreatedBy(createdBy);
    }

    public void updateProjectStatus(int projectId, String status) {
        projectDao.updateStatus(projectId, status);
    }

    public void updateProjectProgress(int projectId, int progress) {
        projectDao.updateProgress(projectId, progress);
    }
}