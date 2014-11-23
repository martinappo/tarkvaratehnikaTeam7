package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockTableModelTest {
	private StockTableModel stockTableModel;
	private PurchaseInfoTableModel purchase;
	private StockItem pilsner;
	private StockItem estrella;
	
	@Before
	public void setUp() {
		stockTableModel = new StockTableModel();
		pilsner = new StockItem(1l, "pilsner", "õlu", 2.0, 8);
		estrella = new StockItem(2l, "estrella", "krõps", 1.0, 10);
		stockTableModel.addItem(pilsner);
		stockTableModel.addItem(estrella);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateNameUniqueness(){
		
		StockItem stockItem = new StockItem(3l, "estrella", "muu krõps", 1.0, 10);
		stockTableModel.addItem(stockItem);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testHasEnoughInStock(){
		purchase = new PurchaseInfoTableModel();
		purchase.addItem(new SoldItem(pilsner, 100));
	}
	
	@Test
	public void testGetItemByIdWhenItemExsists(){
		assertEquals(pilsner.toString(), stockTableModel.getItemById(1).toString());
		assertNotSame(pilsner.toString(), stockTableModel.getItemById(2).toString());
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException(){
		stockTableModel.getItemById(99);
	}
}
