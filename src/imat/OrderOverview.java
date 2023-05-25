package imat;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;

public class OrderOverview extends AnchorPane {
    @FXML
    Label labelOrderDateTime;

    private MainViewController parentController;

    private Order order;

    public OrderOverview(Order order, MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order_overview.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        labelOrderDateTime.setText(order.getDate().toString());
        this.order = order;
        this.parentController = parentController;
    }

    @FXML
    public void openDetails() {
        parentController.openHistoryDetailed(order);
    }
}
