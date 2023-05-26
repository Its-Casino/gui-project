
package imat;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Locale.Category;
import java.util.function.Function;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import se.chalmers.cse.dat216.project.ShoppingItem;
import se.chalmers.cse.dat216.project.User;
import java.util.regex.*;

public class MainViewController implements Initializable {

    @FXML
    AnchorPane paneStart;
    @FXML
    AnchorPane paneHelp;
    @FXML
    AnchorPane paneCart;
    @FXML
    AnchorPane paneCategories;
    @FXML
    AnchorPane paneAccount;
    @FXML
    AnchorPane paneCheckout;
    @FXML
    FlowPane flowCategories;
    @FXML
    FlowPane flowCart;
    @FXML
    VBox vboxCheckoutCart;
    @FXML
    FlowPane flowFavorites;
    @FXML
    VBox vboxHistoryOverview;
    @FXML
    VBox vboxHistoryDetailed;
    @FXML
    HBox hboxOffers;
    @FXML
    AnchorPane paneProducts;
    @FXML
    AnchorPane paneFavorites;
    @FXML
    AnchorPane paneHistory;
    @FXML
    FlowPane productFlow;
    @FXML
    Label labelProductCategory;
    @FXML
    Label labelCartTotal;
    @FXML
    Label labelWelcome;
    @FXML
    AnchorPane anchorPaneStart;
    @FXML
    AnchorPane anchorPaneCategory;
    @FXML
    AnchorPane anchorPaneLists;
    @FXML
    AnchorPane anchorPaneAbout;
    @FXML
    AnchorPane paneAbout;
    @FXML
    AnchorPane paneDeliveryInfo;
    @FXML
    AnchorPane answerOne;
    @FXML
    AnchorPane answerTwo;
    @FXML
    AnchorPane answerThree;
    @FXML
    ImageView answerOneExpand;
    @FXML
    ImageView answerTwoExpand;
    @FXML
    ImageView answerThreeExpand;

    String lastPane;

    User currentUser;

    Map<ProductCategory, AnchorPane> categoryPanes = new HashMap<>();
    Map<Category, List<ProductCard>> categoryProductCards = new HashMap<>();
    Map<String, ProductCategory> stringCategoryMap = new TreeMap<>();
    Map<ProductCategory, String> categoryStringMap = new HashMap<>();

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {
        String iMatDirectory = iMatDataHandler.imatDirectory();
        refreshCategories();
        generateMaps();
        generateCheckout();
        openStart();
        iMatDataHandler.getShoppingCart().addShoppingCartListener(evt -> {
            updateCart();
        });
        updateCart();
        labelWelcome.setText("Hej " + currentUser.getUserName() + "!");
    }

    void updateCart() {
        flowCart.getChildren().clear();
        for (ShoppingItem shoppingItem : iMatDataHandler.getShoppingCart().getItems()) {
            flowCart.getChildren().add(new CartCard(shoppingItem, this));
        }
        vboxCheckoutCart.getChildren().clear();
        vboxCheckoutCart.getChildren().addAll(flowCart.getChildren());
        labelCartTotal.setText(iMatDataHandler.getShoppingCart().getTotal() + " kr");
        din_varukorg_totalt_kostnad.setText("Totalt: " + iMatDataHandler.getShoppingCart().getTotal() + " kr");
    }

    private void generateMaps() {
        currentUser = new User();
        for (int i = 1; i < 5; i++) {
            hboxOffers.getChildren().add(new ProductCard(iMatDataHandler.getProduct(i), this));
        }
        currentUser.setUserName("Rune");
        for (ProductCategory category : ProductCategory.values()) {
            stringCategoryMap.put(convertToText(category), category);
            categoryStringMap.put(category, convertToText(category));
        }
    }

    @FXML
    public void openStart() {
        paneStart.toFront();
        closeCheckout();
        closeCart();
        closeAccount();
        anchorPaneStart.toFront();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
    }

    @FXML
    public void openHelp() {
        paneHelp.toFront();
        closeCheckout();
        closeCart();
        closeAccount();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
    }

    @FXML
    public void openCategories() {
        refreshCategories();
        closeCart();
        closeAccount();
        paneCategories.toFront();
        anchorPaneStart.toBack();
        anchorPaneCategory.toFront();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
    }

    @FXML
    public void openBerry() {
        openProducts(ProductCategory.BERRY);
    }

    @FXML
    public void openBread() {
        openProducts(ProductCategory.BREAD);
    }

