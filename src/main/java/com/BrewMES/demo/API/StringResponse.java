package com.BrewMES.demo.API;
//Class for wrapping string responses in a simple object assosiating the http response code.
public class StringResponse {

    private String respose;
    private int status;

    public StringResponse(String respose, int status) {
        this.respose = respose;
        this.status = status;
    }

    public String getRespose() {
        return respose;
    }

    public int getStatus() {
        return status;
    }

    public void setRespose(String respose) {
        this.respose = respose;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
