package org.hotelapp.inventory.Controller;

import org.hotelapp.commons.Models.ApiResponse;
import org.hotelapp.inventory.Models.RoomDTO;
import org.hotelapp.inventory.Service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {
    private ApiResponse apiResponse;

    @PostMapping("/add/room")
    public ApiResponse addRoom(@RequestBody RoomDTO roomsDto){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(new InventoryService().addRoom(roomsDto));
        }catch (Exception e){

        }
        return apiResponse;
    }

    @DeleteMapping("/remove/room")
    public ApiResponse addRoom(@RequestParam("roomId") String roomId){
        apiResponse = new ApiResponse();
        try{

        }catch (Exception e){

        }
        return apiResponse;
    }
}
