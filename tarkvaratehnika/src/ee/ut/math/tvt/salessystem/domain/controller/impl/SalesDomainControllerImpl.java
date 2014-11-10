package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;


/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	List purchases;
	List stockitems;
	
	public SalesDomainControllerImpl() {
		//purchases = loadHistoryState();
		purchases = session.createQuery("from "+"Purchase").list();
		stockitems = session.createQuery("from "+"StockItem").list();
	}
	public void submitNewStockItem(StockItem newitem) 
			throws VerificationFailedException{
		addToStock(newitem);
		
	}
	public void editStockItem(StockItem editable) throws VerificationFailedException{
		for(Object i: stockitems){
			if(((StockItem)i).getId()==editable.getId()){
				i=editable;
				refresh();
			}
		}
	}
	
	public void endSession() {
	    HibernateUtil.closeSession();
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
		//TODO remove item from stock database and update table
	}
	private void addToHistory(List<SoldItem> goods) {
		Date calendar = new Date(System.currentTimeMillis());
		Purchase toAdd = new Purchase(calendar, goods);
		purchases.add(toAdd);
		session.beginTransaction();
		session.save(toAdd);
		session.getTransaction().commit();
		
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
		//TODO write query to StockItem table
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
		return null;
	}

	public List<Purchase> getHistoryState() {
		return purchases;
	}
	public List<StockItem> getStockState(){
		return stockitems;
	}
	
	
}
