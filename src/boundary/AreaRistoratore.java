package boundary;

import control.DataBaseManager;
import java.util.Scanner;

public class AreaRistoratore {
    private final String email;
    private final DataBaseManager dbManager;

    public AreaRistoratore(String email, DataBaseManager dbManager) {
        this.email = email;
        this.dbManager = dbManager;
    }

    public void avvia(Scanner scanner) {
        int scelta;
        do {
            System.out.println("\n== Area Ristoratore (" + email + ")==");
            System.out.println("1. Gestione Menù.");
            System.out.println("2. Area Notifiche.");
            System.out.println("0. Esci.");
            System.out.print("Scegli un'opzione: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch(scelta) {
                case 1 -> System.out.println("Gestione Menù non implementata.");
                case 2 -> System.out.println("Area Notifiche non implementata.");
                case 0 -> System.out.println("Uscita in corso...");
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);
    }
}
