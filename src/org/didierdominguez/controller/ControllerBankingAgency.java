package org.didierdominguez.controller;

import org.didierdominguez.bean.AutoBank;
import org.didierdominguez.bean.BankingAgency;

public class ControllerBankingAgency {
    private static ControllerBankingAgency instance;
    private BankingAgency[] bankingAgencies = new BankingAgency[100];
    private int id;
    private boolean update;

    private ControllerBankingAgency() {
        id = 0;
    }

    public static ControllerBankingAgency getInstance() {
        if (instance == null) {
            instance = new ControllerBankingAgency();
        }
        return instance;
    }

    public void createBankingAgency(String name, String address, String phoneNumber, Integer noPayOffice,
            Integer noCustomerService, Double cash) {
        for (int i = 0; i < bankingAgencies.length; i++) {
            if (bankingAgencies[i] == null && (searchBankingAgency(name) == null)) {
                id++;
                bankingAgencies[i] = new BankingAgency(id, name.toUpperCase(), address.toUpperCase(),
                        phoneNumber, noPayOffice, noCustomerService, cash, 0);
                System.out.println("Agency added successfully");
                break;
            }
        }
    }

    public BankingAgency[] getBankingAgencies() {
        return bankingAgencies;
    }

    public void updateBankingAgency(Integer id, String name, String address, String phoneNumber, Integer noPayOffice,
            Integer noCustomerService, Double cash) {
        BankingAgency bankingAgency = searchBankingAgency(id);
        BankingAgency bankingAgencyName = searchBankingAgency(name);
        update = false;
        if (((bankingAgency != null) && (bankingAgencyName != null) && (bankingAgency.getId() == bankingAgencyName.getId()))
                || ((bankingAgency != null) && (bankingAgencyName == null))) {
            bankingAgency.setName(name);
            bankingAgency.setAddress(address);
            bankingAgency.setPhoneNumber(phoneNumber);
            bankingAgency.setNoPayOffice(noPayOffice);
            bankingAgency.setNoCustomerService(noCustomerService);
            bankingAgency.setCash(cash);
            update = true;
            System.out.println("Agency updated successfully");
        }
    }

    public boolean updateBankingAgency() {
        return update;
    }

    public void deleteBankingAgency(Integer id) {
        for (int i = 0; i < bankingAgencies.length; i++) {
            if (bankingAgencies[i] != null && bankingAgencies[i].getId() == id) {
                bankingAgencies[i] = null;
                System.out.println("Agency deleted successfully");
            }
        }
    }

    public BankingAgency searchBankingAgency(Integer id) {
        for (BankingAgency bankingAgency1 : bankingAgencies) {
            if (bankingAgency1 != null && bankingAgency1.getId() == id) {
                return bankingAgency1;
            }
        }
        return null;
    }

    public BankingAgency searchBankingAgency(String param) {
        for (BankingAgency bankingAgency1 : bankingAgencies) {
            if (bankingAgency1 != null && bankingAgency1.getName().equalsIgnoreCase(param.trim())) {
                return bankingAgency1;
            }
        }
        return null;
    }

    public BankingAgency[] searchBankingAgencies(String param) {
        BankingAgency[] bankingAgencies = new BankingAgency[100];
        int count = 0;
        for (BankingAgency agencies : getBankingAgencies()) {
            if (agencies != null) {
                if (agencies.getName().contains(param) || agencies.getAddress().contains(param)) {
                    bankingAgencies[count] = agencies;
                    count++;
                }
            }
        }
        return bankingAgencies;
    }

    public void updateBankingAgency(Integer id, Integer employee) {
        BankingAgency bankingAgency = searchBankingAgency(id);
        if (bankingAgency != null) {
            bankingAgency.setEmployees(bankingAgency.getEmployees() + employee);
        }
    }

    public String[] getTotalCash() {
        double totalBankingAgency = 0;
        double totalAutoBank = 0;

        for (BankingAgency agencies : getBankingAgencies()) {
            if (agencies != null) {
                totalBankingAgency =+ agencies.getCash();
            }
        }

        for (AutoBank autoBank: ControllerAutoBank.getInstance().getAutoBanks()) {
            if (autoBank != null) {
                totalAutoBank =+ autoBank.getCash();
            }
        }

        return new String[]{String.valueOf(totalBankingAgency), String.valueOf(totalAutoBank),
                String.valueOf(totalAutoBank + totalBankingAgency)};
    }

    public BankingAgency getMostEmployees() {
        BankingAgency[] mostDebt = bankingAgencies;
        BankingAgency tempBankingAgency;

        for(int i=0; i < mostDebt.length; i++) {
            for (int j = 1; j < (mostDebt.length - i); j++) {
                if(mostDebt[j-1] != null && mostDebt[j] != null) {
                    if ((mostDebt[j - 1].getEmployees()) < mostDebt[j].getEmployees()) {
                        tempBankingAgency = mostDebt[j - 1];
                        mostDebt[j - 1] = mostDebt[j];
                        mostDebt[j] = tempBankingAgency;
                    }
                }
            }
        }
        return mostDebt[0];
    }
}
