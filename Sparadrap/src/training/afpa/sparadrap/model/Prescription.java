package training.afpa.sparadrap.model;

import org.apache.pdfbox.pdmodel.font.PDType0Font;
import training.afpa.sparadrap.ExceptionTracking.InputException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import training.afpa.sparadrap.utility.Display;

import java.io.File;
import java.io.IOException;

public class Prescription implements Serializable{

    private LocalDate prescriptionDate;
    private String doctorLastName;
    private String customerLastName;
    private Map<Drug, Integer> drugsQuantityPrescriptionsList = new HashMap<>();
    public Integer purchaseNumber;
    private String pathPdf;

    /**
     * CONSTRUCTOR
     * @param prescriptionDateIn String
     * @param doctorLastNameIn String
     * @param customerLastNameIn String
     * @throws InputException
     */
    public Prescription(String prescriptionDateIn, String doctorLastNameIn, String customerLastNameIn) throws InputException {
        this.setPrescriptionDate(prescriptionDateIn);
        this.setDoctorLastName(doctorLastNameIn);
        this.setCustomerLastName(customerLastNameIn);
    }

    /**
     * CONSTRUCTOR
     */
    public Prescription(){};

    /**
     * GETTER prescriptionDate
     * @return LocalDate
     */
    public LocalDate getPrescriptionDate() {
        return this.prescriptionDate;
    }

    /**
     * SETTER prescriptionDate
     * @param prescriptionDateIn String
     * @throws InputException
     */
    public void setPrescriptionDate(String prescriptionDateIn) throws InputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate prescriptionDateInLD = null;
        try {
            prescriptionDateInLD = LocalDate.parse(prescriptionDateIn, formatter);

        }catch(DateTimeParseException dtpe){
            JOptionPane.showMessageDialog(null, "Date de prescription invalide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        if (prescriptionDateInLD.isAfter(LocalDate.now())) {
            throw new InputException("La date de prescription ne peut être après la date d'aujourd'hui!");
        } else {
            this.prescriptionDate = prescriptionDateInLD;
        }

    }

    /**
     * GETTER doctorLastName
     * @return String
     */
    public String getDoctorLastName() {
        return this.doctorLastName;
    }

    /**
     * SETTER doctorLastName
     * @param doctorLastNameIn String
     * @throws InputException
     */
    public void setDoctorLastName(String doctorLastNameIn) throws InputException {
        String doctorLastNameInSt = doctorLastNameIn.trim();
        for (Doctor doctor : Doctor.doctorsList){
            try {
                if (doctor.getLastName().equals(doctorLastNameInSt)) {
                    this.doctorLastName = doctorLastNameInSt;
                    doctor.setDoctorPrescriptionsList(this);
                    return;
                }
            }catch(NullPointerException npe){}
        }
        if(this.doctorLastName == null){
            throw new InputException("Ce medecin n'est pas enregistré.");
        }
    }

    /**
     * GETTER custormerLastName
     * @return String
     */
    public String getCustomerLastName() {
        return this.customerLastName;
    }

    /**
     * SETTER customerLastName
     * @param customerLastNameIn String
     * @throws InputException
     */
    public void setCustomerLastName(String customerLastNameIn) throws InputException {
        String customerLastNameInTrimed = customerLastNameIn.trim();
        for (Customer customer : Customer.customersList){
            try {
                if (customer.getLastName().equals(customerLastNameInTrimed)) {
                    this.customerLastName = customerLastNameInTrimed;
                    return;
                }
            }catch(NullPointerException npe){}
        }
        if(this.customerLastName == null){
            throw new InputException("Ce client n'est pas enregistré.");
        }
    }

    /**
     * GETTER getDrugsQuantityPrescriptionList
     * @return Map<Drug, Integer>
     */
    public Map<Drug, Integer> getDrugsQuantityPrescriptionList() {
        return this.drugsQuantityPrescriptionsList;
    }

