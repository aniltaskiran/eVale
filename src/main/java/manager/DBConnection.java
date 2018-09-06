package manager;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    private  String jdbcDriverStr = "com.mysql.jdbc.Driver";

    private  String jdbcRemoteURL = "jdbc:mysql://mydbinstance.cci6nljv0je1.eu-central-1.rds.amazonaws.com/eVale?useUnicode=true&characterEncoding=UTF-8";
    private  String remoteHostUser = "msdb";
    private  String remoteHostPassword = "2018.09.01DB";

    private  String jdbcURL = "jdbc:mysql://localhost:3306/eVale?useUnicode=true&characterEncoding=UTF-8";
    private  String localHostUser = "root";
    private  String localHostPassword = "M1405.010041m";

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private void startConnection(){
        try {
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcRemoteURL,remoteHostUser,remoteHostPassword);
            //connection = DriverManager.getConnection(jdbcURL,localHostUser,localHostPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*
    public Boolean registerCustomerToStandBy(Customer customer){
        try {
            startConnection();
            String sqlStatement = String.format(
                    "REPLACE INTO " +
                            DB_TABLE_NAMES.TB_STAND_BY_CUSTOMERS.toString() +
                            "(PHONE, PHONE_HASH, CAR_MODEL, CAR_MODEL_ID,ZONE, STATUS, DATE, TIMESTAMP)" +
                            " values ('%s','%s','%s','%s','%s', '%s', NOW(), '%s');",
                    customer.getPhone(),
                    customer.getPhoneHash(),
                    customer.getCarModel(),
                    customer.getCarModelID(),
                    customer.getZone(),
                    customer.getStatus(),
                    CurrentTimestamp.getTimestamp());

            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);

            String sqlStatement2 = String.format(
                    "INSERT INTO " +
                            DB_TABLE_NAMES.BACKUP_TB_STAND_BY_CUSTOMERS.toString() +
                            "(PHONE, PHONE_HASH, CAR_MODEL, CAR_MODEL_ID,ZONE, STATUS, DATE, TIMESTAMP)" +
                            " values ('%s','%s','%s','%s','%s', '%s', NOW(),'%s');",
                    customer.getPhone(),
                    customer.getPhoneHash(),
                    customer.getCarModel(),
                    customer.getCarModelID(),
                    customer.getZone(),
                    customer.getStatus(),
                    CurrentTimestamp.getTimestamp());

            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement2);


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
            return false;
        } finally {
            close();
        }
        return true;
    }
    */

    public Boolean setDeliveredCar(Car car) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        return executeUpdateWithStatement(sqlStatement.setDeliveredCar(car));
    }

    public Boolean setZoneToCar(Car car) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        return executeUpdateWithStatement(sqlStatement.setZoneToCar(car));
    }

    public Boolean updateCurrentCar(Car car) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        return executeUpdateWithStatement(sqlStatement.updateCurrentCar(car));
    }

    public Boolean saveTipForValet(Valet valet) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        // TODO: SQL STATEMENT
        return executeUpdateWithStatement(sqlStatement.saveTipForValet(valet));
    }

    public Boolean registerCar(Car car) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        // TODO: SQL STATEMENT
        return executeUpdateWithStatement(sqlStatement.registerCar(car));
    }

    public Boolean giveAuthorizationToValet(Admin admin) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        return executeUpdateWithStatement(sqlStatement.giveAuthorizationToValet(admin));
    }

    public Boolean setValetInfo(Valet valet) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        return executeUpdateWithStatement(sqlStatement.setValetInfo(valet));
    }

    public ArrayList<Zone> getZoneList(String venueId){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.getZoneList(venueId));
        try {
            return parseTBZoneResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public ArrayList<Valet> getValetPerformance(Valet valet){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.getValetPerformance(valet));
        try {
            return parseTBValetResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }
    public ArrayList<Car> getDeliveryWaitingList(Valet valet){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.deliveryWaitingList(valet));
        try {
            return parseWaitingDeliveryCarResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }


    public ArrayList<Car> getCurrentCarList(Valet valet){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.getCurrentCarList(valet));
        try {
            return parseCurrentCarResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public ArrayList<Car> getZoneWaitingList(Valet valet){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.getZoneWaitingList(valet));
        try {
            return parseTBCurrentAndRegisteredCarResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public Valet getValetInfoFromPhone(String phoneNumber) {
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.getValetInfoWithPhone(phoneNumber));
        try {
            return parseTBValetObjectResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public boolean checkKeyNumberIsAvailable(Car car){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.checkKeyNumberIsAvailable(car));
        try {
            return parseGenericTypeIsAvailable(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
    }

    public boolean checkLicenseTagIsAvailable(Car car){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.checkLicenseTagIsAvailable(car));
        try{
            return parseGenericTypeIsAvailable(resultSet);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            close();
        }

    }

    public boolean checkCarIsRegistered(Car car){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.checkCarIsAvailable(car));
        try{
            return parseCarIsRegistered(resultSet);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
    }

    public Car getPhoneAndBrandId (Car car){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
       // ResultSet resultSet = executeQueryWithStatement(sqlStatement.getPhoneAndBrandId(car));
        try{
            return  parseGetPhoneAndBrandIdResultSet(resultSet);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }



    private Boolean executeUpdateWithStatement(String sqlStatement) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close();
        }
        return true;
    }

    private ResultSet executeQueryWithStatement(String sqlStatement) {
        try {
            statement = connection.createStatement();
            return statement.executeQuery(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Valet parseTBValetObjectResultSet(ResultSet resultSet) throws Exception {
        Valet valet = null;
        while(resultSet.next()){

            valet = new Valet();

           valet.setPhone(resultSet.getString(SqlStatement.TB_VALET.PHONE.toString()));
           valet.setFirstName(resultSet.getString(SqlStatement.TB_VALET.FIRSTNAME.toString()));
           valet.setSurname(resultSet.getString(SqlStatement.TB_VALET.SURNAME.toString()));
           valet.setAdmin(resultSet.getBoolean(SqlStatement.TB_VALET.IS_ADMIN.toString()));
           valet.setAuthorized(resultSet.getBoolean(SqlStatement.TB_VALET.IS_AUTHORIZED.toString()));
           valet.setVenueId(resultSet.getString(SqlStatement.TB_VALET.VENUE_ID.toString()));

        }
        return valet;
    }

    public ArrayList<Valet> parseTBValetResultSet(ResultSet resultSet) throws Exception {
        ArrayList <Valet> valetList = new ArrayList<Valet>();
        while(resultSet.next()){

            Valet valet = new Valet();


            valetList.add(valet);
        }

        return valetList;
    }

    public ArrayList<Zone> parseTBZoneResultSet(ResultSet resultSet) throws Exception {
        ArrayList <Zone> zoneList = new ArrayList<Zone>();
        while(resultSet.next()){

            Zone zone = new Zone();

           zone.setOrderId(resultSet.getInt(SqlStatement.SOURCE_TB_ZONE.ORDER_ID.toString()));
           zone.setVenueId(resultSet.getString(SqlStatement.SOURCE_TB_ZONE.VENUE_ID.toString()));
           zone.setZoneName(resultSet.getString(SqlStatement.SOURCE_TB_ZONE.ZONE_NAME.toString()));

           zoneList.add(zone);
        }

        return zoneList;
    }

    public ArrayList<Car> parseTBCurrentAndRegisteredCarResultSet(ResultSet resultSet) throws Exception {
        ArrayList <Car> carList = new ArrayList<Car>();
        while(resultSet.next()){

            Car car = new Car();
            car.setLicenseTag(resultSet.getString(SqlStatement.TB_CURRENT_CAR.LICENSE_TAG.toString()));
            car.setKeyNumber(resultSet.getString(SqlStatement.TB_CURRENT_CAR.KEY_NUMBER.toString()));
            car.setBrandId(resultSet.getInt(SqlStatement.TB_REGISTERED_CAR.BRAND_ID.toString()));
            carList.add(car);
        }

        return carList;
    }


    public ArrayList<Car> parseWaitingDeliveryCarResultSet(ResultSet resultSet) throws Exception {
        ArrayList <Car> carList = new ArrayList<Car>();
        while(resultSet.next()){

            Car car = new Car();
            car.setLicenseTag(resultSet.getString(SqlStatement.TB_CURRENT_CAR.LICENSE_TAG.toString()));
            car.setKeyNumber(resultSet.getString(SqlStatement.TB_CURRENT_CAR.KEY_NUMBER.toString()));
            car.setBrandId(resultSet.getInt(SqlStatement.TB_REGISTERED_CAR.BRAND_ID.toString()));
            car.setZone(resultSet.getString(SqlStatement.TB_CURRENT_CAR.ZONE.toString()));
            carList.add(car);
        }

        return carList;
    }

    public ArrayList<Car> parseCurrentCarResultSet(ResultSet resultSet) throws Exception {
        ArrayList <Car> carList = new ArrayList<Car>();
        while(resultSet.next()){

            Car car = new Car();
            car.setLicenseTag(resultSet.getString(SqlStatement.TB_CURRENT_CAR.LICENSE_TAG.toString()));
            car.setKeyNumber(resultSet.getString(SqlStatement.TB_CURRENT_CAR.KEY_NUMBER.toString()));
            car.setBrandId(resultSet.getInt(SqlStatement.TB_REGISTERED_CAR.BRAND_ID.toString()));
            car.setZone(resultSet.getString(SqlStatement.TB_CURRENT_CAR.ZONE.toString()));
            car.setRegistrationTimestamp(resultSet.getString(SqlStatement.TB_CURRENT_CAR.REGISTER_TIMESTAMP.toString()));
            carList.add(car);
        }

        return carList;
    }


    public boolean parseGenericTypeIsAvailable(ResultSet resultSet) throws Exception{
        while (resultSet.next()){
            return false;
        }
        return true;
    }

    public boolean parseCarIsRegistered(ResultSet resultSet) throws Exception{
        while (resultSet.next()){
            return true;
        }
        return false;
    }

    public Car parseGetPhoneAndBrandIdResultSet(ResultSet resultSet) throws Exception {

        while(resultSet.next()){

            Car car = new Car();
            car.setPhone(resultSet.getString(SqlStatement.TB_REGISTERED_CAR.PHONE.toString()));
            car.setBrandId(resultSet.getInt(SqlStatement.TB_REGISTERED_CAR.BRAND_ID.toString()));

            return car;
        }

        return null;
    }

    public void close(){
        try {
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        } catch(Exception e){}
    }
}