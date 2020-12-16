package com.ifmo.vnaydyuk.animal;

public abstract class TextAnimal implements Animal  {
    private final String sound;
    private final String name;

    protected TextAnimal(String name, String sound) {
        this.name = name;
        this.sound = sound;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String saySomething() {
        return sound;
    }
}
