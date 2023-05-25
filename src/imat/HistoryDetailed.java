package imat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class HistoryDetailed extends AnchorPane {
    @FXML
    Label labelProductName;
    @FXML
    Label labelProductCost;
    @FXML
    Label labelProductCount;
    @FXML
    ImageView imageItem;

    MainViewController parentController;
    Product product;
    ShoppingItem sItem;
    Double count = 0d;

    public HistoryDetailed(ShoppingItem sItem, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history_detailed.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.product = sItem.getProduct();
        this.sItem = sItem;
        this.parentController = parentController;
        this.labelProductName.setText(product.getName());
        this.labelProductCost.setText("Pris: " + sItem.getTotal() + " kr");
        this.imageItem.setImage(parentController.iMatDataHandler.getFXImage(product));
        update();
    }

    public void update() {
        this.labelProductCount.setText("Antal köpt: " + ((Double) sItem.getAmount()).toString());
        this.labelProductCost.setText("Pris: " + sItem.getTotal() + " kr");
    }
}
