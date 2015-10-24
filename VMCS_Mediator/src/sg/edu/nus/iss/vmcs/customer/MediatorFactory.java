/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.customer;

/**
 *
 * @author xiejiabao
 */
public class MediatorFactory {
    
    public static int PAYMENT_BY_COIN = 0;
    public static int PAYMENT_BY_CARD = 1;
    
    private static CardMediator cardMediator = new CardMediator();
    private static CoinMediator coinMediator = new CoinMediator();
    
    public static CardMediator getCardMediator(){
        return cardMediator;
    }
    
    public static CoinMediator getCoinMediator(){
        return coinMediator;
    }
    
    public static PaymentMediator getMediator(int option){
        if(option==PAYMENT_BY_COIN){
            return coinMediator;
        }else if(option==PAYMENT_BY_CARD){
            return cardMediator;
        }else{
           return null;
        }
    }
}
