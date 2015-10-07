/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.state;

import sg.edu.nus.iss.vmcs.customer.TransactionController;

/**
 *
 * @author Liu Xinzhuo
 */
public class CompleteState implements TransactionState{

    @Override
    public boolean handle(TransactionController txCtrl) {
        System.out.println(this.getClass().getName()+" start!");
        txCtrl.getDispenseController().dispenseDrink(txCtrl.getSelection());
        int totalMoneyInserted = txCtrl.getCoinReceiver().getTotalInserted();
        int change = totalMoneyInserted - txCtrl.getPrice();
        if (change > 0) {
            txCtrl.getChangeGiver().giveChange(change);
        } else {
            txCtrl.getCustomerPanel().setChange(0);
        }
        txCtrl.getCoinReceiver().storeCash();
        txCtrl.getDispenseController().allowSelection(true);

        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new IdleState());
        System.out.println("Set Next State:" + txCtrl.getState().getClass().getName());
        System.out.println(this.getClass().getName()+" end!");
        return true;
    } 
    
}
