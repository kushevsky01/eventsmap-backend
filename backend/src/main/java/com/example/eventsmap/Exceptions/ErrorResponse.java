package com.example.eventsmap.Exceptions;

public class ErrorResponse {

    private String title;

    private String errrorText;

    public ErrorResponse(String title, String errrorText) {
        this.title = title;
        this.errrorText = errrorText;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getErrrorText() {
        return errrorText;
    }

    public void setErrrorText(String errrorText) {
        this.errrorText = errrorText;
    }
}
