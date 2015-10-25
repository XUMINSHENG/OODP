/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

import sg.edu.nus.iss.vmcs.customer.TransactionController;

/**
 *
 * @author Liu Xinzhuo
 */
public abstract class TransactionControllerState {
    
    protected final TransactionController txCtrl;

    protected TransactionControllerState(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
    }
    
    public void startTransaction(int drinkIdentifier)
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
    public void startPayment()
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
    public void processMoneyReceived(int total)
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
    public void completeTransaction(int change)
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
    public void cancelTransaction()
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
    public void startMaintenance ()
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
    public void endMaintenance()
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
    public void terminateFault()
    {
        throw new UnsupportedOperationException("Wrong State!");
    }
}
