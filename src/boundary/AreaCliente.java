package boundary;

import control.DataBaseManager;
import java.util.Scanner;

public class AreaCliente {
    private final String email;
    private final DataBaseManager dbManager;

    public AreaCliente(String email, DataBaseManager dbManager) {
        this.email = email;
        this.dbManager = dbManager;
    }

    public void avvia(Scanner scanner) {
        int scelta;
        do {
            System.out.println("\n== Area Cliente (" + email + ") ==");
            System.out.println("1. Visualizza Ristoranti.");
            System.out.println("2. Visualizza Carrello.");
            System.out.println("0. Esci.");
            System.out.print("Scegli un'opzione: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch(scelta) {
                case 1 -> System.out.println("Visualizza Ristoranti non implementata.");
                case 2 -> System.out.println("Visualizza Carrello non implementata.");
                case 0 -> System.out.println("Uscita in corso...");
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);
    }
}
