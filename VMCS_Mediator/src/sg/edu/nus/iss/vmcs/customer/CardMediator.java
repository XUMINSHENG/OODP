/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import sg.edu.nus.iss.vmcs.store.Card;

/**
 *
 * @author xieqiang
 */
public class CardMediator extends PaymentMediator{
    
    private TransactionController txCtrl;
    private CardReaderColleague cardReader;
    
    @Override
    public void startPayment() {
        resetPayment();
        txCtrl.getCustomerPanel().setCardInsertBoxActive(true);
    }
    
    @Override
    public void invalidPayment(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processPayment(int total) {
        txCtrl.getCustomerPanel().setCardInsertBoxActive(false);
        txCtrl.getCustomerPanel().displayInvalidCard(false);
        txCtrl.getCustomerPanel().setTotalMoneyInserted(total);
        txCtrl.processMoneyReceived(total);
    }

    @Override
    public void continuePayment() {
        invalidPayment("Not Enough Money inside Card");
        txCtrl.getCustomerPanel().setCardInsertBoxActive(true);
        txCtrl.getCustomerPanel().displayInvalidCard(true);
        txCtrl.getCustomerPanel().setTotalMoneyInserted(0);
        cardReader.reset();
    }

    @Override
    public void completePayment(int price) {
        cardReader.chargeCard(price);
    }

    @Override
    public void resetPayment() {
        cardReader.reset();
    }
    
    @Override
    public TransactionController getTxCtrl() {
        return txCtrl;
    }

    @Override
    public void setTxCtrl(TransactionController txCtrl) {
        this.txCtrl = txCtrl;
    }

    @Override
    public void cancelPayment() {
        txCtrl.getCustomerPanel().setCardInsertBoxActive(false);
        txCtrl.getCustomerPanel().displayInvalidCard(false);
        txCtrl.getCustomerPanel().setTotalMoneyInserted(0);
        cardReader.reset();
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CardReaderColleague getCardReader() {
        return cardReader;
    }
    
    
}
