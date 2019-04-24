package org.didierdominguez.controller;

public class ControllerUser {
    private static ControllerUser instance;
    private Boolean authenticate;

    private ControllerUser() {
        authenticate = Boolean.FALSE;
    }

    public static ControllerUser getInstance() {
        if (instance == null) {
            instance = new ControllerUser();
        }
        return instance;
    }

    public Boolean authenticate(String user, String password) {
        if (user.equals("admin") && password.equals("123456")) {
            authenticate = Boolean.TRUE;
        } else {
            authenticate = Boolean.FALSE;
        }
        return authenticate;
    }
}
