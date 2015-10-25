/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

/**
 *
 * @author xiejiabao
 */
public abstract class PaymentMediator {
    private TransactionController txCtrl;
    
    public abstract void refresh();
    public abstract void startPayment();
    public abstract void invalidPayment(String msg);
    public abstract void processPayment(int total);
    public abstract void continuePayment();
    public abstract void completePayment(int total);
    public abstract void resetPayment();
    public abstract void cancelPayment();

    public TransactionController getTxCtrl() {
        return txCtrl;
    }

    public void setTxCtrl(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
    }
    
    
}
