package model;

import static model.SqlStatement.TB_VALET.PHONE;

public class SqlStatement {
   public enum DB_TABLE_NAMES{
       TB_VALET, TB_CURRENT_CAR, TB_REGISTERED_CAR,
       SOURCE_TB_ZONE,
       LOG_TB_CAR
       ;
    }

    public enum TB_VALET {
        PHONE, FIRSTNAME, SURNAME, IS_AUTHORIZED, IS_ADMIN, VENUE_ID;
    }

    public enum SOURCE_TB_ZONE {
       ORDER_ID, ZONE_NAME, VENUE_ID;
    }

    public enum TB_CURRENT_CAR {
        LICENSE_TAG, VENUE_ID, KEY_NUMBER, ZONE, CAR_STATUS, LICENSE_TAG_HASH, REGISTER_TIMESTAMP,DELIVER_VALET_ID,REGISTER_VALET_ID, REGISTER_DATE, DELIVER_DATE;
    }

    public enum TB_REGISTERED_CAR {
       LICENSE_TAG, BRAND_ID, PHONE, LICENSE_TAG_HASH;;
    }

    public static String CAR_WAITING_DELIVERY_STATUS = "2";
    public static String CAR_DELIVERED_STATUS = "0";

    public String getZoneList(String venueId) {
      //  SELECT * FROM SOURCE_TB_ZONE WHERE VENUE_ID = '345001';

        String sqlStatement = String.format(
                "SELECT * FROM " +
                        DB_TABLE_NAMES.SOURCE_TB_ZONE.toString() +
                        " WHERE " +
                        SOURCE_TB_ZONE.VENUE_ID.toString() +
                        " = '%s';",
                        venueId);

        return  sqlStatement;
    }

    public String getValetPerformance(Valet valet) {

        //  select * from TB_VALET_INCOME WHERE VENUE_ID = 35;
        String sqlStatement = String.format(
                "SELECT * FROM " +
                        // TODO: TB NAME
                       // DB_TABLE_NAMES.TB_VALET_INCOME.toString() +
                        " WHERE " +
                        SOURCE_TB_ZONE.VENUE_ID.toString() +
                        " = '%s';",
                valet.getVenueID());

        return  sqlStatement;
    }


    public String setValetInfo(Valet valet) {
        String sqlStatement = String.format(
                "INSERT INTO "+ DB_TABLE_NAMES.TB_VALET.toString() +
                        " (PHONE, FIRSTNAME, SURNAME, IS_AUTHORIZED, IS_ADMIN, VENUE_ID) " +
                        "VALUES ('%s','%s', '%s',%B, false,'%s') " +
                        "ON DUPLICATE KEY UPDATE FIRSTNAME = '%s', SURNAME = '%s', IS_AUTHORIZED = %B, VENUE_ID = '%s';",
                valet.getPhone(),
                valet.getFirstName(),
                valet.getSurname(),
                valet.isAuthorized(),
                valet.getVenueID(),
                valet.getFirstName(),
                valet.getSurname(),
                valet.isAuthorized(),
                valet.getVenueID());

        return  sqlStatement;
    }

    public String saveTipAndMoveCarToLogCar(Valet valet) {

        String sqlStatement = String.format(
                "INSERT INTO "+ DB_TABLE_NAMES.LOG_TB_CAR.toString() +
                "(VENUE_ID, LICENSE_TAG, KEY_NUMBER, CAR_OWNER_PHONE, CAR_BRAND_ID, " +
                        "LAST_ZONE, CAR_STATUS, REGISTER_DATE, REGISTER_TIMESTAMP, " +
                        "REGISTER_VALET_ID, DELIVER_VALET_ID, DELIVER_TIMESTAMP, " +
                        "DELIVER_DATE, LICENSE_TAG_HASH, VALET_INCOME)" +
                        "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',UNIX_TIMESTAMP(),NOW(),'%s','%s');",
                valet.getVenueID(),
                valet.getCar().getLicenseTag(),
                valet.getCar().getKeyNumber(),
                valet.getCar().getPhone(),
                valet.getCar().getBrandID(),
                valet.getCar().getZone(),
                CAR_DELIVERED_STATUS,
                valet.getCar().getRegisterDate(),
                valet.getCar().getRegisterTimestamp(),
                valet.getCar().getRegisterValetId(),
                valet.getPhone(),
                valet.getCar().getLicenseTagHash(),
                valet.getIncome());

        return  sqlStatement;
    }



    public String getValetInfoWithPhone(Valet valet) {
        String sqlStatement = String.format(
                "SELECT * FROM " +
                        DB_TABLE_NAMES.TB_VALET.toString() +
                        " WHERE " +
                        PHONE.toString() +
                        " = '%s'", valet.getPhone());
        return  sqlStatement;
    }

