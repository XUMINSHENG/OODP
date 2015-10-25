/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.cardService;

/**
 * this only provides card object by third party card service 
 * in case of more modification, i set static methods
 * 
 * 
 * @author xiejiabao
 */
public class CardService{
    
//    fake card object for usage
    private static final Card validCard = new Card("100C card", 100);
    private static final Card invalidCard = new Card("invalid card", 0);
    
//    it should get card info from the third party card services 
    public static Card readCard(String id){
        if(id != invalidCard.getCardId()){
            return validCard;
        }else{
            return invalidCard;
        }
    }
    
}
