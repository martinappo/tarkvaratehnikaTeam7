package ee.ut.math.tvt.salessystem.ui.model;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockTableModelTest {
	private StockTableModel stockTableModel;
	
	@Before
	public void setUp() {
		stockTableModel = new StockTableModel();
		stockTableModel.addItem(new StockItem(1l, "lauaviin", "viin", 4.0, 3));
		stockTableModel.addItem(new StockItem(1l, "pilsner", "õlu", 2.0, 8));
		stockTableModel.addItem(new StockItem(1l, "lays", "krõps", 1.0, 10));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateNameUniqueness(){
		stockTableModel.addItem(new StockItem(1l, "lauaviin", "viin", 4.0, 3));
	}
	
	@Test
	public void testHasEnoughInStock(){
		
	}
	
	@Test
	public void testGetItemByIdWhenItemExsists(){
		
	}
	
	@Test
	public void testGetItemByIdWhenThrowsException(){
		
	}
}
