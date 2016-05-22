package controller;

import dao.AccumulatorDao;
import dao.BusDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import sample.ServiceLocator;
import util.XWPFUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Елена on 19.12.2015.
 */
public class BatteryCardController {

    private DBCotroller dbControl = new DBCotroller();
    @FXML
    private TextField txtFactory;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtDateCreate;
    @FXML
    private TextField txtFactoryNumber;
    @FXML
    private TextField txtGarageNumber;

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
    @FXML
    private TextField txtChairman;
    @FXML
    private TextField txtFirstMember;
    @FXML
    private TextField txtSecondMember;

    private Accumulator accumulator;

    private AccumulatorDao accumulatorDao = ServiceLocator.getAccumulatorDaoInstance();

    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    private String[] headers = {
            "Модель автомобиля, государственный номер автомобиля, гаражный номер автомобиля",
            "Дата установки АКБ на автомобиль",
            "Дата снятия АКБ с автомобиля",
            "Пробег АКБ с начала эксплуатации, тыс. км",
            "Техническое состояние АКБ",
            "Причины снятия АКБ с эксплутации"};

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

        XWPFUtils.appendNewRun(paragraph, "Карточка учета работы АКБ", 16, true, false, true);
        XWPFUtils.appendNewRun(paragraph, accumulator.getNumber() + "   ", 14, false, true, false);
        XWPFUtils.appendNewRun(paragraph, nowDateString + " г.", 14, false, true, false);

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFUtils.appendNewRun(paragraph, "Наименование организации: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, "ОАО Ольса", 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Тип АКБ: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, accumulator.getType(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Изготовитель: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, accumulator.getFactory(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Дата изготовления: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, accumulator.getCreateDate(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Стоимость комплекта АКБ: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, accumulator.getCost().toString(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Заводской номер шины: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, accumulator.getFactoryNumber(), 12, false, false, true);

        XWPFUtils.appendNewRun(paragraph, "Гаражный номер АКБ: ", 12, true, false, false);
        XWPFUtils.appendNewRun(paragraph, accumulator.getNumber() + " руб.", 12, false, false, true);

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

        File file = new File("Документ учета работы АКБ от " + nowDateString + ".docx");
        FileOutputStream out = new FileOutputStream(file);
        document.write(out);
        out.close();
        try {
            java.awt.Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initScene(Accumulator accumulator) {
        this.accumulator = accumulatorDao.findById(accumulator.getId());

        txtFactory.setText(this.accumulator.getFactory());
        txtCost.setText(this.accumulator.getCost().toString());
        txtDateCreate.setText(this.accumulator.getCreateDate());
        txtType.setText(this.accumulator.getType());
        txtFactoryNumber.setText(this.accumulator.getFactoryNumber());
        txtGarageNumber.setText(this.accumulator.getNumber());

        ObservableList<Card> result = FXCollections.observableArrayList();
        for (AccumulatorAuto accumulatorAuto : accumulator.getAutoList()) {
            Auto auto = accumulatorAuto.getAuto();
            Double mileage = auto.getTrips().stream().mapToDouble(Trip::getMileage).sum();
            String autoNumber = auto.getGosNumber() + ", " + auto.getNumber() + ", " + auto.getModel();
            result.add(new Card(accumulator.getId().toString(), autoNumber, accumulatorAuto.getStartDate(),
                    accumulatorAuto.getEndDate(),  accumulatorAuto.getReason(), accumulatorAuto.getCondition(),
                    mileage.intValue()));

        }
        tableCard.setItems(result);
    }
}
