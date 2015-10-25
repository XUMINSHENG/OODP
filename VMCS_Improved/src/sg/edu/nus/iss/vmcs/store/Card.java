/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.store;

/**
 *
 * @author xiejiabao
 */
public class Card extends StoreObject{
    private int value;
    private String cardId;
    
    public Card(){
        
    }
    
    public Card(String id){
        this.cardId = id;
//        according to cardId, get the value inside card from the card provider
//        and then set vaule
        setValue(100);
    }
    
    public void setValue(int v){
        this.value = v;
    }
    
    public int getValue(){
        return this.value;
    }
}
