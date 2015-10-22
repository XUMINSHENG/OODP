/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

import java.awt.Button;

/**
 *
 * @author xiejiabao
 */
public class CardButton extends Button{
    private String name;
    private String cardId;
    
    public CardButton(String name, String id){
        super(name);
        this.name = name;
        this.cardId = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
