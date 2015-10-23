/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.vmcs.customer.CoinReceiver;
import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.DispenseController;
import sg.edu.nus.iss.vmcs.customer.TransactionController;

/**
 *
 * @author Liu Xinzhuo
 */
public class FaultState implements TransactionControllerState{
    
    private final TransactionController txCtrl;
    private final CustomerPanel custPanel;
    private final CoinReceiver coinReceiver;
    private final DispenseController dispenseCtrl;
    
    
    public FaultState(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
        this.custPanel = txCtrl.getCustomerPanel();
        this.coinReceiver = txCtrl.getCoinReceiver();
        this.dispenseCtrl = txCtrl.getDispenseController();
    }

    FaultState() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void startTransaction(int drinkIdentifier) {
        Logger.getLogger(FaultState.class.getName()).log(Level.SEVERE,"Wrong State!");
    }

    @Override
    public void processMoneyReceived(int total) {
        Logger.getLogger(FaultState.class.getName()).log(Level.SEVERE,"Wrong State!");
    }

    @Override
    public void completeTransaction() {
        Logger.getLogger(FaultState.class.getName()).log(Level.SEVERE,"Wrong State!");
    }

    @Override
    public void cancelTransaction() {
        Logger.getLogger(FaultState.class.getName()).log(Level.SEVERE,"Wrong State!");
    }

    @Override
    public void startMaintenance() {
        Logger.getLogger(FaultState.class.getName()).log(Level.SEVERE,"Wrong State!");
    }

    @Override
    public void endMaintenance() {
        Logger.getLogger(FaultState.class.getName()).log(Level.SEVERE,"Wrong State!");
    }

    @Override
    public void terminateFault() {
        System.out.println("TerminateTransaction: Begin");
        dispenseCtrl.allowSelection(false);
        coinReceiver.stopReceive();
	coinReceiver.refundCash();
	if(custPanel!=null)
        {
            custPanel.setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        System.out.println("TerminateTransaction: End");
    }
}
