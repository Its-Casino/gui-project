
package imat;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class MainViewController implements Initializable {

    @FXML
    AnchorPane panePage;
    @FXML
    Label labelMainPage;

    Map<Pages, AnchorPane> webPanes = new HashMap<>();

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    enum Pages {
        LANDING_PAGE_LOGGED_IN,
        LANDING_PAGE_LOGGED_OUT,
        HELP_PAGE,
        CATEGORY_PAGE,
    };

    public void initialize(URL url, ResourceBundle rb) {
        webPanes.put(Pages.LANDING_PAGE_LOGGED_OUT, new LandingPage(this, false));
        webPanes.put(Pages.LANDING_PAGE_LOGGED_IN, new LandingPage(this, true));
        webPanes.put(Pages.HELP_PAGE, new HelpPage(this));
        String iMatDirectory = iMatDataHandler.imatDirectory();
        labelMainPage.setText(iMatDirectory);
        System.out.println(panePage);
    }

    public void goLanding(Boolean loggedIn) {
        if (loggedIn) {
            panePage = webPanes.get(Pages.LANDING_PAGE_LOGGED_IN);
            return;
        }
        panePage = webPanes.get(Pages.LANDING_PAGE_LOGGED_OUT);
        return;
    }

}