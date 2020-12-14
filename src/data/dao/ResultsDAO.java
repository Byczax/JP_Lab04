package data.dao;

import data.models.Results;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ResultsDAO implements DAO<Results> {
    private final List<Results> resultsList = new ArrayList<>();
    private final String resultsFileName = "results.csv";

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

    public void update(String t, String[] params) {

    }


    public void delete(String t) {

    }

    public void readFile(String filename) {
        resultsList.clear();
        try {
            Scanner data = new Scanner(new File(filename));
            while (data.hasNext()) {
                String row = data.nextLine();
                String[] strData = row.split(";", -1);
                List<String> answers = new ArrayList<>(Arrays.asList(strData).subList(3, strData.length));
                resultsList.add(new Results(strData[0], UUID.fromString(strData[1]), UUID.fromString(strData[2]), answers));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    private void addToFile(String filename, Results results) {
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
