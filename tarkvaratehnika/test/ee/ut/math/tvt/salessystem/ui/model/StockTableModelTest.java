package ee.ut.math.tvt.salessystem.ui.model;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockTableModelTest {
	private StockTableModel stockTableModel;
	
	@Before
	public void setUp() {
		stockTableModel = new StockTableModel();
		stockTableModel.addItem(new StockItem(1l, "pilsner", "õlu", 2.0, 8));
		stockTableModel.addItem(new StockItem(2l, "estrella", "krõps", 1.0, 10));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateNameUniqueness(){
		
		StockItem stockItem = new StockItem(3l, "estrella", "muu krõps", 1.0, 10);
		stockTableModel.addItem(stockItem);
	}
	
	
	public void testHasEnoughInStock(){
		
	}
	
	
	public void testGetItemByIdWhenItemExsists(){
		
	}
	

	public void testGetItemByIdWhenThrowsException(){
		
	}
}
