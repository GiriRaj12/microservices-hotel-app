package org.hotelapp.users.Controller;

import org.hotelapp.commons.Models.ApiResponse;
import org.hotelapp.users.Models.UserDTO;
import org.hotelapp.users.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private ApiResponse apiResponse;

    @PostMapping("/user/register")
    public ApiResponse createUser(@RequestBody UserDTO userDTO){
        try{
            apiResponse.setResponse(new UserService().createUser(userDTO));
        }catch (Exception e){
            setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    @PostMapping("/user/login")
    public ApiResponse loginUser(@RequestBody UserDTO userDTO){
        try{
            apiResponse.setResponse(true);
            apiResponse.setData(new UserService().loginUser(userDTO));
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
