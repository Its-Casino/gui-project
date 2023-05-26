package imat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartCard extends AnchorPane {
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

    public CartCard(ShoppingItem sItem, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("shopping-cart-card_new.fxml"));
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
        this.labelProductCost.setText(sItem.getTotal() + " kr");
        this.imageItem.setImage(parentController.iMatDataHandler.getFXImage(product));
        update();
    }

    public void update() {
        this.labelProductCount.setText(((Double) sItem.getAmount()).toString());
        this.labelProductCost.setText(sItem.getTotal() + " kr");
    }

    @FXML
    public void increaseItem() {
        parentController.iMatDataHandler.getShoppingCart().addProduct(product);
        update();
    }

    @FXML
    public void decreaseItem() {
        for (ShoppingItem shoppingItem : parentController.iMatDataHandler.getShoppingCart().getItems()) {
            if (shoppingItem.getProduct() == product) {
                shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            }
        }
        parentController.iMatDataHandler.getShoppingCart().fireShoppingCartChanged(sItem, true);
        update();
    }

    @FXML
    public void removeItem() {
        parentController.iMatDataHandler.getShoppingCart().removeProduct(product);
        update();
        parentController.openCart();
    }
}
