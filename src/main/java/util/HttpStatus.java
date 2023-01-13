package util;

public enum HttpStatus {
    Information("100","Continue"),
    Success("200", "OK"),
    Redirection("302","Found"),
    ClientError("404","Not Found"),
    ServerError("502","Bad Gate");


    String statusNumber;
    String statusString;
    HttpStatus(String statusNumber, String statusString) {
        this.statusNumber = statusNumber;
        this.statusString = statusString;
    }

    @Override
    public String toString(){
        return this.statusNumber+" "+this.statusString;
    }
}
