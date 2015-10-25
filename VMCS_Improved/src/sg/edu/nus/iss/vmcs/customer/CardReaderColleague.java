/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.cardService.Card;
import sg.edu.nus.iss.vmcs.cardService.CardService;
import sg.edu.nus.iss.vmcs.store.StoreObject;

/**
 *
 * @author xiejiabao
 */
public class CardReaderColleague extends PaymentColleague{
    private Card card;
    
    public CardReaderColleague(PaymentMediator m) {
        super(m);
    }

    @Override
    public void reset() {
        this.card = null;
    }
    
    public void readCard(String id){
//        verify card id and get total money inside the card        
        Card c = CardService.readCard(id);
        
        if(c==null||c.getValue()==0){
            getMediator().continuePayment();
        }else{
            this.card = c;
            getMediator().processPayment(c.getValue());
        }
    }
    
    public void chargeCard(int balance){
        System.out.println("Charge: " + Integer.toString(this.card.getValue() - balance) +" C");
    }
    
}
