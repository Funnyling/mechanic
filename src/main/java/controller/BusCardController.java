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
import util.XWPFUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class BusCardController {
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
    private TextField txtChairman;
    @FXML
    private TextField txtFirstMember;
    @FXML
    private TextField txtSecondMember;
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

    private String[] headers = {
            "Модель автомобиля (прицепа), его государственный номер",
            "Дата установки шины на колесо автомобиля",
            "Дата снятия шины с автомобиля",
            "Пробег шины с начала эксплуатации, км",
            "Техническое состояние шины",
            "Причины снятия шины с эксплутации"};

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
        Date now = new Date();
        String nowDateString = format.format(now);

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFUtils.appendNewRun(paragraph, "Карточка учета", 16, true, false, true);
        XWPFUtils.appendNewRun(paragraph, "работы автомобильной шины", 16, true, false, true);
        XWPFUtils.appendNewRun(paragraph, nowDateString, 14, false, true, false);

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFUtils.appendNewRun(paragraph, "Наименование организации: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, "ОАО Ольса", 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Обозначение шины: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, bus.getIndication(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Модель шины: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, bus.getModel(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Заводской номер шины: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, bus.getFactoryNumber(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Дата изготовления: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, bus.getDateCreate(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Норма слойности или индекс грузоподъемности: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, bus.getNorm() + " руб.", 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Стоимость комплекта шин: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, bus.getCost() + " руб.", 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Предприятие-изготовитель шины: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, bus.getFactory(), 12, false, false, true);

        XWPFTable table = document.createTable();
        XWPFUtils.appendTableHeader(table, headers);
        XWPFUtils.appentTableBody(table, tableCard.getItems());

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFUtils.appendNewRun(paragraph, "Заключение комиссии по определению пригодности шины к эксплуатации", 12,
                false, false, true);

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFUtils.appendNewRun(paragraph, "______________________________________________________________________________",
                12, false, false, false);

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFUtils.appendNewRun(paragraph, "Председатель комиссии: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, txtChairman.getText() + " ___________________________", 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Члены комиссии: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, txtFirstMember.getText() + " ___________________________", 12, false, false, true);
        XWPFUtils.appendNewRun(paragraph, txtSecondMember.getText() + " ___________________________", 12, false, false, true);

        File file = new File("Документ учета работы шины от " + nowDateString + ".docx");
        FileOutputStream out = new FileOutputStream(file);
        document.write(out);
        out.close();
        try {
            java.awt.Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initScene(Bus bus) {
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
            Double mileage = auto.getTrips().stream().mapToDouble(Trip::getMileage).sum();
            String autoNumber = auto.getGosNumber() + ", " + auto.getNumber() + ", " + auto.getModel();
            result.add(new Card(bus.getId().toString(), autoNumber, busAuto.getStartDate(), busAuto.getEndDate(),
                    busAuto.getReason(), busAuto.getCondition(), mileage.intValue()));

        }
        tableCard.setItems(result);
    }

    private void appendNewRun(XWPFParagraph paragraph, String text, int fontSize, boolean bold,
                              boolean needUnderline, boolean needBreak) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setBold(bold);
        run.setText(text);
        if (needUnderline) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
        if (needBreak) {
            run.addBreak();
        }
    }
}
