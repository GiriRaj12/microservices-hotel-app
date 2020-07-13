package org.hotelapp.inventory.Service;

import org.hotelapp.inventory.Models.RoomDTO;

public class InventoryService {
    public boolean addRoom(RoomDTO roomDTO){
        validateDetailsOfTheRoom(roomDTO);
        createRoom(roomDTO);
        return true;
    }

    private void createRoom(RoomDTO roomDTO) {

    }

    private void validateDetailsOfTheRoom(RoomDTO roomDTO) {

    }
}
