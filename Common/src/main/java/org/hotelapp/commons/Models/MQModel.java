package org.hotelapp.commons.Models;

import org.hotelapp.commons.Constants.Services;


public class MQModel {

    private Services targetService;

    private String userId;

    private String roomId;

    private int updatedCount;

    private String action;

    public int getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(int updatedCount) {
        this.updatedCount = updatedCount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Services getTargetService() {
        return targetService;
    }

    public void setTargetService(Services targetService) {
        this.targetService = targetService;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

}
