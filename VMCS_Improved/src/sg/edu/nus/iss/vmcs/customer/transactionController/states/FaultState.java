/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

import sg.edu.nus.iss.vmcs.customer.CoinReceiver;
import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.DispenseController;
import sg.edu.nus.iss.vmcs.customer.PaymentMediator;
import sg.edu.nus.iss.vmcs.customer.TransactionController;

/**
 *
 * @author Liu Xinzhuo
 */
public class FaultState implements TransactionControllerState{
    
    private final TransactionController txCtrl;
    private final CustomerPanel custPanel;
//    private final CoinReceiver coinReceiver;
    private final DispenseController dispenseCtrl;
    private final PaymentMediator mediator;
    
    public FaultState(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
        this.custPanel = txCtrl.getCustomerPanel();
//        this.coinReceiver = txCtrl.getCoinReceiver();
        this.mediator = txCtrl.getMediator();
        this.dispenseCtrl = txCtrl.getDispenseController();
    }

    public FaultState() {
        throw new UnsupportedOperationException("Not supported yet."); 
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
    public void completeTransaction(int change) {
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
        throw new UnsupportedOperationException("Wrong State!");
    }

    @Override
    public void terminateFault() {
        System.out.println("TerminateTransaction: Begin");
        dispenseCtrl.allowSelection(false);
        if(mediator!=null){
            mediator.cancelPayment();
        }
//        coinReceiver.stopReceive();
//	coinReceiver.refundCash();
	if(custPanel!=null)
        {
            custPanel.setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        System.out.println("TerminateTransaction: End");
    }

    @Override
    public void startPayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
