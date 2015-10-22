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
public class CardInsertBox extends Panel{
    private CardButton validCard;
    private CardButton invalidCard;
    
    private TransactionController txCtrl;
    
    public CardInsertBox(TransactionController t){
        this.txCtrl = t;
        validCard = new CardButton("Valid Card","valid card");
        invalidCard = new CardButton("Invalid Card","invalid card");
        validCard.addActionListener(new CardInsertListener(MediatorFactory.getCardMediator().getCardReader()));
        invalidCard.addActionListener(new CardInsertListener(MediatorFactory.getCardMediator().getCardReader()));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(validCard);
        add(invalidCard);
    }
    
    public void setActive(boolean active){
        if(validCard!=null)
            validCard.setEnabled(active);
        if(invalidCard!=null)
            invalidCard.setEnabled(active);
    }
}
