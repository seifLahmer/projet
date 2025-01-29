package Entite;

public class Product {
    private int produitId;
    private String nom;
    private String description;
    private double prix;
    private String typeProduit;
    private int quantiteDisponible;

    public Product(int produitId, String nom, String description, double prix, String typeProduit, int quantiteDisponible) {
        this.produitId = produitId;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.typeProduit = typeProduit;
        this.quantiteDisponible = quantiteDisponible;
    }

    public Product(String nom, String description, double prix, String typeProduit, int quantiteDisponible) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.typeProduit = typeProduit;
        this.quantiteDisponible = quantiteDisponible;
    }

    public int getProduitId() { return produitId; }
    public void setProduitId(int produitId) { this.produitId = produitId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public String getTypeProduit() { return typeProduit; }
    public void setTypeProduit(String typeProduit) { this.typeProduit = typeProduit; }

    public int getQuantiteDisponible() { return quantiteDisponible; }
    public void setQuantiteDisponible(int quantiteDisponible) { this.quantiteDisponible = quantiteDisponible; }

    @Override
    public String toString() {
        return "Produit{" +
                "produitId=" + produitId +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", typeProduit='" + typeProduit + '\'' +
                ", quantiteDisponible=" + quantiteDisponible +
                '}';
    }
}
