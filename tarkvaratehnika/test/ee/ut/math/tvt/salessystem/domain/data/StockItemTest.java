package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StockItemTest {
	private StockItem stockitem;

	@Before
	public void setUp() {
		stockitem = new StockItem(1l, "lauaviin", "viin", 4.0, 3);
	}
	
	@Test
	public void testClone(){
		StockItem stockitem2 = (StockItem) stockitem.clone();
		assertEquals(stockitem2.toString(), stockitem.toString());
		assertNotSame(stockitem2, stockitem);
		
	}
	
	@Test
	public void testGetColumn(){
		assertEquals(stockitem.getColumn(0), stockitem.getId());
		assertEquals(stockitem.getColumn(1), stockitem.getName());
		assertEquals(stockitem.getColumn(2), stockitem.getPrice());
		assertEquals(stockitem.getColumn(3), stockitem.getQuantity());
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetColumnException(){
		stockitem.getColumn(99);
	}

}
