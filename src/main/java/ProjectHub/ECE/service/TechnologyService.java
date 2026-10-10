package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.TechnologyDao;
import ProjectHub.ECE.model.Technology;

@Service
public class TechnologyService {

    private final TechnologyDao technologyDao;

    public TechnologyService(TechnologyDao technologyDao) {
        this.technologyDao = technologyDao;
    }

    public void addTechnology(Technology technology) {
        technologyDao.save(technology);
    }

    public List<Technology> getAllTechnologies() {
        return technologyDao.getAllTechnologies();
    }
}