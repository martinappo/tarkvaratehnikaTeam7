package ee.ut.math.tvt.salessystem.domain.controller.impl;

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
			item.setPurchase(toAdd);
			session.save(item);
		}
		session.getTransaction().commit();
		
	}
	
	private void addToStock(StockItem good) {
		if (isInStock(good)) {
			StockItem toEdit = stockitems.get(indexOf(good));
			toEdit.setQuantity(toEdit.getQuantity() + good.getQuantity());
			session.beginTransaction();
			StockItem s = (StockItem) session.get(StockItem.class, new Long(good.getId()));
			int qToAdd = s.getQuantity() + good.getQuantity();
			good.setQuantity(qToAdd);
			s = good;
			session.getTransaction().commit();
		} else {
			stockitems.add(good);
			session.beginTransaction();
			session.save(good);
			session.getTransaction().commit();
		}
		
	}
	
	private boolean isInStock(StockItem item) {
		for (StockItem i : stockitems) {
			if (i.getId() == item.getId())
				return true;
		}
		return false;
	}
	
	private int indexOf(StockItem item) {
		for (int i = 0; i < stockitems.size(); i++) {
			if (item.getId() == stockitems.get(i).getId())
				return i;
		}
		return -1;
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
