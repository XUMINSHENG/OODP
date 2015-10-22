/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.store.CashStoreItem;
import sg.edu.nus.iss.vmcs.store.Coin;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreController;
import sg.edu.nus.iss.vmcs.store.StoreItem;
import sg.edu.nus.iss.vmcs.store.StoreObject;
import sg.edu.nus.iss.vmcs.system.MainController;
import sg.edu.nus.iss.vmcs.util.VMCSException;

/**
 *
 * @author xiejiabao
 */
public class ChangeGiverColleague extends PaymentColleague{

    public ChangeGiverColleague(PaymentMediator m) {
        super(m);
    }

    @Override
    public void start() {
        displayChangeStatus();
    }

    @Override
    public void process(StoreObject s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void complete() {
        giveChange(getTotal());
    }

    @Override
    public void reset() {
        getMediator().getTransactionController().getCustomerPanel().resetChange();
    }
    
    private void displayChangeStatus(){
		CustomerPanel custPanel=getMediator().getTransactionController().getCustomerPanel();
		if(custPanel==null)
			return;
		boolean isAnyDenoEmpty=false;
		MainController mainCtrl=getMediator().getTransactionController().getMainController();
		StoreController storeCtrl=mainCtrl.getStoreController();
		StoreItem[] cashStoreItems=storeCtrl.getStore(Store.CASH).getItems();
		for(int i=0;i<cashStoreItems.length;i++){
			StoreItem storeItem=cashStoreItems[i];
			CashStoreItem cashStoreItem=(CashStoreItem)storeItem;
			int quantity=cashStoreItem.getQuantity();
			if(quantity==0)
				isAnyDenoEmpty=true;
        	}
	custPanel.displayChangeStatus(isAnyDenoEmpty);
    }
    
    private boolean giveChange(int changeRequired){
	if(changeRequired==0)
		return true;
    	try{
    		int changeBal=changeRequired;
		MainController mainCtrl=getMediator().getTransactionController().getMainController();
		StoreController storeCtrl=mainCtrl.getStoreController();
		int cashStoreSize=storeCtrl.getStoreSize(Store.CASH); 
		for(int i=cashStoreSize-1;i>=0;i--){
    			StoreItem cashStoreItem=storeCtrl.getStore(Store.CASH).getStoreItem(i);
    			int quantity=cashStoreItem.getQuantity();
    			Coin coin=(Coin)cashStoreItem.getContent();
				int value=coin.getValue();
				int quantityRequired=0;
				while(changeBal>0&&changeBal>=value&&quantity>0){
					changeBal-=value;
					quantityRequired++;
					quantity--;
				}
				mainCtrl.getMachineryController().giveChange(i,quantityRequired);
			}
			getMediator().getTransactionController().getCustomerPanel().setChange(changeRequired-changeBal);
			if(changeBal>0)
				getMediator().getTransactionController().getCustomerPanel().displayChangeStatus(true);
		}
		catch(VMCSException ex){
                    getMediator().terminatePayment();
//			getMediator().getTransactionController().terminateFault();
			return false;
		}
		return true;
	}

    @Override
    public void terminate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
