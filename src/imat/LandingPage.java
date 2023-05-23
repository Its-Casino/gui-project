package imat;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LandingPage extends AnchorPane {
    private MainViewController parentController;
    private FXMLLoader fxmlLoader;

    public LandingPage(MainViewController parentController, boolean loggedIn) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_landing_logged_out.fxml"));
        if (loggedIn) {
            fxmlLoader = new FXMLLoader(getClass().getResource("imat_landing_logged_in.fxml"));
        }
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.parentController = parentController;
    }
}