    @FXML
    public void openCabbage() {
        openProducts(ProductCategory.CABBAGE);
    }

    @FXML
    public void openCitrusFruit() {
        openProducts(ProductCategory.CITRUS_FRUIT);
    }

    @FXML
    public void openDairies() {
        openProducts(ProductCategory.DAIRIES);
    }

    @FXML
    public void openColdDrinks() {
        openProducts(ProductCategory.COLD_DRINKS);
    }

    @FXML
    public void openExoticFruit() {
        openProducts(ProductCategory.EXOTIC_FRUIT);
    }

    @FXML
    public void openFish() {
        openProducts(ProductCategory.FISH);
    }

    @FXML
    public void openFlourSaltSugar() {
        openProducts(ProductCategory.FLOUR_SUGAR_SALT);
    }

    @FXML
    public void openFruit() {
        openProducts(ProductCategory.FRUIT);
    }

    @FXML
    public void openHerb() {
        openProducts(ProductCategory.HERB);
    }

    @FXML
    public void openHotDrinks() {
        openProducts(ProductCategory.HOT_DRINKS);
    }

    @FXML
    public void openMeat() {
        openProducts(ProductCategory.MEAT);
    }

    @FXML
    public void openMelons() {
        openProducts(ProductCategory.MELONS);
    }

    @FXML
    public void openNutsAndSeeds() {
        openProducts(ProductCategory.NUTS_AND_SEEDS);
    }

    @FXML
    public void openPasta() {
        openProducts(ProductCategory.PASTA);
    }

    @FXML
    public void openPod() {
        openProducts(ProductCategory.POD);
    }

    @FXML
    public void openPotatoRice() {
        openProducts(ProductCategory.POTATO_RICE);
    }

    @FXML
    public void openRootVegetable() {
        openProducts(ProductCategory.ROOT_VEGETABLE);
    }

    @FXML
    public void openSweet() {
        openProducts(ProductCategory.SWEET);
    }

    @FXML
    public void openVegetableFruit() {
        openProducts(ProductCategory.VEGETABLE_FRUIT);
    }

    private void refreshCategories() {
        flowCategories.getChildren().clear();
        for (String categoryString : stringCategoryMap.keySet()) {
            if (!categoryPanes.containsKey(stringCategoryMap.get(categoryString))) {
                categoryPanes.put(stringCategoryMap.get(categoryString),
                        new CategoryCard(stringCategoryMap.get(categoryString), this));
            }
        }
        for (String categoryString : stringCategoryMap.keySet()) {
            flowCategories.getChildren().add(categoryPanes.get(stringCategoryMap.get(categoryString)));
        }
    }

    @FXML
    public void openProducts(ProductCategory category) {
        List<Product> currProducts = iMatDataHandler.getProducts(category);
        Collections.sort(currProducts, Comparator.comparing(Product::getName));
        productFlow.getChildren().clear();
        for (Product product : currProducts) {
            productFlow.getChildren().add(new ProductCard(product, this));
        }
        labelProductCategory.setText(categoryStringMap.get(category));
        paneProducts.toFront();
        closeCart();
        closeAccount();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
    }

    @FXML
    public void openCart() {
        lastPane = "Cart";
        flowCart.getChildren().clear();
        for (ShoppingItem shoppingItem : iMatDataHandler.getShoppingCart().getItems()) {
            flowCart.getChildren().add(new CartCard(shoppingItem, this));
        }
        closeAccount();
        closeCheckout();
        paneCheckout.toBack();
        paneCart.toFront();
        paneAccount.toBack();
    }

    @FXML
    public void closeCart() {
        paneCart.toBack();
    }

    @FXML
    public void openAccount() {
        paneAccount.toFront();
        closeCheckout();
        closeCart();
    }

    @FXML
    public void openAbout() {
        paneAbout.toFront();
        closeCheckout();
        closeCart();
        closeAccount();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toFront();
    }

    @FXML
    public void openAnswerOne() {
        answerOne.toFront();
        answerOneExpand.toFront();
        answerTwoExpand.toBack();
        answerThreeExpand.toBack();

    }

    @FXML
    public void openAnswerTwo() {
        answerTwo.toFront();
        answerTwoExpand.toFront();
        answerOneExpand.toBack();
        answerThreeExpand.toBack();
    }

    public void openAnswerThree() {
        answerThree.toFront();
        answerThreeExpand.toFront();
        answerOneExpand.toBack();
        answerTwoExpand.toBack();
    }

