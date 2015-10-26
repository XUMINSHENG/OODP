/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.store;

import java.util.HashMap;

/**
 * This entity object represents a column of coins in the vending machine&#46;
 * It can store a coin of any particular type&#46; There can be as many CashStoreItem
 * as desire in the vending machine&#46; There can be more than one CashStoreItem storing
 * the same or different types of Coin This number will be configurable&#46; This will
 * be implemented as an instance of StoreItem&#46;
 *
 * @see CashStore
 * @see Coin
 * @see DrinksBrand
 * @see DrinksStore
 * @see DrinksStoreItem
 * @see Store
 * @see StoreController
 * @see StoreItem
 * @see StoreObject
 * 
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class CashStoreItem extends StoreItem {
    
        protected CashStoreItem nextCashStoreItem;

	/**
	 * This constructor creates an instance of {@link CashStoreItem} object.
	 * @param coin the coin associated with this CashStoreItem.
	 * @param qty the quantity of the Coin.
	 */
	public CashStoreItem(Coin coin, int qty) {
		super((StoreObject) coin, qty);
	}
        public void setNestCashStoreItem(CashStoreItem nextCashStoreItem){
            this.nextCashStoreItem = nextCashStoreItem;
        }
        public CashStoreItem getNextCashStoreItem(){
            return nextCashStoreItem;
        }
        
        /**
         * 
         * @param changeBal
         * @param itemQuantityRequired
         * @return 
         */
        public int handleChange(int changeBal, HashMap<CashStoreItem, Integer> itemQuantityRequired){
            int quantity = this.getQuantity();
            Coin coin = (Coin)this.getContent();
            int value = coin.getValue();
            int quantityRequired = 0;
            while(changeBal>0 && changeBal>=value && quantity >0){
                changeBal -= value;
                quantityRequired++;
                quantity--;
            }
            // if this Coin needs to deduct, 
            // it and its number to deduct will be put into map
            // afterward it will be decrement by Machinery Controller
            if(quantityRequired>0){
                itemQuantityRequired.put(this, quantityRequired);
            }
            if(changeBal==0){
                return changeBal;
            }else{
                if (this.getNextCashStoreItem()!=null){
                    changeBal=this.getNextCashStoreItem().handleChange(changeBal, itemQuantityRequired);
                }
                return changeBal;
            }
        }
        
        @Override
        public void notifyObservers(Object arg) {
            super.notifyObservers("Cash"); //To change body of generated methods, choose Tools | Templates.
        }
        
}//End of class CashStoreItem