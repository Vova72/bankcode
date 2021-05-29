package com.vovarusskih72.bankcode.model.dto.results;

public class ErrorInformation {
    private String infor;

    public ErrorInformation(String infor) {
        this.infor = infor;
    }

    public String getInfor() {
        return infor;
    }

    public void setInfor(String infor) {
        this.infor = infor;
    }

    @Override
    public String toString() {
        return "ErrorInformation{" +
                "infor='" + infor + '\'' +
                '}';
    }
}
