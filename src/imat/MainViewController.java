
package imat;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.User;

public class MainViewController implements Initializable {

    @FXML
    AnchorPane paneLoggedIn;
    @FXML
    AnchorPane paneLoggedOut;
    @FXML
    AnchorPane paneHelp;

    User currentUser;

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
        goLanding(false);
    }

    public void goLanding(Boolean loggedIn) {
        if (loggedIn) {
            paneLoggedIn.toFront();
            return;
        }
        paneLoggedOut.toFront();
        return;
    }

    @FXML
    public void openStart() {
        if (currentUser != null) {
            goLanding(true);
        } else {
            goLanding(false);
        }
    }

    @FXML
    public void openHelp() {
        paneHelp.toFront();
    }
}