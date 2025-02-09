package Entite;

import javafx.beans.property.*;
import java.util.Date;

public class Maintenance {

    private IntegerProperty maintenanceId;       // MaintenanceId (clé primaire)
    private IntegerProperty equipementId;        // EquipementId (clé étrangère)
    private ObjectProperty<Date> maintenanceDate; // MaintenanceDate
    private StringProperty description;          // Description de la maintenance
    private DoubleProperty cout;                 // Cout de la maintenance
    private StringProperty effectuePar;          // EffectuéPar (nom du technicien)

    // Constructor
    public Maintenance(int maintenanceId, int equipementId, Date maintenanceDate, String description, double cout, String effectuePar) {
        this.maintenanceId = new SimpleIntegerProperty(maintenanceId);
        this.equipementId = new SimpleIntegerProperty(equipementId);
        this.maintenanceDate = new SimpleObjectProperty<>(maintenanceDate);
        this.description = new SimpleStringProperty(description);
        this.cout = new SimpleDoubleProperty(cout);
        this.effectuePar = new SimpleStringProperty(effectuePar);
    }

    // Getters and Setters
    public int getMaintenanceID() {
        return maintenanceId.get();
    }

    public void setMaintenanceID(int maintenanceID) {
        this.maintenanceId.set(maintenanceID);
    }

    public IntegerProperty maintenanceIdProperty() {
        return maintenanceId;
    }

    public int getEquipementId() {
        return equipementId.get();
    }

    public void setEquipementId(int equipementId) {
        this.equipementId.set(equipementId);
    }

    public IntegerProperty equipementIdProperty() {
        return equipementId;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate.get();
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate.set(maintenanceDate);
    }

    public ObjectProperty<Date> maintenanceDateProperty() {
        return maintenanceDate;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public double getCout() {
        return cout.get();
    }

    public void setCout(double cout) {
        this.cout.set(cout);
    }

    public DoubleProperty coutProperty() {
        return cout;
    }

    public String getEffectuePar() {
        return effectuePar.get();
    }

    public void setEffectuePar(String effectuePar) {
        this.effectuePar.set(effectuePar);
    }

    public StringProperty effectueParProperty() {
        return effectuePar;
    }

    // toString for easy display
    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceId=" + maintenanceId.get() +
                ", equipementId=" + equipementId.get() +
                ", maintenanceDate=" + maintenanceDate.get() +
                ", description='" + description.get() + '\'' +
                ", cout=" + cout.get() +
                ", effectuePar='" + effectuePar.get() + '\'' +
                '}';
    }

    public int getEquipement() {
        return equipementId.get();
    }


}
