package com.example.kelro.bit_services.Entity;

public class Assignment {
    private int id;
    private String task_name;
    private String task_info;
    private String status;
    private String date;
    private String start_time;
    private String end_time;
    private String contact_number;
    private String street;
    private String suburb;
    private String state;
    private String postcode;
    private String unit_no;

    public Assignment(String task_name, String task_info, String status, String date, String start_time, String end_time, String contact_number, String street, String suburb, String state, String postcode, String unit_no) {
        this.task_name = task_name;
        this.task_info = task_info;
        this.status = status;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.contact_number = contact_number;
        this.street = street;
        this.suburb = suburb;
        this.state = state;
        this.postcode = postcode;
        this.unit_no = unit_no;
    }

    public int getId() {
        return id;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_info() {
        return task_info;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getContact_number() {
        return contact_number;
    }

    public String getStreet() {
        return street;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getUnit_no() {
        return unit_no;
    }

}
