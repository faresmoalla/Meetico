package tn.esprit.service;

import tn.esprit.entity.SmsRequest;

public interface SmsSender {


    void sendSms(SmsRequest smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}