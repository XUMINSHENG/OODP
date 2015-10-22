/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.machinery.MachineryController;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.util.VMCSException;


/**
 *
 * @author xiejiabao
 */
public class PaymentMediator {
    
    private TransactionController txCtrl;
    private PaymentColleague coinReceiver;
    private PaymentColleague changeGiver;
    private PaymentColleague cardReader;
    private int price;
    private int paymentOption;
    
    public static final int PAYMENT_BY_CARD = 1;
    public static final int PAYMENT_BY_COIN = 2;
    public static final int PAYMENT_INVALID = 400;
    
    
    public PaymentMediator(TransactionController t){
        this.txCtrl = t;
        coinReceiver = new CoinReceiverColleague(this);
        changeGiver = new ChangeGiverColleague(this);
        cardReader = new CardReaderColleague(this);
        this.price = 0;
        this.paymentOption = 0;
    }
    
//    when txCtrl.startTransaction()
    public void resetPayment(){
        coinReceiver.reset();
        changeGiver.reset();
        cardReader.reset();
        this.price = 0;
        this.paymentOption = 0;
    }
    
//    payment option listener
    public void startPayment(int option){
        switch(option){
            case PAYMENT_BY_CARD:
                cardReader.start();
                cardReader.setTotal(this.price);
            case PAYMENT_BY_COIN:
                changeGiver.start();
                coinReceiver.start();
            default: break;
        }
        this.paymentOption = option;
        txCtrl.getCustomerPanel().setPaymentOptionBoxActive(false);
    }
    
//    when receive invalid coin or card
    public void invalidPayment(){
        switch(paymentOption){
            case PAYMENT_BY_CARD:
                txCtrl.getCustomerPanel().displayInvalidCard(true);
            case PAYMENT_BY_COIN:
                txCtrl.getCustomerPanel().displayInvalidCoin(true);
		txCtrl.getCustomerPanel().setChange("Invalid Coin");
            default:
                break;
        }
    }
    
//    once receive coin or read card
    public void processPayment(){
        switch(paymentOption){
            case PAYMENT_BY_CARD:
                break;
            case PAYMENT_BY_COIN:
                if(coinReceiver.getTotal()>price){
                    completePayment();
                }else{
                    continuePayment();
                }
            default:
                break;
        }
    }
    
//    after txCtrl.processMoneyReceived()
    public void continuePayment(){
        switch(paymentOption){
            case PAYMENT_BY_CARD:
                txCtrl.getCustomerPanel().setCardInsertBoxActive(true);
            case PAYMENT_BY_COIN:
                txCtrl.getCustomerPanel().setCoinInputBoxActive(true);
            default:     
        }
    }
    
//    when txCtrl.completeTransaction()
    public void completePayment(){
        switch(paymentOption){
            case PAYMENT_BY_CARD:
                cardReader.complete();
            case PAYMENT_BY_COIN:
                int change = coinReceiver.getTotal() - this.price;
                if(change > 0){
                    changeGiver.setTotal(change);
                    changeGiver.complete();
                }else{
                    txCtrl.getCustomerPanel().setChange(0);
                }
                coinReceiver.complete();
            default:
                
        }
    }
    
//    when txCtrl.ternimateTransaction
    public void terminatePayment(){
        coinReceiver.terminate();
        changeGiver.terminate();
        cardReader.terminate();
        resetPayment();
    }
    
    public void setPrice(int p){
        this.price = p;
    }
    
    public TransactionController getTransactionController(){
        return this.txCtrl;
    }   

    public PaymentColleague getCoinReceiver() {
        return coinReceiver;
    }

    public PaymentColleague getCardReader() {
        return cardReader;
    }
    
    
}
