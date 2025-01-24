package Entite;
import java.util.Date;


public class Equipment {
    private int equipementID;
    private String equipementName;
    private String category;
    private int quantity;
    private Date achatDate;
    private Date lastMaintenanceDate;
    private String etat;

    public Equipment(int equipementID, String equipementName, String category, int quantity, Date achatDate, Date lastMaintenanceDate, String etat) {
        this.equipementID = equipementID;
        this.equipementName = equipementName;
        this.category = category;
        this.quantity = quantity;
        this.achatDate = achatDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.etat = etat;
    }

    public int getEquipementID() {
        return equipementID;
    }

    public void setEquipementID(int equipementID) {
        this.equipementID = equipementID;
    }

    public String getEquipementName() {
        return equipementName;
    }

    public void setEquipementName(String equipementName) {
        this.equipementName = equipementName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getAchatDate() {
        return achatDate;
    }

    public void setAchatDate(Date achatDate) {
        this.achatDate = achatDate;
    }

    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipementID=" + equipementID +
                ", equipementName='" + equipementName + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", achatDate=" + achatDate +
                ", lastMaintenanceDate=" + lastMaintenanceDate +
                ", etat='" + etat + '\'' +
                '}';
    }
}

