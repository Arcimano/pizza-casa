package control;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private static final String DB_URL = "jdbc:sqlite:pizzacasa.db";

    public DataBaseManager() {
        creaTabellaUtenti();
        creaTabellaRegistrazioni();
        creaTabellaClienti();
        creaTabellaRistoratori();
        creaTabellaRiders();
        creaTabellaOrdini();
        creaTabellaOrdiniCompletati();
    }

    private void creaTabellaUtenti() {
        String sql = """
                CREATE TABLE IF NOT EXISTS utenti (
                    email TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    tipo_utente TEXT NOT NULL
                );
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
             stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella utenti: " + e.getMessage());
        }
    }

    private void creaTabellaRegistrazioni() {
        String sql = """
                CREATE TABLE IF NOT EXISTS registrazioni (
                    email TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    tipo_utente TEXT NOT NULL,
                    nome TEXT NOT NULL,
                    cognome TEXT NOT NULL,
                    telefono TEXT NOT NULL,
                    nome_ristorante TEXT NOT NULL,
                    indirizzo TEXT NOT NULL,
                    cap TEXT NOT NULL
                );
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
             stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella registrazioni: " + e.getMessage());
        }
    }

    private void creaTabellaClienti() {
        String sql = """
                CREATE TABLE IF NOT EXISTS clienti (
                    email TEXT PRIMARY KEY,
                    nome TEXT NOT NULL,
                    cognome TEXT NOT NULL,
                    indirizzo TEXT NOT NULL,
                    cap TEXT NOT NULL,
                    telefono TEXT NOT NULL,
                    numero_carta TEXT NOT NULL,
                    FOREIGN KEY (email) REFERENCES utenti(email)
                );
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
             stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella clienti: " + e.getMessage());
        }
    }

    private void creaTabellaRistoratori() {
        String sql = """
                CREATE TABLE IF NOT EXISTS ristoratori (
                    email TEXT PRIMARY KEY,
                    nome TEXT NOT NULL,
                    cognome TEXT NOT NULL,
                    telefono TEXT NOT NULL,
                    nome_ristorante TEXT NOT NULL,
                    indirizzo TEXT NOT NULL,
                    cap TEXT NOT NULL,
                    FOREIGN KEY (email) REFERENCES utenti(email)
                );
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
             stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella ristoratori: " + e.getMessage());
        }
    }

    private void creaTabellaRiders() {
        String sql = """
                CREATE TABLE IF NOT EXISTS riders (
                    email TEXT PRIMARY KEY,
                    nome TEXT NOT NULL,
                    cognome TEXT NOT NULL,
                    telefono TEXT NOT NULL,
                    cap TEXT NOT NULL,
                    FOREIGN KEY (email) REFERENCES utenti(email)
                );
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
             stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella riders: " + e.getMessage());
        }
    }

    public void creaTabellaOrdini() {
        String sql = """
                CREATE TABLE IF NOT EXISTS ordini (
                    idordine INTEGER PRIMARY KEY AUTOINCREMENT,
                    email_ristoratore TEXT NOT NULL,
                    email_cliente TEXT NOT NULL,
                    email_rider TEXT NOT NULL,
                    orario_inizio DATETIME NOT NULL,
                    FOREIGN KEY (email_ristoratore) REFERENCES ristoratori(email),
                    FOREIGN KEY (email_cliente) REFERENCES clienti(email),
                    FOREIGN KEY (email_rider) REFERENCES riders(email)
                );
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella ordini: " + e.getMessage());
        }
    }

    public void creaTabellaOrdiniCompletati() {
        String sql = """
                CREATE TABLE IF NOT EXISTS ordini_completati (
                    idordine INTEGER PRIMARY KEY,
                    email_ristoratore TEXT NOT NULL,
                    email_cliente TEXT NOT NULL,
                    email_rider TEXT NOT NULL,
                    orario_inizio DATETIME NOT NULL,
                    orario_fine DATETIME NOT NULL,
                    FOREIGN KEY (idordine) REFERENCES ordini(idordine),
                    FOREIGN KEY (email_ristoratore) REFERENCES ristoratori(email),
                    FOREIGN KEY (email_cliente) REFERENCES clienti(email),
                    FOREIGN KEY (email_rider) REFERENCES riders(email),
                    FOREIGN KEY (orario_inizio) REFERENCES ordini(orario_inizio)
                );
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
             stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della tabella ordini_completati: " + e.getMessage());
        }
    }

    public String verificaCredenziali (String email, String password) {
        String sql = "SELECT tipo_utente FROM utenti WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return rs.getString("tipo_utente");
                }
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica delle credenziali: " + e.getMessage());
        }
        return null;
    }   

    public boolean aggiungiRichiestaRegistrazioneRistoratore(String email, String password, String nome, String cognome, 
                                                            String telefono, String nomeRistorante, String indirizzo, String cap) {
        String sql = """
                INSERT INTO registrazioni (email, password, tipo_utente, nome, cognome, telefono, nome_ristorante, indirizzo, cap)
                VALUES (?, ?, 'ristoratore', ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, nome);
            pstmt.setString(4, cognome);
            pstmt.setString(5, telefono);
            pstmt.setString(6, nomeRistorante);
            pstmt.setString(7, indirizzo);
            pstmt.setString(8, cap);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiunta della richiesta di registrazione: " + e.getMessage());
            return false;
        }
    }

    public List<String> recuperaRichiesteRegistrazione(String tipoUtente) {
        String sql = "SELECT email FROM registrazioni WHERE tipo_utente = ?";
        List<String> richieste = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, tipoUtente);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    richieste.add(rs.getString("email"));
                }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero delle richieste di registrazione: " + e.getMessage());
        }
        return richieste;
    }

    public void visualizzaDettagliRichiesta(String email) {
        String sql = """
                SELECT nome, cognome, telefono, nome_ristorante, indirizzo, cap
                FROM registrazioni
                WHERE email = ?;
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
       
            if (rs.next()) {
                System.out.println("\n== Dettagli Richiesta ==");
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Cognome: " + rs.getString("cognome"));
                System.out.println("Telefono: " + rs.getString("telefono"));
                System.out.println("Nome Ristorante: " + rs.getString("nome_ristorante"));
                System.out.println("Indirizzo: " + rs.getString("indirizzo"));
                System.out.println("CAP: " + rs.getString("cap"));
            } else {
                   System.out.println("Dettagli non trovati per l'email: " + email);
            }
        } catch (SQLException e) {
               System.err.println("Errore durante il recupero dei dettagli della richiesta: " + e.getMessage());
        }
    }

    public boolean accettaRichiesta(String email) {
        String sqlInserisciUtente = "INSERT INTO utenti (email, password, tipo_utente) SELECT email, password, tipo_utente FROM registrazioni WHERE email = ?";
        String sqlInserisciRistoratore = "INSERT INTO ristoratori (email, nome, cognome, telefono, nome_ristorante, indirizzo, cap) SELECT email, nome, cognome, telefono, nome_ristorante, indirizzo, cap FROM registrazioni WHERE email = ?";
        String sqlElimina = "DELETE FROM registrazioni WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmtUtenti = conn.prepareStatement(sqlInserisciUtente);
             PreparedStatement pstmtRistoratore = conn.prepareStatement(sqlInserisciRistoratore);
             PreparedStatement pstmtElimina = conn.prepareStatement(sqlElimina)) {
            pstmtUtenti.setString(1, email);
            pstmtUtenti.executeUpdate();

            pstmtRistoratore.setString(1, email);
            pstmtRistoratore.executeUpdate();

            pstmtElimina.setString(1, email);
            pstmtElimina.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Errore durante l'accettazione della richiesta di registrazione: " + e.getMessage());
            return false;
        }
    }

    public boolean rifiutaRichiesta(String email) {
        String sql = "DELETE FROM registrazioni WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Errore durante il rifiuto della richiesta di registrazione: " + e.getMessage());
            return false;
        }
    }

    public boolean registraCliente(String email, String password, String nome, String cognome, String indirizzo, String cap, String telefono, String numeroCarta) {
        String sqlUtenti = "INSERT INTO utenti (email, password, tipo_utente) VALUES (?, ?, 'cliente')";
        String sqlClienti = """
                INSERT INTO clienti (email, nome, cognome, indirizzo, cap, telefono, numero_carta)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmtUtenti = conn.prepareStatement(sqlUtenti);
             PreparedStatement pstmtClienti = conn.prepareStatement(sqlClienti)) {
            
            pstmtUtenti.setString(1, email);
            pstmtUtenti.setString(2, password);
            pstmtUtenti.executeUpdate();

            pstmtClienti.setString(1, email);
            pstmtClienti.setString(2, nome);
            pstmtClienti.setString(3, cognome);
            pstmtClienti.setString(4, indirizzo);
            pstmtClienti.setString(5, cap);
            pstmtClienti.setString(6, telefono);
            pstmtClienti.setString(7, numeroCarta);
            pstmtClienti.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione del cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean registraRider(String email, String password, String nome, String cognome, String telefono, String cap) {
        String sqlUtenti = "INSERT INTO utenti (email, password, tipo_utente) VALUES (?, ?, 'rider')";
        String sqlRiders = """
                INSERT INTO riders (email, nome, cognome, telefono, cap)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmtUtenti = conn.prepareStatement(sqlUtenti);
            PreparedStatement pstmtRiders = conn.prepareStatement(sqlRiders)) {
            pstmtUtenti.setString(1, email);
            pstmtUtenti.setString(2, password);
            pstmtUtenti.executeUpdate();

            pstmtRiders.setString(1, email);
            pstmtRiders.setString(2, nome);
            pstmtRiders.setString(3, cognome);
            pstmtRiders.setString(4, telefono);
            pstmtRiders.setString(5, cap);
            pstmtRiders.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione del rider: " + e.getMessage());
            return false;
        }
    }

    public boolean inviaOrdineAlRider(String emailRistoratore, String emailCliente, String emailRider) {
        String sql = """
                INSERT INTO ordini (email_ristoratore, email_cliente, email_rider, orario_inizio)
                VALUES (?, ?, ?, datetime('now'));
                """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailRistoratore);
            pstmt.setString(2, emailCliente);
            pstmt.setString(3, emailRider);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Errore durante l'invio dell'ordine al rider: " + e.getMessage());
            return false;
        }
    }

    public List<String[]> recuperaOrdiniRider(String emailRider) {
        String sql = """
                SELECT
                    o.idordine,
                    r.nome AS nome_ristorante, r.indirizzo AS indirizzo_ristorante, r.cap AS cap_ristorante, r.telefono AS telefono_ristorante,
                    c.nome AS nome_cliente, c.cognome AS cognome_cliente, c.indirizzo AS indirizzo_cliente, c.cap AS cap_cliente, c.telefono AS telefono_cliente,
                    o.orario_inizio
                FROM ordini o
                JOIN ristoratori r ON o.email_ristoratore = r.email
                JOIN clienti c ON o.email_cliente = c.email
                WHERE o.email_rider = ?;
                ORDER BY o.orario_inizio DESC;
                """;

        List<String[]> ordini = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emailRider);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ordini.add(new String[]{
                    rs.getString("nome_ristorante"),
                    rs.getString("indirizzo_ristorante"),
                    rs.getString("cap_ristorante"),
                    rs.getString("telefono_ristorante"),
                    rs.getString("nome_cliente"),
                    rs.getString("cognome_cliente"),
                    rs.getString("indirizzo_cliente"),
                    rs.getString("cap_cliente"),
                    rs.getString("telefono_cliente"),
                    rs.getString("orario_inizio"),
                    String.valueOf(rs.getInt("idordine"))
                });
            } 
        } catch (SQLException e) {
        System.err.println("Errore durante il recupero degli ordini del rider: " + e.getMessage());
        }

        return ordini;
    }

    public boolean completaOrdine(int ordineId) {
        String inserisciOrdineCompletato = """
                INSERT INTO ordini_completati (idordine, email_ristoratore, email_cliente, email_rider, orario_inizio, orario_fine)
                SELECT idordine, email_ristoratore, email_cliente, email_rider, orario_inizio, datetime('now')
                FROM ordini
                WHERE idordine = ?;
                """;

                String eliminaOrdine = "DELETE FROM ordini WHERE idordine = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmtInserisci = conn.prepareStatement(inserisciOrdineCompletato);
            PreparedStatement pstmtElimina = conn.prepareStatement(eliminaOrdine)) {

                pstmtInserisci.setInt(1, ordineId);
                int rowsInserted = pstmtInserisci.executeUpdate();

                if (rowsInserted > 0) {
                    pstmtElimina.setInt(1, ordineId);
                    pstmtElimina.executeUpdate();
                    return true;
                }
        } catch (SQLException e) {
            System.err.println("Errore durante il completamento dell'ordine: " + e.getMessage());
        }
        return false;
    }
}
