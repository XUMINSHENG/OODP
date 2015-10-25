/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

/**
 *
 * @author xieqiang
 */
public class CardMediator extends PaymentMediator{
    
    private CardReaderColleague cardReader;
    
    public CardMediator(){
//        this.getTxCtrl() = t;
        cardReader = new CardReaderColleague(this);
    }
    
    @Override
    public void startPayment() {
        resetPayment();
        getTxCtrl().getCustomerPanel().setCardInsertBoxActive(true);
    }
    
    @Override
    public void invalidPayment(String msg) {
        System.out.println(msg);
    }

    @Override
    public void processPayment(int total) {
        getTxCtrl().getCustomerPanel().setCardInsertBoxActive(false);
        getTxCtrl().getCustomerPanel().displayInvalidCard(false);
        getTxCtrl().getCustomerPanel().setTotalMoneyInserted(total);
        getTxCtrl().processMoneyReceived(total);
    }

    @Override
    public void continuePayment() {
        invalidPayment("Not Enough Money inside Card");
        cardReader.reset();
        getTxCtrl().getCustomerPanel().setCardInsertBoxActive(true);
        getTxCtrl().getCustomerPanel().displayInvalidCard(true);
        getTxCtrl().getCustomerPanel().setTotalMoneyInserted(0);
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
    public void cancelPayment() {
        getTxCtrl().getCustomerPanel().setCardInsertBoxActive(false);
        getTxCtrl().getCustomerPanel().displayInvalidCard(false);
        getTxCtrl().getCustomerPanel().setTotalMoneyInserted(0);
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
