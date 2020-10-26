package com.ifmo.vbaydyuk.hw2;

import java.util.Objects;

import static java.util.Objects.hash;

/**
 * Vk user entity
 *
 * @author vbaydyuk
 * @since 19.10.2020
 */
public class VkUser {
    private final int id;
    private final String firstName;
    private final String lastName;

    public VkUser(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkUser)) return false;
        VkUser vkUser = (VkUser) o;
        return getId() == vkUser.getId() &&
                Objects.equals(getFirstName(), vkUser.getFirstName()) &&
                Objects.equals(getLastName(), vkUser.getLastName());
    }

    @Override
    public int hashCode() {
        return hash(getId(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "VkUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
