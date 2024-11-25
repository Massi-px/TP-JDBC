package org.example;
import java.sql.*;
import java.util.Scanner;
public class Update {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {

            System.out.print("Entrez l'id de l'utilisateur que vous voulez modifier: ");

            String id = scanner.nextLine();

            System.out.print("Entrez le statut que vous voulez modifier: ");

            Connection connection = Database.getInstance().getConnection();

            PreparedStatement stDeleteUser = connection.prepareStatement("UPDATE Acces SET  Statut = ? WHERE id = ?");
            stDeleteUser.setString(1, id);
            stDeleteUser.setString(1, id);
            int rowsAffected = stDeleteUser.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucun utilisateur modifié");
            }
            else{
                System.out.print("Statut de l'utilisateur modifié");
            }
            connection.close();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution de la requête !");
            e.printStackTrace();
        }
    }
}
