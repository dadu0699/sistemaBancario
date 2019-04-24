package org.didierdominguez.controller;

import org.didierdominguez.bean.ATM;

public class ControllerATM {
    private static ControllerATM instance;
    private ATM[] atms = new ATM[100];
    private int id;
    private boolean update;

    private ControllerATM(){
        id = 0;
    }

    public static ControllerATM getInstance() {
        if (instance == null) {
            instance = new ControllerATM();
        }
        return instance;
    }

    public void createATM(String location, Double cash) {
        for (int i = 0; i < atms.length; i++) {
            if (atms[i] == null) {
                id++;
                atms[i] = new ATM(id, location.toUpperCase(), cash, true, 0);
                System.out.println("ATM added successfully");
                break;
            }
        }
    }

    public ATM[] getAtms() {
        return atms;
    }

    public void updateATM(Integer id, String location, Double cash, Boolean state) {
        ATM atm = searchATM(id);
        update = false;
        if (atm != null) {
            atm.setLocation(location);
            atm.setCash(cash);
            atm.setState(state);
            update = true;
            System.out.println("ATM updated successfully");
        }
    }

    public boolean updateATM() {
        return update;
    }

    public void deleteATM(Integer id) {
        for (int i = 0; i < atms.length; i++) {
            if (atms[i] != null && atms[i].getId() == id) {
                atms[i] = null;
                System.out.println("ATM deleted successfully");
            }
        }
    }

    public ATM searchATM(Integer id) {
        for (ATM atm: atms) {
            if (atm != null && atm.getId() == id) {
                return atm;
            }
        }
        return null;
    }

    public ATM searchATM(String param) {
        for (ATM atm: atms) {
            if (atm != null && atm.getLocation().equalsIgnoreCase(param.trim())) {
                return atm;
            }
        }
        return null;
    }

    public ATM[] searchATMs(String param) {
        ATM[] atms = new ATM[100];
        int count = 0;
        for (ATM atm : getAtms()) {
            if (atm != null) {
                if (atm.getLocation().contains(param)) {
                    atms[count] = atm;
                    count++;
                }
            }
        }
        return atms;
    }
}
