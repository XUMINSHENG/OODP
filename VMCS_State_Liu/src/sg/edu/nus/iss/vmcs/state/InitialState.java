/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.state;

import java.awt.Frame;
import sg.edu.nus.iss.vmcs.customer.CustomerPanel;
import sg.edu.nus.iss.vmcs.customer.TransactionController;
import sg.edu.nus.iss.vmcs.system.SimulatorControlPanel;

/**
 *
 * @author Liu Xinzhuo
 */
public class InitialState implements TransactionState{

    @Override
    public boolean handle(TransactionController txCtrl) {
                System.out.println(this.getClass().getName()+" start!");
                SimulatorControlPanel scp = txCtrl.getMainController().getSimulatorControlPanel();
	        CustomerPanel custPanel = new CustomerPanel((Frame) scp, txCtrl);
                txCtrl.setCustPanel(custPanel);
		custPanel.display();
		txCtrl.getDispenseController().updateDrinkPanel();
		txCtrl.getDispenseController().allowSelection(true);
		txCtrl.getChangeGiver().displayChangeStatus();
		txCtrl.getCoinReceiver().setActive(false);
                txCtrl.setState(new IdleState());
                System.out.println("Set Next State:" + txCtrl.getState().getClass().getName());
                System.out.println(this.getClass().getName()+" end!");
                return true;
    }
    
}
