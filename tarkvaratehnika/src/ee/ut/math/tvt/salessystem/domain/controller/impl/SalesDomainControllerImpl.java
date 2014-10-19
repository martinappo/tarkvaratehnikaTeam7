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
	
	public SalesDomainControllerImpl() {
		purchases = loadHistoryState();
	}

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is
		// underaged and
		// cannot buy chupa-chups
		// throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
		addToHistory(goods);

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

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		List<StockItem> dataset = new ArrayList<StockItem>();

		StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0,
				5);
		StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0,
				8);
		StockItem frankfurters = new StockItem(3l, "Frankfurters",
				"Beer sauseges", 15.0, 12);
		StockItem beer = new StockItem(4l, "Free Beer", "Student's delight",
				0.0, 100);

		dataset.add(chips);
		dataset.add(chupaChups);
		dataset.add(frankfurters);
		dataset.add(beer);

		return dataset;
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

	public List<Purchase> getHistoryState() {
		return purchases;
	}
	
	
}
