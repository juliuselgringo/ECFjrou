package training.afpa.sparadrap.view;

import training.afpa.sparadrap.model.Drug;
import training.afpa.sparadrap.model.Mutual;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;

public class MutualSwing {

    public static void mutualMenu(){
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel,"Sélectionner une mutuelle dans le tableau: ",10,10);
        JTable table = setTable(panel);

        JButton detailButton = Gui.buttonMaker(panel,"Détails de la mutuelle", 130);

        detailButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row >= 0) {
                Mutual mutual = Mutual.mutualsList.get(row);
                displayMutual(mutual);
            }
        });

        JButton back2Button = Gui.buttonMaker(panel,"Retour",340);
        back2Button.addActionListener(ev -> frame.dispose());

        JButton exitButton2 = Gui.buttonMaker(panel, "Quitter", 370);
        exitButton2.addActionListener(eve -> System.exit(0));
    }

    public static void displayMutual(Mutual mutual){
        JFrame frame = Gui.setPopUpFrame(1200,500);
        JPanel panel = Gui.setPanel(frame);
        Gui.textAreaMaker(panel, mutual.toString(),10,10,1200,300 );

        JButton back2Button = Gui.buttonMaker(panel,"Retour",340);
        back2Button.addActionListener(ev -> frame.dispose());

        JButton exitButton2 = Gui.buttonMaker(panel, "Quitter", 370);
        exitButton2.addActionListener(eve -> System.exit(0));
    }

    public static JTable setTable(JPanel panel){
        String[] header = new String[]{"Nom","Adresse","Code Postal","Ville","Téléphone", "Email", "Taux de remboursement"};
        JTable table = Gui.tableMaker(panel, Mutual.createMutualMatrice(),header,800, 40,800,800);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return table;
    }
}
