package model;

public enum Error {
     BAD_REQUEST(400), UNAUTHORIZED(401), NOT_FOUND(404);

    private final int code;

    private Error(int code){
        this.code = code;

    }

    public String getErrorCode() {
        return String.valueOf(code);
    }

    public String getErrorMessage(){
        switch (this) {
            case BAD_REQUEST : return "Bad Request";
            case NOT_FOUND   : return "Not found";
            case UNAUTHORIZED: return "Unauthorized";
            default: return "EXCEPTION";
        }

    }
}