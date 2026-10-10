package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.ProjectMemberDao;
import ProjectHub.ECE.model.ProjectMember;

@Service
public class ProjectMemberService {

    private final ProjectMemberDao projectMemberDao;

    public ProjectMemberService(ProjectMemberDao projectMemberDao) {
        this.projectMemberDao = projectMemberDao;
    }

    public void addMember(ProjectMember member) {
        projectMemberDao.save(member);
    }

    public List<ProjectMember> getMembersByProjectId(int projectId) {
        return projectMemberDao.findByProjectId(projectId);
    }

    public void removeMembers(int projectId) {
        projectMemberDao.deleteByProjectId(projectId);
    }
    public void removeMember(int id) {
    projectMemberDao.deleteById(id);
}
}