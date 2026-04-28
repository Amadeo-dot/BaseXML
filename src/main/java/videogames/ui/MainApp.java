package videogames.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import videogames.dao.BaseXVideojocDAOImpl;
import videogames.dao.VideojocDAO;
import videogames.db.BaseXConnection;
import videogames.model.Videojoc;

public class MainApp {

    public static void main(String[] args) {
        BaseXConnection connexio = null;
        Scanner teclat = new Scanner(System.in);

        try {
            connexio = new BaseXConnection();
            VideojocDAO dao = new BaseXVideojocDAOImpl(connexio);
            boolean sortir = false;

            while (!sortir) {
                mostrarMenu();
                String opcio = teclat.nextLine();

                if (opcio.equals("1")) {
                    llistar(dao);
                } else if (opcio.equals("2")) {
                    cercarPerId(dao, teclat);
                } else if (opcio.equals("3")) {
                    alta(dao, teclat);
                } else if (opcio.equals("4")) {
                    afegirPlataforma(dao, teclat);
                } else if (opcio.equals("5")) {
                    modificarPreu(dao, teclat);
                } else if (opcio.equals("6")) {
                    modificarEstat(dao, teclat);
                } else if (opcio.equals("7")) {
                    baixa(dao, teclat);
                } else if (opcio.equals("0")) {
                    sortir = true;
                } else {
                    System.out.println("Opcio no valida");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            teclat.close();
            if (connexio != null) {
                connexio.close();
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n---- MENU VIDEOJOCS ----");
        System.out.println("1. Llistar cataleg");
        System.out.println("2. Cercar per ID");
        System.out.println("3. Alta videojoc");
        System.out.println("4. Afegir plataforma");
        System.out.println("5. Modificar preu");
        System.out.println("6. Modificar estat");
        System.out.println("7. Baixa videojoc");
        System.out.println("0. Sortir");
        System.out.print("Escull opcio: ");
    }

    private static void llistar(VideojocDAO dao) throws Exception {
        List<Videojoc> llista = dao.llistarCataleg();
        if (llista.isEmpty()) {
            System.out.println("No hi ha videojocs");
            return;
        }

        for (int i = 0; i < llista.size(); i++) {
            System.out.println(llista.get(i));
        }
    }

    private static void cercarPerId(VideojocDAO dao, Scanner teclat) throws Exception {
        System.out.print("ID: ");
        String id = teclat.nextLine();

        Videojoc joc = dao.cercarPerId(id);
        if (joc == null) {
            System.out.println("No existeix");
        } else {
            System.out.println(joc);
        }
    }

    private static void alta(VideojocDAO dao, Scanner teclat) throws Exception {
        Videojoc joc = new Videojoc();

        System.out.print("ID: ");
        joc.setId(teclat.nextLine());

        System.out.print("Estat: ");
        joc.setEstat(teclat.nextLine());

        System.out.print("Titol: ");
        joc.setTitol(teclat.nextLine());

        System.out.print("Desenvolupador: ");
        joc.setDesenvolupador(teclat.nextLine());

        System.out.print("Preu: ");
        joc.setPreu(Double.parseDouble(teclat.nextLine()));

        System.out.print("Any llancament: ");
        joc.setAnyLlancament(Integer.parseInt(teclat.nextLine()));

        System.out.print("Plataformes separades per coma: ");
        joc.setPlataformes(textALlista(teclat.nextLine()));

        dao.altaVideojoc(joc);
        System.out.println("Alta feta");
    }

    private static void afegirPlataforma(VideojocDAO dao, Scanner teclat) throws Exception {
        System.out.print("ID del joc: ");
        String id = teclat.nextLine();

        System.out.print("Nova plataforma: ");
        String plataforma = teclat.nextLine();

        dao.afegirPlataforma(id, plataforma);
        System.out.println("Plataforma afegida");
    }

    private static void modificarPreu(VideojocDAO dao, Scanner teclat) throws Exception {
        System.out.print("ID del joc: ");
        String id = teclat.nextLine();

        System.out.print("Preu nou: ");
        double preuNou = Double.parseDouble(teclat.nextLine());

        dao.modificarPreu(id, preuNou);
        System.out.println("Preu modificat");
    }

    private static void modificarEstat(VideojocDAO dao, Scanner teclat) throws Exception {
        System.out.print("ID del joc: ");
        String id = teclat.nextLine();

        System.out.print("Estat nou: ");
        String estatNou = teclat.nextLine();

        dao.modificarEstat(id, estatNou);
        System.out.println("Estat modificat");
    }

    private static void baixa(VideojocDAO dao, Scanner teclat) throws Exception {
        System.out.print("ID del joc: ");
        String id = teclat.nextLine();

        dao.baixaVideojoc(id);
        System.out.println("Baixa feta");
    }

    private static List<String> textALlista(String text) {
        List<String> llista = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) {
            return llista;
        }

        String[] parts = text.split(",");
        for (int i = 0; i < parts.length; i++) {
            String valor = parts[i].trim();
            if (!valor.isEmpty()) {
                llista.add(valor);
            }
        }
        return llista;
    }
}
