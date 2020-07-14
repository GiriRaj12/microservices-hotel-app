package org.hotelapp.commons.Models;

import java.util.List;

public class ApiResponse {

    private boolean response;

    private Object data;

    private List<?> datas;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<?> getDatas() {
        return datas;
    }

    public void setDatas(List<?> datas) {
        this.datas = datas;
    }

    public void setFalseResponse(String message){
        this.response = false;
        this.message = message;
    }
}
