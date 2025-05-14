package boundary;

import control.DataBaseManager;
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
                case "ristoratore" -> new AreaRistoratore(email, dbManager).avvia(scanner);
                case "cliente" -> new AreaCliente(email, dbManager).avvia(scanner);
                case "rider" -> new AreaRider(email, dbManager).avvia(scanner);
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

    private void avviaAreaGestore(String email) {
        AreaGestore areaGestore = new AreaGestore(email, dbManager);
        areaGestore.avvia(scanner);
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
}
