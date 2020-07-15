package org.hotelapp.users.Controller;

import org.hotelapp.commons.Models.ApiResponse;
import org.hotelapp.users.Models.UserDTO;
import org.hotelapp.users.Services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    private ApiResponse apiResponse;

    @PostMapping("/user/register")
    public ApiResponse createUser(@RequestBody UserDTO userDTO){
        try{
            apiResponse = new ApiResponse();
            apiResponse.setResponse(new UserService().createUser(userDTO));
        }catch (Exception e){
            e.printStackTrace();
            setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    @PostMapping("/user/login")
    public ApiResponse loginUser(@RequestBody UserDTO userDTO){
        try{
            apiResponse = new ApiResponse();
            apiResponse.setResponse(true);
            apiResponse.setData(new UserService().loginUser(userDTO));
        }catch (Exception e){
            setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    @GetMapping("/user/get")
    public ApiResponse getUsers(){
        try{
            apiResponse = new ApiResponse();
            apiResponse.setResponse(true);
            apiResponse.setDatas(new UserService().getUsersList());
        }catch (Exception e){
            setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    private void setFalseResponse(String message){
        apiResponse.setResponse(false);
        apiResponse.setMessage(message);
    }

}
