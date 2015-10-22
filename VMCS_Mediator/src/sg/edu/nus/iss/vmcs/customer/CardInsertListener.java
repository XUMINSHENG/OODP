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
public class CardInsertListener implements ActionListener{
    private CardReaderColleague cardReader;
    
    public CardInsertListener(CardReaderColleague c){
        this.cardReader = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardButton card = (CardButton)e.getSource();
        cardReader.readCard(card.getCardId());
    }
}
