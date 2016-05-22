package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Battery;
import model.Bus;
import model.Card;
import model.DBCotroller;

import java.io.File;
import java.io.IOException;

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


    private Battery battery = new Battery();

    @FXML
    private void initialize() {
        setBus();
        setTable();
    }

    private void setBus() {
        battery = (Battery)ExportData.getInstance().myObject;

        txtFactory.setText(battery.getFactory());
        txtCost.setText(battery.getCost());
        txtGarageNumber.setText(battery.getGarageNumber());
        txtDateCreate.setText(battery.getDateCreate());
        txtFactoryNumber.setText(battery.getFactoryNumber());
        txtType.setText(battery.getType());
    }



    private void setTable(){

        Card card = new Card();
        autoColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("auto"));
        dateAddColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("dateAdd"));
        dateDelColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("dateDel"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("reason"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("state"));
        milageColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("milage"));

        ObservableList<Card> tmp = dbControl.selectCard(battery.getId());
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
        File file = new File("D:\\курс 5\\9 семестр\\курсовой\\документы\\Карточка_учета_работы_АКБ.doc");
        try {
            java.awt.Desktop.getDesktop().open(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
