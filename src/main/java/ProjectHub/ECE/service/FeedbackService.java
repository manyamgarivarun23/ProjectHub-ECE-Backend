package ProjectHub.ECE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ProjectHub.ECE.dao.FeedbackDao;
import ProjectHub.ECE.model.Feedback;

@Service
public class FeedbackService {

    private final FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public void addFeedback(Feedback feedback) {
        feedbackDao.save(feedback);
    }

    public List<Feedback> getFeedbackByProjectId(int projectId) {
        return feedbackDao.findByProjectId(projectId);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackDao.findAll();
    }

    public void updateFeedbackStatus(int feedbackId, String status) {
        feedbackDao.updateStatus(feedbackId, status);
    }
}