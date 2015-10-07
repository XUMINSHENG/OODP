/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.state;

import sg.edu.nus.iss.vmcs.customer.TransactionController;
import sg.edu.nus.iss.vmcs.store.DrinksBrand;
import sg.edu.nus.iss.vmcs.store.Store;
import sg.edu.nus.iss.vmcs.store.StoreItem;

/**
 *
 * @author Liu Xinzhuo
 */
public class IdleState implements TransactionState
{

    @Override
    public boolean handle(TransactionController txCtrl) {
        System.out.println(this.getClass().getName()+" start!");
        int drinkIdentifier = txCtrl.getDrinkIdentifier();
        txCtrl.setSelection(drinkIdentifier);
        StoreItem storeItem = txCtrl.getMainController().getStoreController().getStoreItem(Store.DRINK, drinkIdentifier);
        DrinksBrand drinksBrand = (DrinksBrand) storeItem.getContent();
        txCtrl.setPrice(drinksBrand.getPrice());
        txCtrl.getChangeGiver().resetChange();
        txCtrl.getDispenseController().ResetCan();
        txCtrl.getChangeGiver().displayChangeStatus();
        txCtrl.getDispenseController().allowSelection(false);
        txCtrl.getCoinReceiver().startReceiver();
        txCtrl.getCustomerPanel().setTerminateButtonActive(true);
        txCtrl.setState(new PaymentState());
        System.out.println("Set Next State:" + txCtrl.getState().getClass().getName());
        System.out.println(this.getClass().getName()+" end!");
        return true;
        }
}
