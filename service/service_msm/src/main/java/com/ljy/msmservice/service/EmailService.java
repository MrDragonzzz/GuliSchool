package com.ljy.msmservice.service;

import java.util.Map;

public interface EmailService {
    public void send(String email, Map<String, Object> params);
}
