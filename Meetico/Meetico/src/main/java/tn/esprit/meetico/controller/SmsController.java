package tn.esprit.meetico.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.meetico.entity.SmsRequest;
import tn.esprit.meetico.service.SmsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsservice;

    @Autowired
    public SmsController(SmsService smsservice) {
        this.smsservice = smsservice;
    }

    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        smsservice.sendSms(smsRequest);
    }
}