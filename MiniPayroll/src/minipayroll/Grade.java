/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minipayroll;

import java.util.Scanner;

/**
 *
 * @author solyc
 */
public class Grade {

    String position;
    double taxRate, payRate;

    Grade(String pos, double payRate, double taxRate) {
        setPosition(pos, payRate, taxRate);
    }

    public void setPosition(String pos, double payRate, double taxRate) {
        position = pos;
        calcRates(payRate, taxRate);
    }

    private void calcRates(double pay, double tax) {
        if (this.position.equals("Manager")) {
            payRate = 120;
            taxRate = 0.2;
        } else if (this.position.equals("Team leader")) {
            payRate = 60;
            taxRate = 0.15;
        } else if (this.position.equals("Team member")) {
            payRate = 30;
            taxRate = 0.12;
        } else {
            //Scanner input = new Scanner(System.in);
            System.out.println("Enter pay rate per hour:");
            payRate = pay;
            System.out.println("Enter tax rate per salary:");
            taxRate = tax;
        }
    }

    @Override
    public String toString() {
        String output = "Position:" + position + "\n";
        output += "Pay rate per hour: " + String.valueOf(payRate) + "\n";
        output += "Tax rate: " + String.valueOf(taxRate) + "\n";
        return output;
    }

}
