package model;

import static model.SqlStatement.TB_VALET.PHONE;

public class SqlStatement {
   public enum DB_TABLE_NAMES{
       TB_VALET, SOURCE_TB_ZONE, TB_CURRENT_CAR;
    }

    public enum TB_VALET {
        PHONE, FIRSTNAME, SURNAME, IS_AUTHORIZED, IS_ADMIN, VENUE_ID;
    }

    public enum SOURCE_TB_ZONE {
       ORDER_ID, ZONE_NAME, VENUE_ID;
    }

    public enum TB_CURRENT_CAR {
        LICENSE_TAG, VENUE_ID, KEY_NUMBER, ZONE, CAR_STATUS, LISENCE_TAG_HASH
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

    public String setValetInfo(Valet valet) {
        String sqlStatement = String.format(
                "REPLACE INTO "+ DB_TABLE_NAMES.TB_VALET.toString() +
                        " (PHONE, FIRSTNAME, SURNAME, IS_AUTHORIZED, IS_ADMIN, VENUE_ID) " +
                        "VALUES ('%s','%s', '%s',true,false,'%s');",
                valet.getPhone(),
                valet.getFirstName(),
                valet.getSurname(),
                valet.getVenueId());

        return  sqlStatement;
    }

    public String giveAuthorizationToValet(Admin admin) {
        String sqlStatement = String.format(
                "INSERT INTO "+ DB_TABLE_NAMES.TB_VALET.toString() +
                        "(PHONE, IS_AUTHORIZED, VENUE_ID)" +
                        "VALUES ('%s',true, '%s')" +
                        "ON DUPLICATE KEY UPDATE IS_AUTHORIZED = true, VENUE_ID = '%s';",
                admin.getValetPhone(),
                admin.getVenueId(),
                admin.getVenueId());

        return  sqlStatement;
    }


    public String getValetInfoWithPhone(String phone) {
        String sqlStatement = String.format(
                "SELECT * FROM " +
                        DB_TABLE_NAMES.TB_VALET.toString() +
                        " WHERE " +
                        PHONE.toString() +
                        " = '%s'", phone);
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


}
