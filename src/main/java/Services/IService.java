package Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Date ;
import java.util.Map ;

public interface IService <T>{
    void Ajouter(T objet) throws SQLException;
    void Supprimer(T objet) throws SQLException;
    void Update(T objet, Map<String,Object> data) throws SQLException;

    List<T> findAll() throws SQLException;
    T findById(int Memberid) throws SQLException;

}
