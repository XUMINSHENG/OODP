/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.store.Card;
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

//    @Override
//    public void start() {
//        reset();
//        getMediator().getTransactionController().getCustomerPanel().setCardInsertBoxActive(true);
//    }
//
//    @Override
//    public void process(StoreObject s) {
//        
//    }
//
//    @Override
//    public void complete() {
//        chargeCard(getTotal());
//    }

    @Override
    public void reset() {
        this.card = new Card();
    }

//    @Override
//    public void terminate() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    public void readCard(String id){
//        verify card id and get total money inside the card
        Card c = new Card(id);
        c.setValue(100);
        
        if(c==null){
            getMediator().invalidPayment("Invalid Card");
        }
        this.card = c;
        getMediator().processPayment(c.getValue());
    }
    
    public void chargeCard(int price){
        System.out.println("Charge " + price);
    }
    
}
