package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.util.HashMap;

public class ProductPane extends AnchorPane {
    @FXML
    FlowPane flowProducts;

    HashMap<Product, ProductCard> productCards = new HashMap<>();

    public ProductPane(ProductCategory category, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_product_page.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        // TODO Auto-generated constructor stub
        refreshProductList(category);
        loadFromMap();
    }

    private void refreshProductList(ProductCategory category) {
        for (Product product: IMatDataHandler.getInstance().getProducts(category)) {
            ProductCard card = new ProductCard(product);
            productCards.put(product, card);
        }
    }

    private void loadFromMap() {
        for (Product product: productCards.keySet()) {
            flowProducts.getChildren().add(productCards.get(product));
        }
    }
}
