package videogames.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.basex.api.client.LocalSession;
import org.basex.core.Context;

public class BaseXConnection {

    private static final String NOM_BD = "cataleg_videojocs";
    private final Context context;

    public BaseXConnection() throws Exception {
        context = new Context();
        inicialitzarBaseDades();
    }

    private void inicialitzarBaseDades() throws Exception {
        try (LocalSession session = new LocalSession(context)) {
            try {
                // Si la base de dades ja existeix, no fem res
                session.execute("INFO DB " + NOM_BD);
                System.out.println("Base de dades '" + NOM_BD + "' carregada.");
            } catch (Exception e) {
                // Primera execució: creem la base de dades a partir de l'XML inicial
                System.out.println("Primera execució: creant la base de dades...");
                Path arxiuTemporal = copiarXmlTemporal();
                try {
                    String ruta = arxiuTemporal.toAbsolutePath().toString().replace("\\", "/");
                    session.execute("CREATE DB " + NOM_BD + " \"" + ruta + "\"");
                    System.out.println("Base de dades creada correctament.");
                } finally {
                    Files.deleteIfExists(arxiuTemporal);
                }
            }
        }
    }

    private Path copiarXmlTemporal() throws IOException {
        InputStream flux = getClass().getClassLoader().getResourceAsStream("cataleg.xml");
        if (flux == null) {
            throw new IOException("No s'ha trobat cataleg.xml als recursos del projecte.");
        }
        Path arxiuTemporal = Files.createTempFile("cataleg-", ".xml");
        try (flux) {
            Files.copy(flux, arxiuTemporal, StandardCopyOption.REPLACE_EXISTING);
        }
        return arxiuTemporal;
    }

    public LocalSession getSession() throws IOException {
        return new LocalSession(context);
    }

    public String getNomBd() {
        return NOM_BD;
    }

    public void close() {
        context.close();
    }
}
