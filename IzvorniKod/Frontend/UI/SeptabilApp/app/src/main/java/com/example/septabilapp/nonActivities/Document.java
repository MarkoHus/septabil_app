package com.example.septabilapp.nonActivities;

public class Document {
    private String ID;
    private String scan;
    private Boolean isSigned;

    public Document(String ID, String scan, Boolean isSigned){
        this.ID=ID;
        this.scan=scan;
        this.isSigned=isSigned;
    }



    public String getID() {
        return ID;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Boolean getSigned() {
        return isSigned;
    }

    public void setSigned(Boolean signed) {
        isSigned = signed;
    }
}
