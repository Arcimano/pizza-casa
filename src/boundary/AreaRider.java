package boundary;

import control.DataBaseManager;

import java.util.List;
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
            System.out.println("2. Completa Ordine.");
            System.out.println("0. Esci.");
            System.out.print("Scegli un'opzione: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch(scelta) {
                case 1 -> visualizzaOrdini();
                case 2 -> System.out.println("Completamento ordine non implementato.");
                case 0 -> System.out.println("Uscita in corso...");
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);
    }

    private void visualizzaOrdini() {
        List<String[]> ordini = dbManager.recuperaOrdiniRider(email);

        if(ordini.isEmpty()) {
            System.out.println("Nessun ordine disponibile.");
            return;
        }

        System.out.println("\n== Ordini Assegnati ==");
        for (String[] ordine : ordini) {
            String nomeRistorante = ordine[0];
            String indirizzoRistorante = ordine[1];
            String capRistorante = ordine[2];
            String telefonoRistorante = ordine[3];
            String nomeCliente = ordine[4];
            String cognomeCliente = ordine[5];
            String indirizzoCliente = ordine[6];
            String capCliente = ordine[7];
            String telefonoCliente = ordine[8];
            String orarioInvio = ordine[9];

            System.out.println("\n--- Ordine ---");
            System.out.println("Informazioni Ristorante:");
            System.out.println("Nome: " + nomeRistorante);
            System.out.println("Indirizzo: " + indirizzoRistorante);
            System.out.println("CAP: " + capRistorante);
            System.out.println("Telefono: " + telefonoRistorante);

            System.out.println("Informazioni Cliente:");
            System.out.println("Nome: " + nomeCliente);
            System.out.println("Cognome: " + cognomeCliente);
            System.out.println("Indirizzo: " + indirizzoCliente);
            System.out.println("CAP: " + capCliente);
            System.out.println("Telefono: " + telefonoCliente);

            System.out.println("Orario di Invio: " + orarioInvio);
            System.out.println("-------------------");

        }
    }
    
}
