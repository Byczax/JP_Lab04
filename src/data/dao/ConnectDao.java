package data.dao;

import data.models.Connection;
import data.models.Survey;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConnectDao implements DAO<Connection> {
    List<Connection> connections = new ArrayList<>();
    String connectionsFileName = "connections.csv";


    public List<Connection> getAll() {
        readFile(connectionsFileName);
        return connections;
    }


    public void add(Connection connection) {
        connections.add(connection);
        addToFile(connectionsFileName,connection);
    }


    public void update(String t, String[] params) {

    }


    public void delete(String t) {

    }

    public void readFile(String filename) {
        try {
            Scanner data = new Scanner(new File(filename));
            while (data.hasNext()) {
                String row = data.nextLine();
                String[] strData = row.split(";");
                connections.add(new Connection(UUID.fromString(strData[0]), UUID.fromString(strData[1])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void updateFile(String filename) {
        try {
            FileWriter updatedFile = new FileWriter(filename, false);
            for (Connection connection : connections) {
                updatedFile.write(connection.getServiceUUID() + ";" + connection.getSurveyUUID() +"\n");
            }
            updatedFile.close();
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }

    public void addToFile(String filename, Connection connection) {
        try {
            FileWriter fileToUpdate = new FileWriter(filename, true);
            fileToUpdate.write(connection.getServiceUUID() + ";" + connection.getSurveyUUID() + "\n");
            fileToUpdate.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public Survey loadConnectData(String name){
//        var survey = connections.stream().filter(e -> e.getName().equals(name)).findFirst();
//        return survey.orElse(null);
//    }
}
