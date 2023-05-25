
package imat;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.User;

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
    AnchorPane checkout;
    @FXML
    FlowPane flowCategories;
    @FXML
    AnchorPane paneProducts;
    @FXML
    FlowPane productFlow;
    @FXML
    Label labelProductCategory;

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
    }

    @FXML
    public void openHelp() {
        paneHelp.toFront();
        closeCheckout();
    }

    @FXML
    public void openCategories() {
        refreshCategories();
        paneCategories.toFront();
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
        labelProductCategory.setText(CategoryCard.convertToText(category));
        paneProducts.toFront();
    }

    @FXML
    public void openAccount() {
        paneAccount.toFront();
    }

    @FXML
    public void closeAccount() {
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
    public void closeCheckout() {
        checkout.toBack();
    }

    @FXML
    public void mouseTrap(Event e) {
        e.consume();
    }
}