package org.hotelapp.users.Services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hotelapp.commons.Models.Users;
import org.hotelapp.commons.Utilities.InvalidPayloadException;
import org.hotelapp.commons.Utilities.JsonUtils;
import org.hotelapp.commons.Utilities.StringUtils;
import org.hotelapp.users.Models.UserDTO;
import org.hotelapp.users.MongoUtils.MongoUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
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

    public List<Users> getUsersList(){
        return null;
    }

    private Users pullAndValidateUser(UserDTO userDTO) {
        Users users = MongoUtils.getUserByEmail(userDTO.getEmailId());
        System.out.println(JsonUtils.toJson(users));
        if(!userDTO.getPassword().equals(getDecodedString(users.getPassWord())))
            throw new InvalidPayloadException("Wrong credentials");
        else
            return users;
    }

    private void checkUserAldreadyExists(UserDTO userDTO) {
        Users users = MongoUtils.getUserByEmail(userDTO.getEmailId());
        System.out.println(JsonUtils.toJson(users));
        if(users != null){
            throw new InvalidPayloadException("User aldready exists");
        }
    }

    private void validateBasics(UserDTO userDTO){
        if(StringUtils.checkNull(userDTO.getEmailId()))
            throw new InvalidPayloadException("Email id cannot be null or Empty");
        if(StringUtils.checkNull(userDTO.getPassword()))
            throw new InvalidPayloadException("Password cannot be null or Empty");
    }

    private void persistUser(UserDTO userDTO) {
        Users users = new Users();
        users.setEmailId(userDTO.getEmailId());
        users.setId(UUID.randomUUID().toString());
        users.setName(userDTO.getName());
        users.setPassWord(Base64.encodeBase64String(userDTO.getPassword().getBytes()));
        System.out.println(JsonUtils.toJson(users));
        MongoUtils.saveUser(users);
    }

    public String getDecodedString(String value){
        return new String(Base64.decodeBase64(value.getBytes()));
    }
}
