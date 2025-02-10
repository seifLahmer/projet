package Entite;

import java.util.Date;
import java.util.Objects;

public class Payment {
    private int paymentId;
    private int memberId;
    private double amount;
    private Date paymentDate;
    private String paymentStatus;
    private int abonnementId ;

    // Constructeur complet
    public Payment(int paymentId, double amount, Date paymentDate, String paymentStatus) {
        this.paymentId = paymentId;
        this.memberId = memberId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.abonnementId = abonnementId;
    }

    public Payment(int memberId, double amount, Date paymentDate, String paymentStatus, int abonnementId) {
        this.memberId = memberId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.abonnementId = abonnementId;
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

    public int getAbonnementId() {
        return abonnementId;
    }

    public void setAbonnementId(int abonnementId) {}

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", memberId=" + memberId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", abonnementId=" + abonnementId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return paymentId == payment.paymentId && memberId == payment.memberId && Double.compare(amount, payment.amount) == 0 && abonnementId == payment.abonnementId && Objects.equals(paymentDate, payment.paymentDate) && Objects.equals(paymentStatus, payment.paymentStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, memberId, amount, paymentDate, paymentStatus, abonnementId);
    }
}
