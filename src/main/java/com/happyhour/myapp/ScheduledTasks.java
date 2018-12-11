package com.happyhour.myapp;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.happyhour.myapp.service.CouponService;
import com.happyhour.myapp.service.HappyOrderService;
import com.happyhour.myapp.service.ReservationService;
import com.happyhour.myapp.service.dto.CouponDTO;
import com.happyhour.myapp.service.dto.HappyOrderDTO;
import com.happyhour.myapp.service.dto.ReservationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private CouponService couponService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private HappyOrderService happyOrderService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", dateFormat.format(new Date()));
//    }


    // @Scheduled(fixedRate = 5000)
    @Scheduled(cron="0 0 5-7 * * *")
    public void cancel() {

        LocalDate checkDate = LocalDate.now();


        // cancel all old order
        List<HappyOrderDTO> happyOrderDTOS = happyOrderService.cronJobCacel(checkDate, Long.parseLong("4"));
        if(!happyOrderDTOS.isEmpty()) {
            for (HappyOrderDTO happyOrderDTO: happyOrderDTOS) {
                if(happyOrderDTO.getOrderStatusId() == Long.parseLong("2") || happyOrderDTO.getOrderStatusId() == Long.parseLong("1")){
                    happyOrderDTO.setOrderStatusId(Long.parseLong("4"));
                }
                happyOrderService.save(happyOrderDTO);
            }
        }
        // cancel all old reservation
        List<ReservationDTO> reservationDTOS = reservationService.cronJobCancel(checkDate, "cancel");
        if(!reservationDTOS.isEmpty()) {
            for (ReservationDTO reservationDTO: reservationDTOS) {
                if(!reservationDTO.getStatus().equalsIgnoreCase("cancel") ){
                    reservationDTO.setStatus("cancel");
                }
                reservationService.save(reservationDTO);
            }
        }

        // cancel all order coupon

        List<CouponDTO> couponDTOS = couponService.cronJobCancel(checkDate, checkDate);

        if(!couponDTOS.isEmpty()) {
            for (CouponDTO couponDTO: couponDTOS) {
                if(couponDTO.isIsActive()){
                    couponDTO.setIsActive(false);
                }
                couponService.save(couponDTO);
            }
        }
    }
}