    /**
     * SETTER drugsDrugsQuantityPrescritionList
     * @param drug Drug
     */
    public void setDrugsQuantityPrescriptionList(Drug drug, int quantity) {
        this.drugsQuantityPrescriptionsList.put(drug,quantity);
    }

    /**
     * GETTER pathPdf
     * @return pathPdf String
     */
    public String getPathPdf(){
        return this.pathPdf;
    }

    /**
     * SETTER pathPdf
     * @param pathPdf String
     */
    public void setPathPdf(String pathPdf){
        this.pathPdf = pathPdf.trim();
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Date: " + this.getPrescriptionDate().format(formatter) +
                "\nNom du medecin: " + this.getDoctorLastName() +
                "\nNom du client:  " + this.getCustomerLastName() +
                "\nListe des médicament: " + this.getDrugsQuantityToString();
    }

    /**
     * TO STRING POUR L ENREGISTREMENT DES PRESCRIPTION EN PDF
     * @return
     */
    public String toStringForPdf(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return " // " + this.getPrescriptionDate().format(formatter) +
                " / Medecin: " + this.getDoctorLastName() +
                " / Client:  " + this.getCustomerLastName() +
                " / Médicaments: " + this.getDrugsQuantityToString();
    }

    /**
     * L AFFICHAGE DE QUANTITE COMMANDE OU SORTIE DE STOCK
     * @return String
     */
    public String getDrugsQuantityToString() {
        String stringToReturn = "";
        for (Map.Entry<Drug,Integer> entry : this.getDrugsQuantityPrescriptionList().entrySet()){
            stringToReturn += entry.getKey().toStringForPdf() + " : " + entry.getValue().toString() + " qté(s)";
        }
        return stringToReturn;
    }

    /**
     * SAUVEGARDER UNE PRESCRIPTION EN PDF
     * @throws IOException
     */
    public void savePrescriptionAsPdf() throws IOException {
        String pathHistoric = "C:\\Users\\DEV01\\OneDrive - AFPA\\Documents\\ECFjrou\\Sparadrap\\src\\historic\\"
                + this.getCustomerLastName() + this.prescriptionDate + ".pdf";
        try(PDDocument document = new PDDocument()){
            PDPage page = new PDPage();
            document.addPage(page);

            PDType0Font font = PDType0Font.load(document,
                    new File("C:\\Users\\DEV01\\OneDrive - AFPA\\Documents\\ECFjrou\\Sparadrap\\src\\training\\afpa\\sparadrap\\fonts\\arial.ttf"));

            try(PDPageContentStream contentStream = new PDPageContentStream(document, page)){

                contentStream.beginText();
                contentStream.setFont(font, 12);

                List<String> lines = sliceTextForPdf(this.toStringForPdf(),100);
                contentStream.newLineAtOffset(50, 700);

                for (String line : lines){
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -20);
                }
                contentStream.endText();

            }

            document.save(pathHistoric);
            this.setPathPdf(pathHistoric);
            JOptionPane.showMessageDialog(null,"PDF créé !");

        }catch(IOException ioe){
            System.err.println("erreur : " + ioe.getMessage());
        }
    }

    /**
     * DECOUPE LE TEXTE DES PRESCRIPTION EN LIGNE POUR L ENRGISTREMENT PDF
     * @param text String
     * @param lineMax int
     * @return List
     */
    public static List<String> sliceTextForPdf(String text, int lineMax){
        List<String> stringToReturn = new ArrayList<>();
        int index = 0;
        while(index < text.length()){
            int endLine = Math.min(text.length(), index + lineMax);
            stringToReturn.add(text.substring(index, endLine));
            index = endLine;

        }
        return stringToReturn;
    }

    /**
     * OUVRE UNE PRESCRIPTION PDF
     */
    public void openPdfPrescription(){
        try {
            File pdfFile = new File(this.pathPdf);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    Display.error("Desktop n'est pas supporté");
                }
            }
        } catch(NullPointerException npe){
                    Display.error("Le fichier n'existe pas");
                    JOptionPane.showMessageDialog(null, "Le fichier n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
