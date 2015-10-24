/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.TransactionController;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;

/**
 *
 * @author Liu Xinzhuo
 */
public class MaintenanceState implements TransactionControllerState{
    
    private final TransactionController txCtrl;
    
    public MaintenanceState(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
    }
    
    @Override
    public void startTransaction(int drinkIdentifier) {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void processMoneyReceived(int total) {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void completeTransaction() {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void cancelTransaction() {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void startMaintenance() {
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void endMaintenance() {
                CustomerPanel custPanel=txCtrl.getCustomerPanel();
		if(custPanel==null){
			txCtrl.getMainController().getSimulatorControlPanel().setActive(SimulatorControlPanel.ACT_CUSTOMER, true);
		}
		else{
			txCtrl.refreshCustomerPanel();
		}
                txCtrl.setState(new IdleState(txCtrl));
    }

    @Override
    public void terminateFault() {
        throw new UnsupportedOperationException("Wrong State!");
    }
    
}
