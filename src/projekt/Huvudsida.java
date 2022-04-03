package projekt;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Huvudsida extends Application {

    //Slumpa recept
    //Skapandet grupp, hbox, vbox för popupfönstret till "slumpa recept"
    private Group popupGrupp = new Group();
    private HBox popupKnapplayout = new HBox(5);

    //Globala variabler
    private int k;
    private Stage window;
    private Scene main;
    private Scene popscene = new Scene(popupGrupp,1024,720);

    //Huvudsidan
    private Group mainLayout = new Group();

    //Boxes
    private GridPane laggaTill = new GridPane();
    private VBox header = new VBox();
    private VBox iconHolder = new VBox();
    private VBox matIcon = new VBox();
    private HBox buttonlayout = new HBox(5);
    //private VBox laggaTill = new VBox();
    //recept under filter

    //slumpa
    private VBox headerPopup = new VBox();

    //Filterboxes
    private VBox checkboxHolder = new VBox();
    private HBox titlar = new HBox();
    private HBox gridpanes = new HBox();
    private GridPane checkKolh = new GridPane();
    private GridPane checkGron = new GridPane();
    private GridPane checkProt = new GridPane();

    //Visa recept box
    private GridPane visarecept = new GridPane();
    private HBox besken = new HBox();

    //Topprankade recept
    private ListView<String> topRecept = new ListView<>();
    private ListView<String> sokRecept = new ListView<>();
    private ArrayList<Recept> receptLista;
    private ArrayList<Recept> resultList;
    private ArrayList<Recept> recipeNameHolder = new ArrayList<>();

    //Image
    private Image headerImg = new Image("file:src/images/header-small.png", 1024, 128, false, false);
    private Image magnGlass = new Image("file:src/images/magnify.png", 32, 32, false, false);
    private Image matgladje = new Image("file:src/images/matgladje.png", 480, 200, false, false);
    //private Image visaReceptBilden = new Image("", 200, 200, false, false);

    //Knappar
    private Button button1 = new Button("Filter");
    private Button button2 = new Button("Topprankade recept");
    private Button button3 = new Button("Lägg till recept");
    private Button button4 = new Button("Slumpa en maträtt!");
    private Button button5 = new Button("Tillbaka");
    private Button button6 = new Button("Slumpa igen");
    private Button insertRecept = new Button("Lägg till recept");
    private Button resetReceptFields = new Button("Rensa");
    private ImageButton homeIcon = new ImageButton(new Image("file:src/images/iconforHome.png"), 28, 28);

    //Textfält
    private TextField search = new TextField();
    private TextField bildUrl = new TextField();
    private TextField laggTillReceptNamn = new TextField();
    private TextField hemsida = new TextField();
    private TextField sattBetyg = new TextField();
    private TextField laggTillLank = new TextField();
    private TextField receptBeskrivning = new TextField();
    private ObservableList<Integer> betygOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5);
    private ObservableList<String> svarighetsOptions = FXCollections.observableArrayList("Lätt", "Medel", "Svår");

    //Comboboxar
    private ComboBox<Integer> betygBox = new ComboBox<>(betygOptions);
    private ComboBox<String> huvudIngBox = new ComboBox<>();
    private ComboBox<String> kolhydratBox = new ComboBox<>();
    private ComboBox<String> gronsakBox = new ComboBox<>();
    private ComboBox<String> svarighetsgradBox = new ComboBox<>(svarighetsOptions);

    //Labels
    private Label rubrikVisa = new Label("Högst betygsatta recept");
    private Label rubrikSok = new Label("Sökresultat");
    private Label rubrikLaggaTill = new Label("Lägg till recept");
    private Label bild = new Label("Länk till bild*");
    private Label receptNamn = new Label("Namnet på receptet");
    private Label franHemsida = new Label("Från vilken sida är receptet?");
    private Label betyg = new Label("Betyg");
    private Label lankHemsida = new Label("Länk till hemsidan*");
    private Label huvudIng = new Label("Huvudingredienser");
    private Label gronsak = new Label("Grönsaker");
    private Label kolhydrat = new Label("Kolhydrater");
    private Label huvudIng2 = new Label("Huvudingrediens");
    private Label gronsak2 = new Label("Grönsak");
    private Label kolhydrat2 = new Label("Kolhydrat");
    private Label visaReceptNamn = new Label();
    private Label visaReceptBesk = new Label();
    private Label visaReceptLank = new Label();
    private Label svarighetsgrad = new Label("Svårighetsgrad");
    private Label beskRecept = new Label("Vänligen beskriv kortfattat receptet (140)");
    private Label beskrivning = new Label();


    //Linjer
    private Line line1 = new Line(5, 5, 5, 200);
    private Line line2 = new Line(5, 5, 5, 200);

    //Hyperlänk
    private Hyperlink receptLank = new Hyperlink("Klicka på mig för receptets hemsida!");

    //Imageviews
    private ImageView visaReceptBild = new ImageView();

    //Checkboxar
    //Protein
    private CheckBox anka = new CheckBox("Anka");
    private CheckBox bonor = new CheckBox("bönor");
    private CheckBox flaskkott = new CheckBox("fläskkött");
    private CheckBox kalkon = new CheckBox("kalkon");
    private CheckBox kikartor = new CheckBox("kikärtor");
    private CheckBox kolja = new CheckBox("kolja");
    private CheckBox kyckling = new CheckBox("kyckling");
    private CheckBox lammkott = new CheckBox("lammkött");
    private CheckBox lax = new CheckBox("lax");
    private CheckBox linser = new CheckBox("linser");
    private CheckBox majskyckling = new CheckBox("majskyckling");
    private CheckBox notkott = new CheckBox("nötkött");
    private CheckBox pumpa = new CheckBox("pumpa");
    private CheckBox rakor = new CheckBox("räkor");
    private CheckBox torsk = new CheckBox("torsk");
    private CheckBox vegobites = new CheckBox("vegobites");
    private CheckBox vegofars = new CheckBox("vegofärs");
    private CheckBox viltkott = new CheckBox("viltkött");
    //kolhydrater
    private CheckBox couscous = new CheckBox("couscous");
    private CheckBox nudlar = new CheckBox("nudlar");
    private CheckBox pasta = new CheckBox("pasta");
    private CheckBox potatis = new CheckBox("potatis");
    private CheckBox ris = new CheckBox("ris");
    private CheckBox rotfrukter = new CheckBox("rotfrukter");
    private CheckBox rodbetor = new CheckBox("rödbetor");
    private CheckBox tortillabrod = new CheckBox("tortillabröd");
    //Grönsaker
    private CheckBox bellaverde = new CheckBox("bellaverde");
    private CheckBox blandsallad = new CheckBox("blandsallad");
    private CheckBox broccoli = new CheckBox("broccoli");
    private CheckBox champinjon = new CheckBox("champinjon");
    private CheckBox chili = new CheckBox("chili");
    private CheckBox fankol = new CheckBox("fänkål");
    private CheckBox gurka = new CheckBox("Gurka");
    private CheckBox krossadeTomater = new CheckBox("krossade tomater");
    private CheckBox lok = new CheckBox("lök");
    private CheckBox paprika = new CheckBox("paprika");
    private CheckBox purjolok = new CheckBox("purjolök");
    private CheckBox rucola = new CheckBox("rucola");
    private CheckBox rodlok = new CheckBox("rödlök");
    private CheckBox sallad = new CheckBox("sallad");
    private CheckBox sparris = new CheckBox("sparris");
    private CheckBox spenat = new CheckBox("spenat");
    private CheckBox tomat = new CheckBox("tomat");
    private CheckBox viktkal = new CheckBox("vitkål");
    private CheckBox vitlok = new CheckBox("vitlök");
    private CheckBox zucchini = new CheckBox("zucchini");

    //klass för att få en bild som knapp
    public class ImageButton extends Button {
        private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2;";
        private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 3 1 1 3;";

        public ImageButton(Image originalImage, double h, double w) {
            ImageView image = new ImageView(originalImage);
            image.setFitHeight(h);
            image.setFitHeight(w);
            image.setPreserveRatio(true);
            setGraphic(image);
            setStyle(STYLE_NORMAL);

            setOnMousePressed(event -> setStyle(STYLE_PRESSED));
            setOnMouseReleased(event -> setStyle(STYLE_NORMAL));
        }
    }

    //Visa recept
    public void visaRecept(int id){
        mainLayout.getChildren().remove(matIcon);
        laggaTill.getChildren().clear();
        visarecept.getChildren().clear();

        //Består av beskrivning för recepten
        besken.getChildren().clear();
        besken.getChildren().add(visaReceptBesk);

        //Sätter in variabler in i gridpane
        visarecept.add(visaReceptNamn, 0, 0);
        visarecept.add(visaReceptBild, 0, 1);
        visarecept.add(visaReceptBesk, 0, 2);
        visarecept.add(receptLank, 0, 3);
        if(id >= 0) {
            visaReceptNamn.setText(receptLista.get(id).getNamn());
            visaReceptBesk.setText(receptLista.get(id).getBeskrivning());
            Image bildPaMat = new Image(receptLista.get(id).getBildUrl());
            visaReceptBild.setFitWidth(275);
            visaReceptBild.setPreserveRatio(true);
            visaReceptBild.setImage(bildPaMat);
            visaReceptLank.setText(receptLista.get(id).getLank());
        }

        //Öppnar upp en webbläsare för användaren
        receptLank.setOnAction(e -> {
            if(Desktop.isDesktopSupported()){
                try{
                    Desktop.getDesktop().browse(new URI(visaReceptLank.getText()));
                } catch (IOException | URISyntaxException e1){
                    e1.printStackTrace();
                }
            }
        });

        header.getChildren().remove(visarecept);
        header.getChildren().add(visarecept);
    }

    //Filter
    public void filter(){

        mainLayout.getChildren().remove(matIcon);
        visarecept.getChildren().clear();
        laggaTill.getChildren().clear();
        checkboxHolder.getChildren().removeAll(titlar, gridpanes);

        //lägger till labels
        titlar.getChildren().removeAll(huvudIng, gronsak, kolhydrat);
        titlar.getChildren().addAll(huvudIng, gronsak, kolhydrat);

        //Lägger till checkboxar för kolhydrat
        checkKolh.getChildren().clear();
        checkKolh.add(tortillabrod, 2, 1); checkKolh.add(rodbetor, 2, 2); checkKolh.add(rotfrukter, 2, 3); checkKolh.add(ris, 2, 4);
        checkKolh.add(potatis, 3, 1); checkKolh.add(pasta, 3, 2); checkKolh.add(nudlar, 3, 3); checkKolh.add(couscous, 3, 4);

        //Lägger till checkboxar för protein
        checkProt.getChildren().clear();
        checkProt.add(anka, 1, 1); checkProt.add(bonor, 1, 2); checkProt.add(flaskkott, 1, 3); checkProt.add(kalkon, 1, 4); checkProt.add(viltkott, 1, 5);
        checkProt.add(kikartor, 2, 1); checkProt.add(kolja, 2, 2); checkProt.add(kyckling, 2, 3); checkProt.add(lammkott, 2, 4); checkProt.add(vegofars, 2, 5);
        checkProt.add(lax, 3, 1); checkProt.add(linser, 3, 2); checkProt.add(majskyckling, 3, 3); checkProt.add(notkott, 3, 4); checkProt.add(vegobites, 3, 5);
        checkProt.add(pumpa, 4, 1); checkProt.add(rakor, 4, 2); checkProt.add(torsk, 4, 3);

        //Lägger till checkboxar för grönsaker
        checkGron.getChildren().clear();
        checkGron.add(bellaverde, 1, 1); checkGron.add(blandsallad, 1, 2); checkGron.add(broccoli, 1, 3); checkGron.add(champinjon, 1, 4); checkGron.add(gurka, 1, 5);
        checkGron.add(chili, 2, 1); checkGron.add(fankol, 2, 2); checkGron.add(krossadeTomater, 2, 3); checkGron.add(lok, 2, 4); checkGron.add(zucchini, 2, 5);
        checkGron.add(paprika, 3, 1); checkGron.add(purjolok, 3, 2); checkGron.add(rucola, 3, 3); checkGron.add(rodlok, 3, 4); checkGron.add(vitlok, 3, 5);
        checkGron.add(sallad, 4, 1); checkGron.add(sparris, 4, 2); checkGron.add(spenat, 4, 3); checkGron.add(tomat, 4, 4); checkGron.add(viktkal, 4, 5);

        //Lägger till gridpanes i en hbox
        gridpanes.getChildren().removeAll(checkProt, line1, checkGron,line2, checkKolh);
        gridpanes.getChildren().addAll(checkProt, line1, checkGron,line2, checkKolh);

        //Lägger till gridpanes och titlar i "hållaren"
        checkboxHolder.getChildren().addAll(titlar, gridpanes);

        //Lägger till hållaren i huvudprogrammet
        header.getChildren().removeAll(checkboxHolder);
        header.getChildren().addAll(checkboxHolder);
    }

    //Ett problem som dyker upp när man tar bort saker är att slumpa-funktionen blir krux. Anledningen till det är att
    // när man lägger till så ökar recept_id och när man tar bort så försvinner den platsen. Ifall man då försöker komma
    // åt den platsen så får man "index out of bounds" för den platsen finns ju inte längre. Arrayen blir för stor
    // och då försöker man få ut data på index som inte finns helt enkelt. Värt att nämna men inget problem för vi
    // behövde inte ta bort saker i kravspecifikationen från Tobias.
    // Det verkar som att problemet är att maxid, som tar värde från recept_id, inte uppdaterar till det antalet recept_id som faktiskt finns...fett weird.
    // Det är ju säkert inget problem när man gör om allting med databasen, men ändå.
    public void slumpa (){
        //alternativ för att rensa
        visarecept.getChildren().clear();
        checkboxHolder.getChildren().clear();
        //Ifall listan har element måste den rensas för att fyllas på med ett random recept
        if(receptLista != null){
            receptLista.clear();
        }
        receptLista = receptDB.GetRndRec();

        //Skickar in id för att visa slumpat recept
        visaRecept(0);

        window.setScene(popscene);

        popupKnapplayout.getChildren().clear();
        popupKnapplayout.getChildren().addAll(button5,button6);

        headerPopup.getChildren().clear();
        headerPopup.getChildren().addAll(new ImageView(headerImg));

        //sätter position för receptet
        visarecept.setLayoutX(256);
        visarecept.setLayoutY(180);

        popupGrupp.getChildren().clear();
        popupGrupp.getChildren().addAll(headerPopup,popupKnapplayout, visarecept);

        //Slumpar om receptet igen
        button6.setOnAction(actionEvent -> slumpa());

        //Tillbaka-knapp
        button5.setOnAction(actionEvent -> {
            window.setScene(main);
            mainLayout.getChildren().add(matIcon);
        });
    }

    //Visa topprankade recept
    public void showTop() {

        mainLayout.getChildren().remove(matIcon);

        if(receptLista != null){
            receptLista.clear();
            topRecept.getItems().clear();
        }

        //Hämta lista med receptnamn
        receptLista = receptDB.getTopRecList();

        for (Recept res : receptLista) {
            topRecept.getItems().add(res.getNamn() + " - Betyg: " + res.getBetyg());
        }

        // Alternativt sätt för att rensa
        laggaTill.getChildren().clear();
        checkboxHolder.getChildren().clear();
        visarecept.getChildren().clear();

        laggaTill.add(rubrikVisa, 0 ,0);
        laggaTill.add(topRecept, 0 ,1);

        topRecept.getSelectionModel().selectedIndexProperty().addListener(ov -> {
            visaRecept(topRecept.getSelectionModel().getSelectedIndex());
        });

        laggaTill.getChildren().removeAll(rubrikVisa, topRecept);
        laggaTill.getChildren().addAll(rubrikVisa, topRecept);

        header.getChildren().remove(laggaTill);
        header.getChildren().add(laggaTill);
    }

    public void searchRes() {
        mainLayout.getChildren().remove(matIcon);
        //alternativ för att rensa
        laggaTill.getChildren().clear();
        checkboxHolder.getChildren().clear();

        if(resultList != null){
            topRecept.getItems().clear();
            recipeNameHolder.clear();
            header.getChildren().remove(visarecept);
            resultList.clear();
            receptLista.clear();
        }

        receptLista = receptDB.getAllRecept();

        sokRecept.getItems().clear();
        resultList = receptDB.search(search.getText());

        if (resultList != null) {
            for (Recept res : resultList) {
                sokRecept.getItems().add(res.getNamn());

            }
        }

        //Funktion för att få se recept från söklistan.
        sokRecept.getSelectionModel().selectedIndexProperty().addListener(ov -> {
            k = sokRecept.getSelectionModel().getSelectedIndex();
            if (k >= 0 ) {
                visaRecept((resultList.get(sokRecept.getSelectionModel().getSelectedIndex()).getReceptId() - 1));
            }
        });

        laggaTill.add(rubrikSok, 0 ,0);
        laggaTill.add(sokRecept, 0 ,1);


        laggaTill.getChildren().removeAll(rubrikSok, sokRecept);
        laggaTill.getChildren().addAll(rubrikSok, sokRecept);

        header.getChildren().remove(laggaTill);
        header.getChildren().add(laggaTill);
    }

    public void addRec(){

        mainLayout.getChildren().remove(matIcon);
        laggaTill.getChildren().clear();
        checkboxHolder.getChildren().clear();
        visarecept.getChildren().clear();
        laggTillReceptNamn.clear();
        bildUrl.clear();
        hemsida.clear();
        laggTillLank.clear();
        receptBeskrivning.clear();
        recipeNameHolder.clear();
        huvudIngBox.getItems().clear();
        kolhydratBox.getItems().clear();
        gronsakBox.getItems().clear();

        //Hämtar data från "protein" från databasen och lägger in det i en combobox
        huvudIngBox.getItems().addAll(receptDB.fillHuvudComboBox());
        kolhydratBox.getItems().addAll(receptDB.fillKolhydratComboBox());
        gronsakBox.getItems().addAll(receptDB.fillGronsakComboBox());


        //GridPane visar inmatning för recept
        //Vänsterspalt
        laggaTill.add(rubrikLaggaTill, 0 ,0);
        laggaTill.add(receptNamn, 0, 2);
        laggaTill.add(laggTillReceptNamn, 0, 3);
        laggaTill.add(bild, 0 ,4);
        laggaTill.add(bildUrl, 0 ,5);
        laggaTill.add(huvudIng2, 0, 6);
        laggaTill.add(huvudIngBox, 0, 7);
        laggaTill.add(gronsak2, 0, 8);
        laggaTill.add(gronsakBox, 0, 9);
        laggaTill.add(svarighetsgrad, 0, 10);
        laggaTill.add(svarighetsgradBox, 0, 11);
        laggaTill.add(insertRecept, 0, 12);
        //Högerspalt
        laggaTill.add(franHemsida, 1, 2);
        laggaTill.add(hemsida, 1 ,3);
        laggaTill.add(lankHemsida, 1, 4);
        laggaTill.add(laggTillLank, 1, 5);
        laggaTill.add(kolhydrat2, 1, 6);
        laggaTill.add(kolhydratBox, 1, 7);
        laggaTill.add(betyg, 1, 8);
        laggaTill.add(betygBox,1,9);
        laggaTill.add(beskRecept, 1, 10);
        laggaTill.add(receptBeskrivning, 1, 11);
        laggaTill.add(resetReceptFields, 1, 0);

        //Rensar fälten i lägga till recept - FUNKAR INTE SOM DEN SKA
        resetReceptFields.setOnAction(actionEvent -> {
            betygBox.valueProperty().set(null);
            svarighetsgradBox.valueProperty().set(null);
            addRec();
        });

        insertRecept.setOnAction(actionEvent -> {
            //Alertbox
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Är du säker?");
            alert.setHeaderText("Vill du lägga in det här receptet i databasen?");
            alert.setContentText("Välj ett alternativ");
            ButtonType yes = new ButtonType("Lägg till receptet");
            ButtonType no = new ButtonType("Jag ångrar mig", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yes, no);

            //variabel som används för att kolla ifall ett recept redan finns
            boolean finns = false;
            //Ifall samma namn finns i arrayen finns det i databasen och då är det en dublett. Då läggs den inte till
            for(int i = 0; i < recipeNameHolder.size(); i++){
                if(recipeNameHolder.get(i).equals(laggTillReceptNamn.getText())){
                    finns = true;
                }
            }

            Optional<ButtonType> result = alert.showAndWait();
            //Ifall man valde ja och receptet inte finns redan läggs det till. Ifall man skrivit något i alla rutor kollas också.
            //Ifall man inte har gjort det kan man inte lägga in i databasen. Det enda som är ok att inte skriva i är beskrivning av recept.
            if(result.get() == yes && !finns && !bildUrl.getText().isEmpty() && !laggTillReceptNamn.getText().isEmpty() && !hemsida.getText().isEmpty() && !laggTillLank.getText().isEmpty()
            && !betygBox.getSelectionModel().isEmpty() && !svarighetsgradBox.getSelectionModel().isEmpty() && !kolhydratBox.getSelectionModel().isEmpty()
            && !huvudIngBox.getSelectionModel().isEmpty() && !gronsakBox.getSelectionModel().isEmpty()){
                receptDB.insertRecipe(bildUrl.getText(), receptBeskrivning.getText(), laggTillReceptNamn.getText(), hemsida.getText(), betygBox.getSelectionModel().getSelectedItem(),
                        laggTillLank.getText(), svarighetsgradBox.getSelectionModel().getSelectedItem(), kolhydratBox.getSelectionModel().getSelectedItem(),
                        huvudIngBox.getSelectionModel().getSelectedItem(), gronsakBox.getSelectionModel().getSelectedItem());
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Information Dialog");
                confirmation.setHeaderText(null);
                confirmation.setContentText("Receptet är nu tillagt i databasen!");
                confirmation.showAndWait();
            }
            else if (finns){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error Dialog");
                error.setContentText(null);
                error.setHeaderText("Receptet finns redan!");
                error.showAndWait();
            }
            else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error Dialog");
                error.setContentText(null);
                error.setHeaderText("Du måste skriva/välja något i varje ruta!");
                error.showAndWait();
            }
        });

        //lägger till VBoxen(laggaTill) i VBoxen header
        header.getChildren().remove(laggaTill);
        header.getChildren().add(laggaTill);
    }

    public void showStart() {
        matIcon.getChildren().clear();
        mainLayout.getChildren().remove(matIcon);
        laggaTill.getChildren().clear();
        checkboxHolder.getChildren().clear();
        visarecept.getChildren().clear();

        //Lägger till Matglädjeikonen
        matIcon.getChildren().add(new ImageView(matgladje));
        matIcon.setPrefWidth(512);
        beskrivning.setText("Vår vision, oavsett tidigare matkunskaper och erfarenheter ska alla ha möjligheten att laga god och inspirerande mat. " +
                "Vi samlar topprankade recept från Sveriges mest populära recepthemsidor just för att du ska få uppleva den matglädjen du förtjänar (syfte)");
        beskrivning.setWrapText(true);
        matIcon.getChildren().add(beskrivning);
        matIcon.setLayoutY(140);
        matIcon.setLayoutX(275);

        mainLayout.getChildren().add(matIcon);

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        showStart();

        //Lägger till bilden i headern
        header.getChildren().addAll(new ImageView(headerImg));
        ImageView icon = new ImageView(magnGlass);

        HBox buttonlayout = new HBox();

        //Knapplayout för VBox header
        buttonlayout.setLayoutX(10);
        buttonlayout.setLayoutY(80);
        buttonlayout.setSpacing(20);
        iconHolder.setLayoutX(918);
        iconHolder.setLayoutY(88);
        search.setPromptText("Sök");
        button1.getStyleClass().add("special");
        button2.getStyleClass().add("special");
        button3.getStyleClass().add("special");
        button4.getStyleClass().add("special");
        button5.getStyleClass().add("special");
        button6.getStyleClass().add("special");
        insertRecept.getStyleClass().add("special");
        resetReceptFields.getStyleClass().add("special");

        //Linjer för filter
        line1.setStrokeWidth(1);
        line1.setStroke(Color.BLACK);
        line2.setStrokeWidth(1);
        line2.setStroke(Color.BLACK);
        line1.getStyleClass().add("line");
        line2.getStyleClass().add("line");

        //Laggatill gridpane
        laggaTill.setAlignment(Pos.CENTER);
        laggaTill.setHgap(20);
        laggaTill.setVgap(20);
        laggaTill.setPadding(new Insets(20, 20, 20, 20));

        //Textfält layout
        //receptBeskrivning.setPrefHeight(75);

        //Comboboxar layout
        //Combo Box för att sätta betyg
        betygBox.promptTextProperty().set("Välj betyg");
        //betygBox.promptTextProperty().setValue("Välj betyg");
        //Combo Box för att välja huvudingrediens
        huvudIngBox.promptTextProperty().setValue("Välj huvudingrediens");
        //Combo Box för att välja kolhydrat
        kolhydratBox.promptTextProperty().setValue("Välj Kolhydrat");
        //Combo Box för att välja grönsak
        gronsakBox.promptTextProperty().setValue("Välj grönsak");
        //Förtext på bildlänkfältet
        bildUrl.promptTextProperty().setValue("https://...png/jpg");
        //Förtext på svårighetsgrad
        svarighetsgradBox.promptTextProperty().setValue("Välj svårighetsgrad");
        //Förtext på länkfältet
        laggTillLank.setPromptText("https://");

        //sokrecept
        sokRecept.setPrefWidth(512);
        topRecept.setPrefWidth(512);
        topRecept.setPrefHeight(275);

        //Filter layout
        gridpanes.setSpacing(5);
        gridpanes.setPadding(new Insets(5));
        checkKolh.setHgap(10);
        checkKolh.setVgap(10);
        checkProt.setHgap(10);
        checkProt.setVgap(10);
        checkGron.setHgap(10);
        checkGron.setVgap(10);
        //titlar
        titlar.setSpacing(210);
        titlar.setPadding(new Insets(10));
        huvudIng.getStyleClass().add("title");
        gronsak.getStyleClass().add("title");
        kolhydrat.getStyleClass().add("title");

        //koordinater för var hboxen börjar.
        popupKnapplayout.setLayoutX(10);
        popupKnapplayout.setLayoutY(80);
        popupKnapplayout.setSpacing(10);

        //visa recept layout
        visarecept.setPadding(new Insets(10));
        visarecept.setVgap(10);
        visarecept.setAlignment(Pos.CENTER);
        visaReceptBesk.setWrapText(true);
        visaReceptNamn.setWrapText(true);
        visaReceptNamn.getStyleClass().add("title");
        visaReceptBesk.setPrefWidth(500);
        visaReceptNamn.setPrefWidth(500);

        //söklayout
        search.getStyleClass().add("header-section");

        buttonlayout.getChildren().addAll(button1,button2,button3,button4,search, homeIcon);
        iconHolder.getChildren().add(icon);
        mainLayout.getChildren().addAll(header,buttonlayout,iconHolder);

        main = new Scene(mainLayout, 1024, 720);
        main.getStylesheets().add("file:src/css/style.css");

        /*for (String s: receptLista) {
            topRecept.getItems().add(s);
        }*/

        //Hämta sträng från sökfältet, anropa search() med sökning
        search.setOnKeyPressed((event) -> {if (event.getCode() == KeyCode.ENTER) {
            searchRes();
            search.setText("");
            search.setPromptText("Sök");
        }
        });

        //Sätter style-class för element i lägga till recept
        rubrikLaggaTill.getStyleClass().add("title");
        rubrikVisa.getStyleClass().add("title");
        rubrikSok.getStyleClass().add("title");
        laggTillReceptNamn.getStyleClass().add("content-section");
        bildUrl.getStyleClass().add("content-section");
        hemsida.getStyleClass().add("content-section");
        sattBetyg.getStyleClass().add("content-section");
        laggTillLank.getStyleClass().add("content-section");
        popscene.getStylesheets().add("file:src/css/style.css");

        //Lägger till filter och recept under filter
        button1.setOnAction(e -> filter());

        //Ändrar scenen till visa toprankade recept
        button2.setOnAction(actionEvent -> showTop());

        //Ändrar scenen till lägga till recept
        button3.setOnAction(actionEvent -> addRec());

        //Slumpar maträtt
        button4.setOnAction(actionEvent -> slumpa());

        homeIcon.setOnAction(actionEvent -> showStart());

        window.setTitle("Recept");
        window.setScene(main);
        window.show();
    }
}