    @FXML
    public void closeAccount() {
        paneAccount.toBack();
    }

    @FXML
    public void closeCheckout() {
        paneCheckout.toBack();
    }

    @FXML
    public void openCheckout() {
        paneCheckout.toFront();
        closeCart();
        closeAccount();
    }

    @FXML
    public void openFavorites() {
        paneFavorites.toFront();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toFront();
        anchorPaneAbout.toBack();
        populateFavorites();
    }

    void populateFavorites() {
        flowFavorites.getChildren().clear();
        for (Product product : iMatDataHandler.favorites()) {
            flowFavorites.getChildren().add(new ProductCard(product, this));
        }
    }

    @FXML
    public void openDeliveryInfo() {
        paneDeliveryInfo.toFront();
        paneAccount.toBack();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
    }

    @FXML
    public void mouseTrap(Event e) {
        e.consume();
    }

    @FXML
    public void openHistory() {
        lastPane = "History";
        vboxHistoryOverview.getChildren().clear();
        for (Order order : iMatDataHandler.getOrders()) {
            vboxHistoryOverview.getChildren().add(new OrderOverview(order, this));
        }
        paneHistory.toFront();
        paneAccount.toBack();
        anchorPaneStart.toBack();
        anchorPaneCategory.toBack();
        anchorPaneLists.toBack();
        anchorPaneAbout.toBack();
    }

    public void openHistoryDetailed(Order order) {
        vboxHistoryDetailed.getChildren().clear();
        for (ShoppingItem item : order.getItems()) {
            vboxHistoryDetailed.getChildren().add(new HistoryDetailed(item, this));
        }
    }

    @FXML
    public void createFauxOrder() {
        iMatDataHandler.getShoppingCart().addProduct(iMatDataHandler.findProducts("Äpple").get(0));
        iMatDataHandler.placeOrder(false);
    }

