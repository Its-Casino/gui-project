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
        String val;
        switch (category) {
            case POD:
                val = ("Baljväxter");
                break;

            case BREAD:
                val = ("Bröd");
                break;

            case BERRY:
                val = ("Bär");
                break;

            case CITRUS_FRUIT:
                val = ("Citrusfrukter");
                break;

            case HOT_DRINKS:
                val = ("Varm dryck");
                break;

            case COLD_DRINKS:
                val = ("Kall dryck");
                break;

            case EXOTIC_FRUIT:
                val = ("Exotisk frukt");
                break;

            case FISH:
                val = ("Fisk");
                break;

            case VEGETABLE_FRUIT:
                val = ("Grönsaker");
                break;

            case CABBAGE:
                val = ("Sallad");
                break;

            case MEAT:
                val = ("Kött");
                break;

            case DAIRIES:
                val = ("Mjölkprodukter");
                break;

            case MELONS:
                val = ("Melon");
                break;

            case FLOUR_SUGAR_SALT:
                val = ("Skafferivaror");
                break;

            case NUTS_AND_SEEDS:
                val = ("Nötter och frön");
                break;

            case PASTA:
                val = ("Pasta");
                break;

            case POTATO_RICE:
                val = ("Potatis och ris");
                break;

            case ROOT_VEGETABLE:
                val = ("Rotfrukter");
                break;

            case FRUIT:
                val = ("Frukt");
                break;

            case SWEET:
                val = ("Sötsaker");
                break;

            case HERB:
                val = ("Örter");
                break;

            default:
                val = ("UNEXPECTED ERROR");
                break;
        }
        return val;
    }
}
