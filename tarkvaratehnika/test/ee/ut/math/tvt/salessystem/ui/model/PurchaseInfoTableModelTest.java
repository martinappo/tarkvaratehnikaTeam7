package ee.ut.math.tvt.salessystem.ui.model;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class PurchaseInfoTableModelTest {
	private PurchaseInfoTableModel purchase;
	private StockItem pilsner;
	private StockItem estrella;
	private SoldItem myyk1;
	private SoldItem myyk2;
	
	@Before
	public void setUp() {
		purchase = new PurchaseInfoTableModel();
		pilsner = new StockItem(1l, "pilsner", "olu", 2.0, 8);
				
	}
	@Test(expected=IllegalArgumentException.class)
	public void testHasEnoughInStock(){
		purchase.addItem(new SoldItem(pilsner, 50));
	}
	
	@Test
	public void testPurchasePriceIsCorrect(){
		myyk1=new SoldItem(pilsner,5);
		assertEquals((Double) purchase.getColumnValue(myyk1,4), 10.0, 0.0001 );
	}
	@Test
	public void testPurchaseWithZeroQuantity(){
		myyk1=new SoldItem(pilsner,0);
		assertEquals((Double) purchase.getColumnValue(myyk1,4), 0.0, 0.0001 );
	}
}
