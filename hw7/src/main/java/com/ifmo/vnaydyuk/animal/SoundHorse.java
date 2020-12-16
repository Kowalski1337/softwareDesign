package com.ifmo.vnaydyuk.animal;

import com.ifmo.vnaydyuk.service.SoundService;

public class SoundHorse extends SoundAnimal {
    public SoundHorse(SoundService soundService, String filePath, String name) {
        super(soundService, filePath, name, "IGOGOGO");
    }
}
