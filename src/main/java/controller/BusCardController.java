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
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
        String nowDateString = format.format(now);
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(16);
        run.setBold(true);
        run.setText("Карточка учета");
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(16);
        run.setBold(true);
        run.setText("работы автомобильной шины");
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(14);
        run.setUnderline(UnderlinePatterns.SINGLE);
        run.setText(nowDateString);

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Наименование организации: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText("ОАО Ольса");
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Обозначение шины: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(bus.getIndication());
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Модель шины: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(bus.getModel());
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Заводской номер шины: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(bus.getFactoryNumber());
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Дата изготавления: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(bus.getDateCreate());
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Норма слойности или индекс грузоподъемности: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(bus.getNorm());
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Стоимость комплекта шин: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(bus.getCost() + " руб.");
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Предприятие-изготовитель шины: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(bus.getFactory());
        run.addBreak();

        XWPFTable table = document.createTable();

        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Модель автомобиля (прицепа), его государственный номер");
        headerRow.addNewTableCell().setText("Дата установки шины на колесо автомобиля");
        headerRow.addNewTableCell().setText("Дата снятия шины с автомобиля");
        headerRow.addNewTableCell().setText("Пробег шины с начала эксплуатации, км");
        headerRow.addNewTableCell().setText("Техническое состояние шины");
        headerRow.addNewTableCell().setText("Причины снятия шины с эксплутации");

        for (Card card : tableCard.getItems()) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(card.getAuto());
            tableRow.getCell(1).setText(card.getDateAdd());
            tableRow.getCell(2).setText(card.getDateDel());
            tableRow.getCell(3).setText(String.valueOf(card.getMilage()));
            tableRow.getCell(4).setText(card.getState());
            tableRow.getCell(5).setText(card.getReason());
        }

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.addBreak();
        run.setFontSize(12);
        run.setText("Заключение комиссии по определению пригодности шины к эксплуатации");
        run.addBreak();

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setFontSize(12);
        run.setText("______________________________________________________________________________");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Председатель комиссии: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(txtChairman.getText() + " ___________________________");
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setText("Члены комиссии: ");
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(txtFirstMember.getText() + " ___________________________");
        run.addBreak();

        run = paragraph.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run = paragraph.createRun();
        run.setBold(false);
        run.setText(txtFirstMember.getText() + " ___________________________");

        File file = new File("Документ от " + nowDateString + ".docx");
        FileOutputStream out = new FileOutputStream(file);
        document.write(out);
        out.close();
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
            Double mileage = auto.getTrips().stream().mapToDouble(Trip::getMileage).sum();
            String autoNumber = auto.getGosNumber() + ", " + auto.getNumber() + ", " + auto.getModel();
            result.add(new Card(bus.getId().toString(), autoNumber, busAuto.getStartDate(), busAuto.getEndDate(),
                    busAuto.getReason(), busAuto.getCondition(), mileage.intValue()));

        }
        tableCard.setItems(result);
    }
}
