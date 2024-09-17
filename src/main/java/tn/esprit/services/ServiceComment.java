package tn.esprit.services;

import tn.esprit.interfaces.CommentInterface;
import tn.esprit.interfaces.PostInterface;
import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceComment implements CommentInterface<Comment> {

    Connection cnx = MyDataBase.getInstance().getCnx();

    @Override
    public boolean add(Comment c) {

        String sql = "INSERT INTO `commentaire`(`ID_Commentaire`, `ID_User`, `Date`, `Heure`, `Description` , `idpost`) VALUES ( ?, ?,?,?,?,?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, c.getId());
            pstmt.setInt(2, c.getUser());
            pstmt.setDate(3, c.getDate());
            pstmt.setTime(4, c.getHeure());
            pstmt.setString(5, c.getDescription());
            pstmt.setInt(6, c.getPostid());

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
    public void edit(Comment c) {
        String req = "UPDATE commentaire SET  ID_User=?, Date=?, Heure=?, Description=?  WHERE ID_Commentaire=?";
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
        String req = "DELETE FROM commentaire WHERE `ID_Commentaire`=?";
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
    public List<Comment> getAll(int xx) {
        List<Comment> posts = new ArrayList<>();
        String req = "SELECT * FROM commentaire WHERE idpost = ? ORDER BY ID_Commentaire DESC";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
                 pstmt.setInt(1, xx);
             System.out.println(req);

            try (ResultSet res = pstmt.executeQuery()) {
                System.out.println(res);
                while (res.next()) {
                     xx = res.getInt("idpost");
                    int id = res.getInt("ID_Commentaire");
                    int user = res.getInt("ID_User");
                    Date date = res.getDate("Date");
                    Time heure = res.getTime("Heure");
                    String description = res.getString("Description");

                    // Create the Comment object, ensuring that no NullPointerExceptions occur
                    Comment c = new Comment(id, user, date, heure, description, xx);
                    posts.add(c);
                }
            }
        } catch (SQLException e) {
            // Consider using a logger here instead of printStackTrace
            e.printStackTrace();
        }

        return posts;
    }

    @Override
    public Comment getById(int id) {
        String req = "SELECT * FROM commentaire WHERE ID_Commentaire = ?";
        Comment post = null;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Set the value of the id parameter
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                int user = res.getInt("ID_User");
                int idpost = res.getInt("idpost");
                Date date = res.getDate("Date");

                Time heure = res.getTime("Heure");

                String description = res.getString("Description");

                post = new Comment(id, user, date ,heure, description,idpost  );
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;

    }


}
