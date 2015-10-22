/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.util.ArrayList;

/**
 *
 * @author xieqiang
 */
public class CoinMediator extends PaymentMediator{

    private TransactionController txCtrl;
    private CoinReceiverColleague coinReceiver;
    private ChangeGiverColleague changeGiver;
    
    public CoinMediator(){
        coinReceiver = new CoinReceiverColleague(this);
        changeGiver = new ChangeGiverColleague(this);
    }

    @Override
    public void startPayment() {
        resetPayment();
        txCtrl.getCustomerPanel().setCoinInputBoxActive(true);
    }

    @Override
    public void invalidPayment(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void processPayment(int total) {
        txCtrl.getCustomerPanel().setCoinInputBoxActive(false);
        txCtrl.getCustomerPanel().displayInvalidCoin(false);
        txCtrl.getCustomerPanel().setTotalMoneyInserted(total);
        txCtrl.getCustomerPanel().setChange("");
        txCtrl.processMoneyReceived(total);
    }

    @Override
    public void continuePayment() {
        txCtrl.getCustomerPanel().setCoinInputBoxActive(true);
    }

    @Override
    public void completePayment(int total) {
        if(total>0){
            changeGiver.giveChange(total);
        }else{
            txCtrl.getCustomerPanel().setChange(0);
        }
        coinReceiver.storeCash();
    }
    
    @Override
    public void resetPayment() {
        coinReceiver.reset();
        changeGiver.reset();
        txCtrl.getCustomerPanel().resetChange();
    }
    
    @Override
    public TransactionController getTxCtrl() {
        return txCtrl;
    }

    @Override
    public void setTxCtrl(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
    }

    @Override
    public void cancelPayment() {
        coinReceiver.stopReceive();
        coinReceiver.refundCash();
    }

    public CoinReceiverColleague getCoinReceiver() {
        return coinReceiver;
    }

    @Override
    public void refresh() {
        changeGiver.displayChangeStatus();
    }
    
    
    
}
