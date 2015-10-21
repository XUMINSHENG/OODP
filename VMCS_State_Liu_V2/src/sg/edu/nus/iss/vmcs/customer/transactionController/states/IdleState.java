/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.vmcs.customer.ChangeGiver;
import sg.edu.nus.iss.vmcs.customer.CoinReceiver;
import sg.edu.nus.iss.vmcs.customer.DispenseController;
import sg.edu.nus.iss.vmcs.customer.TransactionController;
import sg.edu.nus.iss.vmcs.store.DrinksBrand;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreItem;

/**
 *
 * @author Liu Xinzhuo
 */
public class IdleState implements TransactionControllerState{

    private final TransactionController txCtrl;
    private StoreItem storeItem;
    private DrinksBrand drinksBrand;
    private final CoinReceiver coinReceiver;
    private final DispenseController dispenseCtrl;
    private final ChangeGiver changeGiver;
    
    public IdleState(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
        this.changeGiver = txCtrl.getChangeGiver();
        this.coinReceiver = txCtrl.getCoinReceiver();
        this.dispenseCtrl = txCtrl.getDispenseController();
    }
    
    @Override
    public void startTransaction(int drinkIdentifier) {
            this.storeItem= txCtrl.getMainController().getStoreController().getStoreItem(Store.DRINK,drinkIdentifier);
            this.drinksBrand = (DrinksBrand) storeItem.getContent();
            txCtrl.setSelection(drinkIdentifier);
            txCtrl.setPrice(drinksBrand.getPrice());
            this.changeGiver.resetChange();
            dispenseCtrl.ResetCan();
            changeGiver.displayChangeStatus();
            dispenseCtrl.allowSelection(false);
            txCtrl.getCustomerPanel().setTerminateButtonActive(true);
            coinReceiver.startReceiver();
            txCtrl.setState(new TransactionState(txCtrl));
    }

    @Override
    public void processMoneyReceived(int total) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null,"Wrong State!");
        txCtrl.terminateFault();
    }

    @Override
    public void completeTransaction() {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null,"Wrong State!"); 
        txCtrl.terminateFault();
    }

    @Override
    public void cancelTransaction() {
        coinReceiver.stopReceive();
	coinReceiver.refundCash();
	dispenseCtrl.allowSelection(true);
	txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new IdleState(txCtrl));
    }

    @Override
    public void startMaintenance() {
	dispenseCtrl.allowSelection(false);
	coinReceiver.stopReceive();
	coinReceiver.refundCash();
	if(txCtrl.getCustomerPanel()!=null){
		txCtrl.getCustomerPanel().setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new MaintenanceState(txCtrl));
}

    @Override
    public void endMaintenance() {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null,"Wrong State!");
        txCtrl.terminateFault();
    }

    @Override
    public void terminateFault() {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null,"Wrong State!");
        txCtrl.terminateFault();
    }
    
}
