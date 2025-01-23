package Test;

import Entite.Reservation;
import Services.ServiceReservation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) throws SQLException {
        ServiceReservation sr  = new ServiceReservation();
        Reservation r1 = new Reservation(1,1,new Date(2025-1900, 8,15));
        Reservation r2 = new Reservation(1,1,new Date(2025-1900, 9,15));
        Reservation r3 = new Reservation(1,1,new Date(2025-1900, 10,15));
        sr.ajouter(r1);
        sr.ajouter(r2);
        sr.ajouter(r3);
        sr.supprimer(r3);

        System.out.println(sr.getById(2));


    }
}