    public String checkKeyNumberIsAvailable(Car car){

       String sqlStatement = String.format(
               "SELECT * FROM " +
                       DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                       " WHERE " +
                       TB_CURRENT_CAR.KEY_NUMBER.toString() +
                       " = '%s' AND " +
                       TB_CURRENT_CAR.VENUE_ID.toString() +
                       " = '%s' ;", car.getKeyNumber(), car.getVenueID());
       return sqlStatement;
    }

    public String checkLicenseTagIsAvailable(Car car){
       String sqlStatement = String.format(
               " SELECT * FROM " +
                       DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                       " WHERE " +
                       TB_CURRENT_CAR.LICENSE_TAG.toString() +
                       " = '%s' ;", car.getLicenseTag());

       return sqlStatement;
    }

    public String checkCarIsAvailable (Car car){
        String sqlStatement = String.format(
                " SELECT * FROM " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " WHERE " +
                        TB_REGISTERED_CAR.LICENSE_TAG.toString() +
                        " = '%s' ;", car.getLicenseTag());
        return sqlStatement;
    }

    public String getPhoneAndBrandId(Car car){
        String sqlStatement = String.format(
                "SELECT " +
                        TB_REGISTERED_CAR.PHONE.toString() +
                        " , " +
                        TB_REGISTERED_CAR.BRAND_ID.toString() +
                        " FROM " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " WHERE " +
                        TB_REGISTERED_CAR.LICENSE_TAG.toString() +
                        " = '%s' ;", car.getLicenseTag());
        return sqlStatement;
    }

    public String getTBCurrentCarLog(Valet valet){
        String sqlStatement = String.format(
                "SELECT *" +
                        " FROM " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " AS CC " +
                        "INNER JOIN " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " AS RGC ON CC.LICENSE_TAG = RGC.LICENSE_TAG " +
                        "WHERE CC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = '%s' ;", valet.getIncomeLicenseTag());
        return sqlStatement;


        /*
        select *
from TB_CURRENT_CAR AS CC
INNER JOIN TB_REGISTERED_CAR AS RGC ON

         */
    }



    /*
    INSERT INTO TB_REGISTERED_CAR (LICENSE_TAG, BRAND_ID, PHONE, LICENSE_TAG_HASH)
VALUES ('34BJK1903','1','543434','asdasdas')
ON DUPLICATE KEY UPDATE BRAND_ID = '1', PHONE = '543434';


     */

