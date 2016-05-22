package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.dialog.Dialogs;

import java.sql.*;

/**
 * Created by Елена on 26.08.2015.
 */
public class DBCotroller {

    private String getConnectionString() {
        String conString = "jdbc:sqlserver://localhost:1433;databaseName=mechanicDB;user=test_mechanic;password=pass1234;";
     /*   try {
            BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream("D:\\Java\\examples\\praktika\\src\\ConnectionString")));
            conString = br.readLine();

            br.close();
        }
        catch (IOException e){
            Dialogs.create()
                    .message("Нет соединения с базой\n" + e.getMessage())
                    .showError();
            e.printStackTrace();
        }
*/
        return conString;
    }

    public Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
        }
        try {
            String url = getConnectionString();
            dbConnection = DriverManager.getConnection(getConnectionString());
            return dbConnection;
        } catch (SQLException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
        }
        return dbConnection;
    }

    public int maxId() {
//        int _id = 0;
//
//        //получение данных из файла БД
//        Connection dbConnection = null; //переменная; хранит соединение с базой данных
//        Statement statement = null; //переменная; хранит и выполняет sql-запросы
//        ResultSet rs = null; //переменная; получает результаты выполнения sql-запросов
//
//        try {
//            dbConnection = getDBConnection();
//            statement = dbConnection.createStatement();
//
//            //выполнение SQL запроса
//            rs = statement.executeQuery("Select MAX(Id_шина)as Id_шина From Шина");
//
//            //обработка результатов всех запросов
//            while (rs.next()) {
//                _id = rs.getInt("Id_шина");
//            }
//
//        } catch (SQLException e) {
//            Dialogs.create()
//                    .message(e.getMessage())
//                    .showError();
//            e.printStackTrace();
//        } finally { //закрытие всех объектов, чтобы соединение с БД не осталось занимая пути...
//            try {
//                if (rs != null) rs.close();
//                if (statement != null) statement.close();
//            } catch (Exception e) {
//                Dialogs.create()
//                        .message(e.getMessage())
//                        .showError();
//            }
//        }
//        return _id;
        return 0;
    }

    public void delete(String deleteSQL) {

        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполняем запрос delete SQL
            statement.execute(deleteSQL);
            Dialogs.create()
                    .title("Результат операции")
                    .message("Запись  удалена\n Обновите таблицу")
                    .showInformation();
        } catch (SQLException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
            }
        }
    }

    public void update(String updateSQL) {

        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполняем запрос update SQL
            statement.execute(updateSQL);
            Dialogs.create()
                    .title("Результат операции")
                    .message("Запись  изменене\n Обновите таблицу")
                    .showInformation();
        } catch (SQLException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
            }
        }
    }

    public void insertBus(Bus bus) {

        String insertSQL = "INSERT INTO Шина " +
                "(Id_шина, Стоимость_комплекта, Дата_изготовления, Завод_изготовитель, Обозначение, Модель, Заводской_номер, Норма_слойности)" +
                " VALUES ('" + bus.getId() + "', '" + bus.getCost() +
                "', '" + bus.getDateCreate() + "', '" + bus.getFactory() +
                "', '" + bus.getIndication() + "', '" + bus.getModel() +
                "', '" + bus.getFactoryNumber() + "', '" + bus.getNorm() + "')";

        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполняем запрос insert SQL
            statement.executeUpdate(insertSQL);

            Dialogs.create()
                    .title("Результат операции, сэр")
                    .message("Запись  добавлена, сэр\n Обновите таблицу")
                    .showInformation();
        } catch (SQLException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
            }
        }
    }

    public ObservableList<Card> selectCard(String id) {
        ObservableList<Card> cards = FXCollections.observableArrayList();

        //получение данных из файла БД
        Connection dbConnection = null; //переменная; хранит соединение с базой данных
        Statement statement = null; //переменная; хранит и выполняет sql-запросы
        ResultSet rs = null; //переменная; получает результаты выполнения sql-запросов

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            //выполнение SQL запроса
            rs = statement.executeQuery("SELECT     dbo.Рейс.Пробег_км, dbo.Авто.Гаражный_номер, dbo.Авто.Гос_номер, dbo.Авто.Марка, dbo.Авто.Id_Авто, \n" +
                    "\t\t\tdbo.Шины_на_авто.Тех_состояние, dbo.Шины_на_авто.Причина_снятия, \n" +
                    "            Cast(dbo.Шины_на_авто.Дата_снятия as Date)as Дата_снятия, Cast(dbo.Шины_на_авто.Дата_установки as Date)as Дата_установки\n" +
                    "FROM         dbo.Авто INNER JOIN\n" +
                    "                      dbo.Рейс ON dbo.Авто.Id_Авто = dbo.Рейс.Id_Авто INNER JOIN\n" +
                    "                      dbo.Шины_на_авто ON dbo.Авто.Id_Авто = dbo.Шины_на_авто.Id_Авто INNER JOIN\n" +
                    "                      dbo.Шина ON dbo.Шины_на_авто.Id_шина = dbo.Шина.Id_шина\n" +
                    "WHERE     (dbo.Шина.Id_шина = " + id + ")\n" +
                    "ORDER BY dbo.Авто.Id_Авто");

            //обработка результатов всех запросов
            while (rs.next()) {
                cards.add(
                        new Card(rs.getString("Id_авто"),
                                rs.getString("Гаражный_номер") + ", " + rs.getString("Гос_номер") + ", " + rs.getString("Марка"),
                                rs.getString("Дата_установки"),
                                rs.getString("Дата_снятия"),
                                rs.getString("Причина_снятия"),
                                rs.getString("Тех_состояние"),
                                rs.getInt("Пробег_км")
                        ));
            }

        } catch (SQLException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
            e.printStackTrace();
        } finally { //закрытие всех объектов, чтобы соединение с БД не осталось занимая пути...
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (Exception e) {
                Dialogs.create()
                        .message(e.getMessage())
                        .showError();
            }
        }
        return cards;
    }

    public ObservableList<Bus> selectBus(String sql) {
        ObservableList<Bus> buses = FXCollections.observableArrayList();

        //получение данных из файла БД
        Connection dbConnection = null; //переменная; хранит соединение с базой данных
        Statement statement = null; //переменная; хранит и выполняет sql-запросы
        ResultSet rs = null; //переменная; получает результаты выполнения sql-запросов

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            //выполнение SQL запроса
            rs = statement.executeQuery(sql);

            //обработка результатов всех запросов
//            while (rs.next()) {
//                buses.add(
//                        new Bus(rs.getString("Id_шина"), rs.getString("Стоимость_комплекта"), rs.getString("Дата_изготовления"), rs.getString("Завод_изготовитель"),
//                                rs.getString("Заводской_номер"), rs.getString("Обозначение"), rs.getString("Модель"), rs.getString("Норма_слойности")));
//            }

        } catch (SQLException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
            e.printStackTrace();
        } finally { //закрытие всех объектов, чтобы соединение с БД не осталось занимая пути...
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (Exception e) {
                Dialogs.create()
                        .message(e.getMessage())
                        .showError();
            }
        }
        return buses;
    }

    public ObservableList<Battery> selectBattery(String sql) {
        ObservableList<Battery> batteries = FXCollections.observableArrayList();

        //получение данных из файла БД
        Connection dbConnection = null; //переменная; хранит соединение с базой данных
        Statement statement = null; //переменная; хранит и выполняет sql-запросы
        ResultSet rs = null; //переменная; получает результаты выполнения sql-запросов

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            //выполнение SQL запроса
            rs = statement.executeQuery(sql);

            //обработка результатов всех запросов
            while (rs.next()) {
                batteries.add(
                        new Battery(rs.getString("Id_Аккумулятор"), rs.getString("Стоимость"), rs.getString("Дата_изготовления"), rs.getString("Завод_изготовитель"),
                                rs.getString("Заводской_номер"), rs.getString("Тип"), rs.getString("Гаражный_номер")));
            }

        } catch (SQLException e) {
            Dialogs.create()
                    .message(e.getMessage())
                    .showError();
            e.printStackTrace();
        } finally { //закрытие всех объектов, чтобы соединение с БД не осталось занимая пути...
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (Exception e) {
                Dialogs.create()
                        .message(e.getMessage())
                        .showError();
            }
        }
        return batteries;
    }
}
