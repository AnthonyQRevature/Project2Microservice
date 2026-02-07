package com.example;

public enum UserRole
{
    guest(0),
    user(1),
    admin(2),
    super_user(3); //super is a keyword

    public final int value;

    UserRole(int val)
    {
        this.value = val;
    }

    public static UserRole of(int val)
    {
        return switch (val) {
            case 0 -> guest;
            case 1 -> user;
            case 2 -> admin;
            case 3 -> super_user;
            default -> guest;
        };
    }
}