    public String updateBrandID(Car car){
        String sqlStatement = String.format(
                " UPDATE " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " SET BRAND_ID = '%s' WHERE " +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = '%s';", car.getBrandID(), car.getLicenseTag());
        return sqlStatement;
    }

    public String registerCar (Car car){
       String sqlStatement = String.format(
               "INSERT INTO " +
                       DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                       " (LICENSE_TAG, BRAND_ID, PHONE, LICENSE_TAG_HASH) " +
                       " VALUES " +
                       " ( '%s', '%d', '%s', '%s')" +
                       "ON DUPLICATE KEY UPDATE " +
                       TB_REGISTERED_CAR.BRAND_ID.toString() +
                       " = '%s', " +
                       TB_REGISTERED_CAR.PHONE.toString() +
                       " = '%s';",
               car.getLicenseTag(),
               car.getBrandID(),
               car.getPhone(),
               car.getLicenseTagHash(),
               car.getBrandID(),
               car.getPhone());
       
       return sqlStatement;
    }


    public String saveCarToCurrentCar(Car car) {
        String sqlStatement = String.format(
                "INSERT INTO "+ DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        "(LICENSE_TAG, VENUE_ID, KEY_NUMBER, REGISTER_DATE, REGISTER_TIMESTAMP, REGISTER_VALET_ID)" +
                        "VALUES ('%s','%s','%s',NOW(), UNIX_TIMESTAMP(), '%s');",
                car.getLicenseTag(),
                car.getVenueID(),
                car.getKeyNumber(),
                car.getValetID());

        return  sqlStatement;
    }

    public String getZoneWaitingList(Valet valet) {
        String sqlStatement = String.format(
                " SELECT CC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        ", CC." +
                        TB_CURRENT_CAR.KEY_NUMBER.toString() +
                        ", RGC." +
                        TB_REGISTERED_CAR.BRAND_ID.toString() +
                        " FROM " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " AS CC " +
                        "INNER JOIN " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " AS RGC ON CC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = RGC." +
                        TB_REGISTERED_CAR.LICENSE_TAG.toString() +
                        " WHERE " +
                        TB_CURRENT_CAR.ZONE.toString() +
                        " IS NULL AND " + TB_CURRENT_CAR.VENUE_ID.toString() +
                        " = '%s' ;", valet.getVenueID());

        return sqlStatement;
    }

    public String deliveryWaitingList(Valet valet) {
        String sqlStatement = String.format(
                " SELECT CC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        ", CC." +
                        TB_CURRENT_CAR.KEY_NUMBER.toString() +
                        ", CC." +
                        TB_CURRENT_CAR.ZONE.toString() +
                        ", RGC." +
                        TB_REGISTERED_CAR.BRAND_ID.toString() +
                        " FROM " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " AS CC " +
                        "INNER JOIN " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " AS RGC ON CC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = RGC." +
                        TB_REGISTERED_CAR.LICENSE_TAG.toString() +
                        " WHERE " +
                        TB_CURRENT_CAR.CAR_STATUS.toString() +
                        " = '%s' AND " + TB_CURRENT_CAR.VENUE_ID.toString() +
                        " = '%s' ;", CAR_WAITING_DELIVERY_STATUS, valet.getVenueID());

        return sqlStatement;
    }

    public String getCurrentCarList(Valet valet) {
        String sqlStatement = String.format(
                " SELECT CC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        ", CC." +
                        TB_CURRENT_CAR.KEY_NUMBER.toString() +
                        ", CC." +
                        TB_CURRENT_CAR.ZONE.toString() +
                        ", CC." +
                        TB_CURRENT_CAR.REGISTER_DATE.toString() +
                        ", RGC." +
                        TB_REGISTERED_CAR.BRAND_ID.toString() +
                        ", RGC." +
                        TB_REGISTERED_CAR.PHONE.toString() +
                        " FROM " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " AS CC " +
                        "INNER JOIN " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " AS RGC ON CC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = RGC." +
                        TB_REGISTERED_CAR.LICENSE_TAG.toString() +
                        " WHERE " +
                        TB_CURRENT_CAR.VENUE_ID.toString() +
                        " = '%s' ;", valet.getVenueID());

        return sqlStatement;
    }

    public String getCurrentCarWithDateRange(Admin admin) {
        String sqlStatement = String.format(
                "SELECT " +
                        "REGISTER_DATE, DELIVER_DATE, CRC.LICENSE_TAG, ZONE, RGC.BRAND_ID,VL.FIRSTNAME, VL.SURNAME" +
                         " FROM " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " AS CRC " +
                        "INNER JOIN " +
                        DB_TABLE_NAMES.TB_REGISTERED_CAR.toString() +
                        " AS RGC ON CRC." +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = RGC." +
                        TB_REGISTERED_CAR.LICENSE_TAG.toString() +
                        " INNER JOIN " +
                        DB_TABLE_NAMES.TB_VALET.toString() +
                        " AS VL ON VL." +
                        TB_VALET.PHONE.toString() +
                        " = CRC." +
                        TB_CURRENT_CAR.DELIVER_VALET_ID.toString() +
                        " OR VL." +
                        TB_VALET.PHONE.toString() +
                        " = CRC." +
                        TB_CURRENT_CAR.REGISTER_VALET_ID.toString() +
                        " WHERE CRC." +
                        TB_CURRENT_CAR.VENUE_ID.toString() +
                        " = '%s' AND " +
                        TB_CURRENT_CAR.REGISTER_DATE.toString() +
                        " > '%s' AND " +
                        TB_CURRENT_CAR.REGISTER_DATE.toString() +
                        " < '%s' " +
                        ";",
                admin.getVenueID(),
                admin.getSelectedDateFirst(),
                admin.getSelectedDateSecond());

        return sqlStatement;
    }


    public String setZoneToCar (Car car){

        String sqlStatement = String.format(
                " UPDATE " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " SET ZONE = '%s' WHERE " +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = '%s';", car.getZone(), car.getLicenseTag());
        return sqlStatement;
    }


    public String setCarStatus(Car car){
        String sqlStatement = String.format(
                " UPDATE " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " SET CAR_STATUS = '%s' WHERE " +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = '%s';", car.getStatus(), car.getLicenseTag());
        return sqlStatement;
    }

    public String updateCurrentCar (Car car){

        String sqlStatement = String.format(
                " UPDATE " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " SET CAR_STATUS = '%s' WHERE " +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = '%s';", car.getStatus(), car.getLicenseTag());
        return sqlStatement;
    }

    public String removeValetAuthorization (Valet valet){

        String sqlStatement = String.format(
                " UPDATE " +
                        DB_TABLE_NAMES.TB_VALET.toString() +
                        " SET " +
                        TB_VALET.IS_AUTHORIZED.toString() +
                        " = '%s', " +
                        TB_VALET.VENUE_ID.toString() +
                        " = '0' WHERE " +
                        TB_VALET.PHONE.toString() +
                        " = '%s';", valet.isAuthorized(), valet.getPhone());
        return sqlStatement;
    }

    public String removeFromTBCurrentCar (Valet valet){

        String sqlStatement = String.format(
                " DELETE FROM " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " WHERE " +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = '%s';", valet.getIncomeLicenseTag());
        return sqlStatement;
    }

    public String wrongEntity (Car car){
        String sqlStatement = String.format(

                " DELETE FROM " +
                        DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        " WHERE " +
                        TB_CURRENT_CAR.LICENSE_TAG.toString() +
                        " = '%s'; ",
                car.getLicenseTag());

        return sqlStatement;
    }



/*
   UPDATE TB_VALET
set
  IS_AUTHORIZED = false,
  VENUE_ID = '0'
WHERE PHONE = '2128834401';
*/
}
