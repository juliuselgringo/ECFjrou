package training.afpa.sparadrap.model;

import java.io.*;
import java.util.ArrayList;

public class DataSave {

    public static void serialization() throws IOException {

        try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\DEV01\\OneDrive - AFPA\\Documents\\ECFjrou\\Sparadrap\\serializedData\\data.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(Customer.customersList);


            out.writeObject(Doctor.doctorsList);


            out.writeObject(Mutual.mutualsList);


            out.writeObject(Purchase.purchasesHistory);


            out.writeObject(Drug.drugsList);
            System.out.println("Sérialisation réalisé avec succès");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void deserialization() throws IOException {
        try (FileInputStream fileIn = new FileInputStream("C:\\Users\\DEV01\\OneDrive - AFPA\\Documents\\ECFjrou\\Sparadrap\\serializedData\\data.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn)) {

            Customer.customersList = (ArrayList<Customer>) in.readObject();
            Doctor.doctorsList = (ArrayList<Doctor>) in.readObject();
            Mutual.mutualsList = (ArrayList<Mutual>) in.readObject();
            Purchase.purchasesHistory = (ArrayList<Purchase>) in.readObject();
            Drug.drugsList = (ArrayList<Drug>) in.readObject();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
