package tn.esprit.services;

import javafx.geometry.Pos;
import tn.esprit.interfaces.PostInterface;
import tn.esprit.models.Post;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePost implements PostInterface<Post> {

    Connection cnx = MyDataBase.getInstance().getCnx();

    @Override
    public boolean add(Post c) {

        String sql = "INSERT INTO `post`(`ID_post`, `ID_User`, `Date`, `Heure`, `Description`) VALUES ( ?, ?,?,?,?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, c.getId());
            pstmt.setInt(2, c.getUser());
            pstmt.setDate(3, c.getDate());
            pstmt.setTime(4, c.getHeure());
            pstmt.setString(5, c.getDescription());

            int res = pstmt.executeUpdate();

            if (res > 0) {
                System.out.println("Ajout effectuée avec succès !");
                return true ;
            } else {
                System.out.println("Aucune ajout effectuée ");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false ;
        }

        return false;
    }

    @Override
    public void edit(Post c) {
        String req = "UPDATE post SET  ID_User=?, Date=?, Heure=?, Description=?  WHERE ID_post=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1, c.getUser());
            pstmt.setDate(2, c.getDate());
            pstmt.setTime(3, c.getHeure());
            pstmt.setString(4, c.getDescription());
            pstmt.setInt(5, c.getId());


            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Modification effectuée avec succès !");
            } else {
                System.out.println("Vérifier l' id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM post WHERE ID_post=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();


            if (rowsAffected > 0) {
                System.out.println("Suppression effectuée avec succès !");
            } else {
                System.out.println("Aucune ligne supprimée. Vérifiez l'ID.");
            }
        } catch (SQLException e) {
            // Handle the exception more gracefully, e.g., log the error or display a user-friendly message
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post ORDER BY ID_post DESC";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("ID_post");
                int user = res.getInt("ID_User");
                Date date = res.getDate("Date");

                Time heure = res.getTime("Heure");

                String description = res.getString("Description");

                 Post c = new Post(id, user, date ,heure, description  );
                posts.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;

    }

    @Override
    public Post getById(int id) {
        String req = "SELECT * FROM post WHERE ID_post = ?";
        Post post = null;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Set the value of the id parameter
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                 int user = res.getInt("ID_User");
                Date date = res.getDate("Date");

                Time heure = res.getTime("Heure");

                String description = res.getString("Description");

                 post = new Post(id, user, date ,heure, description  );
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;

    }


}
