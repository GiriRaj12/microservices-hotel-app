package org.hotelapp.inventory.Controller;

import org.hotelapp.commons.Models.ApiResponse;
import org.hotelapp.inventory.Models.RoomDTO;
import org.hotelapp.inventory.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin
@RestController
public class InventoryController {

    @GetMapping("/")
    public String hello(){
        return "Hellow World";
    }

    private ApiResponse apiResponse;

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/inventory/add/room")
    public ApiResponse addRoom(@RequestBody RoomDTO roomsDto){
        apiResponse = new ApiResponse();
        try{
             apiResponse.setResponse(inventoryService.addRoom(roomsDto));
        }catch (Exception e){

        }
        return apiResponse;
    }

    @DeleteMapping("/inventory/remove/room")
    public ApiResponse addRoom(@RequestParam("roomId") String roomId){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(inventoryService.removeRoom(roomId));
        }catch (Exception e){
            apiResponse.setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    @GetMapping("/inventory/get/rooms")
    public ApiResponse getRooms(){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(true);
            apiResponse.setDatas(new InventoryService().getAllRooms());
        }catch (Exception e){
            apiResponse.setFalseResponse(e.getMessage());
            e.printStackTrace();
        }
        return apiResponse;
    }
}
