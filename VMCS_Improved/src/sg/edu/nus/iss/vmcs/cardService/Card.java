/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.cardService;

/**
 *
 * @author xiejiabao
 */
public class Card{
    private int value;
    private String cardId;
    
    public Card(){
        
    }
    
    public Card(String id){
        this.cardId = id;
//        according to cardId, get the value inside card from the card provider
//        and then set vaule
       
    }
    
    public Card(String id, int value){
        this.cardId = id;
        this.value = value;
    }
    
    public String getCardId(){
        return cardId;
    }
    
    public void setValue(int v){
        this.value = v;
    }
    
    public int getValue(){
        return this.value;
    }
}
