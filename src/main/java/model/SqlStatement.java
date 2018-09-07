package model;

import static model.SqlStatement.TB_VALET.PHONE;

public class SqlStatement {
   public enum DB_TABLE_NAMES{
       TB_VALET, SOURCE_TB_ZONE, TB_CURRENT_CAR, TB_REGISTERED_CAR;
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
                valet.getVenueId());

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
                valet.getVenueId(),
                valet.getFirstName(),
                valet.getSurname(),
                valet.isAuthorized(),
                valet.getVenueId());

        return  sqlStatement;
    }

    public String saveTipForValet(Valet valet) {
       // TODO: tip kaydedildiğinde current cardan log'a aktar ardından log car'daki deliver column'u düzenle ardundan current cardan drop et
        String sqlStatement = String.format(
           //     "INSERT INTO "+ DB_TABLE_NAMES.TB_VALET_INCOME.toString() +
                        "(PHONE, DATE, TIMESTAMP, INCOME, VENUE_ID, LICENSE_TAG)" +
                        "VALUES ('%s', NOW(), '%s', '%s', '%s', '%s');",
                valet.getPhone(),
                valet.getTimestamp(),
                valet.getIncome(),
                valet.getVenueId(),
                valet.getIncomeLicenseTag());

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
                       " = '%s' ;", car.getKeyNumber(), car.getVenueId());
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

    /*
    INSERT INTO TB_REGISTERED_CAR (LICENSE_TAG, BRAND_ID, PHONE, LICENSE_TAG_HASH)
VALUES ('34BJK1903','1','543434','asdasdas')
ON DUPLICATE KEY UPDATE BRAND_ID = '1', PHONE = '543434';


     */

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
               car.getBrandId(),
               car.getPhone(),
               car.getLicenseTagHash(),
               car.getBrandId(),
               car.getPhone());
       
       return sqlStatement;
    }


    public String saveCarToCurrentCar(Car car) {
        String sqlStatement = String.format(
                "INSERT INTO "+ DB_TABLE_NAMES.TB_CURRENT_CAR.toString() +
                        "(LICENSE_TAG, VENUE_ID, KEY_NUMBER, REGISTER_DATE, REGISTER_TIMESTAMP, REGISTER_VALET_ID)" +
                        "VALUES ('%s','%s','%s',NOW(), UNIX_TIMESTAMP(), '%s');",
                car.getLicenseTag(),
               car.getVenueId(),
                car.getKeyNumber(),
                car.getValetId());

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
                        " = '%s' ;", valet.getVenueId());

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
                        TB_CURRENT_CAR.ZONE.toString() +
                        " IS NULL AND " + TB_CURRENT_CAR.VENUE_ID.toString() +
                        " = '%s' ;", valet.getVenueId());

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
                        TB_CURRENT_CAR.REGISTER_TIMESTAMP.toString() +
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
                        TB_CURRENT_CAR.VENUE_ID.toString() +
                        " = '%s' ;", valet.getVenueId());

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
                admin.getVenueId(),
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


    public String setDeliveredCar (Car car){

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


/*
   UPDATE TB_VALET
set
  IS_AUTHORIZED = false,
  VENUE_ID = '0'
WHERE PHONE = '2128834401';
*/
}
