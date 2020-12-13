package data.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Connection {
    UUID serviceUUID;
    UUID surveyUUID;
    List<Integer> results = new ArrayList<>();

    public Connection(UUID serviceUUID, UUID surveyUUID) {
        this.serviceUUID = serviceUUID;
        this.surveyUUID = surveyUUID;
    }

    public UUID getServiceUUID() {
        return serviceUUID;
    }

    public UUID getSurveyUUID() {
        return surveyUUID;
    }

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }
}
