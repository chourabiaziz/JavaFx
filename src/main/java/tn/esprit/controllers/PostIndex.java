package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.Post;
import tn.esprit.services.ServicePost;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PostIndex implements Initializable {



    @FXML
    private Button add;
    @FXML
    private Button dash;

    @FXML
    private ListView<Post> listView;

    @FXML
    private TextField search;

    private ServicePost factureService = new ServicePost() ;
    private List<Post> listContrat;
    private static final int ITEMS_PER_PAGE = 5;
    private static final int TOTAL_ITEMS = 100;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add.setOnAction(this::navigate_add);
        dash.setOnAction(this::navigate_dash);
        listContrat = new ArrayList<>();

        listView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> contratListView) {
                return new ContratListCell();
            }
        });

        afficherContrats();

    }
    private void navigate_dash(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        Parent root = null;
        try {
            Node source = (Node) event.getSource();
            root = loader.load();
            System.out.println("FXML file loaded successfully.");
            Dashboard controller = loader.getController();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle("Ajouter un statut");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void search(javafx.scene.input.KeyEvent keyEvent) {
        String searched = search.getText().trim().toLowerCase();
        if (!searched.isEmpty()) {
            List<Post> filteredContrats = listContrat.stream().filter(x ->
                            String.valueOf(x.getUser()).toLowerCase().contains(searched) ||
                                    String.valueOf(x.getDescription()).contains(searched)
                    )
                    .collect(Collectors.toList());
            listView.getItems().clear();
            listView.getItems().addAll(filteredContrats);
        } else {
            afficherContrats();
        }
    }

    private class ContratListCell extends ListCell<Post> {

    protected void updateItem(Post facture, boolean empty) {
        super.updateItem(facture, empty);

        if (empty || facture == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Create an HBox to hold the contract details
            HBox card = new HBox();
            card.setStyle("-fx-alignment: center ;-fx-background-color: #6d89ef; -fx-padding: 10px; -fx-spacing: 10px;");

            Label user = new Label();
            if (facture.getUser() == 3) {
                user.setText("User : ADMINISTRATEUR" );

            }else {
                user.setText("User : CLIENT" );

            }            Label contenu = new Label(   facture.getDescription());

            Label date = new Label("créé en " + facture.getDate());
            Label heure = new Label(" : " + facture.getHeure());

            user.setStyle("-fx-font-weight: bold;");
            date.setStyle("-fx-font-weight: bold;");
            Button button0 = new Button("Consulter");
            Button button1 = new Button("Modifier");
            Button button3 = new Button("Supprimer");
            HBox buttonBox = new HBox(button0 , button1 ,button3);
            button0.setStyle("-fx-background-color: #cacaca; -fx-padding: 10px; -fx-border-radius: 50px;");
            button1.setStyle("-fx-background-color: #6e6e6e; -fx-padding: 10px; -fx-border-radius: 50px;");
            button3.setStyle("-fx-background-color: red; -fx-padding: 10px; -fx-border-radius: 50px;");
            buttonBox.setStyle("-fx-spacing: 10px;");
            user.setMinWidth(120);
            user.setMaxWidth(120);
            contenu.setMinWidth(370);
            contenu.setMaxWidth(370);

            card.getChildren().addAll(user,contenu,date ,heure, buttonBox);
//buttons actions
            button1.setOnAction(event -> {

                ServicePost sp = new ServicePost() ;

                int id = facture.getId() ;
                String description1 = facture.getDescription() ;
                String date1 = facture.getDate().toString() ;
                String heure1 = facture.getHeure().toString() ;
                int user1= facture.getUser();

                edit(event, id ,user1,description1, date1,heure1);
            });

            button0.setOnAction(event -> {
                    int id = facture.getId() ;
                    String description1 = facture.getDescription() ;
                String date1 = facture.getDate().toString() ;
                String heure1 = facture.getHeure().toString() ;
                    int user1= facture.getUser();

                   show(event, id ,user1,description1, date1,heure1);
            });
            button3.setOnAction(event -> {
                ServicePost sp = new ServicePost() ;
                    sp.delete(facture.getId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText("Statut supprimé  avec succès");
                    Optional<ButtonType> result = alert.showAndWait();

                     if (result.isPresent() && result.get() == ButtonType.OK) {
                      afficherContrats(); ;
                    } else {
                         afficherContrats();}                    } );
            setGraphic(card);
        }
    }}


    public void show(ActionEvent event , int id , int user0 , String description0, String date0 , String heure0) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostShow.fxml"));
        Parent root = null;
        try {
            Node source = (Node) event.getSource();
            root = loader.load();
            System.out.println("FXML file loaded successfully.");
            PostShow controller = loader.getController();
            System.out.println("from index to show id = " +id);
            controller.setPost(id , user0, description0 , date0 , heure0);
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle("facture");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void edit(ActionEvent event , int id , int user0 , String description0, String date0 , String heure0) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostEdit.fxml"));
        Parent root = null;
        try {
            Node source = (Node) event.getSource();
            root = loader.load();
            System.out.println("FXML file loaded successfully.");
            PostEdit controller = loader.getController();
            controller.setPost(id , user0, description0 , date0 , heure0);
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle("Modifier ");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private Pagination pagination;
private void afficherContrats() {
    // Fetch all contracts
    listContrat = factureService.getAll();
    int totalPageCount = (int) Math.ceil((double) listContrat.size() / ITEMS_PER_PAGE);
    pagination.setPageCount(totalPageCount);
    System.out.println("list des ppp" + listContrat);

    // Set up pagination to update ListView
    pagination.setPageFactory(pageIndex -> {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, listContrat.size());
        ObservableList<Post> pageContrats = FXCollections.observableArrayList(listContrat.subList(fromIndex, toIndex));
        listView.setItems(pageContrats);
        return new VBox(); // Placeholder, the actual content is set dynamically
    });
}




    private void navigate_add(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostAdd.fxml"));
        Parent root = null;
        try {
            Node source = (Node) event.getSource();
            root = loader.load();
            System.out.println("FXML file loaded successfully.");
            PostAdd controller = loader.getController();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle("Ajouter un statut");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
