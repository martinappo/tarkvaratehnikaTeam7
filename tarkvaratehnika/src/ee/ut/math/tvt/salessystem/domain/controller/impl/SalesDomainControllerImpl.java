package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;


/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	List<Purchase> purchases;
	List<StockItem> stockitems;
	
	public SalesDomainControllerImpl() {
		purchases = loadHistoryState();
		stockitems = loadStockState();
	}
	public void submitNewStockItem(StockItem newitem) 
			throws VerificationFailedException{
		addToStock(newitem);
		
	}
	public void editStockItem(StockItem editable) throws VerificationFailedException{
		for(StockItem i:stockitems){
			if(i.getId()==editable.getId()){
				i=editable;
				refresh();
			}
		}
	}

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is
		// underaged and
		// cannot buy chupa-chups
		// throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
		addToHistory(goods);
		removeFromStock(goods);

	}
	
	private void removeFromStock(List<SoldItem> goods) {
		for (SoldItem s: goods) {
			long id = s.getId();
			StockItem item = null;
			for (StockItem i: stockitems) {
				if (i.getId() == id)
					item = i;
					if (item != null && item.getQuantity() != 0) {
						item.setQuantity(item.getQuantity() - s.getQuantity());
					}
			}
			
			writeStock();
		}
	}
	private void addToHistory(List<SoldItem> goods) {
		Calendar calendar = Calendar.getInstance();
		purchases.add(new Purchase(calendar, goods));
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("etc/historyDatabase.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			oos.writeObject(purchases);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void refresh() {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("etc/stockDatabase.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			oos.writeObject(stockitems);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void addToStock(StockItem good) {
		stockitems.add(good);
		writeStock();
	}
	
	private void writeStock () {
		try {
			FileOutputStream fout;
			fout = new FileOutputStream("etc/stockDatabase.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(stockitems);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
	}



	@SuppressWarnings("unchecked")
	private List<Purchase> loadHistoryState() {
		List<Purchase> purchases = new ArrayList<>();
		FileInputStream fin;
		ObjectInputStream ois;
		try {
			fin = new FileInputStream("etc/historyDatabase.dat");
			ois = new ObjectInputStream(fin);
			try {
				purchases = (List<Purchase>) ois.readObject();
			} catch (EOFException e) {
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			fin.close();
			ois.close();
		} catch (EOFException e) {
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return purchases;
	}
	
	@SuppressWarnings("unchecked")
	private List<StockItem> loadStockState() {
		List<StockItem> stockitems = new ArrayList<>();
		FileInputStream fin;
		ObjectInputStream ois;
		try {
			fin = new FileInputStream("etc/stockDatabase.dat");
			ois = new ObjectInputStream(fin);
			try {
				stockitems = (List<StockItem>) ois.readObject();
			} catch (EOFException e) {
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			fin.close();
			ois.close();
		} catch (EOFException e) {
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stockitems;
	}

	public List<Purchase> getHistoryState() {
		return purchases;
	}
	public List<StockItem> getStockState(){
		return stockitems;
	}
	
}
