package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.ProjectTechnologyDao;
import ProjectHub.ECE.model.ProjectTechnology;

@Service
public class ProjectTechnologyService {

    private final ProjectTechnologyDao projectTechnologyDao;

    public ProjectTechnologyService(ProjectTechnologyDao projectTechnologyDao) {
        this.projectTechnologyDao = projectTechnologyDao;
    }

    public boolean addProjectTechnology(ProjectTechnology projectTechnology) {

        if (projectTechnologyDao.exists(
                projectTechnology.getProjectId(),
                projectTechnology.getTechnologyId())) {

            return false;
        }

        projectTechnologyDao.save(projectTechnology);

        return true;
    }

    public List<ProjectTechnology> getTechnologiesByProjectId(int projectId) {
        return projectTechnologyDao.findByProjectId(projectId);
    }

    public void removeTechnologies(int projectId) {
        projectTechnologyDao.deleteByProjectId(projectId);
    }
}