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
public class FaultState extends TransactionControllerState{
    
    private final TransactionController txCtrl;
    private final CustomerPanel custPanel;
    private final DispenseController dispenseCtrl;
    private final PaymentMediator mediator;
    
    public FaultState(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
        this.custPanel = txCtrl.getCustomerPanel();
        this.mediator = txCtrl.getMediator();
        this.dispenseCtrl = txCtrl.getDispenseController();
    }

    @Override
    public void terminateFault() {
        System.out.println("TerminateTransaction: Begin");
        dispenseCtrl.allowSelection(false);
        if(mediator!=null){
            mediator.cancelPayment();
        }
	if(custPanel!=null)
        {
            custPanel.setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        System.out.println("TerminateTransaction: End");
    }
    
    @Override
    public void startMaintenance() {
	dispenseCtrl.allowSelection(false);
        if(mediator!=null){
            mediator.cancelPayment();
        }
        
	if(txCtrl.getCustomerPanel()!=null){
            txCtrl.getCustomerPanel().setTerminateButtonActive(false);
	}
        txCtrl.refreshMachineryDisplay();
        txCtrl.setState(new MaintenanceState(txCtrl));
    }
}
