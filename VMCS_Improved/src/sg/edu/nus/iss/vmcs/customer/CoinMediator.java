/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.util.ArrayList;
import sg.edu.nus.iss.vmcs.customer.transactionController.states.FaultState;

/**
 *
 * @author xieqiang
 */
public class CoinMediator extends PaymentMediator{

    private CoinReceiverColleague coinReceiver;
    private ChangeGiverColleague changeGiver;
    
    public CoinMediator(){
        coinReceiver = new CoinReceiverColleague(this);
        changeGiver = new ChangeGiverColleague(this);
    }

    @Override
    public void startPayment() {
        resetPayment();
        getTxCtrl().getCustomerPanel().setCoinInputBoxActive(true);
    }

    @Override
    public void invalidPayment(String msg) {
        System.out.println(msg);
        getTxCtrl().getCustomerPanel().displayInvalidCoin(true);
        continuePayment();
    }
    
    @Override
    public void processPayment(int total) {
        getTxCtrl().getCustomerPanel().setCoinInputBoxActive(false);
        getTxCtrl().getCustomerPanel().displayInvalidCoin(false);
        getTxCtrl().getCustomerPanel().setTotalMoneyInserted(total);
        getTxCtrl().getCustomerPanel().setChange("");
        getTxCtrl().processMoneyReceived(total);
    }

    @Override
    public void continuePayment() {
        getTxCtrl().getCustomerPanel().setCoinInputBoxActive(true);
    }

    @Override
    public void completePayment(int total) {
        if(total>0){
            if(!changeGiver.giveChange(total)){
                getTxCtrl().terminateFault();
            };
        }else{
            getTxCtrl().getCustomerPanel().setChange(0);
        }
        if(!coinReceiver.storeCash()){
            getTxCtrl().terminateFault();
        }
    }
    
    @Override
    public void resetPayment() {
        coinReceiver.reset();
        changeGiver.reset();
        getTxCtrl().getCustomerPanel().resetChange();
    }

    @Override
    public void cancelPayment() {
        coinReceiver.stopReceive();
        coinReceiver.refundCash();
        getTxCtrl().getCustomerPanel().setPaymentOptionBoxActive(false);
        getTxCtrl().getCustomerPanel().setCoinInputBoxActive(false);
        getTxCtrl().getCustomerPanel().displayInvalidCoin(false);
        getTxCtrl().getCustomerPanel().setTotalMoneyInserted(0);
    }

    public CoinReceiverColleague getCoinReceiver() {
        return coinReceiver;
    }

    @Override
    public void refresh() {
        changeGiver.displayChangeStatus();
    }
    
    
    
}
