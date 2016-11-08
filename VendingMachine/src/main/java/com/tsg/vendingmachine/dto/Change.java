/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.vendingmachine.dto;

/**
 *
 * @author apprentice
 */
public class Change {

    private int pennies;
    private int nickels;
    private int dimes;
    private int quarters;

    public Change(double totalChange) {

        quarters = (int) (totalChange / .25);
        totalChange = rounder(totalChange % .25);
        dimes = (int) (totalChange / .10);
        totalChange = rounder(totalChange % .10);
        nickels = (int) (totalChange / .05);
        totalChange = rounder(totalChange % .05);
        pennies = (int) (totalChange / .01);
        totalChange = rounder(totalChange % .01);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.pennies;
        hash = 53 * hash + this.nickels;
        hash = 53 * hash + this.dimes;
        hash = 53 * hash + this.quarters;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Change other = (Change) obj;
        if (this.pennies != other.pennies) {
            return false;
        }
        if (this.nickels != other.nickels) {
            return false;
        }
        if (this.dimes != other.dimes) {
            return false;
        }
        if (this.quarters != other.quarters) {
            return false;
        }
        return true;
    }

    public int getPennies() {
        return pennies;
    }

    public void setPennies(int pennies) {
        this.pennies = pennies;
    }

    public int getNickels() {
        return nickels;
    }

    public void setNickels(int nickels) {
        this.nickels = nickels;
    }

    public int getDimes() {
        return dimes;
    }

    public void setDimes(int dimes) {
        this.dimes = dimes;
    }

    public int getQuarters() {
        return quarters;
    }

    public void setQuarters(int quarters) {
        this.quarters = quarters;
    }

    public double rounder(double num) {
        return Math.round(num * 100.0) / 100.0;
    }

    public String displayChange(double totalchange) {

        return "Quarters: " + getQuarters() + "Dimes: " + getDimes() + "Nickels: " + getNickels() + "Pennies: " + getPennies();
    }

}
