package manager;

import Security.Hash;
import Utils.CurrentTimestamp;
import model.Customer;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DBConnection {
    enum DB_TABLE_NAMES{
        TB_CUSTOMERS, TB_STAND_BY_CUSTOMERS,BACKUP_TB_STAND_BY_CUSTOMERS;
    }

    enum TB_CUSTOMERS{
        ID, PHONE, PHONE_HASH, CAR_MODEL, CAR_MODEL_ID,DATE;
    }
    enum TB_STAND_BY_CUSTOMERS{
        PHONE, PHONE_HASH, CAR_MODEL, CAR_MODEL_ID, ZONE, STATUS,TIMESTAMP;
    }
    enum BACKUP_TB_STAND_BY_CUSTOMERS {
        PHONE, PHONE_HASH, CAR_MODEL, CAR_MODEL_ID, ZONE,DATE;
    }

    private  String jdbcDriverStr = "com.mysql.jdbc.Driver";
    private  String jdbcURL = "jdbc:mysql://localhost:3306/eVale?useUnicode=true&characterEncoding=UTF-8";
    private  String localHostUser = "root";
    private  String localHostPassword = "M1405.010041m";

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private void startConnection(){
        try {
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcURL,localHostUser,localHostPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public Boolean registerCustomer(Customer customer){
        try {
            startConnection();
            String sqlStatement = String.format(
                    "INSERT INTO " +
                            DB_TABLE_NAMES.TB_CUSTOMERS.toString() +
                            "(ID, PHONE, PHONE_HASH, CAR_MODEL, CAR_MODEL_ID, DATE, TIMESTAMP)" +
                            " values (default, '%s','%s','%s','%s', NOW(),'%s');",
                    customer.getPhone(),
                    customer.getPhoneHash(),
                    customer.getCarModel(),
                    customer.getCarModelID(),
                    CurrentTimestamp.getTimestamp());

            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
            return false;
        } finally {
            close();
        }
        return true;
    }


    public Boolean deleteCustomerFromStandBy(String phone){
        try {
            startConnection();
            String sqlStatement = String.format(
                    "DELETE FROM " +
                            DB_TABLE_NAMES.TB_STAND_BY_CUSTOMERS.toString() +
                            "WHERE " +
                            TB_STAND_BY_CUSTOMERS.PHONE.toString() +
                            " =%s;", phone);

            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
            return false;
        } finally {
            close();
        }
        return true;
    }


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

    public ArrayList<Customer> getStandByCustomerList(){
        try {
            startConnection();
            String sqlStatement = String.format(
                    "SELECT * from " +
                            DB_TABLE_NAMES.TB_STAND_BY_CUSTOMERS.toString() + ";");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);
            return parseTBCustomerListStandByResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            close();
        }
        return null;
    }

    public ArrayList<Customer> getCustomerInfoFromPhone(String phoneNumber){
        try {
            startConnection();
            String sqlStatement = String.format(
                    "SELECT * from " +
                            DB_TABLE_NAMES.TB_CUSTOMERS.toString() +
                            " where phone = '%s';", phoneNumber);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);
            return parseTBCustomerResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            close();
        }
        return null;
    }

    public Customer getCustomerInfoFromPhoneHash(String phoneHash){
        try {
            startConnection();
            String sqlStatement = String.format(
                    "SELECT * from " +
                            DB_TABLE_NAMES.TB_STAND_BY_CUSTOMERS.toString() +
                            " where " + TB_STAND_BY_CUSTOMERS.PHONE_HASH.toString() + " = '%s';", phoneHash);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);
            return parseTBStandByCustomerResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            close();
        }
        return null;
    }

    public ArrayList<Customer> parseTBCustomerResultSet(ResultSet resultSet) throws Exception {
        ArrayList customerList = new ArrayList<Customer>();

        while(resultSet.next()){
            String phone = resultSet.getString(TB_CUSTOMERS.PHONE.toString());
            String carModel = resultSet.getString(TB_CUSTOMERS.CAR_MODEL.toString());
            Integer carModelID = resultSet.getInt(TB_CUSTOMERS.CAR_MODEL_ID.toString());
            customerList.add(new Customer(phone,carModel,carModelID));
        }
        return customerList;
    }

    public ArrayList<Customer> parseTBCustomerListStandByResultSet(ResultSet resultSet) throws Exception {
        ArrayList customerList = new ArrayList<Customer>();

        while(resultSet.next()){
            String phone = resultSet.getString(TB_STAND_BY_CUSTOMERS.PHONE.toString());
            String zone = resultSet.getString(TB_STAND_BY_CUSTOMERS.ZONE.toString());
            String carModel = resultSet.getString(TB_STAND_BY_CUSTOMERS.CAR_MODEL.toString());
            Integer carModelID = resultSet.getInt(TB_STAND_BY_CUSTOMERS.CAR_MODEL_ID.toString());
            Integer status = resultSet.getInt(TB_STAND_BY_CUSTOMERS.STATUS.toString());
            String timestamp = resultSet.getString(TB_STAND_BY_CUSTOMERS.TIMESTAMP.toString());
            customerList.add(new Customer(phone,zone,carModel,carModelID,status,timestamp));
        }
        return customerList;
    }

    public Customer parseTBStandByCustomerResultSet(ResultSet resultSet) throws Exception {
        while(resultSet.next()){
            String phone = resultSet.getString(TB_STAND_BY_CUSTOMERS.PHONE.toString());
            String zone = resultSet.getString(TB_STAND_BY_CUSTOMERS.ZONE.toString());
            String carModel = resultSet.getString(TB_STAND_BY_CUSTOMERS.CAR_MODEL.toString());
            Integer carModelID = resultSet.getInt(TB_STAND_BY_CUSTOMERS.CAR_MODEL_ID.toString());
            Integer status = resultSet.getInt(TB_STAND_BY_CUSTOMERS.STATUS.toString());
            String timestamp = resultSet.getString(TB_STAND_BY_CUSTOMERS.TIMESTAMP.toString());

            return new Customer(phone,zone,carModel,carModelID,status,timestamp);
        }
        return null;
    }

    private void close(){
        try {
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        } catch(Exception e){}
    }
}