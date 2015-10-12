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
    public void displayCustomerPanel() {
        if (state.handle(this)!=true)
        {
            setState(new FaultState());
            terminateFault();
        }
    }

    /**
     * This method will start the customer transaction&#46; It receives the
     * identification for the selected drink brand (item) from the Customer
     * Panel&#46; The following actions are performed in the method:
     * <br>
     * 1- The price of the selected item is obtained&#46;
     * <br>
     * 2- The Refund/ Change Tray Display is reset&#46;
     * <br>
     * 3- The Can Collection Box is reset&#46;
     * <br>
     * 4- The Drink Selection Box is deactivated to disallow the selection of
     * further drinks when the transaction is in progress&#46;
     * <br>
     * 5- The Coin Receiver will be instructed to start receiving the coins&#46;
     *
     * @param drinkIdentifier the drink brand item identifier.
     */
    public void startTransaction() {
        if (state.handle(this)!=true)
        {
            setState(new FaultState());
            terminateFault();
        }
    }

    /**
     * This method processes the money received by the Coin Receiver during the
     * progress of a transaction&#46; The following actions are performed during
     * this method:
     * <br>
     * 1- The current total money inserted is obtained from the Coin
     * Receiver&#46;
     * <br>
     * 2- If the received money is more than or equal to the price of the drink,
     * method CompleteTransaction of the Transaction Controller is
     * triggered&#46;
     * <br>
     * 3- If the received money is less than the price of the drink, the Coin
     * Receiver is instructed to continue receiving the coin&#46;
     *
     * @param total the total money received&#46;
     */
    public void processMoneyReceived() {
        if (state.handle(this)!=true)
        {
            setState(new FaultState());
            terminateFault();
        }
    }

    /**
     * This method is performed when the Transaction Controller is informed that
     * coin entry is complete and the money received is sufficient to dispense
     * the drink. The following actions are performed.
     * <br>
     * 1- Dispense the drink.
     * <br>
     * 2- Give change if necessary.
     * <br>
     * 3- Store the Coins that have been entered into the Cash Store.
     * <br>
     * 4- Reset the Drink Selection Box to allow further transactions.
     */
    public void completeTransaction() {
        if (state.handle(this)!=true)
        {
            setState(new FaultState());
            terminateFault();
        }
    }

    /**
     * If the TransactionController is informed that a fault was discovered
     * while dispensing a drink, giving change or storing Coins, it will use
     * this method to deactivate the Drink Selection Box and instruct the
     * CoinReceiver to refund the money inserted by the customer.
     */
    public void terminateFault() {
        if (state.handle(this) != true) {
            System.out.println("Fault can't be Terminated");
        }
    }

    /**
     * If the TransactionController receivers a request to terminate the current
     * transaction then following will occur:
     * <br>
     * 1- If there is no transaction in progress or coin input is completed then
     * the CustomerPanel will be instructed to deactivate the
     * DrinkSelectionBox&#46;
     * <br>
     * 2- If coin input is not yet complete, the CoinReceiver will be instructed
     * to stop coin input and refund the money entered so far&#46;
     * <br>
     * 3- The DrinkSelectionBox is then reset to allow further transactions&#46;
     */
    public void terminateTransaction() {
        setState(new MaintenceState());
        state.handle(this);
    }

    /**
     * This method will cancel an ongoing customer transaction.
     */
    public void cancelTransaction() {
        setState(new CancelState());
        if (state.handle(this)!=true)
        {
            setState(new FaultState());
            terminateFault();
        }
    }

    /**
     * This method refreshes the CustomerPanel when maintainer logs-out.
     */
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

    /**
     * This method will close down the transaction control function of the
     * vending machine.
     */
    public void closeDown() {
        if (custPanel != null) {
            custPanel.closeDown();
        }
    }

    /**
     * This method sets whether the change is given.
     *
     * @param changeGiven TRUE the change is given, otherwise FALSE.
     */
    public void setChangeGiven(boolean changeGiven) {
        this.changeGiven = changeGiven;
    }

    /**
     * This method returns whether the change is given.
     *
     * @return TRUE if the change is given, otherwise FALSE.
     */
    public boolean isChangeGiven() {
        return changeGiven;
    }

    /**
     * This method sets whether the drink is dispensed.
     *
     * @param drinkDispensed TRUE the drink is dispensed, otherwise, FALSE.
     */
    public void setDrinkDispensed(boolean drinkDispensed) {
        this.drinkDispensed = drinkDispensed;
    }

    /**
     * This method returns whether the drink is dispensed.
     *
     * @return TRUE if the drink is dispensed, otherwise FALSE.
     */
    public boolean isDrinkDispensed() {
        return drinkDispensed;
    }

    /**
     * This method sets the price of the selected drink.
     *
     * @param price the price of the selected drink.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * This method returns the price of the selected drink.
     *
     * @return the price of the selected drink.
     */
    public int getPrice() {
        return price;
    }

    /**
     * This method sets the selected drink index.
     *
     * @param selection the selected drink index.
     */
    public void setSelection(int selection) {
        this.selection = selection;
    }

    /**
     * This method returns the selected drink index.
     *
     * @return the selected drink index.
     */
    public int getSelection() {
        return selection;
    }

    /**
     * This method returns the CustomerPanel.
     *
     * @return the CustomerPanel.
     */
    public CustomerPanel getCustomerPanel() {
        return custPanel;
    }

    /**
     * This method returns the DispenseController.
     *
     * @return the DispenseController.
     */
    public DispenseController getDispenseController() {
        return dispenseCtrl;
    }

    /**
     * This method returns the ChangeGiver.
     *
     * @return the ChangeGiver.
     */
    public ChangeGiver getChangeGiver() {
        return changeGiver;
    }

    /**
     * This method returns the CoinReceiver.
     *
     * @return the CoinReceiver.
     */
    public CoinReceiver getCoinReceiver() {
        return coinReceiver;
    }

    /**
     * This method refreshes the MachinerySimulatorPanel.
     */
    public void refreshMachineryDisplay() {
        mainCtrl.getMachineryController().refreshMachineryDisplay();

    }

    /**
     * This method returns the MainController.
     *
     * @return the MainController.
     */
    public MainController getMainController() {
        return mainCtrl;
    }

    /**
     * This method will nullify reference to customer panel.
     */
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
