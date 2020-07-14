package org.hotelapp.billing.Controllers;

import org.hotelapp.billing.Models.BillingDTO;
import org.hotelapp.billing.Services.BillingService;
import org.hotelapp.commons.Models.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@RestController
public class BillingController {

    private ApiResponse apiResponse;

    @PostMapping("/billing/book")
    public ApiResponse addBooking(@RequestBody BillingDTO billingDTO){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(true);
            apiResponse.setData(new BillingService().addBooking(billingDTO));
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
            apiResponse.setDatas(new BillingService().getBookings(emailId));
        }catch (Exception e){
            apiResponse.setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }

    @DeleteMapping("/billing/delete")
    public ApiResponse deleteBooking(@RequestParam("bookingId") String bookingId){
        apiResponse = new ApiResponse();
        try{
            apiResponse.setResponse(new BillingService().deleteBooking(bookingId));
        }catch (Exception e){
            apiResponse.setFalseResponse(e.getMessage());
        }
        return apiResponse;
    }
}
