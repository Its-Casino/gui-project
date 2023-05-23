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
    private MainViewController parentController;
    private ProductCategory category;

    public CategoryCard(ProductCategory category, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category_card.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.category = category;
        this.parentController = parentController;
    }
}
