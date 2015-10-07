/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.state;

import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.TransactionController;

/**
 *
 * @author Liu Xinzhuo
 */
public class MaintenceState implements TransactionState{

    @Override
    public boolean handle(TransactionController txCtrl) {
        System.out.println(this.getClass().getName()+" start!");
        txCtrl.getDispenseController().allowSelection(false);
        txCtrl.getCoinReceiver().stopReceive();
        txCtrl.getCoinReceiver().refundCash();
        CustomerPanel custPanel = txCtrl.getCustomerPanel();
        if (custPanel != null) {
            custPanel.setTerminateButtonActive(false);
        }
        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new IdleState());
        System.out.println("Set Next State:" + txCtrl.getState().getClass().getName());
        System.out.println(this.getClass().getName()+" end!");
        return true;
    }
    
}
