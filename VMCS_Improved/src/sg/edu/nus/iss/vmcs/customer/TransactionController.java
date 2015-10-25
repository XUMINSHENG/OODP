package sg.edu.nus.iss.vmcs.customer;


import java.awt.Frame;

import sg.edu.nus.iss.vmcs.customer.transactionController.states.IdleState;
import sg.edu.nus.iss.vmcs.customer.transactionController.states.TransactionControllerState;
import sg.edu.nus.iss.vmcs.system.MainController;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;

public class TransactionController {
	private MainController mainCtrl;
	private CustomerPanel custPanel;
	private DispenseController dispenseCtrl;
        private PaymentMediator mediator;
        
        private TransactionControllerState state;
        
        public void setState(TransactionControllerState state) {
            this.state = state;
        }

	private boolean changeGiven=false;
	private boolean drinkDispensed=false;
	private int price=0;
	private int selection=-1;
	
	public TransactionController(MainController mainCtrl) {
		this.mainCtrl = mainCtrl;
		dispenseCtrl=new DispenseController(this);
                state = new IdleState(this);
	}

	public MainController getMainController() {
		return mainCtrl;
	}

	public void displayCustomerPanel() {
		SimulatorControlPanel scp = mainCtrl.getSimulatorControlPanel();
	        custPanel = new CustomerPanel((Frame) scp, this);
		custPanel.display();
		dispenseCtrl.updateDrinkPanel();
		dispenseCtrl.allowSelection(true);
	}
	
	public void startTransaction(int drinkIdentifier){
		this.state.startTransaction(drinkIdentifier);
	}
        
        public void startPayment(PaymentMediator m){
            this.mediator = m;
            this.state.startPayment();
        }
	
	public void processMoneyReceived(int total){
		this.state.processMoneyReceived(total);
	}
	
	public void completeTransaction(int change){
		this.state.completeTransaction(change);
    	}
	
	public void terminateFault(){
            state.terminateFault();
	}
	
	public void startMaintenance(){
            this.state.startMaintenance();
	}
        
        public void endMaintenance()
        {
            this.state.endMaintenance();
        }
	
	public void cancelTransaction(){
		this.state.cancelTransaction();
	}

	public void refreshCustomerPanel(){
		dispenseCtrl.updateDrinkPanel();
		dispenseCtrl.allowSelection(true);
                this.mediator = null;
                custPanel.setPaymentOptionBoxActive(false);
		custPanel.setTerminateButtonActive(true);
	}

	public void closeDown() {
		if (custPanel != null)
			custPanel.closeDown();
	}

	public void setChangeGiven(boolean changeGiven) {
		this.changeGiven = changeGiven;
	}

	public boolean isChangeGiven() {
		return changeGiven;
	}

	public void setDrinkDispensed(boolean drinkDispensed) {
		this.drinkDispensed = drinkDispensed;
	}

	public boolean isDrinkDispensed() {
		return drinkDispensed;
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
	
	public CustomerPanel getCustomerPanel(){
		return custPanel;
	}
	
	public DispenseController getDispenseController(){
		return dispenseCtrl;
	}
	
        public PaymentMediator getMediator(){
            return mediator;
        }
	
	public void refreshMachineryDisplay(){
		mainCtrl.getMachineryController().refreshMachineryDisplay();
		
	}

	public void nullifyCustomerPanel(){
		custPanel=null;
	}
}//End of class TransactionController