package org.hotelapp.users.Services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hotelapp.commons.Models.Users;
import org.hotelapp.commons.Utilities.JsonUtils;
import org.hotelapp.commons.Utilities.StringUtils;
import org.hotelapp.users.Models.UserDTO;

import java.util.UUID;

public class UserService {
    public boolean createUser(UserDTO userDTO){
        System.out.println(JsonUtils.toJson(userDTO));
        validateBasics(userDTO);
        checkUserAldreadyExists(userDTO);
        persistUser(userDTO);
        return true;
    }

    public Users loginUser(UserDTO userDTO){
        System.out.println(JsonUtils.toJson(userDTO));
        validateBasics(userDTO);
        return pullAndValidateUser(userDTO);
    }

    private Users pullAndValidateUser(UserDTO userDTO) {
        Users users = new Users();
        if(!userDTO.getPassword().equals(Base64.decodeBase64(users.getPassWord().getBytes())))
            throw new IllegalArgumentException("Wrong credentials");
        else
            return users;
    }

    private void checkUserAldreadyExists(UserDTO userDTO) {
        Users users = null;
        if(users != null){
            throw new IllegalArgumentException("User aldready exists");
        }
    }

    private void validateBasics(UserDTO userDTO){
        if(StringUtils.checkNull(userDTO.getEmailId()))
            throw new IllegalStateException("Email id cannot be null or Empty");
        if(StringUtils.checkNull(userDTO.getPassword()))
            throw new IllegalStateException("Password cannot be null or Empty");
    }

    private void persistUser(UserDTO userDTO) {
        Users users = new Users();
        users.setEmailId(userDTO.getEmailId());
        users.setId(UUID.randomUUID().toString());
        users.setName(userDTO.getName());
        users.setPassWord(Base64.encodeBase64String(userDTO.getPassword().getBytes()));
        System.out.println(JsonUtils.toJson(users));
        //save User;
    }
}
