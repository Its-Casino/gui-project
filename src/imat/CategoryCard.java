package imat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ProductCategory;

public class CategoryCard extends AnchorPane {
    @FXML
    Label labelCategory;
    @FXML
    ImageView imageCategory;

    private ProductCategory category;
    private MainViewController parentController;

    public CategoryCard(ProductCategory category, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.category = category;
        this.parentController = parentController;
        this.labelCategory.setText(convertToText(category));
    }

    @FXML
    public void openProductPage() {
        this.parentController.openProducts(category);
    }

    public static String convertToText(ProductCategory category) {
        switch (category) {
            case POD:
                return ("Baljväxter");

            case BREAD:
                return ("Bröd");

            case BERRY:
                return ("Bär");

            case CITRUS_FRUIT:
                return ("Citrusfrukter");

            case HOT_DRINKS:
                return ("Varm dryck");

            case COLD_DRINKS:
                return ("Kall dryck");

            case EXOTIC_FRUIT:
                return ("Exotisk frukt");

            case FISH:
                return ("Fisk");

            case VEGETABLE_FRUIT:
                return ("Grönsaker");

            case CABBAGE:
                return ("Sallad");

            case MEAT:
                return ("Kött");

            case DAIRIES:
                return ("Mjölkprodukter");

            case MELONS:
                return ("Melon");

            case FLOUR_SUGAR_SALT:
                return ("Skafferivaror");

            case NUTS_AND_SEEDS:
                return ("Nötter och frön");

            case PASTA:
                return ("Pasta");

            case POTATO_RICE:
                return ("Potatis och ris");

            case ROOT_VEGETABLE:
                return ("Rotfrukter");

            case FRUIT:
                return ("Frukt");

            case SWEET:
                return ("Sötsaker");

            case HERB:
                return ("Örter");

            default:
                return ("UNEXPECTED ERROR");

        }
    }
}
