/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

import sg.edu.nus.iss.vmcs.customer.ChangeGiver;
import sg.edu.nus.iss.vmcs.customer.CoinReceiver;
import sg.edu.nus.iss.vmcs.customer.DispenseController;
import sg.edu.nus.iss.vmcs.customer.PaymentMediator;
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
//    private final CoinReceiver coinReceiver;
    private final DispenseController dispenseCtrl;
//    private final ChangeGiver changeGiver;
    private PaymentMediator mediator;
    
    public IdleState(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
//        this.changeGiver = txCtrl.getChangeGiver();
//        this.coinReceiver = txCtrl.getCoinReceiver();
        this.dispenseCtrl = txCtrl.getDispenseController();
    }
    
    @Override
    public void startTransaction(int drinkIdentifier) {
            this.storeItem= txCtrl.getMainController().getStoreController().getStoreItem(Store.DRINK,drinkIdentifier);
            this.drinksBrand = (DrinksBrand) storeItem.getContent();
            txCtrl.setSelection(drinkIdentifier);
            txCtrl.setPrice(drinksBrand.getPrice());
//            this.changeGiver.resetChange();
            dispenseCtrl.ResetCan();
//            changeGiver.displayChangeStatus();
            dispenseCtrl.allowSelection(false);
            txCtrl.getCustomerPanel().setTerminateButtonActive(true);
//            coinReceiver.startReceiver();
            mediator = null;
            txCtrl.getCustomerPanel().setPaymentOptionBoxActive(true);
            
    }

    @Override
    public void processMoneyReceived(int total) {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void completeTransaction(int change) {
       throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void cancelTransaction() {
//        coinReceiver.stopReceive();
//	coinReceiver.refundCash();
        if(mediator!=null){
            mediator.cancelPayment();
        }
	dispenseCtrl.allowSelection(true);
	txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new IdleState(txCtrl));
    }

    @Override
    public void startMaintenance() {
	dispenseCtrl.allowSelection(false);
        if(mediator!=null){
            mediator.cancelPayment();
        }
	//coinReceiver.stopReceive();
	//coinReceiver.refundCash();
        
	if(txCtrl.getCustomerPanel()!=null){
		txCtrl.getCustomerPanel().setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new MaintenanceState(txCtrl));
}

    @Override
    public void endMaintenance() {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void terminateFault() {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void startPayment() {
        txCtrl.getCustomerPanel().setPaymentOptionBoxActive(false);
        this.mediator = txCtrl.getMediator();
        this.mediator.startPayment();
        txCtrl.setState(new TransactionState(txCtrl));
    }
    
}
