package videogames.dao;

import java.util.ArrayList;
import java.util.List;
import org.basex.api.client.LocalQuery;
import org.basex.api.client.LocalSession;
import videogames.db.BaseXConnection;
import videogames.model.Videojoc;

public class BaseXVideojocDAOImpl implements VideojocDAO {

    private final BaseXConnection connexio;

    public BaseXVideojocDAOImpl(BaseXConnection connexio) {
        this.connexio = connexio;
    }

    @Override
    public List<Videojoc> llistarCataleg() throws Exception {
        List<Videojoc> llista = new ArrayList<>();

        String consulta = "for $j in db:get('" + connexio.getNomBd() + "')/cataleg/joc "
                + "let $plats := string-join($j/plataformes/plataforma, ',') "
                + "return string-join((data($j/@id), data($j/@estat), data($j/titol), data($j/desenvolupador), "
                + "data($j/preu), $plats, data($j/any_llancament)), '|')";

        try (LocalSession session = connexio.getSession()) {
            LocalQuery query = session.query(consulta);
            try {
                while (query.more()) {
                    Videojoc joc = convertirFilaAObjecte(query.next());
                    if (joc != null) {
                        llista.add(joc);
                    }
                }
            } finally {
                query.close();
            }
        }

        return llista;
    }

    @Override
    public Videojoc cercarPerId(String id) throws Exception {
        String idSegur = escaparComillaSimple(id);

        String consulta = "for $j in db:get('" + connexio.getNomBd() + "')/cataleg/joc[@id='" + idSegur + "'] "
                + "let $plats := string-join($j/plataformes/plataforma, ',') "
                + "return string-join((data($j/@id), data($j/@estat), data($j/titol), data($j/desenvolupador), "
                + "data($j/preu), $plats, data($j/any_llancament)), '|')";

        try (LocalSession session = connexio.getSession()) {
            LocalQuery query = session.query(consulta);
            try {
                if (query.more()) {
                    return convertirFilaAObjecte(query.next());
                }
            } finally {
                query.close();
            }
        }

        return null;
    }

    @Override
    public void altaVideojoc(Videojoc joc) throws Exception {
        StringBuilder xml = new StringBuilder();
        xml.append("<joc id=\"").append(escaparXml(joc.getId())).append("\" ");
        xml.append("estat=\"").append(escaparXml(joc.getEstat())).append("\">");
        xml.append("<titol>").append(escaparXml(joc.getTitol())).append("</titol>");
        xml.append("<desenvolupador>").append(escaparXml(joc.getDesenvolupador())).append("</desenvolupador>");
        xml.append("<preu>").append(joc.getPreu()).append("</preu>");
        xml.append("<plataformes>");

        List<String> llistaPlataformes = joc.getPlataformes();
        for (int i = 0; i < llistaPlataformes.size(); i++) {
            xml.append("<plataforma>").append(escaparXml(llistaPlataformes.get(i))).append("</plataforma>");
        }

        xml.append("</plataformes>");
        xml.append("<any_llancament>").append(joc.getAnyLlancament()).append("</any_llancament>");
        xml.append("</joc>");

        String consulta = "insert node " + xml + " into db:get('" + connexio.getNomBd() + "')/cataleg";
        executarUpdate(consulta);
    }

    @Override
    public void afegirPlataforma(String id, String plataforma) throws Exception {
        String idSegur = escaparComillaSimple(id);
        String plataformaSegura = escaparXml(plataforma);

        String consulta = "insert node <plataforma>" + plataformaSegura + "</plataforma> "
                + "into db:get('" + connexio.getNomBd() + "')/cataleg/joc[@id='" + idSegur + "']/plataformes";
        executarUpdate(consulta);
    }

    @Override
    public void modificarPreu(String id, double preuNou) throws Exception {
        String idSegur = escaparComillaSimple(id);

        String consulta = "replace value of node db:get('" + connexio.getNomBd() + "')/cataleg/joc[@id='" + idSegur + "']/preu "
                + "with '" + preuNou + "'";
        executarUpdate(consulta);
    }

    @Override
    public void modificarEstat(String id, String estatNou) throws Exception {
        String idSegur = escaparComillaSimple(id);
        String estatSegur = escaparComillaSimple(estatNou);

        String consulta = "replace value of node db:get('" + connexio.getNomBd() + "')/cataleg/joc[@id='" + idSegur + "']/@estat "
                + "with '" + estatSegur + "'";
        executarUpdate(consulta);
    }

    @Override
    public void baixaVideojoc(String id) throws Exception {
        String idSegur = escaparComillaSimple(id);
        String consulta = "delete node db:get('" + connexio.getNomBd() + "')/cataleg/joc[@id='" + idSegur + "']";
        executarUpdate(consulta);
    }

    private void executarUpdate(String consulta) throws Exception {
        try (LocalSession session = connexio.getSession()) {
            LocalQuery query = session.query(consulta);
            try {
                query.execute();
            } finally {
                query.close();
            }
        }
    }

    private Videojoc convertirFilaAObjecte(String fila) {
        String[] parts = fila.split("\\|", -1);
        if (parts.length < 7) {
            return null;
        }

        Videojoc joc = new Videojoc();
        joc.setId(parts[0]);
        joc.setEstat(parts[1]);
        joc.setTitol(parts[2]);
        joc.setDesenvolupador(parts[3]);
        joc.setPreu(convertirADouble(parts[4]));
        joc.setPlataformes(convertirAPlataformes(parts[5]));
        joc.setAnyLlancament(convertirAInt(parts[6]));
        return joc;
    }

    private List<String> convertirAPlataformes(String text) {
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

    private double convertirADouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return 0;
        }
    }

    private int convertirAInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    private String escaparComillaSimple(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("'", "''");
    }

    private String escaparXml(String text) {
        if (text == null) {
            return "";
        }
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
