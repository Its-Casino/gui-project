
package imat;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.User;

public class MainViewController implements Initializable {

    @FXML
    AnchorPane paneLoggedIn;
    @FXML
    AnchorPane paneLoggedOut;
    @FXML
    AnchorPane paneHelp;
    @FXML
    AnchorPane paneCategories;
    @FXML
    AnchorPane paneAccount;
    @FXML
    AnchorPane paneCheckout;
    @FXML
    FlowPane flowCategories;
    @FXML
    AnchorPane paneProducts;
    @FXML
    FlowPane productFlow;
    @FXML
    Label labelProductCategory;

    User currentUser;

    Map<ProductCategory, AnchorPane> categoryPanes = new HashMap<>();

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        refreshCategories();
        openStart();
        generateCheckout();
        for (ProductCategory cat : ProductCategory.values()) {
            System.out.println(cat.name());
        }
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
        goLanding(currentUser != null);
    }

    @FXML
    public void openHelp() {
        paneHelp.toFront();
        closeCheckout();
    }

    @FXML
    public void openCategories() {
        refreshCategories();
        paneCategories.toFront();
    }

    private void refreshCategories() {
        flowCategories.getChildren().clear();
        for (ProductCategory category : ProductCategory.values()) {
            if (!categoryPanes.containsKey(category)) {
                categoryPanes.put(category, new CategoryCard(category, this));
            }
        }
        flowCategories.getChildren().addAll(categoryPanes.values());
    }

    @FXML
    public void openProducts(ProductCategory category) {
        productFlow.getChildren().clear();
        for (Product product : iMatDataHandler.getProducts(category)) {
            productFlow.getChildren().add(new ProductCard(product, this));
        }
        labelProductCategory.setText(CategoryCard.convertToText(category));
        paneProducts.toFront();
    }

    @FXML
    public void openAccount() {
        paneAccount.toFront();
    }

    @FXML
    public void closeAccount() {
        paneAccount.toBack();
    }

    @FXML
    public void logIn() {
        currentUser = new User();
        currentUser.setUserName("Rune");
        currentUser.setPassword("123");
        openStart();
    }

    @FXML
    public void closeCheckout() {
        paneCheckout.toBack();
    }

    @FXML
    public void mouseTrap(Event e) {
        e.consume();
    }

    @FXML
    private ImageView varukorg_wizard_state_image;
    @FXML
    private ImageView leveransadress_wizard_state_image;
    @FXML
    private ImageView leveranstid_wizard_state_image;
    @FXML
    private ImageView betalning_wizard_state_image;

    @FXML
    private Label din_varukorg_antal_varor;
    @FXML
    private Label din_varukorg_totalt_kostnad;

    @FXML
    private TextField leveransadress_fornamn;
    @FXML
    private TextField leveransadress_efternamn;
    @FXML
    private TextField leveransadress_gatuadress;
    @FXML
    private TextField leveransadress_postnummer;
    @FXML
    private TextField leveransadress_postort;
    @FXML
    private TextField leveransadress_mobilnummer;
    @FXML
    private TextField leveransadress_hemtelefon;
    @FXML
    private CheckBox spara_adress_checkbox;
    @FXML
    private CheckBox stall_kassorna_checkbox;

    @FXML
    private ComboBox<String> leveranstid_dag;
    @FXML
    private ComboBox<String> leveranstid_manad;
    @FXML
    private Label datum_tillgangligt_state;
    @FXML
    private ImageView datum_tillgangligt_state_image;
    @FXML
    private RadioButton leveranstid_07_11_button;
    @FXML
    private RadioButton leveranstid_08_12_button;
    @FXML
    private RadioButton leveranstid_09_13_button;
    @FXML
    private RadioButton leveranstid_10_14_button;
    @FXML
    private RadioButton leveranstid_11_15_button;
    @FXML
    private Label leveranstid_vald_datum;
    @FXML
    private Label leveranstid_vald_tid;

    @FXML
    private TextField betalning_kortnummer;
    @FXML
    private TextField betalning_manad_ar;
    @FXML
    private TextField betalning_cvc;
    @FXML
    private CheckBox betalning_spara_betalning;
    @FXML
    private Label betalning_antal_varor;
    @FXML
    private Label betalning_moms;
    @FXML
    private Label betalning_total_kostnad;
    @FXML private AnchorPane checked_image_anchorpane;

    private String vald_leveranstid;
    private String vald_leveransdag;

    private List<String> list_of_weekends = Arrays.asList("6", "7", "13", "14", "20", "21", "27", "28");

    private List<String> list_of_months_31 = Arrays.asList("Juli","Augusti","Oktober","December");

    private List<String> list_of_months_30 = Arrays.asList("Juni","September","November");

    private String vald_leveransmanad;

    void generateCheckout() {

        for (int i = 29; i <= 31; i++) {
            String dag = Integer.toString(i);
            leveranstid_dag.getItems().addAll(dag);
        }

        leveranstid_dag.getSelectionModel().select("29");

        leveranstid_manad.getItems().addAll("Maj", "Juni", "Juli", "Augusti",
                "September", "Oktober", "November", "December");
        leveranstid_manad.getSelectionModel().select("Maj");


        if (iMatDataHandler.getCustomer().getFirstName() != "") {
            leveransadress_fornamn.setText(iMatDataHandler.getCustomer().getFirstName());
        }
        if (iMatDataHandler.getCustomer().getLastName() != "") {
            leveransadress_efternamn.setText(iMatDataHandler.getCustomer().getLastName());
        }
        if (iMatDataHandler.getCustomer().getAddress() != "") {
            leveransadress_gatuadress.setText(iMatDataHandler.getCustomer().getAddress());
        }
        if (iMatDataHandler.getCustomer().getPostCode() != "") {
            leveransadress_postnummer.setText(iMatDataHandler.getCustomer().getPostCode());
        }
        if (iMatDataHandler.getCustomer().getPostAddress() != "") {
            leveransadress_postnummer.setText(iMatDataHandler.getCustomer().getPostAddress());
        }
        if (iMatDataHandler.getCustomer().getMobilePhoneNumber() != "") {
            leveransadress_mobilnummer.setText(iMatDataHandler.getCustomer().getMobilePhoneNumber());
        }
        if (iMatDataHandler.getCustomer().getPhoneNumber() != "") {
            leveransadress_mobilnummer.setText(iMatDataHandler.getCustomer().getPhoneNumber());
        }

        ToggleGroup leveranstidToggleGroup = new ToggleGroup();
        leveranstid_07_11_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_08_12_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_09_13_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_10_14_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_11_15_button.setToggleGroup(leveranstidToggleGroup);


        leveranstid_dag.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                vald_leveransdag = newValue;
                leveranstid_vald_datum.setText(String.format("Leveransdatum:    %s   %s", newValue, leveranstid_manad.getValue()));
                datum_tillgangligt_state.setText("Datumet är tillgängligt");
                checked_image_anchorpane.setLayoutX(326);
                checked_image_anchorpane.setLayoutY(281);
                datum_tillgangligt_state_image.setLayoutX(326);
                datum_tillgangligt_state_image.setLayoutY(281);
                checked_image_anchorpane.toBack();
                for (String listItem : list_of_weekends) {
                    if (newValue.contains(listItem)) {
                        datum_tillgangligt_state.setText("Datumet är INTE tillgängligt!");
                        checked_image_anchorpane.setLayoutX(196);
                        checked_image_anchorpane.setLayoutY(335);
                        datum_tillgangligt_state_image.setLayoutX(196);
                        datum_tillgangligt_state_image.setLayoutY(335);
                        checked_image_anchorpane.toFront();
                    }
                }
            }
        });
        leveranstid_manad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                vald_leveransmanad = newValue;
                if(vald_leveransmanad == "Maj") {
                    leveranstid_dag.getItems().clear();
                    for (int i = 29; i <= 31; i++) {
                        String dag = Integer.toString(i);
                        leveranstid_dag.getItems().addAll(dag);
                    }
                } else if () {

                }

                leveranstid_vald_datum.setText(String.format("Leveransdatum:    %s   %s", leveranstid_dag.getValue(),newValue));
            }
        });

        leveranstidToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (leveranstidToggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) leveranstidToggleGroup.getSelectedToggle();
                    vald_leveranstid = selected.getText();
                }
            }
        });
    }
}