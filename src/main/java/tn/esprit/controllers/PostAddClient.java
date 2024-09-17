package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Post;
import tn.esprit.services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class PostAddClient implements Initializable {
    @FXML
    private Button submit;
    @FXML
    private Button post;
    @FXML
    private TextField description;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        post.setOnAction(this::navigate_index);
        submit.setOnAction(this::send);

    }

    private void send(ActionEvent event) {
        Post p = new Post();
        ServicePost sp = new ServicePost() ;
        p.setDate(  Date.valueOf(LocalDate.now()));
        p.setHeure( Time.valueOf(LocalTime.now()));
        p.setDescription(description.getText());
        p.setUser(1);
        try {
            if (description.getText().length() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de l'ajout du commentaire");
                alert.setContentText("Champ ne peut pas étre vide");
                alert.showAndWait();
            }else {
                sp.add(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Statut ajouté avec succès");
                Optional<ButtonType> result = alert.showAndWait();

// Check if the user clicked OK
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    navigate_index(event);
                } else {
                    navigate_index(event);


                }

            }

        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Échec de l'ajout du statut");
            alert.setContentText("Une erreur est survenue lors de l'ajout du contrat. Veuillez réessayer.");
            alert.showAndWait();
        }
    }






    private void navigate_index(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostIndexClient.fxml"));
        Parent root = null;
        try {
            Node source = (Node) event.getSource();
            root = loader.load();
            System.out.println("FXML file loaded successfully.");
            PostIndexClient controller = loader.getController();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle("Ajouter un statut");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
