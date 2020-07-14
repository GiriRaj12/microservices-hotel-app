package org.hotelapp.billing.Services;

import org.hotelapp.billing.Models.BillingDTO;
import org.hotelapp.billing.Utils.MongoUtils;
import org.hotelapp.commons.Constants.Services;
import org.hotelapp.commons.Models.Bookings;
import org.hotelapp.commons.Models.MQModel;
import org.hotelapp.commons.Utilities.JsonUtils;
import org.hotelapp.commons.Utilities.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class BillingService {

    public Bookings addBooking(BillingDTO billingDTO) throws Exception {
        System.out.println(JsonUtils.toJson(billingDTO));
        validateBooking(billingDTO);
        return createBooking(billingDTO);
    }

    private Bookings createBooking(BillingDTO billingDTO) throws ParseException, IOException, TimeoutException {
        Bookings bookings = buildBooking(billingDTO);
        System.out.println(JsonUtils.toJson(bookings));
        MongoUtils.save(bookings);
        triggerTaskForUpdates(buildMQ(bookings,"remove"));
        return bookings;
    }

    public List<Bookings> getBookings(String userEmail){
        if(StringUtils.checkNull(userEmail))
            throw new IllegalArgumentException("User email cannot be null or empty");
        return MongoUtils.get(userEmail);
    }

    public boolean deleteBooking(String bookingId) throws IOException, TimeoutException {
        Bookings bookings = MongoUtils.getById(bookingId);
        MongoUtils.deleteById(bookingId);
        triggerTaskForUpdates(buildMQ(bookings,"add"));
        return true;
    }

    private MQModel buildMQ(Bookings bookings, String action) {
        MQModel mqModel = new MQModel();
        mqModel.setUpdatedCount(bookings.getCount());
        mqModel.setAction(action);
        mqModel.setUserId(bookings.getUserId());
        mqModel.setRoomId(bookings.getRoomId());
        mqModel.setTargetService(Services.INVENTORY);
        return mqModel;
    }


    private void triggerTaskForUpdates(MQModel mqModel) throws IOException, TimeoutException {
        new RabbitMQService().publishMessage(JsonUtils.toJson(mqModel));
    }

    private Bookings buildBooking(BillingDTO billingDTO) throws ParseException {
        Bookings bookings = new Bookings();
        bookings.setCount(billingDTO.getCount());
        bookings.setDate(billingDTO.getDate());
        bookings.setDatelong(getDateLong(billingDTO.getDate()));
        bookings.setUserEmail(billingDTO.getUserEmail());
        bookings.setImageUrl(billingDTO.getImageUrl());
        bookings.setRoomClass(billingDTO.getRoomClass());
        bookings.setRoomName(billingDTO.getRoomName());
        bookings.setId(UUID.randomUUID().toString());
        bookings.setRoomId(billingDTO.getRoomId());
        bookings.setPrice(billingDTO.getPrice());
        System.out.println(JsonUtils.toJson(bookings));
        return bookings;
    }

    private Long getDateLong(String date) throws ParseException {
        DateFormat inputFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return inputFormat.parse(date).toInstant().toEpochMilli();
    }

    private void validateBooking(BillingDTO billingDTO) {
        if(StringUtils.checkNull(billingDTO.getDate()) || StringUtils.checkNull(billingDTO.getImageUrl()) ||
        StringUtils.checkNull(billingDTO.getRoomName()) || StringUtils.checkNull(billingDTO.getUserEmail()) ||
        StringUtils.checkNull(billingDTO.getUserId()))
            throw new IllegalArgumentException("Booking Details are note enough to initiate booking");
        System.out.println("Into validate booking");
    }

}
