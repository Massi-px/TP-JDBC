package org.example;

import java.sql.*;
import java.util.ArrayList;

public class DAOAcces {
    private Connection conn;
    private Statement stAccesStatement;
    public DAOAcces() {
        try {
            conn = Database.getInstance().getConnection();
            stAccesStatement = conn.createStatement();
        }
        catch (SQLException e) {
            System.err.println("Erreur lors de la connexion !");
            e.printStackTrace();
        }
    }

    public ArrayList<Acces> listAcces() {
        ArrayList<Acces> accesList = new ArrayList<>();
        String strQuery = "SELECT * FROM Acces";
        try (PreparedStatement stAccesStatement = conn.prepareStatement(strQuery);
             ResultSet rsAccesStatement = stAccesStatement.executeQuery()) {
            while (rsAccesStatement.next()) {
                Acces acces = new Acces(
                        rsAccesStatement.getInt("id"),
                        rsAccesStatement.getString("prenom"),
                        rsAccesStatement.getString("login"),
                        rsAccesStatement.getString("password"),
                        rsAccesStatement.getString("statut"),
                        rsAccesStatement.getInt("age")
                );
                accesList.add(acces);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête !");
            e.printStackTrace();
        }
        return accesList;
    }

    public void ajouterDAO(Acces a) {
        String sql = "INSERT INTO Acces (prenom, login, password, statut, age) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stAddUser = conn.prepareStatement(sql)) {
            stAddUser.setString(1, a.getPrenom());
            stAddUser.setString(2, a.getLogin());
            stAddUser.setString(3, a.getPassword());
            stAddUser.setString(4, a.getStatut());
            stAddUser.setInt(5, a.getAge());
            stAddUser.executeUpdate();
            System.out.println("Acces ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête !");
            e.printStackTrace();
        }
    }

    public void supprimerDAO(Acces a) {
        String sql = "DELETE FROM Acces WHERE id = ?";
        try (PreparedStatement stDeleteUser = conn.prepareStatement(sql)) {
            stDeleteUser.setInt(1, a.getId());
            int rowsAffected = stDeleteUser.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucun utilisateur supprimé");
            } else {
                System.out.println("Utilisateur supprimé");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête !");
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion !");
            e.printStackTrace();
        }
    }

}
