package manager;

import model.Admin;
import model.SqlStatement;
import model.Valet;
import model.Zone;

import java.sql.*;

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

    public Zone getZoneList(String venueId){
        startConnection();
        SqlStatement sqlStatement = new SqlStatement();
        ResultSet resultSet = executeQueryWithStatement(sqlStatement.);
        try {
            return parseTBZoneResultSet(resultSet);
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
            return parseTBValetResultSet(resultSet);
        } catch (Exception e) {
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

    public Valet parseTBValetResultSet(ResultSet resultSet) throws Exception {
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

    public Zone parseTBZoneResultSet(ResultSet resultSet) throws Exception {
        Zone zone = null;
        while(resultSet.next()){

            zone = new Zone();

           zone.setOrderId(resultSet.getString());
        }
        return valet;
    }

    public void close(){
        try {
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        } catch(Exception e){}
    }
}