package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.ProjectComponentDao;
import ProjectHub.ECE.model.ProjectComponent;

@Service
public class ProjectComponentService {

    private final ProjectComponentDao projectComponentDao;

    public ProjectComponentService(ProjectComponentDao projectComponentDao) {
        this.projectComponentDao = projectComponentDao;
    }

    public void addProjectComponent(ProjectComponent projectComponent) {
        projectComponentDao.save(projectComponent);
    }

    public List<ProjectComponent> getComponentsByProjectId(int projectId) {
        return projectComponentDao.findByProjectId(projectId);
    }

    public void removeComponents(int projectId) {
        projectComponentDao.deleteByProjectId(projectId);
    }
}