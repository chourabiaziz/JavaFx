package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private Button admin;

    @FXML
    private Button client;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin.setOnAction(this::navigate_index);
        client.setOnAction(this::navigate_index_client);
    }


    private void navigate_index(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostIndex.fxml"));
        Parent root = null;
        try {
            Node source = (Node) event.getSource();
            root = loader.load();
            System.out.println("FXML file loaded successfully.");
            PostIndex controller = loader.getController();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle("Ajouter un statut");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void navigate_index_client(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostIndexClient.fxml"));
        Parent root = null;
        try {
            Node source = (Node) event.getSource();
            root = loader.load();
            System.out.println("FXML file loaded successfully.");
            PostIndexClient controller = loader.getController();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle("bienvenue chére client ");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
