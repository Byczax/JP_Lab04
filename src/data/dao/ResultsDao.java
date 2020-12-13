package data.dao;

import data.models.Results;
import data.models.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ResultsDao implements DAO<Results> {
    List<Results> resultsList = new ArrayList<>();
    String resultsFileName = "results.csv";
    List<List<String>> answersList = new ArrayList<>();

    public void setAnswersList(List<List<String>> answersList) {
        this.answersList = answersList;
    }

    public List<List<String>> getAnswersList() {
        return answersList;
    }

    @Override
    public List<Results> getAll() {
        readFile(resultsFileName);
        return resultsList;
    }

    @Override
    public void add(Results results) {
        resultsList.add(results);
        addToFile(resultsFileName, results);
    }

    @Override
    public void update(String t, String[] params) {

    }

    @Override
    public void delete(String t) {

    }

    @Override
    public void readFile(String filename) {
        try {
            Scanner data = new Scanner(new File(filename));
            while (data.hasNext()) {
                List<String> answers = new ArrayList<>();
                String row = data.nextLine();
                String[] strData = row.split(";");
                for (int i = 3; strData.length > i; i++) {
                    answers.add(strData[i]);
                }
                resultsList.add(new Results(strData[0], UUID.fromString(strData[1]), UUID.fromString(strData[2]), answers));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }


    public void addToFile(String filename, Results results) {
        try {
            FileWriter fileToUpdate = new FileWriter(filename, true);
            fileToUpdate.write(results.getUser() + ";" + results.getServiceId() + ";" + results.getSurveyId());
            for (String answer : results.getAnswers()
            ) {
                fileToUpdate.write(";" + answer);
            }
            fileToUpdate.write("\n");
            fileToUpdate.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
