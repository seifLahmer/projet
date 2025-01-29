package Services;

import Entite.Payment;
import Entite.Product;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IService<T> {
    void ajouter(T t) throws SQLException;
    void supprimer (T t ) throws SQLException;
    void update (T t ) throws SQLException ;


    List<T> getAll () throws SQLException ;
    T getById (int id ) throws SQLException ;
}
