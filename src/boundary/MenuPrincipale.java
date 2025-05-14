package boundary;

import control.DataBaseManager;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipale {
    private final Scanner scanner;
    private final DataBaseManager dbManager;

    public MenuPrincipale() {
        this.scanner = new Scanner(System.in);
        this.dbManager = new DataBaseManager();
    }

    public void avvia() {
        int scelta;

        do {
            System.out.println("== Benvenuto a Pizza@Casa! ==");
            System.out.println("\n1. Accedi");
            System.out.println("2. Registrati");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> accedi();
                case 2 -> registrati();
                case 0 -> System.out.println("Uscita in corso...");
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);
    }

    private void accedi() {
        System.out.println("\n== Accesso Utente ==");
        System.out.println("Inserisci la tua email: ");
        String email = scanner.nextLine();
        System.out.println("Inserisci la tua password: ");
        String password = scanner.nextLine();
        
        String tipoUtente = dbManager.verificaCredenziali(email, password);

        if (tipoUtente == null) {
            System.out.println("Credenziali non valide. Riprova.");
        } else {
            switch (tipoUtente) {
                case "gestore" -> avviaAreaGestore(email);
                case "ristoratore" -> avviaAreaRistoratore(email);
                case "cliente" -> avviaAreaCliente(email);
                case "rider" -> avviaAreaRider(email);
                default -> System.out.println("Tipo utente non riconosciuto.");
            }
        }
    }

    private void registrati() {
        System.out.println("\n== Registrazione Utente ==");
        int scelta;

        System.out.println("1. Registrati come Ristoratore.");
        System.out.println("2. Registrati come Cliente.");
        scelta = Integer.parseInt(scanner.nextLine());
        switch (scelta) {
            case 1 -> registraRistoratore();
            case 2 -> registraCliente();
            default -> System.out.println("Opzione non valida. Riprova.");
        }
    }

    private void registraRistoratore() {
        System.out.println("\n== Registrazione Ristoratore ==");
        System.out.println("Inserisci la tua email: ");
        String email = scanner.nextLine();
        System.out.println("Inserisci la tua password: ");
        String password = scanner.nextLine();

        System.out.print("Inserisci il tuo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Inserisci il tuo cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Inserisci il tuo numero di telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("Inserisci il nome del tuo ristorante: ");
        String nomeRistorante = scanner.nextLine();
        System.out.print("Inserisci l'indirizzo del tuo ristorante: ");
        String indirizzo = scanner.nextLine();
        System.out.print("Inserisci il tuo CAP:");
        String cap = scanner.nextLine();
        if (dbManager.aggiungiRichiestaRegistrazioneRistoratore(email, password, nome, cognome, telefono, nomeRistorante, indirizzo, cap)) {
            System.out.println("Registrazione richiesta con successo. Attendi l'approvazione.");
        } else {
            System.out.println("Errore durante la registrazione. Riprova.");
        }
    }

    private void registraCliente() {
        System.out.println("\n== Registrazione Cliente ==");

        System.out.print("Inserisci la tua email: ");
        String email = scanner.nextLine();
        System.out.print("Inserisci la tua password: ");
        String password = scanner.nextLine();
        System.out.print("Inserisci il tuo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Inserisci il tuo cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Inserisci il tuo indirizzo: ");
        String indirizzo = scanner.nextLine();
        System.out.print("Inserisci il tuo CAP: ");
        String cap = scanner.nextLine();
        System.out.print("Inserisci il tuo numero di telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("Inserisci il numero della carta di credito: ");
        String numeroCarta = scanner.nextLine();

        if (dbManager.registraCliente(email, password, nome, cognome, indirizzo, cap, telefono, numeroCarta))
            System.out.println("Registrazione avvenuta con successo.");
        else
            System.out.println("Errore durante la registrazione. Riprova.");
    }

    private void avviaAreaGestore(String email) {
        int scelta;
        do {
            System.out.println("\n== Area Gestore ==");
            System.out.println("1. Visualizza richieste di registrazione.");
            System.out.println("2. Aggiungi nuovo rider.");
            System.out.println("3. Visualizza report mensile.");
            System.out.println("0. Esci dall'area gestore.");
            System.out.println("Scegli un'opzione: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> visualizzaRichiesteRegistrazione();
                case 2 -> aggiungiRider();
                case 3 -> visualizzaReportMensile();
                case 0 -> System.out.println("Uscita dall'area gestore in corso...");
                default -> System.out.println("Opzione non valida. Riprova.");
            }
        } while (scelta != 0);
    }

    private void visualizzaRichiesteRegistrazione() {
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

    private void aggiungiRider() {
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

    private void avviaAreaRistoratore(String email) {
        System.out.println("Accesso all'area ristoratore...");
        // Implementa la logica per l'area ristoratore
    }

    private void avviaAreaCliente(String email) {
        System.out.println("Accesso all'area cliente...");
        // Implementa la logica per l'area cliente
    }

    private void avviaAreaRider(String email) {
        System.out.println("Accesso all'area rider...");
        // Implementa la logica per l'area rider
    }
}
