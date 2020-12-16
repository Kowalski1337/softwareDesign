package com.ifmo.vnaydyuk.animal;

import com.ifmo.vnaydyuk.service.SoundService;

public class SoundAnimal extends TextAnimal implements Animal {
    private final String filePath;
    private final SoundService soundService;

    protected SoundAnimal(SoundService soundService, String filePath, String name, String sound) {
        super(name, sound);
        this.filePath = filePath;
        this.soundService = soundService;
    }

    @Override
    public String saySomething() {
        try {
            soundService.playSound(filePath);
        } catch (InterruptedException e) {
            System.out.println("Cannot play sound");
        }
        return super.saySomething();
    }
}
