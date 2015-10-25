/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author xiejiabao
 */
public class PaymentOptionListener implements ActionListener{
    private TransactionController txCtrl;

    public PaymentOptionListener(TransactionController t){
        this.txCtrl = t;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        PaymentOptionButton option = (PaymentOptionButton)e.getSource();
        PaymentMediator mediator = MediatorFactory.getMediator(option.getPaymentOption());
        mediator.setTxCtrl(this.txCtrl);
        txCtrl.startPayment(mediator);
//        this.mediator.startPayment(option.getPaymentOption());
    }
    
}
