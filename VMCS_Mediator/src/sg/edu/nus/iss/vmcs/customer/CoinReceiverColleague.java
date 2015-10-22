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

    private final int PAYMENT_OPTION = PaymentMediator.PAYMENT_BY_COIN;
    private ArrayList coins;
    
    public CoinReceiverColleague(PaymentMediator m) {
        super(m);
        reset();
    }

    @Override
    public void start() {
        reset();
        getMediator().getTransactionController().getCustomerPanel().setCoinInputBoxActive(true);
        getMediator().getTransactionController().getCustomerPanel().setTotalMoneyInserted(0);
    }

    @Override
    public void process(StoreObject s) {
        Coin coin = (Coin)s;
        coins.add(coin);
        int value = coin.getValue();
        setTotal(getTotal()+value);
        getMediator().processPayment(PAYMENT_OPTION);
    }

    @Override
    public void complete() {
        storeCash();
    }

    @Override
    public void reset() {
        coins = new ArrayList();
        setTotal(0);
    }
    
    public void receiveCoin(double weight){
        CashStore store = (CashStore)getMediator().getTransactionController().getMainController().getStoreController().getStore(Store.CASH);
        Coin coin= store.findCoin(weight);
        if(coin==null){
            getMediator().invalidPayment(PAYMENT_OPTION);
	}
	else{
            process(coin);
        }
    }
    
    private boolean storeCash(){
	MachineryController machineryCtrl=getMediator().getTransactionController().getMainController().getMachineryController();
	try{
		for(int i=0;i<coins.size();i++){
			Coin coin=(Coin)coins.get(i);
			machineryCtrl.storeCoin(coin);
		}
		reset();
		getMediator().getTransactionController().getCustomerPanel().setTotalMoneyInserted(0);
	}
	catch(VMCSException ex){
            getMediator().terminatePayment();
//		getMediator().getTransactionController().terminateFault();
		return false;
	}
	return true;
    }

    @Override
    public void terminate() {
        int total = getTotal();
        if(total>0){
            getMediator().getTransactionController().getCustomerPanel().setChange(total);
            getMediator().getTransactionController().getCustomerPanel().setTotalMoneyInserted(0);
            getMediator().getTransactionController().getCustomerPanel().displayInvalidCoin(false);
        }
        reset();
        getMediator().getTransactionController().getCustomerPanel().setCoinInputBoxActive(false);
    }
}
