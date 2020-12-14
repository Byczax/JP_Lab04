package data.dao;

import data.models.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ServiceDAO implements DAO<Service> {
    static List<Service> serviceList = new ArrayList<>();

    String serviceFileName = "services.csv";

    public List<Service> getAll() {
        readFile(serviceFileName);
        return serviceList;
    }

    public void add(Service service) {
        serviceList.add(service);
        addToFile(serviceFileName, service);
    }

    public void update(String old, String[] params) {
        var service = loadServiceData(old);
        int index = serviceList.indexOf(service);
        serviceList.get(index).setName(params[0]);
        serviceList.get(index).setDescription(params[1]);
        updateFile(serviceFileName);
    }

    public void delete(String service) {
        Service loadedService = loadServiceData(service);
        serviceList.remove(loadedService);
        updateFile(serviceFileName);
    }

    public void readFile(String filename) {
        try {
            Scanner data = new Scanner(new File(filename));
            while (data.hasNext()) {
                String row = data.nextLine();
                String[] strData = row.split(";", -1);
                serviceList.add(new Service(UUID.fromString(strData[0]), strData[1], strData[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void updateFile(String filename) {
        try {
            FileWriter updatedFile = new FileWriter(filename, false);
            for (Service service : serviceList) {
                updatedFile.write(service.getUUID() + ";" + service.getName() + ";" + service.getDescription() + "\n");
            }
            updatedFile.close();
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }

    public void addToFile(String filename, Service service) {
        try {
            FileWriter fileToUpdate = new FileWriter(filename, true);
            fileToUpdate.write(service.getUUID() + ";" + service.getName() + ";" + service.getDescription() + "\n");
            fileToUpdate.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Service loadServiceData(String name) {
        var service = serviceList.stream().filter(e -> e.getName().equals(name)).findFirst();
        return service.orElse(null);
    }
}
