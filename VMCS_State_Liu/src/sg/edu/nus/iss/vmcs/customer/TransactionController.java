/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.customer;

/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
import sg.edu.nus.iss.vmcs.system.MainController;
import sg.edu.nus.iss.vmcs.state.*;

/**
 * This control object coordinates the customer transactions for selection of a
 * drink brand, coin input, storage of coins and termination requests for
 * ongoing transactions.
 *
 * @author Team SE16T5E
 * @version 1.0 2008-10-01
 */
public class TransactionController {

    private MainController mainCtrl;
    private CustomerPanel custPanel;
    private DispenseController dispenseCtrl;
    private ChangeGiver changeGiver;
    private CoinReceiver coinReceiver;

    private TransactionState state;

    /**
     * Set to TRUE when change is successfully issued during the transaction.
     */
    private boolean changeGiven = false;
    /**
     * Set to TRUE when the drink is successfully dispensed during the
     * transaction.
     */
    private boolean drinkDispensed = false;
    /**
     * Price of the selected drink.
     */
    private int price = 0;
    /**
     * Identifier of the selected drink.
     */
    private int selection = -1;

    private int drinkIdentifier = -1;

    private int total = -1;

    /**
     * This constructor creates an instance of the TransactionController.
     *
     * @param mainCtrl the MainController.
     */
    public TransactionController(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.state = new InitialState();
        dispenseCtrl = new DispenseController(this);
        coinReceiver = new CoinReceiver(this);
        changeGiver = new ChangeGiver(this);
    }

    /**
     * This method displays and initialize the CustomerPanel.
     */
    public void handle() {
        if (state.handle(this)!=true)
        {
            setState(new FaultState());
            state.handle(this);
        }
    }

    public void refreshCustomerPanel() {
        /*
         if(custPanel==null){
         mainCtrl.getSimulatorControlPanel().setActive(SimulatorControlPanel.ACT_CUSTOMER,true);
         }
         */
        dispenseCtrl.updateDrinkPanel();
        dispenseCtrl.allowSelection(true);
        changeGiver.displayChangeStatus();
        custPanel.setTerminateButtonActive(true);
    }

    public void closeDown() {
        if (custPanel != null) {
            custPanel.closeDown();
        }
    }

    public void setChangeGiven(boolean changeGiven) {
        this.changeGiven = changeGiven;
    }

    public void setDrinkDispensed(boolean drinkDispensed) {
        this.drinkDispensed = drinkDispensed;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public int getSelection() {
        return selection;
    }

    public CustomerPanel getCustomerPanel() {
        return custPanel;
    }

    public DispenseController getDispenseController() {
        return dispenseCtrl;
    }

    public ChangeGiver getChangeGiver() {
        return changeGiver;
    }

    public CoinReceiver getCoinReceiver() {
        return coinReceiver;
    }

    public void refreshMachineryDisplay() {
        mainCtrl.getMachineryController().refreshMachineryDisplay();
    }

    public MainController getMainController() {
        return mainCtrl;
    }

    public void nullifyCustomerPanel() {
        custPanel = null;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public TransactionState getState() {
        return state;
    }

    public void setCustPanel(CustomerPanel custPanel) {
        this.custPanel = custPanel;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDrinkIdentifier() {
        return drinkIdentifier;
    }

    public void setDrinkIdentifier(int drinkIdentifier) {
        this.drinkIdentifier = drinkIdentifier;
    }
}//End of class TransactionController
