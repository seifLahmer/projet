package Entite;
import java.sql.Date;

public class Maintenance {
    private int maintenanceId;
    private int equipementId;
    private Date maintenanceDate;

    // Constructor
    public Maintenance(int maintenanceId, int equipementId, Date maintenanceDate) {
        this.maintenanceId = maintenanceId;
        this.equipementId = equipementId;
        this.maintenanceDate = maintenanceDate;
    }

    // Getters and Setters
    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public int getEquipementId() {
        return equipementId;
    }

    public void setEquipementId(int equipementId) {
        this.equipementId = equipementId;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    @Override
    public String toString() {
        return "Maintenance [MaintenanceId=" + maintenanceId + ", EquipementId=" + equipementId + ", MaintenanceDate=" + maintenanceDate + "]";
    }
}

