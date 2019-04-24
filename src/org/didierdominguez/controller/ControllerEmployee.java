package org.didierdominguez.controller;

import org.didierdominguez.bean.AutoBank;
import org.didierdominguez.bean.BankingAgency;
import org.didierdominguez.bean.Employee;

public class ControllerEmployee {
    private static ControllerEmployee instance;
    private Employee[] employees = new Employee[100];
    private int id;
    private boolean update;

    private ControllerEmployee() {
        id = 0;
    }

    public static ControllerEmployee getInstance() {
        if (instance == null) {
            instance = new ControllerEmployee();
        }
        return instance;
    }

    public void createEmployee(String name, String phoneNumber, Object job) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null && searchEmployee(name) == null) {
                id++;
                employees[i] = new Employee(id, name.toUpperCase(), phoneNumber, job);
                if (job instanceof BankingAgency && !(job instanceof AutoBank)) {
                    ControllerBankingAgency.getInstance().updateBankingAgency(((BankingAgency) job).getId(), 1);
                } else if (job instanceof AutoBank) {
                    ControllerAutoBank.getInstance().updateAutoBank(((AutoBank) job).getId(), 1);
                }
                System.out.println("Employee added successfully");
                break;
            }
        }
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public void updateEmployee(Integer id, String name, String phoneNumber, Object job) {
        Employee employee = searchEmployee(id);
        Employee employeeName = searchEmployee(name);
        update = false;
        if ((employee != null && employeeName != null && employee.getId() == employeeName.getId())
                || (employee != null && employeeName == null)) {
            employee.setName(name);
            employee.setPhoneNumber(phoneNumber);
            employee.setJob(job);
            update = true;
            System.out.println("Employee updated successfully");
        }
    }

    public boolean updateEmployee() {
        return update;
    }

    public void deleteEmployee(Integer id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId() == id) {

                if (employees[i].getJob() instanceof BankingAgency && !(employees[i].getJob() instanceof AutoBank)) {
                    ControllerBankingAgency.getInstance().updateBankingAgency(((BankingAgency) employees[i].getJob())
                            .getId(), -1);
                } else if (employees[i].getJob() instanceof AutoBank) {
                    ControllerAutoBank.getInstance().updateAutoBank(((AutoBank) employees[i].getJob()).getId(),
                            -1);
                }
                employees[i] = null;
                System.out.println("Employee deleted successfully");
            }
        }
    }

    public Employee searchEmployee(Integer id) {
        for (Employee employee: employees) {
            if (employee != null && employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public Employee searchEmployee(String param) {
        for (Employee employee: employees) {
            if (employee != null && employee.getName().equalsIgnoreCase(param.trim())) {
                return employee;
            }
        }
        return null;
    }

    public Employee[] searchEmployees(String param) {
        Employee[] employees = new Employee[100];
        int count = 0;
        for (Employee employee : getEmployees()) {
            if (employee != null) {
                if (employee.getName().contains(param)) {
                    employees[count] = employee;
                    count++;
                }
            }
        }
        return employees;
    }

    public String[] getAgencyWithMoreEmployees() {
        String[] agency = new String[4];
        
        if (ControllerBankingAgency.getInstance().getMostEmployees().getEmployees() > 
                ControllerAutoBank.getInstance().getMostEmployees().getEmployees()) {
            agency[0] = ControllerBankingAgency.getInstance().getMostEmployees().getName();
            agency[1] = ControllerBankingAgency.getInstance().getMostEmployees().getAddress();
            agency[2] = ControllerBankingAgency.getInstance().getMostEmployees().getPhoneNumber();
            agency[3] = String.valueOf(ControllerBankingAgency.getInstance().getMostEmployees().getEmployees());
        } else {
            agency[0] = ControllerAutoBank.getInstance().getMostEmployees().getName();
            agency[1] = ControllerAutoBank.getInstance().getMostEmployees().getAddress();
            agency[2] = ControllerAutoBank.getInstance().getMostEmployees().getPhoneNumber();
            agency[3] = String.valueOf(ControllerAutoBank.getInstance().getMostEmployees().getEmployees());
        }
        return agency;
    }
}
