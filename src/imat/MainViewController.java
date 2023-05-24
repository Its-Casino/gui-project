
package imat;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.User;

import javax.swing.text.html.ImageView;

public class MainViewController implements Initializable {

    @FXML
    AnchorPane paneLoggedIn;
    @FXML
    AnchorPane paneLoggedOut;
    @FXML
    AnchorPane paneHelp;
    @FXML
    AnchorPane paneCategories;
    @FXML
    AnchorPane paneAccount;
    @FXML
    FlowPane flowCategories;
    @FXML
    AnchorPane paneProducts;
    @FXML
    FlowPane productFlow;
    @FXML
    AnchorPane paneShoppingCart;
    @FXML
    AnchorPane paneCheckOut;
    @FXML
    AnchorPane anchorPaneStart;
    @FXML
    AnchorPane anchorPaneCategory;
    @FXML
    AnchorPane anchorPaneLists;
    @FXML
    AnchorPane anchorPaneAbout;

    @FXML
    AnchorPane paneShoppingHistory;
    @FXML
    AnchorPane paneDeliveryInfo;
    @FXML
    ImageView helpArrow;
    User currentUser;

    Map<ProductCategory, AnchorPane> categoryPanes = new HashMap<>();

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        refreshCategories();
        openStart();
        for (ProductCategory cat : ProductCategory.values()) {
            System.out.println(cat.name());
        }
    }

    public void goLanding(Boolean loggedIn) {
        if (loggedIn) {
            paneLoggedIn.toFront();
            return;
        }
        paneLoggedOut.toFront();
        return;
    }

    @FXML
    public void openStart() {
        goLanding(currentUser != null);
        anchorPaneStart.toFront();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
        paneAccount.toBack();
        paneShoppingCart.toBack();
    }

    @FXML
    public void openHelp() {
        paneHelp.toFront();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
        paneAccount.toBack();
        paneShoppingCart.toBack();
    }

    @FXML
    public void openCategories() {
        refreshCategories();
        paneCategories.toFront();
        anchorPaneStart.toBack();
        anchorPaneCategory.toFront();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
        paneAccount.toBack();
        paneShoppingCart.toBack();
    }

    private void refreshCategories() {
        flowCategories.getChildren().clear();
        for (ProductCategory category : ProductCategory.values()) {
            if (!categoryPanes.containsKey(category)) {
                categoryPanes.put(category, new CategoryCard(category, this));
            }
        }
        flowCategories.getChildren().addAll(categoryPanes.values());
    }

    @FXML
    public void openProducts(ProductCategory category) {
        productFlow.getChildren().clear();
        for (Product product : iMatDataHandler.getProducts(category)) {
            productFlow.getChildren().add(new ProductCard(product, this));
        }
        paneProducts.toFront();
    }

    @FXML
    public void openAccount() {
        paneAccount.toFront();
        paneShoppingCart.toBack();
    }

    @FXML
    public void closeAccount() {
        paneAccount.toBack();
    }

    @FXML
    public void openShoppingCart(){
        paneShoppingCart.toFront();
        paneAccount.toBack();
    }
    @FXML
    public void closeShoppingCart(){
        paneShoppingCart.toBack();
    }

    @FXML
    public void openCheckOut(){
        paneCheckOut.toFront();
    }
    @FXML
    public void closeCheckOut(){
        paneCheckOut.toBack();
        paneShoppingCart.toBack();
    }
    @FXML
    public void openShoppingHistory(){
        paneShoppingHistory.toFront();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
        paneAccount.toBack();
    }
    @FXML
    public void openDeliveryInfo(){
        paneDeliveryInfo.toFront();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
        paneAccount.toBack();
    }


    @FXML
    public void logIn() {
        currentUser = new User();
        currentUser.setUserName("Rune");
        currentUser.setPassword("123");
        openStart();
    }

    @FXML
    public void mouseTrap(Event e) {
        e.consume();
    }
}