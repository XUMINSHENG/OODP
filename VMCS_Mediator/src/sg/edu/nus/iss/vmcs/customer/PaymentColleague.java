/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.store.StoreObject;

/**
 *
 * @author xiejiabao
 */
public abstract class PaymentColleague {
    private PaymentMediator mediator;
    private int total;
    
    public PaymentColleague(PaymentMediator m){
        this.mediator = m;
    }
    
/**    
*    once choose payment option
*    resetPayment
*    prepare ui
*/
    public abstract void start();

//        coinreceiver receive coin
//        cardreader read card        
    public abstract void process(StoreObject s);
    
    public abstract void complete();
    
//        clear private variables
//        reset ui
    public abstract void reset();
    
    public abstract void terminate();
    
    public void setMediator(PaymentMediator m){
        this.mediator = m;
    }
    
    public PaymentMediator getMediator(){
        return this.mediator;
    }
    
    public void setTotal(int t){
        this.total = t;
    }
    
    public int getTotal(){
        return this.total;
    }
}
