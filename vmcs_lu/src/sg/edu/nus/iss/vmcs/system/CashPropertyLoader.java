/*
 * Copyright 2003 ISS.
 * The contents contained in this document may not be reproduced in any
 * form or by any means, without the written permission of ISS, other
 * than for the purpose for which it has been supplied.
 *
 */
package sg.edu.nus.iss.vmcs.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import sg.edu.nus.iss.vmcs.store.CashStoreItem;
import sg.edu.nus.iss.vmcs.store.Coin;
import sg.edu.nus.iss.vmcs.store.StoreItem;

/**
 * This control object manages the initialization of the CashStore&#46;
 *
 * @version 3.0 5/07/2003
 * @author Olivo Miotto, Pang Ping Li
 */
public class CashPropertyLoader extends FilePropertyLoader {

	private static final String NAME_LABEL = "Name";
	private static final String WEIGHT_LABEL = "Weight";
	private static final String VALUE_LABEL = "Value";
	private static final String QUANTITY_LABEL = "Quantity";

	private CashStoreItem highestValueCashStoreItem;

	public void setHighestValueCashStoreItem(CashStoreItem highestValueCashStoreItem) {
		this.highestValueCashStoreItem = highestValueCashStoreItem;
	}

	/**
	 * This constructor creates an instance of CashPropertyLoader object.
	 * 
	 * @param filen
	 *            the name of the cash property file.
	 */
	public CashPropertyLoader(String filen) {
		super(filen);
	}

	/**
	 * This method reads the data from the hash table and creates a
	 * CashStoreItem.
	 * 
	 * @param index
	 *            the index of the StoreItem.
	 * @return StoreItem the store item of the given index.
	 */
	public StoreItem getItem(int index) {
		int idx = index + 1;
		Coin coin = new Coin();

		String name = new String(NAME_LABEL + idx);
		String value = getValue(name);
		coin.setName(value);

		name = new String(WEIGHT_LABEL + idx);
		value = getValue(name);
		coin.setWeight(Double.parseDouble(value));

		name = new String(VALUE_LABEL + idx);
		value = getValue(name);
		coin.setValue(Integer.parseInt(value));

		name = new String(QUANTITY_LABEL + idx);
		value = getValue(name);
		int qty = Integer.parseInt(value);

		CashStoreItem item = new CashStoreItem(coin, qty);
		return item;
	}

	/**
	 * This method updates the hash table with the data from the CashStoreItem.
	 * 
	 * @param index
	 *            the index of the item.
	 * @param cashItem
	 *            the cash store item.
	 */
	public void setItem(int index, StoreItem cashItem) {
		int idx = index + 1;

		CashStoreItem item = (CashStoreItem) cashItem;
		Coin cn = (Coin) item.getContent();
		String itn = new String(NAME_LABEL + idx);

		setValue(itn, cn.getName());

		itn = new String(WEIGHT_LABEL + idx);
		setValue(itn, String.valueOf(cn.getWeight()));

		itn = new String(VALUE_LABEL + idx);
		setValue(itn, String.valueOf(cn.getValue()));

		itn = new String(QUANTITY_LABEL + idx);
		setValue(itn, String.valueOf(item.getQuantity()));
	}

	/**
	 * This method reads the properties file into a hash table.
	 * 
	 * @throws IOException
	 *             if fail to load properties from properties file.
	 */
	public void initialize() throws IOException {
		prop = new Properties(System.getProperties());
		FileInputStream stream = new FileInputStream(fileName);
		prop.load(stream);
		stream.close();
		// int cashStoreSize = storeCtrl.getStoreSize(Store.CASH);
		int cashStoreSize = this.getNumOfItems();
		System.out.println(cashStoreSize);
		List<CashStoreItem> CashItemArray = new ArrayList<CashStoreItem>();
		// CashItemArray.set(0, (CashStoreItem) this.getItem(0));
		for (int i = 0; i < cashStoreSize; i++) {
			CashItemArray.add((CashStoreItem) this.getItem(i));
			System.out.println(((Coin) CashItemArray.get(i).getContent()).getValue());
		}
		CashStoreItem temp = null;
		for (int i = 0; i < CashItemArray.size() - 1; i++) {
			for (int j = 0; j < CashItemArray.size() - 1 - i; j++) {
				if (((Coin) CashItemArray.get(j).getContent())
						.getValue() < ((Coin) CashItemArray.get(j + 1).getContent()).getValue()) {
					temp = CashItemArray.get(j);
					CashItemArray.set(j, CashItemArray.get(j + 1));
					CashItemArray.set(j + 1, temp);
				}
			}
		}
		this.setHighestValueCashStoreItem(CashItemArray.get(0));
		for (int j = 0; j < CashItemArray.size() - 1; j++) {
			CashItemArray.get(j).setNextCashStoreItem(CashItemArray.get(j + 1));
		}
		// for (int j = cashStoreSize - 1; j >= 0; j--) { // set chain
		// ((CashStoreItem) storeCtrl.getStore(Store.CASH).getStoreItem(j))
		// .setNextCashStoreItem((CashStoreItem)
		// storeCtrl.getStore(Store.CASH).getStoreItem(j - 1));
		// }
	}

	public CashStoreItem getHighestValueCashStoreItem() {
		return this.highestValueCashStoreItem;
	}
}// End of class CashPropertyLoader