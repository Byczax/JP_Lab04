package data.models;

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

    public UUID getSurveyId() {
        return surveyId;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getUser() {
        return user;
    }

}
