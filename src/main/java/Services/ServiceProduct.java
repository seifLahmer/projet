package Services;

import Entite.Product;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public abstract class ServiceProduct implements IService<Product> {
        private Connection conn = DataSource.getInstance().getCon();
        private Statement stat;

        public ServiceProduct() {
            try {
                stat = conn.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void ajouter(Product product) throws SQLException {
            String query;
            query = "INSERT INTO Product (nom, description, prix, typeProduit, quantiteDisponible) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setString(1, product.getNom());
            pre.setString(2, product.getDescription());
            pre.setDouble(3, product.getPrix());
            pre.setString(4, product.getTypeProduit());
            pre.setInt(5, product.getQuantiteDisponible());

            pre.executeUpdate();
            System.out.println("Produit ajouté avec succès !");
        }

        @Override
        public void supprimer(Product produit) throws SQLException {
            String query = "DELETE FROM Produit WHERE produitId = ?";
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, produit.getProduitId());

            pre.executeUpdate();
            System.out.println("Produit supprimé avec succès !");
        }

        @Override
        public void update(Product produit) throws SQLException {
            String query = "UPDATE Produit SET nom = ?, description = ?, prix = ?, typeProduit = ?, quantiteDisponible = ? WHERE produitId = ?";
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setString(1, produit.getNom());
            pre.setString(2, produit.getDescription());
            pre.setDouble(3, produit.getPrix());
            pre.setString(4, produit.getTypeProduit());
            pre.setInt(5, produit.getQuantiteDisponible());
            pre.setInt(6, produit.getProduitId());

            pre.executeUpdate();
            System.out.println("Produit mis à jour avec succès !");
        }

        @Override
        public List<Product> getAll() throws SQLException {
            List<Product> list = new ArrayList<>();
            String query = "SELECT * FROM Product";
            ResultSet rs = stat.executeQuery(query);

            while (rs.next()) {
                Product produit = new Product(
                        rs.getInt("produitId"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("typeProduit"),
                        rs.getInt("quantiteDisponible")
                );
                list.add(produit);
            }
            return list;
        }

        @Override
        public Product getById(int id) throws SQLException {
            String query = "SELECT * FROM Product WHERE produitId = ?";
            PreparedStatement pre = conn.prepareStatement(query);
            pre.setInt(1, id);

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("produitId"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("typeProduit"),
                        rs.getInt("quantiteDisponible")
                );
            }
            return null;
        }
    }
