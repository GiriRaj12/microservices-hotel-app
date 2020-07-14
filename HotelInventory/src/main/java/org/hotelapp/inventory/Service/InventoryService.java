package org.hotelapp.inventory.Service;

import org.hotelapp.commons.Models.Rooms;
import org.hotelapp.commons.Utilities.JsonUtils;
import org.hotelapp.commons.Utilities.StringUtils;
import org.hotelapp.inventory.Models.RoomDTO;
import org.hotelapp.inventory.Utils.MongoUtils;

import java.util.List;
import java.util.UUID;

public class InventoryService {

    //Accessors
    public boolean addRoom(RoomDTO roomDTO){
        validateDetailsOfTheRoom(roomDTO);
        return createRoom(roomDTO);
    }

    public boolean removeRoom(String id) {
        if(StringUtils.checkNull(id))
            throw new IllegalArgumentException("Id canonot be null or empty");
        MongoUtils.delete(id);
        return true;
    }

    public List<Rooms> getAllRooms(){
        return MongoUtils.get();
    }

    //Logic
    private boolean createRoom(RoomDTO roomDTO) {
        Rooms rooms = buildRooms(roomDTO);
        MongoUtils.save(rooms);
        return true;
    }

    //Builder
    private Rooms buildRooms(RoomDTO roomDTO) {
        Rooms rooms = new Rooms();
        rooms.setId(UUID.randomUUID().toString());
        rooms.setMaxNumbers(roomDTO.getMaxNumbers());
        rooms.setImageUrl(roomDTO.getImageUrl());
        rooms.setName(roomDTO.getRoomName());
        rooms.setPrice(roomDTO.getPrice());
        rooms.setAvailableNumber(roomDTO.getMaxNumbers());
        rooms.setAvailable(true);
        rooms.setRoomClass(roomDTO.getRoomClass());
        rooms.setDescription(roomDTO.getDescription());
        System.out.println(JsonUtils.toJson(rooms));
        return rooms;
    }

    //Utils
    private void validateDetailsOfTheRoom(RoomDTO roomDTO) {
        if(roomDTO == null)
            throw new IllegalArgumentException("Data cannot be null or empty");
        if(StringUtils.checkNull(roomDTO.getImageUrl()) || StringUtils.checkNull(roomDTO.getDescription())
                || StringUtils.checkNull(roomDTO.getRoomClass()) || (roomDTO.getMaxNumbers() == 0))
            throw new IllegalArgumentException("Please fill out all the needed properties");
    }
}
