package controller;

import dao.BusDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import org.apache.poi.xwpf.usermodel.*;
import sample.ServiceLocator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BusCardController {

    private DBCotroller dbControl = new DBCotroller();
    @FXML
    private TextField txtFactory;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtNorm;
    @FXML
    private TextField txtDateCreate;
    @FXML
    private TextField txtFactoryNumber;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtIndication;

    @FXML
    private TableView<Card> tableCard;
    @FXML
    private TableColumn<Card, String> autoColumn;
    @FXML
    private TableColumn<Card, String> dateAddColumn;
    @FXML
    private TableColumn<Card, String> dateDelColumn;
    @FXML
    private TableColumn<Card, String> reasonColumn;
    @FXML
    private TableColumn<Card, String> stateColumn;
    @FXML
    private TableColumn<Card, String> milageColumn;

    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    private BusDao busDao = ServiceLocator.getBusDaoInstance();

    private Bus bus;

    @FXML
    private void initialize() {

        autoColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("auto"));
        dateAddColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("dateAdd"));
        dateDelColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("dateDel"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("reason"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("state"));
        milageColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("milage"));
        tableCard.setItems(null);
    }

    @FXML
    private void openCard() throws IOException {
        //Blank Document
        XWPFDocument document = new XWPFDocument();
        //Write the Document in file system
        Date now = new Date();
        String nowDateString = this.format.format(now);
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setUnderline(UnderlinePatterns.SINGLE);
        run.setText("Карточка учета");
        File file = new File("Документ от " + nowDateString + ".docx");
        FileOutputStream out = new FileOutputStream(file);
        document.write(out);
        out.close();
        System.out.println("createdocument.docx written successully");
        try {
            java.awt.Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh(Bus bus) {
        this.bus = busDao.findById(bus.getId());

        txtFactory.setText(bus.getFactory());
        txtCost.setText(bus.getCost().toString());
        txtNorm.setText(bus.getNorm());
        txtDateCreate.setText(bus.getDateCreate());
        txtFactoryNumber.setText(bus.getFactoryNumber());
        txtModel.setText(bus.getModel());
        txtIndication.setText(bus.getIndication());

        ObservableList<Card> result = FXCollections.observableArrayList();
        for (BusAuto busAuto : bus.getBusAutoList()) {
            Auto auto = busAuto.getAuto();
            List<Trip> trips = auto.getTrips();
            Double mileage = 0D;

            for (Trip trip : trips) {
                mileage += trip.getMileage();
            }
            String autoNumber = auto.getGosNumber() + ", " + auto.getNumber() + ", " + auto.getModel();
            result.add(new Card(bus.getId().toString(), autoNumber, busAuto.getStartDate(), busAuto.getEndDate(),
                    busAuto.getReason(), busAuto.getCondition(), mileage.intValue()));

        }
        tableCard.setItems(result);
    }
}
