package tn.esprit.meetico.service;

import tn.esprit.meetico.entity.SmsRequest;

public interface SmsSender {


    void sendSms(SmsRequest smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}