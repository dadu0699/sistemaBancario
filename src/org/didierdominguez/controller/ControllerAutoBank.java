package org.didierdominguez.controller;

import org.didierdominguez.bean.AutoBank;

public class ControllerAutoBank {
    private static ControllerAutoBank instance;
    private AutoBank[] autoBanks = new AutoBank[100];
    private int id;
    private boolean update;

    private ControllerAutoBank() {
        id = 0;
    }

    public static ControllerAutoBank getInstance() {
        if (instance == null) {
            instance = new ControllerAutoBank();
        }
        return instance;
    }

    public void createAutoBank(String name, String address, String phoneNumber, Integer noPayOffice,
                               Integer noCustomerService, Double cash, Integer autobankingBoxes) {
        for (int i = 0; i < autoBanks.length; i++) {
            if (autoBanks[i] == null && searchAutoBank(name) == null) {
                id++;
                autoBanks[i] = new AutoBank(id, name.toUpperCase(), address.toUpperCase(),
                        phoneNumber, noPayOffice, noCustomerService, cash, 0, autobankingBoxes);
                System.out.println("Auto bank added successfully");
                break;
            }
        }
    }

    public AutoBank[] getAutoBanks() {
        return autoBanks;
    }

    public void updateAutoBank(Integer id, String name, String address, String phoneNumber, Integer noPayOffice,
                               Integer noCustomerService, Double cash, Integer autobankingBoxes) {
        AutoBank autoBank = searchAutoBank(id);
        AutoBank autoBankName = searchAutoBank(name);
        update = false;
        if ((autoBank != null && autoBankName != null && autoBank.getId() == autoBankName.getId())
                || (autoBank != null && autoBankName == null)) {
            autoBank.setName(name);
            autoBank.setAddress(address);
            autoBank.setPhoneNumber(phoneNumber);
            autoBank.setNoPayOffice(noPayOffice);
            autoBank.setNoCustomerService(noCustomerService);
            autoBank.setCash(cash);
            autoBank.setAutobankingBoxes(autobankingBoxes);
            update = true;
            System.out.println("Auto bank updated successfully");
        }
    }

    public boolean updateAutoBank() {
        return update;
    }

    public void deleteAutoBank(Integer id) {
        for (int i = 0; i < autoBanks.length; i++) {
            if (autoBanks[i] != null && autoBanks[i].getId() == id) {
                autoBanks[i] = null;
                System.out.println("Auto bank deleted successfully");
            }
        }
    }

    public AutoBank searchAutoBank(Integer id) {
        for (AutoBank autoBank1 : autoBanks) {
            if (autoBank1 != null && autoBank1.getId() == id) {
                return autoBank1;
            }
        }
        return null;
    }

    public AutoBank searchAutoBank(String param) {
        for (AutoBank autoBank1 : autoBanks) {
            if (autoBank1 != null && autoBank1.getName().equalsIgnoreCase(param.trim())) {
                return autoBank1;
            }
        }
        return null;
    }

    public AutoBank[] searchAutoBankAgencies(String param) {
        AutoBank[] autoBankAgencies = new AutoBank[100];
        int count = 0;
        for (AutoBank agencies : getAutoBanks()) {
            if (agencies != null) {
                if (agencies.getName().contains(param)) {
                    autoBankAgencies[count] = agencies;
                    count++;
                } else if (agencies.getAddress().contains(param)) {
                    autoBankAgencies[count] = agencies;
                    count++;
                }
            }
        }
        return autoBankAgencies;
    }

    public void updateAutoBank(Integer id, Integer employee) {
        AutoBank autoBank = searchAutoBank(id);
        if (autoBank != null) {
            autoBank.setEmployees(autoBank.getEmployees() + employee);
        }
    }

    public AutoBank getMostEmployees() {
        AutoBank[] mostDebt = autoBanks;
        AutoBank tempAutoBank;

        for(int i=0; i < mostDebt.length; i++) {
            for (int j = 1; j < (mostDebt.length - i); j++) {
                if(mostDebt[j-1] != null && mostDebt[j] != null) {
                    if ((mostDebt[j - 1].getEmployees()) < mostDebt[j].getEmployees()) {
                        tempAutoBank = mostDebt[j - 1];
                        mostDebt[j - 1] = mostDebt[j];
                        mostDebt[j] = tempAutoBank;
                    }
                }
            }
        }
        return mostDebt[0];
    }
}
