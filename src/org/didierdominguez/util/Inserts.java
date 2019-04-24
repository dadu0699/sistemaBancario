package org.didierdominguez.util;

import org.didierdominguez.bean.AutoBank;
import org.didierdominguez.bean.BankingAgency;
import org.didierdominguez.bean.Customer;
import org.didierdominguez.controller.*;

public class Inserts {
    private static Inserts instance;

    private Inserts() {}

    public static Inserts getInstance() {
        if (instance == null) {
            instance = new Inserts();
        }
        return instance;
    }

    public void insertData() {
        // BankingAgency
        ControllerBankingAgency.getInstance().createBankingAgency("Agencia1", "10 CALLE 13-43 ZONA 1",
                "5555-5555", 1, 5, 10000.00);
        ControllerBankingAgency.getInstance().createBankingAgency("Agencia2", "11 CALLE 13-43 ZONA 1",
                "5555-5555", 2, 4, 20000.00);
        ControllerBankingAgency.getInstance().createBankingAgency("Agencia3", "12 CALLE 13-43 ZONA 1",
                "5555-5555", 3, 3, 30000.00);
        ControllerBankingAgency.getInstance().createBankingAgency("Agencia4", "13 CALLE 13-43 ZONA 1",
                "5555-5555", 4, 2, 40000.00);
        ControllerBankingAgency.getInstance().createBankingAgency("Agencia5", "14 CALLE 13-43 ZONA 1",
                "5555-5555", 5, 1, 50000.00);

        // AutoBank
        ControllerAutoBank.getInstance().createAutoBank("Agencia Auto Banco 1", "10 CALLE 13-43 ZONA 1",
                "5555-5555", 1, 5, 1000.00, 10);
        ControllerAutoBank.getInstance().createAutoBank("Agencia Auto Banco 2", "11 CALLE 13-43 ZONA 1",
                "5555-5555", 2, 4, 2000.00, 20);
        ControllerAutoBank.getInstance().createAutoBank("Agencia Auto Banco 3", "12 CALLE 13-43 ZONA 1",
                "5555-5555", 3, 3, 3000.00, 30);
        ControllerAutoBank.getInstance().createAutoBank("Agencia Auto Banco 4", "13 CALLE 13-43 ZONA 1",
                "5555-5555", 4, 2, 4000.00, 40);
        ControllerAutoBank.getInstance().createAutoBank("Agencia Auto Banco 5", "14 CALLE 13-43 ZONA 1",
                "5555-5555", 5, 1, 5000.00, 50);

        // ATM
        ControllerATM.getInstance().createATM("Zona 1", 100.00);
        ControllerATM.getInstance().createATM("Zona 2", 200.00);
        ControllerATM.getInstance().createATM("Zona 3", 300.00);
        ControllerATM.getInstance().createATM("Zona 4", 400.00);
        ControllerATM.getInstance().createATM("Zona 5", 500.00);
        ControllerATM.getInstance().createATM("Zona 6", 600.00);
        ControllerATM.getInstance().createATM("Zona 7", 700.00);
        ControllerATM.getInstance().createATM("Zona 8", 800.00);
        ControllerATM.getInstance().createATM("Zona 9", 900.00);
        ControllerATM.getInstance().createATM("Zona 10", 1000.00);

        // Employee
        ControllerEmployee.getInstance().createEmployee("Empleado1", "5555-5555",
                searchAgency("Agencia1"));
        ControllerEmployee.getInstance().createEmployee("Empleado2", "5555-5555",
                searchAgency("Agencia1"));
        ControllerEmployee.getInstance().createEmployee("Empleado3", "5555-5555",
                searchAgency("Agencia1"));
        ControllerEmployee.getInstance().createEmployee("Empleado4", "5555-5555",
                searchAgency("Agencia2"));
        ControllerEmployee.getInstance().createEmployee("Empleado5", "5555-5555",
                searchAgency("Agencia2"));
        ControllerEmployee.getInstance().createEmployee("Empleado6", "5555-5555",
                searchAgency("Agencia2"));
        ControllerEmployee.getInstance().createEmployee("Empleado7", "5555-5555",
                searchAgency("Agencia3"));
        ControllerEmployee.getInstance().createEmployee("Empleado8", "5555-5555",
                searchAgency("Agencia3"));
        ControllerEmployee.getInstance().createEmployee("Empleado9", "5555-5555",
                searchAgency("Agencia3"));
        ControllerEmployee.getInstance().createEmployee("Empleado10", "5555-5555",
                searchAgency("Agencia4"));
        ControllerEmployee.getInstance().createEmployee("Empleado11", "5555-5555",
                searchAgency("Agencia4"));
        ControllerEmployee.getInstance().createEmployee("Empleado12", "5555-5555",
                searchAgency("Agencia4"));
        ControllerEmployee.getInstance().createEmployee("Empleado13", "5555-5555",
                searchAgency("Agencia5"));
        ControllerEmployee.getInstance().createEmployee("Empleado14", "5555-5555",
                searchAgency("Agencia5"));
        ControllerEmployee.getInstance().createEmployee("Empleado15", "5555-5555",
                searchAgency("Agencia5"));
        ControllerEmployee.getInstance().createEmployee("Empleado16", "5555-5555",
                searchAutoBank("Agencia Auto Banco 1"));
        ControllerEmployee.getInstance().createEmployee("Empleado17", "5555-5555",
                searchAutoBank("Agencia Auto Banco 1"));
        ControllerEmployee.getInstance().createEmployee("Empleado18", "5555-5555",
                searchAutoBank("Agencia Auto Banco 1"));
        ControllerEmployee.getInstance().createEmployee("Empleado19", "5555-5555",
                searchAutoBank("Agencia Auto Banco 2"));
        ControllerEmployee.getInstance().createEmployee("Empleado20", "5555-5555",
                searchAutoBank("Agencia Auto Banco 2"));
        ControllerEmployee.getInstance().createEmployee("Empleado21", "5555-5555",
                searchAutoBank("Agencia Auto Banco 2"));
        ControllerEmployee.getInstance().createEmployee("Empleado22", "5555-5555",
                searchAutoBank("Agencia Auto Banco 3"));
        ControllerEmployee.getInstance().createEmployee("Empleado23", "5555-5555",
                searchAutoBank("Agencia Auto Banco 3"));
        ControllerEmployee.getInstance().createEmployee("Empleado24", "5555-5555",
                searchAutoBank("Agencia Auto Banco 3"));
        ControllerEmployee.getInstance().createEmployee("Empleado25", "5555-5555",
                searchAutoBank("Agencia Auto Banco 4"));
        ControllerEmployee.getInstance().createEmployee("Empleado26", "5555-5555",
                searchAutoBank("Agencia Auto Banco 4"));
        ControllerEmployee.getInstance().createEmployee("Empleado27", "5555-5555",
                searchAutoBank("Agencia Auto Banco 4"));
        ControllerEmployee.getInstance().createEmployee("Empleado28", "5555-5555",
                searchAutoBank("Agencia Auto Banco 5"));
        ControllerEmployee.getInstance().createEmployee("Empleado29", "5555-5555",
                searchAutoBank("Agencia Auto Banco 5"));
        ControllerEmployee.getInstance().createEmployee("Empleado30", "5555-5555",
                searchAutoBank("Agencia Auto Banco 5"));
        for (int i = 1; i <= 9; i++) {
            ControllerEmployee.getInstance().createEmployee("Empleado3" + i, "5555-5555",
                    "CALL-CENTER");
        }
        ControllerEmployee.getInstance().createEmployee("Empleado40", "5555-5555",
                "CALL-CENTER");
        ControllerEmployee.getInstance().createEmployee("Empleado41", "5555-5555", "GERENCIA");
        ControllerEmployee.getInstance().createEmployee("Empleado42", "5555-5555", "GERENCIA");
        ControllerEmployee.getInstance().createEmployee("Empleado43", "5555-5555", "MARKETING");
        ControllerEmployee.getInstance().createEmployee("Empleado44", "5555-5555", "MARKETING");
        ControllerEmployee.getInstance().createEmployee("Empleado45", "5555-5555", "INFORMÁTICA");
        ControllerEmployee.getInstance().createEmployee("Empleado46", "5555-5555", "INFORMÁTICA");
        ControllerEmployee.getInstance().createEmployee("Empleado47", "5555-5555", "FINANCIERO");
        ControllerEmployee.getInstance().createEmployee("Empleado48", "5555-5555", "FINANCIERO");
        ControllerEmployee.getInstance().createEmployee("Empleado49", "5555-5555", "RECLAMOS");
        ControllerEmployee.getInstance().createEmployee("Empleado50", "5555-5555", "RECLAMOS");
        ControllerEmployee.getInstance().createEmployee("Empleado51", "5555-5555", "COBROS");
        ControllerEmployee.getInstance().createEmployee("Empleado52", "5555-5555", "COBROS");

        // Customer
        for (int i = 1; i <= 10; i++) {
            ControllerCustomer.getInstance().createCustomer("Cliente" + i, "Zona" + i,
                    "4444-4444");
            if (i % 2 == 0) {
                ControllerAccount.getInstance().createAccount(searchCustomer("Cliente" + i ), (i * 100.00));
            } else {
                ControllerMonetaryAccount.getInstance().createAccount(searchCustomer("Cliente" + i ),
                        (i * 100.00), 25);
            }
        }
    }

    private BankingAgency searchAgency(String param) {
        return ControllerBankingAgency.getInstance().searchBankingAgency(param);
    }

    private AutoBank searchAutoBank(String param) {
        return ControllerAutoBank.getInstance().searchAutoBank(param);
    }

    private Customer searchCustomer(String param) {
        return ControllerCustomer.getInstance().searchCustomer(param);
    }
}
