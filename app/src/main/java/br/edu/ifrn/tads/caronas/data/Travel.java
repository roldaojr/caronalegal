package br.edu.ifrn.tads.caronas.data;

import java.util.Date;

public class Travel extends Entity {
    private User driver;
    private String origin;
    private String destination;
    private Date apertureDate;
    private Date arrivalDate;
    private int vacancies;
    private boolean cancelled;

    public Travel() {
        apertureDate = new Date();
        arrivalDate = new Date();
    }

    public Travel(String id) {
        super(id);
    }

    public User getDriver() {
        return this.driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getApertureDate() { return apertureDate; }

    public void setApertureDate(Date apertureDate) {
        this.apertureDate = apertureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}
