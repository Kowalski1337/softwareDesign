package com.ifmo.vnaydyuk;

import com.ifmo.vnaydyuk.service.SoundService;
import com.ifmo.vnaydyuk.service.SoundServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SoundService getSoundService() {
        return new SoundServiceImpl();
    }
}
