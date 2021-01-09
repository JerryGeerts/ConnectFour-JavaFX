package Model;

import java.io.*;
import java.util.ArrayList;

public class Repository {
    private ArrayList<Object> objects;
    private File file;

    public Repository(File file) {
        objects = new ArrayList<>();
        this.file = file;
    }

    public void addObject(Object object) {
        objects.add(object);
        saveObject();
    }

    public void deleteObject(Object object) {
        objects.remove(object);
        saveObject();
    }

    public void editeObject(Object oldObject, Object newObject) {
        objects.remove(oldObject);
        objects.add(newObject);
        saveObject();
    }

    public ArrayList<Object> getObjects() {
        try {
            FileInputStream fileInput = new FileInputStream(file);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            objects = (ArrayList<Object>) objectInput.readObject();

        } catch (Exception e) {
            System.out.println(e);
        }

        return objects;
    }

    public void saveObject() {
        try {
            FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            objectOutput.writeObject(objects);

            objectOutput.close();
            fileOutput.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
