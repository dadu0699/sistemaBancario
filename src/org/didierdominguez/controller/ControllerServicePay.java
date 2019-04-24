package org.didierdominguez.controller;

import org.didierdominguez.bean.Customer;
import org.didierdominguez.bean.ServicePay;
import org.didierdominguez.util.Verifications;

public class ControllerServicePay {
    private static ControllerServicePay instance;
    private ServicePay[] servicesPay = new ServicePay[100];
    private int id;

    private ControllerServicePay() {
        id = 0;
    }

    public static ControllerServicePay getInstance() {
        if (instance == null) {
            instance = new ControllerServicePay();
        }
        return instance;
    }

    public void createServicePay(String service, String type, Customer customer, Double ammount) {
        for (int i = 0; i < servicesPay.length; i++) {
            if (servicesPay[i] == null) {
                id++;
                servicesPay[i] = new ServicePay(id, service, type, Verifications.getInstance().getDate(), customer, ammount);
                System.out.println("Service Pay added successfully");
                break;
            }
        }
    }
}
