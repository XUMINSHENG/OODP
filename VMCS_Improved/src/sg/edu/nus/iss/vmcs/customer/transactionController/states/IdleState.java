/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

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
public class IdleState extends TransactionControllerState{

    private StoreItem storeItem;
    private DrinksBrand drinksBrand;
    private final DispenseController dispenseCtrl;
    private PaymentMediator mediator;
    
    public IdleState(TransactionController txCtrl) {
        super(txCtrl);
        this.dispenseCtrl = txCtrl.getDispenseController();
    }
    
    @Override
    public void startTransaction(int drinkIdentifier) {
        this.storeItem= txCtrl.getMainController().getStoreController().getStoreItem(Store.DRINK,drinkIdentifier);
        this.drinksBrand = (DrinksBrand) storeItem.getContent();
        txCtrl.setSelection(drinkIdentifier);
        txCtrl.setPrice(drinksBrand.getPrice());
        dispenseCtrl.ResetCan();
        dispenseCtrl.allowSelection(false);
        txCtrl.getCustomerPanel().setTerminateButtonActive(true);
        this.mediator = txCtrl.getMediator();
        if(mediator!=null){
            mediator.cancelPayment();
        }
        mediator = null;
        txCtrl.getCustomerPanel().setPaymentOptionBoxActive(true);
//            this.changeGiver.resetChange();
//            changeGiver.displayChangeStatus();
//            coinReceiver.startReceiver();
        txCtrl.setState(new TransactionState(txCtrl));
    }

    @Override
    public void startMaintenance() {
	dispenseCtrl.allowSelection(false);
        this.mediator = txCtrl.getMediator();
        if(mediator!=null){
            mediator.cancelPayment();
        }
        
	if(txCtrl.getCustomerPanel()!=null){
		txCtrl.getCustomerPanel().setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new MaintenanceState(txCtrl));
    }
    
    @Override
    public void cancelTransaction() {
        System.out.println("transactionstate cancel");
    }
}
