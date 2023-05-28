package imat;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    @FXML
    ImageView imageFavorite;

    MainViewController parentController;
    Product product;
    Boolean favorite = false;
    Double count = 0d;

    public ProductCard(Product product, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card_new.fxml"));
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
        if (this.parentController.iMatDataHandler.favorites().contains(this.product)) {
            favorite = true;
        }
        if (favorite) {
            Image image;
            image = new Image(getClass().getResource("resources/like_filled.png").toString());
            imageFavorite.setImage(image);
        } else {
            Image image;
            image = new Image(getClass().getResource("resources/like_hollow.png").toString());
            imageFavorite.setImage(image);
        }
        this.productName.setText(product.getName());
        this.productPrice.setText(product.getPrice() + " kr");
        this.productImage.setImage(parentController.iMatDataHandler.getFXImage(product));

        update();
        this.parentController.iMatDataHandler.getShoppingCart().addShoppingCartListener(evt -> {
            update();
        });
    }

    public void update() {
        for (ShoppingItem sItem : parentController.iMatDataHandler.getShoppingCart().getItems()) {
            if (sItem.getProduct() == product) {
                count = sItem.getAmount();
            }
        }
        this.productCount.setText(((Integer) count.intValue()).toString());
    }

    @FXML
    public void addCurrentToCart() {
        parentController.iMatDataHandler.getShoppingCart().addProduct(product);
        this.productCount.setText(((Integer) count.intValue()).toString());
    }

    @FXML
    public void removeCurrentFromCart() {
        if (count > 0) {
            parentController.iMatDataHandler.getShoppingCart().removeProduct(product);
            count--;
        }
        if (count > 0) {
            parentController.iMatDataHandler.getShoppingCart().addProduct(product, count);
        }
        this.productCount.setText(((Integer) count.intValue()).toString());
    }

    @FXML
    public void toggleFavorite() {
        if (!favorite) {
            parentController.iMatDataHandler.addFavorite(product);
            parentController.hasFavorites.toFront();
        } else {
            System.out.println("PING");
            parentController.iMatDataHandler.removeFavorite(product);
            if (parentController.iMatDataHandler.favorites().size() == 0) {
                parentController.hasFavorites.toBack();
            }
            parentController.populateFavorites();
        }
        favorite = !favorite;
        if (favorite) {
            Image image;
            image = new Image(getClass().getResource("resources/like_filled.png").toString());
            imageFavorite.setImage(image);
        } else {
            Image image;
            image = new Image(getClass().getResource("resources/like_hollow.png").toString());
            imageFavorite.setImage(image);
        }
        parentController.refreshOffers();
    }
}
