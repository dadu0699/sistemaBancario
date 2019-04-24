package org.didierdominguez.bean;

public class Employee extends Person {
    private Object job;

    public Employee(Integer id, String name, String phone, Object job) {
        super(id, name, phone);
        this.job = job;
    }

    public Object getJob() {
        return job;
    }

    public void setJob(Object job) {
        this.job = job;
    }
}