    private static String convertToText(ProductCategory category) {
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
    @FXML
    private AnchorPane checked_image_anchorpane;
    @FXML
    private AnchorPane checkout_varukorg_pane;
    @FXML
    private AnchorPane checkout_leveransadress_pane;
    @FXML
    private AnchorPane checkout_leveranstid_pane;
    @FXML
    private AnchorPane checkout_betalning_pane;
    @FXML
    private Button leveransadress_continue_button;
    @FXML
    private AnchorPane leveranstidcoverpane;
    @FXML
    private Button button_confirm_delivery;

    private String vald_leveransdag;

    private boolean valid_fornamn;

    private boolean valid_efternamn;

    private boolean valid_gatuadress;

    private boolean valid_postnummer;

    private boolean valid_postort;

    private boolean valid_mobilnummer;

    private boolean valid_hemtelefon = true;

    private List<String> list_of_weekends = Arrays.asList("6", "7", "13", "14", "20", "21", "27", "28");

    private List<String> list_of_months_31 = Arrays.asList("Juli", "Augusti", "Oktober", "December");

    private List<String> list_of_months_30 = Arrays.asList("Juni", "September", "November");

    private List<String> list_of_all_months = Arrays.asList("Maj", "Juni", "Juli", "Augusti", "September", "Oktober",
            "November", "December");

    private String vald_leveransmanad;

    private boolean datum_available;
    private boolean spara_adressen;

    private boolean valid_vald_dag;

    private boolean valid_vald_manad;

    private boolean valid_vald_tid;

    private boolean spara_betalning;

    public void continuetodeliverydate() {
        checkout_leveransadress_pane.toFront();
    }

    public void backfromdeliverydate() {
        checkout_varukorg_pane.toFront();
    }

    public void continuetodeliverytime() {
        checkout_leveranstid_pane.toFront();
    }

    public void backfromdeliverytime() {
        checkout_leveransadress_pane.toFront();
    }

    public void continuetopayment() {
        checkout_betalning_pane.toFront();
    }

    public void backfrompayment() {
        checkout_leveranstid_pane.toFront();
    }

    public void sparaadresscheckbox() {
        spara_adressen = !spara_adressen;
    }

    public void sparabetalningcheckbox() {
        spara_betalning = !spara_betalning;
    }

    public void confirm_payment() {
        if (spara_betalning) {
            iMatDataHandler.getCreditCard().setCardNumber(betalning_kortnummer.getText());
            iMatDataHandler.getCreditCard().setVerificationCode(Integer.parseInt(betalning_cvc.getText()));
        }
        if (spara_adressen) {
            iMatDataHandler.getCustomer().setFirstName(leveransadress_fornamn.getText());
            iMatDataHandler.getCustomer().setLastName(leveransadress_efternamn.getText());
            iMatDataHandler.getCustomer().setAddress(leveransadress_gatuadress.getText());
            iMatDataHandler.getCustomer().setMobilePhoneNumber(leveransadress_mobilnummer.getText());
            if (!leveransadress_hemtelefon.getText().equals(""))
                iMatDataHandler.getCustomer().setPhoneNumber(leveransadress_hemtelefon.getText());
        }
    }

    public static boolean hasNumber(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public static boolean hasLetter(String input) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public static boolean hasSpecialCharacter(String input) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9ÅÄÖåäö ]");
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public void check_if_leveransadress_valid() {
        if (valid_fornamn && valid_efternamn && valid_gatuadress && valid_postnummer && valid_postort
                && valid_mobilnummer && valid_hemtelefon) {
            leveransadress_continue_button.setDisable(false);
        } else
            leveransadress_continue_button.setDisable(true);
    }

    public void check_if_leveranstid_valid() {
        if (valid_vald_dag && valid_vald_manad && valid_vald_tid)
            button_confirm_delivery.setDisable(false);
    }

    private String formatCardNumber(String cardNumber) {
        if (cardNumber.length() > 0) {
            // Remove any non-digit characters
            cardNumber = cardNumber.replaceAll("[^0-9]", "");

            // Insert the formatting dashes
            StringBuilder formattedNumber = new StringBuilder();
            for (int i = 0; i < cardNumber.length(); i++) {
                if (i > 0 && i % 4 == 0) {
                    formattedNumber.append("-");
                }
                formattedNumber.append(cardNumber.charAt(i));
            }
            return formattedNumber.toString();
        }
        return cardNumber;
    }

    private String format_mm_aa(String mm_aa) {
        if (mm_aa.length() > 0) {
            // Remove any non-digit characters
            mm_aa = mm_aa.replaceAll("[^0-9]", "");

            // Insert the formatting dashes
            StringBuilder formattedNumber = new StringBuilder();
            for (int i = 0; i < mm_aa.length(); i++) {
                if (i > 0 && i % 2 == 0) {
                    formattedNumber.append("/");
                }
                formattedNumber.append(mm_aa.charAt(i));
            }
            return formattedNumber.toString();
        }
        return mm_aa;
    }

    private String formatZipCode(String zipCode) {
        if (zipCode.length() > 0) {
            // Remove any non-digit characters
            zipCode = zipCode.replaceAll("[^0-9]", "");

            // Insert the formatting space
            if (zipCode.length() > 3) {
                zipCode = zipCode.substring(0, 3) + " " + zipCode.substring(3);
            }
        }
        return zipCode;
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() > 0) {
            // Remove any non-digit characters
            phoneNumber = phoneNumber.replaceAll("[^0-9]", "");

            // Ensure the phone number length does not exceed 10 digits
            if (phoneNumber.length() > 10) {
                phoneNumber = phoneNumber.substring(0, 10);
            }

            // Insert the formatting dashes and space
            StringBuilder formattedNumber = new StringBuilder();
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (i == 3) {
                    formattedNumber.append("-");
                } else if (i == 6) {
                    formattedNumber.append(" ");
                }
                formattedNumber.append(phoneNumber.charAt(i));
            }

            return formattedNumber.toString();
        }
        return phoneNumber;
    }

    void generateCheckout() {

        leveransadress_continue_button.setDisable(true);
        button_confirm_delivery.setDisable(true);
        betalning_spara_betalning.setStyle("-fx-font-size: 18px");

        TextFormatter<String> mm_aa_formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,4}") && newText.length() <= 4) {
                return change;
            }
            return null;
        });

        betalning_manad_ar.setTextFormatter(mm_aa_formatter);
        
        TextFormatter<String> zipCodeFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 6) {
                return change;
            }
            return null;
        });
        leveransadress_postnummer.setTextFormatter(zipCodeFormatter);

        TextFormatter<String> onlyLetters_numbers = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9]*")) {
                return change;
            }
            return null;
        });
        leveransadress_gatuadress.setTextFormatter(onlyLetters_numbers);

        TextFormatter<String> onlyLetters_fornamn = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-ZäöåÄÖÅ]*")) {
                return change;
            }
            return null;
        });
        leveransadress_fornamn.setTextFormatter(onlyLetters_fornamn);

        TextFormatter<String> onlyLetters_efternamn = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-ZäöåÄÖÅ]*")) {
                return change;
            }
            return null;
        });
        leveransadress_efternamn.setTextFormatter(onlyLetters_efternamn);

        TextFormatter<String> onlyLetters_postort = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-ZäöåÄÖÅ]*")) {
                return change;
            }
            return null;
        });
        leveransadress_postort.setTextFormatter(onlyLetters_postort);



        TextFormatter<String> phoneNumberFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 12) {
                return change;
            }
            return null;
        });
        leveransadress_mobilnummer.setTextFormatter(phoneNumberFormatter);

        TextFormatter<String> hemtelefonFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 12) {
                return change;
            }
            return null;
        });
        leveransadress_hemtelefon.setTextFormatter(hemtelefonFormatter);

        leveransadress_fornamn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                valid_fornamn = true;
                leveransadress_fornamn.setStyle("-fx-border-color: green");
                if (newValue.equals("")) {
                    leveransadress_fornamn.setStyle("-fx-border-color: red");
                    valid_fornamn = false;
                }
                if (hasNumber(newValue) || hasSpecialCharacter(newValue)) {
                    leveransadress_fornamn.setStyle("-fx-background-color: rgba(255,0,0,0.30)");
                    valid_fornamn = false;
                }
                check_if_leveransadress_valid();
            }
        });

        leveransadress_efternamn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                valid_efternamn = true;
                leveransadress_efternamn.setStyle("-fx-border-color: green");
                if (newValue.equals("")) {
                    leveransadress_continue_button.setDisable(true);
                    leveransadress_efternamn.setStyle("-fx-border-color: red");
                    valid_efternamn = false;
                }
                if (hasNumber(newValue) || hasSpecialCharacter(newValue)) {
                    leveransadress_efternamn.setStyle("-fx-background-color: rgba(255,0,0,0.40)");
                    valid_efternamn = false;
                }
                check_if_leveransadress_valid();
            }
        });

        leveransadress_gatuadress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                valid_gatuadress = true;
                leveransadress_gatuadress.setStyle("-fx-border-color: green");
                if (newValue.equals("")) {
                    leveransadress_continue_button.setDisable(true);
                    leveransadress_gatuadress.setStyle("-fx-border-color: red");
                    valid_gatuadress = false;
                }
                if (hasSpecialCharacter(newValue)) {
                    leveransadress_gatuadress.setStyle("-fx-background-color: rgba(255,0,0,0.40)");
                    valid_gatuadress = false;
                }
                check_if_leveransadress_valid();
            }
        });

        leveransadress_postnummer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                valid_postnummer = true;
                String formattedText = formatZipCode(newValue);
                leveransadress_postnummer.setText(formattedText);
                leveransadress_postnummer.setStyle("-fx-border-color: green");
                if (newValue.length() < 5) {
                    leveransadress_continue_button.setDisable(true);
                    leveransadress_postnummer.setStyle("-fx-border-color: red");
                    valid_postnummer = false;
                }
                check_if_leveransadress_valid();
            }
        });

        leveransadress_postort.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                valid_postort = true;
                leveransadress_postort.setStyle("-fx-border-color: green");
                if (newValue.equals("")) {
                    leveransadress_postort.setStyle("-fx-border-color: red");
                    valid_postort = false;
                }
                if (hasNumber(newValue) || hasSpecialCharacter(newValue)) {
                    leveransadress_postort.setStyle("-fx-background-color: rgba(255,0,0,0.40)");
                    valid_postort = false;
                }
                check_if_leveransadress_valid();
            }
        });

        leveransadress_mobilnummer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                valid_mobilnummer = true;
                String formattedText = formatPhoneNumber(newValue);
                leveransadress_mobilnummer.setText(formattedText);
                leveransadress_mobilnummer.setStyle("-fx-border-color: green");
                if (newValue.length() < 12 || !newValue.startsWith("07")) {
                    leveransadress_mobilnummer.setStyle("-fx-border-color: red");
                    valid_mobilnummer = false;
                }
                check_if_leveransadress_valid();
            }
        });

        leveransadress_hemtelefon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                valid_hemtelefon = true;
                leveransadress_hemtelefon.setStyle("-fx-border-color: rgba(0,128,0,0)");
                if (hasLetter(newValue) || hasSpecialCharacter(newValue)) {
                    leveransadress_hemtelefon.setStyle("-fx-background-color: rgba(255,0,0,0.40)");
                    valid_hemtelefon = false;
                }
                check_if_leveransadress_valid();
            }
        });

        for (int i = 1; i <= 31; i++) {
            String dag = Integer.toString(i);
            leveranstid_dag.getItems().addAll(dag);
        }

        leveranstid_dag.getSelectionModel().select("Dag");

        leveranstid_manad.getItems().addAll("Maj", "Juni", "Juli", "Augusti",
                "September", "Oktober", "November", "December");
        leveranstid_manad.getSelectionModel().select("Månad");

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
                if (leveranstid_dag.getValue() != "Dag" && leveranstid_manad.getValue() != "Månad") {
                    valid_vald_dag = true;
                    datum_available = true;
                    datum_tillgangligt_state.setLayoutX(83);
                    leveranstid_vald_datum.setText(
                            String.format("Leveransdatum:    %s   %s", newValue, leveranstid_manad.getValue()));
                    datum_tillgangligt_state.setText("Datumet är tillgängligt");
                    checked_image_anchorpane.setLayoutX(326);
                    checked_image_anchorpane.setLayoutY(281);
                    datum_tillgangligt_state_image.setLayoutX(326);
                    datum_tillgangligt_state_image.setLayoutY(281);
                    checked_image_anchorpane.toBack();
                    leveranstidcoverpane.toBack();
                    check_if_leveranstid_valid();
                    for (String listItem : list_of_weekends) {
                        if (newValue == null) {
                            newValue = oldValue;
                        }
                        if (newValue.contains(listItem)) {
                            leveranstidcoverpane.toFront();
                            valid_vald_dag = false;
                            datum_available = false;
                            button_confirm_delivery.setDisable(true);
                            datum_tillgangligt_state.setText("Datumet är INTE tillgängligt!");
                            checked_image_anchorpane.setLayoutX(196);
                            checked_image_anchorpane.setLayoutY(335);
                            datum_tillgangligt_state_image.setLayoutX(196);
                            datum_tillgangligt_state_image.setLayoutY(335);
                            checked_image_anchorpane.toFront();
                        }
                    }
                    check_if_leveranstid_valid();
                }
            }
        });
        leveranstid_manad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                vald_leveransmanad = newValue;
                valid_vald_manad = true;
                String temp_leveransdag = leveranstid_dag.getValue();
                if (vald_leveransmanad == "Maj") {
                    leveranstid_dag.getItems().clear();
                    for (int i = 29; i <= 31; i++) {
                        String dag = Integer.toString(i);
                        leveranstid_dag.getItems().addAll(dag);
                    }
                    leveranstid_dag.getSelectionModel().select("29");
                } else {
                    leveranstid_dag.getItems().clear();
                    for (int i = 1; i <= 31; i++) {
                        String dag = Integer.toString(i);
                        leveranstid_dag.getItems().addAll(dag);
                    }
                    leveranstid_dag.getSelectionModel().select(temp_leveransdag);
                }

                for (String listItem : list_of_weekends) {
                    if (newValue == null) {
                        newValue = oldValue;
                    }
                    if (!newValue.contains(listItem) && datum_available) {
                        leveranstid_vald_datum.setText(
                                String.format("Leveransdatum:    %s   %s", leveranstid_dag.getValue(), newValue));
                        leveranstidcoverpane.toBack();
                    }
                }
            }
        });

        leveranstidToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (leveranstidToggleGroup.getSelectedToggle() != null) {
                    valid_vald_tid = true;
                    RadioButton selected = (RadioButton) leveranstidToggleGroup.getSelectedToggle();
                    leveranstid_vald_tid.setText(String.format("Tid:    %s", selected.getText()));
                }
                check_if_leveranstid_valid();
            }
        });

        betalning_kortnummer.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                String formatterad_kortnummer = formatCardNumber(newValue);
                if (formatterad_kortnummer.length() <= 19) {
                    betalning_kortnummer.setText(formatterad_kortnummer);
                } else {
                    betalning_kortnummer.setText(oldValue);
                }
            }
        });

        betalning_manad_ar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                String formaterad_mm_aa = format_mm_aa(newValue);
                if(formaterad_mm_aa.length() <= 5) {
                    betalning_manad_ar.setText(formaterad_mm_aa);
                } else {
                    betalning_manad_ar.setText(oldValue);
                }
            }
        });

    }

}