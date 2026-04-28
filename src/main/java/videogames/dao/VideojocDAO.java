package videogames.dao;

import java.util.List;
import videogames.model.Videojoc;

public interface VideojocDAO {

    List<Videojoc> llistarCataleg() throws Exception;

    Videojoc cercarPerId(String id) throws Exception;

    void altaVideojoc(Videojoc joc) throws Exception;

    void afegirPlataforma(String id, String plataforma) throws Exception;

    void modificarPreu(String id, double preuNou) throws Exception;

    void modificarEstat(String id, String estatNou) throws Exception;

    void baixaVideojoc(String id) throws Exception;
}
