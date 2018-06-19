package com.tingcore.cdc.charging.model;

public class Connector {

    private Long id;
    private String label;
    private com.tingcore.charging.assets.model.BasicConnector.ConnectorTypeEnum type;
    private boolean quick;
    private double power;
    private double current;
    private double voltage;
    private ConnectorStatus status;
    private String price;
    private int number;

    public Connector() {}

    public Connector(Long id, String label, int number, com.tingcore.charging.assets.model.BasicConnector.ConnectorTypeEnum type, boolean quick, double power, ConnectorStatus status, String price) {
        this.id = id;
        this.label = label;
        this.number = number;
        this.type = type;
        this.quick = quick;
        this.power = power;
        this.current = current;
        this.voltage = voltage;
        this.status = status;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public double getPower() { return power; }

    public void setPower(double power) { this.power = power; }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public com.tingcore.charging.assets.model.BasicConnector.ConnectorTypeEnum getType() {
        return type;
    }

    public void setType(com.tingcore.charging.assets.model.BasicConnector.ConnectorTypeEnum type) {
        this.type = type;
    }

    public boolean isQuick() {
        return quick;
    }

    public void setQuick(boolean quick) {
        this.quick = quick;
    }

    public ConnectorStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectorStatus status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
