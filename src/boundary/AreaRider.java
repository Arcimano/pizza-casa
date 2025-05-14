package boundary;

import control.DataBaseManager;
import java.util.Scanner;

public class AreaRider {
    private final String email;
    private final DataBaseManager dbManager;

    public AreaRider(String email, DataBaseManager dbManager) {
        this.email = email;
        this.dbManager = dbManager;
    }

    public void avvia(Scanner scanner) {
        int scelta;
        do {
            System.out.println("\n== Area Rider (" + email +")==");
            System.out.println("1. Visualizza Ordini.");
            System.out.println("2. Gestione Profilo.");
            System.out.println("0. Esci.");
            System.out.print("Scegli un'opzione: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch(scelta) {
                case 1 -> System.out.println("Visualizza Ordini non implementata.");
                case 2 -> System.out.println("Gestione Profilo non implementata.");
                case 0 -> System.out.println("Uscita in corso...");
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);
    }
    
}
