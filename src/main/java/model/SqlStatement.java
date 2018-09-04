package model;

import Utils.CurrentTimestamp;
import manager.DBConnection;

public class SqlStatement {
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

    public static String register(Customer customer){
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
        return sqlStatement;
    }
}
