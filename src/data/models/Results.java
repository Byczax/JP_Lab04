package data.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Results {
    String user;
    UUID serviceId;
    UUID surveyId;
    List<String> answers;

    public Results(String user, UUID serviceId, UUID surveyId, List<String> answers) {
        this.user = user;
        this.serviceId = serviceId;
        this.surveyId = surveyId;
        this.answers = answers;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public UUID getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(UUID surveyId) {
        this.surveyId = surveyId;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
