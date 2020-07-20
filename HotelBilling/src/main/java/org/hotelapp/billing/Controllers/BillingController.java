package org.hotelapp.billing.Controllers;

import org.hotelapp.billing.Models.BillingDTO;
import org.hotelapp.billing.Services.BillingService;
import org.hotelapp.commons.Models.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RestController
@CrossOrigin
public class BillingController {

    private ApiResponse apiResponse;

    @Autowired
    private BillingService billingService;

    @PostMapping("/billing/book")
    public ApiResponse addBooking(@RequestBody BillingDTO billingDTO){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(true);
            apiResponse.setData(billingService.addBooking(billingDTO));
        }catch (Exception e){
            apiResponse.setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    @GetMapping("/billing/get")
    public ApiResponse getBookings(@RequestParam("userEmail") String emailId){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(true);
            apiResponse.setDatas(billingService.getBookings(emailId));
        }catch (Exception e){
            apiResponse.setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    @DeleteMapping("/billing/delete")
    public ApiResponse deleteBooking(@RequestParam("bookingId") String bookingId){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(billingService.deleteBooking(bookingId));
        }catch (Exception e){
            apiResponse.setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }
}
