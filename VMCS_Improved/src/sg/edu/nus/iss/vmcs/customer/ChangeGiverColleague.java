/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import sg.edu.nus.iss.vmcs.store.CashStore;
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

//    @Override
//    public void start() {
//        displayChangeStatus();
//    }
//
//    @Override
//    public void process(StoreObject s) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void complete(int total) {
//        giveChange(total);
//    }

    @Override
    public void reset() {
        displayChangeStatus();
    }
    
    public void displayChangeStatus(){
	CustomerPanel custPanel=getMediator().getTxCtrl().getCustomerPanel();
	if(custPanel==null)
		return;
	boolean isAnyDenoEmpty=false;
        MainController mainCtrl=getMediator().getTxCtrl().getMainController();
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
    
    /**
     * 
     * @param changeRequired
     * @return 
     */
    public boolean giveChange(int changeRequired){
        if(changeRequired==0)
            return true;
        try{
            int changeBal= changeRequired;
            System.out.println("changeBal*****"+ changeBal);
            MainController mainCtrl = getMediator().getTxCtrl().getMainController();
            StoreController storeCtrl = mainCtrl.getStoreController();
            int cashStoreSize = storeCtrl.getStoreSize(Store.CASH);
            System.out.println("cashStoreSize*****"+cashStoreSize);
            //get the head of the chain
            CashStoreItem cashStoreItem = ((CashStore)storeCtrl.getStore(Store.CASH)).getHighestValueCashStoreItem();
            //Create a hashMap for collecting the required quantity for each CashStoreItem
            HashMap<CashStoreItem,Integer> cashStoreItemMap = new HashMap<CashStoreItem, Integer>();
            //chain gets started
            changeBal = cashStoreItem.handleChange(changeBal,cashStoreItemMap);
            //give change
            getMediator().getTxCtrl().getMainController().getMachineryController().giveChange(cashStoreItemMap);
            
            getMediator().getTxCtrl().getCustomerPanel().setChange(changeRequired-changeBal);
            if(changeBal>0)
                getMediator().getTxCtrl().getCustomerPanel().displayChangeStatus(true);
        }catch(VMCSException ex){
            getMediator().getTxCtrl().terminateFault();
            return false;
        }
        return true;
    }
}
