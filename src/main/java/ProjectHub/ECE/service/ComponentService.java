package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.ComponentDao;
import ProjectHub.ECE.model.Component;

@Service
public class ComponentService {

    private final ComponentDao componentDao;

    public ComponentService(ComponentDao componentDao) {
        this.componentDao = componentDao;
    }

    public void addComponent(Component component) {
        componentDao.save(component);
    }

    public List<Component> getAllComponents() {
        return componentDao.findAll();
    }

    public Component getComponentById(int id) {
        return componentDao.findById(id);
    }
}