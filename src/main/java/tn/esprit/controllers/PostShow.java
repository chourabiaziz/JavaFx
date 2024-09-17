package tn.esprit.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.Comment;
 import tn.esprit.services.ServiceComment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PostShow implements Initializable {
    @FXML
    private TextField comment;


    @FXML
    private Text description;

    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private Text heure;
    @FXML
    private Text date;

    @FXML
    private Button post;

    @FXML
    private Button qr;

    @FXML
    private Button submit;
    @FXML
    private ListView<Comment> listView;
    private List<Comment> listContrat;
    private static final int ITEMS_PER_PAGE = 5;
    private static final int TOTAL_ITEMS = 100;
    @FXML
    private Pagination pagination;
    @FXML
    private Text user;
    private int id ;
    private int champ_user;
    private String champ_description ;
    private String champ_date;
    private String champ_heure;



    private void generateQRCode(ActionEvent event) {
        String myInformation =
                " USer : Chaima dellali" +
                " Contenu :" +description.getText()

                +" le " + date.getText() + "  : " + heure.getText()
                ;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(myInformation, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", stream);
            byte[] byteArray = stream.toByteArray();
            String qrCodeImage = Base64.getEncoder().encodeToString(byteArray);
            Image image = new Image("data:image/png;base64," + qrCodeImage);
            qrCodeImageView.setImage(image);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la génération du QR Code", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void setPost(int id, int champ_user, String champ_description, String champ_date, String champ_heure) {
      this.champ_user = champ_user;
      this.champ_description = champ_description;
      this.champ_date = champ_date.toString();
      this.champ_heure = champ_heure.toString();
      this.id = id;
        updateUI();
    }




    private void updateUI() {

        description.setText(String.valueOf(champ_description));
        //user.setText(String.valueOf(champ_user));
        date.setText(champ_date);
        heure.setText(String.valueOf(champ_heure));
        listView.setCellFactory(new Callback<ListView<Comment>, ListCell<Comment>>() {
            @Override
            public ListCell<Comment> call(ListView<Comment> contratListView) {
                return new PostShow.ContratListCell();
            }
        });

        afficherContrats();



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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        post.setOnAction(this::navigate_index);
        submit.setOnAction(this::sendComment);
        qr.setOnAction(this::generateQRCode);





    }
    private void sendComment(ActionEvent event) {
        Comment p = new Comment();
        ServiceComment sp = new ServiceComment() ;
        p.setDate(  Date.valueOf(LocalDate.now()));
        p.setHeure( Time.valueOf(LocalTime.now()));
        p.setDescription(comment.getText());
        p.setUser(3);
         p.setPostid(id);




             if (comment.getText().length() == 0) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erreur");
                 alert.setHeaderText("Échec de l'ajout du commentaire");
                 alert.setContentText("Champ ne peut pas étre vide");
                 alert.showAndWait();
             } else {
                 try {
                 sp.add(p);
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Succès");
                 alert.setHeaderText("Commentaire  ajouté avec succès");
                 Optional<ButtonType> result = alert.showAndWait();

// Check if the user clicked OK
                 if (result.isPresent() && result.get() == ButtonType.OK) {
                     comment.setText("");
                     updateUI();
                 } else {
                     updateUI();

                 }

             }
        catch(Exception e){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erreur");
                 alert.setHeaderText("Échec de l'ajout du commentaire");
                 alert.setContentText("Une erreur est survenue lors de l'ajout du commentaire. Veuillez réessayer.");
                 alert.showAndWait();
             }
         }

    }




    //show comments as cards


    private class ContratListCell extends ListCell<Comment> {

        protected void updateItem(Comment facture, boolean empty) {
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

                }                Label contenu = new Label(   facture.getDescription());

                Label date = new Label("créé en " + facture.getDate());
                Label heure = new Label(" : " + facture.getHeure());

                user.setStyle("-fx-font-weight: bold;");
                date.setStyle("-fx-font-weight: bold;");
                 Button button1 = new Button("Modifier");
                Button button3 = new Button("Supprimer");
                HBox buttonBox = new HBox( button1 ,button3);
                 button1.setStyle("-fx-background-color: #6e6e6e; -fx-padding: 10px; -fx-border-radius: 50px;");
                button3.setStyle("-fx-background-color: red; -fx-padding: 10px; -fx-border-radius: 50px;");
                buttonBox.setStyle("-fx-spacing: 10px;");


                user.setMinWidth(120);
                user.setMaxWidth(120);
                contenu.setMinWidth(250);
                contenu.setMaxWidth(250);

                card.getChildren().addAll(user,contenu,date ,heure, buttonBox);
//buttons actions
                button1.setOnAction(event -> {
                     TextField contenuTextField; // A reference to the dynamically created TextField

                    contenuTextField = new TextField(contenu.getText());
                    contenuTextField.setLayoutX(contenu.getLayoutX());
                    contenuTextField.setLayoutY(contenu.getLayoutY());
                    contenuTextField.setPrefWidth(contenu.getBoundsInLocal().getWidth());
                    contenuTextField.setPrefHeight(contenu.getBoundsInLocal().getHeight());
                    if (card.getChildren().contains(contenuTextField)) {

                    }else {
                        card.getChildren().remove(contenu);
                        card.getChildren().remove(date);
                        card.getChildren().remove(heure);
                        card.getChildren().remove(buttonBox);
                        card.getChildren().add(contenuTextField);
                        card.getChildren().add(button1);
                         button1.setText("Confirmer");
                        button1.setOnAction(e -> {
                            ServiceComment sp = new ServiceComment() ;

                            Comment comment1 = sp.getById(facture.getId());
                            comment1.setDescription(contenuTextField.getText());
                            comment1.setDate(Date.valueOf(LocalDate.now()));
                            comment1.setHeure(Time.valueOf(LocalTime.now()));
                            sp.edit(comment1);
                            updateUI();


                        });

                    }





                });


                button3.setOnAction(event -> {
                    ServiceComment sp = new ServiceComment() ;
                    sp.delete(facture.getId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText("Commentaire supprimé  avec succès");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
updateUI();                     } else {
                        updateUI();
                      }                    } );
                setGraphic(card);
            }
        }}


    private void afficherContrats() {
        // Fetch all contracts
         ServiceComment serviceComment = new ServiceComment() ;
        System.out.println("this.id"+this.id);
        System.out.println(".id"+id);

        listContrat = serviceComment.getAll(this.id);
        System.out.println("list des coomm" + listContrat);
        int totalPageCount = (int) Math.ceil((double) listContrat.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(totalPageCount);

        // Set up pagination to update ListView
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * ITEMS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, listContrat.size());
            ObservableList<Comment> pageContrats = FXCollections.observableArrayList(listContrat.subList(fromIndex, toIndex));
            listView.setItems(pageContrats);
            return new VBox(); // Placeholder, the actual content is set dynamically
        });
}



}
