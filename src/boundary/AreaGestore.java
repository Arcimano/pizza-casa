package boundary;

import control.DataBaseManager;
import java.util.Scanner;
import java.util.List;

public class AreaGestore {
    private final String emailGestore;
    private final DataBaseManager dbManager;

    public AreaGestore(String emailGestore, DataBaseManager dbManager) {
        this.emailGestore = emailGestore;
        this.dbManager = dbManager;
    }

    public void avvia(Scanner scanner) {
        int scelta;
        do {
            System.out.println("\n== Area Gestore (" + emailGestore + ") ==");
            System.out.println("1. Visualizza richieste di registrazione.");
            System.out.println("2. Aggiungi nuovo rider.");
            System.out.println("3. Visualizza report mensile.");
            System.out.println("0. Esci dall'area gestore.");
            System.out.println("Scegli un'opzione: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> visualizzaRichiesteRegistrazione(scanner);
                case 2 -> aggiungiRider(scanner);
                case 3 -> visualizzaReportMensile();
                case 0 -> System.out.println("Uscita dall'area gestore in corso...");
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);
    }

    private void visualizzaRichiesteRegistrazione(Scanner scanner) {
        System.out.println("\n== Richieste di Registrazione dei Ristoratori ==");
        List<String> richieste = dbManager.recuperaRichiesteRegistrazione("ristoratore");

        if (richieste.isEmpty()) {
            System.out.println("Nessuna richiesta di registrazione trovata.");
            return;
        }

        for (int i = 0; i < richieste.size(); i++) {
            System.out.println((i + 1) + ". Email: " + richieste.get(i));
        }

        System.out.print("Inserisci il numero della richiesta da visualizzare (0 per annullare): ");
        int scelta = Integer.parseInt(scanner.nextLine());

        if (scelta > 0 && scelta <= richieste.size()) {
            String email = richieste.get(scelta - 1);
            dbManager.visualizzaDettagliRichiesta(email);

            System.out.print("Accettare la richiesta? (y/n): ");
            String risposta = scanner.nextLine();
            if (risposta.equalsIgnoreCase("y")) {
                if (dbManager.accettaRichiesta(email)) {
                    System.out.println("Richiesta accettata con successo!");
                } else {
                    System.out.println("Errore durante l'accettazione della richiesta.");
                }
            } else if (risposta.equalsIgnoreCase("n")){
                if (dbManager.rifiutaRichiesta(email)) {
                    System.out.println("Richiesta rifiutata con successo!");
                } else {
                    System.out.println("Errore durante il rifiuto della richiesta.");
                }
            } else {
                System.out.println("Opzione non valida. Nessuna azione eseguita.");
            }
        }
    }

    private void aggiungiRider(Scanner scanner) {
        System.out.println("\n== Aggiungi Nuovo Rider ==");
        System.out.print("Inserisci l'email del rider: ");
        String email = scanner.nextLine();
        System.out.print("Inserisci la password del rider: ");
        String password = scanner.nextLine();
        System.out.print("Inserisci il nome del rider: ");
        String nome = scanner.nextLine();
        System.out.print("Inserisci il cognome del rider: ");
        String cognome = scanner.nextLine();
        System.out.print("Inserisci il numero di telefono del rider: ");
        String telefono = scanner.nextLine();
        System.out.print("Inserisci il CAP del rider: ");
        String cap = scanner.nextLine();

        if (dbManager.registraRider(email, password, nome, cognome, telefono, cap)) {
            System.out.println("Rider aggiunto con successo.");
        } else {
            System.out.println("Errore durante l'aggiunta del rider. Riprova.");
        }
    }

    private void visualizzaReportMensile() {
        System.out.println("\n== Report Mensile ==");
        // Implementa la logica per visualizzare il report mensile
    }
}
