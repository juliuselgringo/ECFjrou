package training.afpa.sparadrap.view;

import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;

public class DoctorSwing {

    public static void doctorCheckList() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un médecin:",10,10);
        JComboBox doctorBox = getDoctorBox(panel,40);

        JButton backButton = Gui.buttonMaker(panel,"Retour",190);
        backButton.addActionListener(ev -> frame.dispose());

        doctorBox.addActionListener(e -> {
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            JFrame doctorFrame = Gui.setPopUpFrame(1200,400);
            JPanel doctorPanel = Gui.setPanel(doctorFrame);
            Gui.textAreaMaker(doctorPanel, doctor.toString(),10,10,1200,200 );

            JButton back2Button = Gui.buttonMaker(doctorPanel,"Retour",240);
            back2Button.addActionListener(ev -> doctorFrame.dispose());
        });

    }

    public static JComboBox getDoctorBox(JPanel panel,int y) {
        JComboBox doctorBox = Gui.comboBoxMaker(panel,10,y,400);
        for(Doctor doctor : Doctor.doctorsList){
            doctorBox.addItem(doctor);
        }
        return doctorBox;
    }
}
