package Entite;

import java.util.Date;

public class Payment {
    private int paymentId;
    private int memberId;
    private double amount;
    private Date paymentDate;
    private String paymentStatus;
    private int adminId;
    private int abonnementId;


    // Constructeur complet
    public Payment(int paymentId, int memberId, double amount, Date paymentDate, String paymentStatus, int adminId) {
        this.paymentId = paymentId;
        this.memberId = memberId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.adminId = adminId;
        this.abonnementId = 0;

    }

    // Constructeur vide (utile pour certaines opérations)
    public Payment(int paymentId, int memberId, double amount, Date paymentDate, String paymentStatus, int abonnementId, int adminId) {
    }

    // Getters et setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void  abonnementId (int abonnementId){
        this.abonnementId = abonnementId;

    }
    public int getAbonnementId() {
        return abonnementId;

    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    // Méthode toString pour afficher les informations
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", memberId=" + memberId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", adminId=" + adminId +
                ", abonnementId=" + abonnementId +

                '}';
    }
}
