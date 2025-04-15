package top.xkqq.service;

import org.springframework.stereotype.Service;

@Service
public interface SmsService {

    void sendValidateCode(String phone);

    void sendSms(String phone, String validateCode);


}
