package imat;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class HelpPage extends AnchorPane {
    private MainViewController parentController;

    public HelpPage(MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("help_page.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
