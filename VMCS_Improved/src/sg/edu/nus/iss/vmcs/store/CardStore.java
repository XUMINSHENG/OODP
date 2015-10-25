/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.store;

/**
 * this only provides card object for mediator pattern 
 * in case of more modification, i set static methods
 * pretend it is initialed by storeController already
 * 
 * @author xiejiabao
 */
public class CardStore extends Store{
    
    private static final Card validCard = new Card("valid card", 100);
    private static final Card invalidCard = new Card("invalid card", 0);
    
    public static Card getCardById(String id){
        if(id == validCard.getCardId()){
            return validCard;
        }else{
            return invalidCard;
        }
    }
    
    public Card getValidCard(){
        return validCard;
    }
    
    public Card getInvalidCard(){
        return invalidCard;
    }
}
