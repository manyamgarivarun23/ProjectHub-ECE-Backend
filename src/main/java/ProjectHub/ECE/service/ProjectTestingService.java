package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.ProjectTestingDao;
import ProjectHub.ECE.model.ProjectTesting;

@Service
public class ProjectTestingService {

    private final ProjectTestingDao projectTestingDao;

    public ProjectTestingService(ProjectTestingDao projectTestingDao) {
        this.projectTestingDao = projectTestingDao;
    }

    public void addTesting(ProjectTesting testing) {
        projectTestingDao.save(testing);
    }

    public List<ProjectTesting> getTestingByProjectId(int projectId) {
        return projectTestingDao.findByProjectId(projectId);
    }

    public void deleteTesting(int id) {
        projectTestingDao.deleteById(id);
    }
}