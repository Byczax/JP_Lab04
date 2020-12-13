package data.dao;

import data.models.Survey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class SurveyDAO implements DAO<Survey> {
    List<Survey> surveyList = new ArrayList<>();
    String surveysFileName = "surveys.csv";

    public List<Survey> getAll() {
        readFile(surveysFileName);
        return surveyList;
    }

    public void add(Survey survey) {
        surveyList.add(survey);
        addToFile(surveysFileName,survey);
    }

    public void update(String old, String[] params) {
        var service = loadSurveyData(old);
        int index = surveyList.indexOf(service);
        surveyList.get(index).setName(params[0]);
        surveyList.get(index).setDescription(params[1]);
        updateFile(surveysFileName);
    }

    public void delete(String service) {
        var loadedService = loadSurveyData(service);
        surveyList.remove(loadedService);
        updateFile(surveysFileName);
    }

    public void readFile(String filename) {
        try {
            Scanner data = new Scanner(new File(filename));
            while (data.hasNext()) {
                String row = data.nextLine();
                String[] strData = row.split(";");
                surveyList.add(new Survey(UUID.fromString(strData[0]), strData[1], strData[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void updateFile(String filename) {
        try {
            FileWriter updatedFile = new FileWriter(filename, false);
            for (Survey service : surveyList) {
                updatedFile.write(service.getUUID() + ";" + service.getName() + ";" + service.getDescription()+"\n");
            }
            updatedFile.close();
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }

    public void addToFile(String filename, Survey service) {
        try {
            FileWriter fileToUpdate = new FileWriter(filename, true);
            fileToUpdate.write(service.getUUID() + ";" + service.getName() + ";" + service.getDescription()+"\n");
            fileToUpdate.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Survey loadSurveyData(String name){
        var survey = surveyList.stream().filter(e -> e.getName().equals(name)).findFirst();
        return survey.orElse(null);
    }
}
