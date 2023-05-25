package imat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import se.chalmers.cse.dat216.project.IMatDataHandler;



public class CheckoutPage extends AnchorPane {


    private MainViewController parentController;
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
    private ComboBox leveranstid_dag;
    @FXML
    private ComboBox leveranstid_manad;
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

    private IMatDataHandler datahandler = IMatDataHandler.getInstance();

    private String vald_leveranstid;
    private String vald_leveransdag;

    private List<String> list_of_weekends = Arrays.asList("6","7","13","14","20","21","27","28");

    private String vald_leveransmanad;

    public CheckoutPage(MainViewController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("utcheckning.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        /**
        for(int i = 0; i <= 31; i++){
            String dag = Integer.toString(i);
            leveranstid_dag.getItems().addAll("dag");
        }
        leveranstid_dag.getSelectionModel().select("29");

        leveranstid_manad.getItems().addAll("Januari","Februari","Mars","April","Maj","Juni","Juli","Augusti","September","Oktober","November","December");
        leveranstid_manad.getSelectionModel().select("Maj");


        if(datahandler.getCustomer().getFirstName() != null) { leveransadress_fornamn.setText(datahandler.getCustomer().getFirstName()); }
        if(datahandler.getCustomer().getLastName() != null) { leveransadress_efternamn.setText(datahandler.getCustomer().getLastName()); }
        if(datahandler.getCustomer().getAddress() != null) { leveransadress_gatuadress.setText(datahandler.getCustomer().getAddress()); }
        if(datahandler.getCustomer().getPostCode() != null) { leveransadress_postnummer.setText(datahandler.getCustomer().getPostCode()); }
        if(datahandler.getCustomer().getPostAddress() != null) { leveransadress_postnummer.setText(datahandler.getCustomer().getPostAddress()); }
        if(datahandler.getCustomer().getMobilePhoneNumber() != null) { leveransadress_mobilnummer.setText(datahandler.getCustomer().getMobilePhoneNumber()); }
        if(datahandler.getCustomer().getPhoneNumber() != null) { leveransadress_mobilnummer.setText(datahandler.getCustomer().getPhoneNumber()); }


        ToggleGroup leveranstidToggleGroup = new ToggleGroup();
        leveranstid_07_11_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_08_12_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_09_13_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_10_14_button.setToggleGroup(leveranstidToggleGroup);
        leveranstid_11_15_button.setToggleGroup(leveranstidToggleGroup);

        leveranstid_dag.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                vald_leveransdag = newValue;
                for(String listItem : list_of_weekends){
                    if(newValue.contains(listItem)){
                        datum_tillgangligt_state_image.setImage(new Image(""));
                    }
                }
            }
        });

        //leveranstid_manad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
        //    @Override
        //    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
        //        vald_leveransmanad = newValue;
        //        if(vald_leveransmanad == )
        //    }
        //});

        leveranstidToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (leveranstidToggleGroup.getSelectedToggle() != null){
                    RadioButton selected = (RadioButton) leveranstidToggleGroup.getSelectedToggle();
                    vald_leveranstid = selected.getText();
                }
            }
        });
         **/

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.parentController = parentController;
    }

}
