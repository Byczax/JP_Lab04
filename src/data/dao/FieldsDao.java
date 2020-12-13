package data.dao;

import data.models.FieldType;
import data.models.Fields;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class FieldsDao implements DAO<Fields> {
    List<Fields> fieldsList = new ArrayList<>();
    String fieldsFileName = "fields.csv";

    public List<Fields> getAll() {
        readFile(fieldsFileName);
        return fieldsList;
    }

    public void add(Fields fields) {
        fieldsList.add(fields);
        addToFile(fieldsFileName, fields);
    }

    public void update(String old, String[] params) {
        var service = loadFieldData(old);
        int index = fieldsList.indexOf(service);
        fieldsList.get(index).setName(params[0]);
        fieldsList.get(index).setType(FieldType.valueOf(params[1]));
        updateFile(fieldsFileName);
    }

    public void delete(String service) {
        Fields loadedFields = loadFieldData(service);
        fieldsList.remove(loadedFields);
        updateFile(fieldsFileName);
    }

    public void readFile(String filename) {
        try {
            Scanner data = new Scanner(new File(filename));
            while (data.hasNext()) {
                String row = data.nextLine();
                String[] strData = row.split(";",-1);
                fieldsList.add(new Fields(UUID.fromString(strData[0]), strData[1], FieldType.valueOf(strData[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void updateFile(String filename) {
        try {
            FileWriter updatedFile = new FileWriter(filename, false);
            for (Fields fields : fieldsList) {
                updatedFile.write(fields.getUuid() + ";" + fields.getName() + ";" + fields.getType() + "\n");
            }
            updatedFile.close();
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }

    public void addToFile(String filename, Fields fields) {
        try {
            FileWriter fileToUpdate = new FileWriter(filename, true);
            fileToUpdate.write(fields.getUuid() + ";" + fields.getName() + ";" + fields.getType() + "\n");
            fileToUpdate.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Fields loadFieldData(String name) {
        var fields = fieldsList.stream().filter(e -> e.getName().equals(name)).findFirst();
        return fields.orElse(null);
    }
}
