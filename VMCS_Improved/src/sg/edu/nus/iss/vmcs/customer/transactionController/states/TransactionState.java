/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.DispenseController;
import sg.edu.nus.iss.vmcs.customer.PaymentMediator;
import sg.edu.nus.iss.vmcs.customer.TransactionController;

/**
 *
 * @author Liu Xinzhuo
 */
public class TransactionState extends TransactionControllerState{

    private final CustomerPanel custPanel;
    private final DispenseController dispenseCtrl;
    private int selection=-1;
    private PaymentMediator mediator;
    
    private int price = 0;
    
    public TransactionState(TransactionController txCtrl)
    {
        super(txCtrl);
        this.dispenseCtrl = txCtrl.getDispenseController();
        this.custPanel = txCtrl.getCustomerPanel();
        this.selection = txCtrl.getSelection();
        this.price = txCtrl.getPrice();
        this.mediator = txCtrl.getMediator();
    }

    @Override
    public void processMoneyReceived(int total) {
        if(total>=price){
            int change = total - price;
            completeTransaction(change);
        }
	else{
            mediator.continuePayment();
	}
    }

    @Override
    public void completeTransaction(int change) {
	if (dispenseCtrl.dispenseDrink(selection)==false)
        {
            txCtrl.terminateFault();
            return;
        }
	mediator.completePayment(change);
        dispenseCtrl.allowSelection(true);
	txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new IdleState(txCtrl));
    }
    
        @Override
    public void terminateFault() {
        System.out.println("TerminateTransaction: Begin");
        dispenseCtrl.allowSelection(false);
        if(mediator!=null){
            mediator.cancelPayment();
        }
        
//      coinReceiver.stopReceive();
//	coinReceiver.refundCash();
        
	if(custPanel!=null)
        {
            custPanel.setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new FaultState(txCtrl));
        System.out.println("TerminateTransaction: End");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("transactionstate cancel");
        if(mediator!=null)
            mediator.cancelPayment();
	dispenseCtrl.allowSelection(true);
        txCtrl.getCustomerPanel().setPaymentOptionBoxActive(false);
	txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new IdleState(txCtrl));
    }

    @Override
    public void startMaintenance() {
        dispenseCtrl.allowSelection(false);
        if(mediator != null)
            mediator.cancelPayment();
	if(custPanel!=null){
		custPanel.setTerminateButtonActive(false);
	}
//      coinReceiver.stopReceive();
//	coinReceiver.refundCash();
        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new MaintenanceState(txCtrl));
    }

    @Override
    public void startPayment() {
        txCtrl.getCustomerPanel().setPaymentOptionBoxActive(false);
        this.mediator = txCtrl.getMediator();
        this.mediator.startPayment();
    }
}
