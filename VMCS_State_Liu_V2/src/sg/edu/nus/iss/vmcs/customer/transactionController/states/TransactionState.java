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
import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.DispenseController;
import sg.edu.nus.iss.vmcs.customer.TransactionController;

/**
 *
 * @author Liu Xinzhuo
 */
public class TransactionState implements TransactionControllerState{

    private final TransactionController txCtrl;
    private final CoinReceiver coinReceiver;
    private final CustomerPanel custPanel;
    private final DispenseController dispenseCtrl;
    private final ChangeGiver changeGiver;
    private int selection=-1;
    
    private int price = 0;
    
    public TransactionState(TransactionController txCtrl)
    {
        this.txCtrl = txCtrl;
        this.coinReceiver = txCtrl.getCoinReceiver();
        this.dispenseCtrl = txCtrl.getDispenseController();
        this.custPanel = txCtrl.getCustomerPanel();
        this.changeGiver = txCtrl.getChangeGiver();
        this.selection = txCtrl.getSelection();
        this.price = txCtrl.getPrice();
    }
    
    @Override
    public void startTransaction(int drinkIdentifier) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null,"Wrong State!"); 
        txCtrl.terminateFault();
    }

    @Override
    public void processMoneyReceived(int total) {
        if(total>=price)
	completeTransaction();
	else{
	coinReceiver.continueReceive();
	}
    }

    @Override
    public void completeTransaction() {
	if (dispenseCtrl.dispenseDrink(selection)==false)
        {
            return;
        }
	int totalMoneyInserted=coinReceiver.getTotalInserted();
	int change=totalMoneyInserted-price;
	if(change>0){
		if(changeGiver.giveChange(change)==false)
                {
                    return;
                }
	}
	else{
		custPanel.setChange(0);
	}
	if(coinReceiver.storeCash()==false)
        {
            return;
        }
        dispenseCtrl.allowSelection(true);
	txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new IdleState(txCtrl));
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
	if(custPanel!=null){
		custPanel.setTerminateButtonActive(false);
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
