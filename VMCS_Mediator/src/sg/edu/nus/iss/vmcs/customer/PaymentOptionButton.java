/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.awt.Button;

/**
 *
 * @author xiejiabao
 */
public class PaymentOptionButton extends Button{
    private String name;
    private int paymentOption;
    
    public PaymentOptionButton(String name, int option){
        this.name = name;
        this.paymentOption = option;
    }

    public int getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(int paymentOption) {
        this.paymentOption = paymentOption;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
