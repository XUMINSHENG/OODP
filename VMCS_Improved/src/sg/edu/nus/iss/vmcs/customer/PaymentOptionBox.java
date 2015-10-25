/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.awt.FlowLayout;
import java.awt.Panel;

/**
 *
 * @author xiejiabao
 */
public class PaymentOptionBox extends Panel{
    private PaymentOptionButton card;
    private PaymentOptionButton coin;
    private TransactionController txCtrl;
    
    public PaymentOptionBox(TransactionController t){
        this.txCtrl = t;
        card = new PaymentOptionButton("Pay by Card",MediatorFactory.PAYMENT_BY_CARD);
        coin = new PaymentOptionButton("Pay by Coin",MediatorFactory.PAYMENT_BY_COIN);
        
        card.addActionListener(new PaymentOptionListener(this.txCtrl));
        coin.addActionListener(new PaymentOptionListener(this.txCtrl));
        
        setLayout(new FlowLayout());
        add(card);
        add(coin);
    }
    
    public void setActive(boolean active){
        if(card!=null)
            card.setEnabled(active);
        if(coin!=null)
            coin.setEnabled(active);
    }
}
