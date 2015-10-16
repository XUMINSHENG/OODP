/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer.transactionController.states;

/**
 *
 * @author Liu Xinzhuo
 */
public interface TransactionControllerState {
    public void startTransaction(int drinkIdentifier);
    public void processMoneyReceived(int total);
    public void completeTransaction();
    public void cancelTransaction();
    public void startMaintenance ();
    public void endMaintenance();
    public void terminateFault();
}
