package imat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ProductCard extends AnchorPane {
    @FXML
    Label productName;
    @FXML
    Label productPrice;
    @FXML
    Label productCount;
    @FXML
    ImageView productImage;

    MainViewController parentController;
    Product product;
    Double count = 0d;

    public ProductCard(Product product, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.product = product;
        this.parentController = parentController;
        this.productName.setText(product.getName());
        this.productPrice.setText(product.getPrice() + " kr");
        this.productImage.setImage(parentController.iMatDataHandler.getFXImage(product));
        update();
    }

    public void update() {
        for (ShoppingItem sItem : parentController.iMatDataHandler.getShoppingCart().getItems()) {
            if (sItem.getProduct() == product) {
                count = sItem.getAmount();
            }
        }
        this.productCount.setText(count.toString());
    }

    @FXML
    public void addCurrentToCart() {
        parentController.iMatDataHandler.getShoppingCart().addProduct(product);
        update();
    }

    @FXML
    public void removeCurrentFromCart() {
        for (ShoppingItem sItem : parentController.iMatDataHandler.getShoppingCart().getItems()) {
            if (sItem.getProduct() == product) {
                sItem.setAmount(sItem.getAmount() - 1);
            }
        }
        update();
    }
}
