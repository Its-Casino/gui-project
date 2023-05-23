package imat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

public class ProductCard extends AnchorPane {
    @FXML
    Label productName;
    @FXML
    Label productPrice;
    @FXML
    ImageView productImage;

    MainViewController parentController;
    Product product;

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

    }

    @FXML
    public void demoThing() {
        parentController.iMatDataHandler.getShoppingCart().addProduct(product);
        System.out.println(parentController.iMatDataHandler.getShoppingCart().getTotal());
    }

}
