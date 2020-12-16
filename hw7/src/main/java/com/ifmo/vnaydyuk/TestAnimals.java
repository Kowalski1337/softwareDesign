package com.ifmo.vnaydyuk;

import com.ifmo.vnaydyuk.animal.Animal;
import com.ifmo.vnaydyuk.animal.SoundHorse;
import com.ifmo.vnaydyuk.service.SoundService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestAnimals implements CommandLineRunner {
    private final SoundService soundService;

    @Value("${media.sound.horse}")
    String horseSound;

    public TestAnimals(SoundService soundService) {
        this.soundService = soundService;
    }

    @Override
    public void run(String... args) {
        Animal plotva = new SoundHorse(soundService, horseSound, "plotva");
        plotva.saySomething();
    }
}
