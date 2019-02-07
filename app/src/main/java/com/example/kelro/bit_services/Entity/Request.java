package com.example.kelro.bit_services.Entity;

import java.util.Date;

public class Request {
    private int id;
    private String title, info, request_date, complete_date, status;

    public Request(int id, String title, String info, String request_date, String complete_date, String status) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.request_date = request_date;
        this.complete_date = complete_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getRequest_date() {
        return request_date;
    }

    public String getComplete_date() {
        return complete_date;
    }

    public String getStatus() {
        return status;
    }
}
