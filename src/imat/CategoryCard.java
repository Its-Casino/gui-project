package imat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
        setProps();
    }

    @FXML
    public void openProductPage() {
        this.parentController.openProducts(category);
    }

    private void setProps() {
        Image fxImage;
        switch (category) {
            case POD:
                labelCategory.setText("Baljväxter");
                break;
            case BREAD:
                labelCategory.setText("Bröd");
                break;
            case BERRY:
                labelCategory.setText("Bär");
                break;
            case CITRUS_FRUIT:
                labelCategory.setText("Citrusfrukter");
                break;
            case HOT_DRINKS:
                labelCategory.setText("Varm dryck");
                break;
            case COLD_DRINKS:
                labelCategory.setText("Kall dryck");
                break;
            case EXOTIC_FRUIT:
                labelCategory.setText("Exotisk frukt");
                break;
            case FISH:
                labelCategory.setText("Fisk");
                break;
            case VEGETABLE_FRUIT:
                labelCategory.setText("Grönsaker");
                break;
            case CABBAGE:
                labelCategory.setText("Sallad");
                break;
            case MEAT:
                labelCategory.setText("Kött");
                break;
            case DAIRIES:
                labelCategory.setText("Mjölkprodukter");
                break;
            case MELONS:
                labelCategory.setText("Melon");
                break;
            case FLOUR_SUGAR_SALT:
                labelCategory.setText("Skafferivaror");
                break;
            case NUTS_AND_SEEDS:
                labelCategory.setText("Nötter och frön");
                break;
            case PASTA:
                labelCategory.setText("Pasta");
                break;
            case POTATO_RICE:
                labelCategory.setText("Potatis och ris");
                break;
            case ROOT_VEGETABLE:
                labelCategory.setText("Rotfrukter");
                break;
            case FRUIT:
                labelCategory.setText("Frukt");
                break;
            case SWEET:
                labelCategory.setText("Sötsaker");
                break;
            case HERB:
                labelCategory.setText("Örter");
                break;
            default:
                break;
        }
    }
}
