package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bus;
import model.Card;
import model.DBCotroller;

import java.io.File;
import java.io.IOException;

/**
 * Created by ����� on 17.12.2015.
 */
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


    private Bus bus = new Bus();

    @FXML
    private void initialize() {
           setBus();
        setTable();
    }

    private void setBus() {
        bus = (Bus)ExportData.getInstance().myObject;

        txtFactory.setText(bus.getFactory());
        txtCost.setText(bus.getCost());
        txtNorm.setText(bus.getNorm());
        txtDateCreate.setText(bus.getDateCreate());
        txtFactoryNumber.setText(bus.getFactoryNumber());
        txtModel.setText(bus.getModel());
        txtIndication.setText(bus.getIndication());
    }



    private void setTable(){

        Card card = new Card();
        autoColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("auto"));
        dateAddColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("dateAdd"));
        dateDelColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("dateDel"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("reason"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("state"));
        milageColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("milage"));

        ObservableList<Card> tmp = dbControl.selectCard(bus.getId());
        ObservableList<Card> result = FXCollections.observableArrayList();

        int milage=0;
        int i=0;
        String tmpsts =tmp.get(i).getId();
        for(Card b : tmp){
            if(b.getId() == tmpsts)
            {
                milage+=b.getMilage();
                i++;
            }
            else
            {
                tmpsts = b.getId();
                result.add(new Card(tmp.get(i-1).getAuto(), tmp.get(i-1).getDateAdd(),tmp.get(i-1).getDateDel(),tmp.get(i-1).getReason(),tmp.get(i-1).getState(), milage));
                milage = 0;
            }
        }

        tableCard.setItems(tmp);

    }

    @FXML
    private void  openCard()
    {
        File file = new File("D:\\курс 5\\9 семестр\\курсовой\\документы\\Карточка_учета_работы_автомобильной_шины.doc");
        try {
            java.awt.Desktop.getDesktop().open(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

       // "D:\\курс 5\\9 семестр\\курсовой\\документы\\Карточка_учета_работы_автомобильной_шины.doc"
    }
}
