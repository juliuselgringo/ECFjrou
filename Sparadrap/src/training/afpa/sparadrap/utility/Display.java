package training.afpa.vue.terminal;

public class Display {

    /**
     * PRINT LINE OUT
     * @param sentence String
     */
    public static void print(String sentence){
        System.out.println(sentence);
    }

    /**
     * PRINT LINE ERR
     * @param sentence String
     */
    public static void error(String sentence){
        System.err.println(sentence);
    }

    /**
     * PRINT MENU BIBLIOTHEQUE
     */
    public static void menu(){
        System.out.println("Menu de gestion de la bibliotheque\n" +
                "1) Emprunt\n" +
                "2) Abonné\n" +
                "3) Livre\n" +
                "0) Quitter \n"
        );
    }

    /**
     * PRINT MENU PRET
     */
    public static void loanMenu(){
        System.out.println("Menu de gestion des prets\n" +
                "0) RETOUR\n" +
                "1) Nouvel emprunt\n" +
                "2) Retour d'emprunt\n" +
                "3) Liste des emprunts\n" +
                "4) Modifier la date de retour\n"
        );
    }

    /**
     * PRINT MENU ABONNE
     */
    public static void subscriberMenu(){
        System.out.println("Menu de gestion des abonnés\n" +
                "0) RETOUR\n" +
                "1) Nouvel abonné\n" +
                "2) Supprimer un abonné\n" +
                "3) Liste des abonnés\n" +
                "4) Modifier le nom de famille\n" +
                "5) Modifier l'email\n"
        );
    }

    /**
     * PRINT MENU LIVRE
     */
    public static void bookMenu(){
        System.out.println("Menu de gestion des livres\n" +
                "0) RETOUR\n" +
                "1) Nouveau livre\n" +
                "2) Supprimer un livre\n" +
                "3) Liste des livres\n" +
                "4) Modifier la quantité\n" +
                "5) Consulter le stock d'un livre"
        );
    }
}
