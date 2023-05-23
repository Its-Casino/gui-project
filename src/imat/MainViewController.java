
package imat;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
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
    FlowPane flowCategories;

    User currentUser;

    Map<ProductCategory, AnchorPane> categoryPanes = new HashMap<>();

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        populateProductPageMap();
        openStart();
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
    }

    @FXML
    public void openCategories() {
        refreshCategories();
        paneCategories.toFront();
    }

    private void refreshCategories() {
        flowCategories.getChildren().clear();
        if (categoryPanes.size() == 0) {
            for (ProductCategory category : ProductCategory.values()) {
                System.out.println(category.name());
                categoryPanes.put(category, new CategoryCard(category, this));
            }
        }
        flowCategories.getChildren().addAll(categoryPanes.values());
        System.out.println(flowCategories.getChildren());
    }
}