package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;


/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	List<Purchase> purchases;
	List<StockItem> stockitems;
	
	@SuppressWarnings("unchecked")
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
		for (SoldItem sold: goods) {
			for (StockItem stock : stockitems) {
				if (sold.getStockItem().getId() == stock.getId()) {
					session.beginTransaction();
					StockItem s = (StockItem) session.get(StockItem.class, new Long(stock.getId()));
					s.setQuantity(s.getQuantity() - sold.getQuantity());
					session.getTransaction().commit();
				}
			}
		}
	}
	private void addToHistory(List<SoldItem> goods) {
		Date calendar = new Date(System.currentTimeMillis());
		Purchase toAdd = new Purchase(calendar, goods);
		purchases.add(toAdd);
		session.beginTransaction();
		session.save(toAdd);
		for (SoldItem item : goods) {
			SoldItem newItem = new SoldItem(item, toAdd);
			session.save(newItem);
		}
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

	public List<Purchase> getHistoryState() {
		return purchases;
	}
	public List<StockItem> getStockState(){
		return stockitems;
	}
	
	
}
