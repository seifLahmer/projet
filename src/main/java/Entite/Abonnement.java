package Entite;


import java.time.LocalDate;
import java.util.Date;

public class Abonnement {
    private int abonnementId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String status;
    private int memberId;

    // Constructeur complet
    public Abonnement(int abonnementId , LocalDate startDate, LocalDate endDate, double price, String status, int memberId) {
        this.abonnementId = abonnementId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
        this.memberId = memberId;
    }

    // Constructeur vide
    public Abonnement() {
    }

    public static void add(Abonnement abonnement) {
    }

    // Getters et setters
    public int getAbonnementId() {
        return abonnementId;
    }

    public void setAbonnementId(int abonnementId) {
        this.abonnementId = abonnementId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    // Méthode toString pour afficher les informations
    @Override
    public String toString() {
        return "Abonnement{" +
                "abonnementId=" + abonnementId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", memberId=" + memberId +
                '}';
    }
}
