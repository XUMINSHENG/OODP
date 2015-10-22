/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.vmcs.machinery.MachineryController;
import sg.edu.nus.iss.vmcs.store.CashStore;
import sg.edu.nus.iss.vmcs.store.Coin;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreObject;
import sg.edu.nus.iss.vmcs.util.VMCSException;

/**
 *
 * @author xiejiabao
 */
public class CoinReceiverColleague extends PaymentColleague{

    private ArrayList coins;
    private int totalInserted;
    
    public CoinReceiverColleague(PaymentMediator m) {
        super(m);
        reset();
    }
    
    @Override
    public void reset() {
        coins = new ArrayList();
        this.totalInserted = 0;
    }
    
    public void receiveCoin(double weight){
        CashStore store = (CashStore)getMediator().getTxCtrl().getMainController().getStoreController().getStore(Store.CASH);
        Coin coin= store.findCoin(weight);
        if(coin==null){
            getMediator().invalidPayment("Invalid Coin");
	}
	else{
            coins.add(coin);
            this.totalInserted = this.totalInserted + coin.getValue();
            getMediator().processPayment(this.totalInserted);
        }
    }
    
    public boolean storeCash(){
	MachineryController machineryCtrl=getMediator().getTxCtrl().getMainController().getMachineryController();
	try{
		for(int i=0;i<coins.size();i++){
			Coin coin=(Coin)coins.get(i);
			machineryCtrl.storeCoin(coin);
		}
		reset();
		getMediator().getTxCtrl().getCustomerPanel().setTotalMoneyInserted(0);
	}
	catch(VMCSException ex){
            getMediator().cancelPayment();
//		getMediator().getTransactionController().terminateFault();
		return false;
	}
	return true;
    }
    
    public void stopReceive(){
	CustomerPanel custPanel=getMediator().getTxCtrl().getCustomerPanel();
	if(custPanel==null){
		return;
	}
	custPanel.setCoinInputBoxActive(false);
    }
	
    public void refundCash(){
	if(totalInserted==0)
		return;
	getMediator().getTxCtrl().getCustomerPanel().setChange(totalInserted);
	getMediator().getTxCtrl().getCustomerPanel().setTotalMoneyInserted(0);
	getMediator().getTxCtrl().getCustomerPanel().displayInvalidCoin(false);
	reset();
    }
    
    
}
