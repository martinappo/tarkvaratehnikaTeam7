package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SoldItemTest {
	private Purchase purchase;
	private StockItem stockitem;

	@Before
	public void setUp() {
		stockitem = new StockItem(1l, "lauaviin", "viin", 4.0, 3);
	}
	
	@Test
	public void testGetSum(){
		SoldItem solditem = new SoldItem(stockitem, 2);
		assertEquals(solditem.getSum(), 8.0, 0.0001 );
	}
	
	@Test
	public void testGetSumWithZeroQuantity(){
		SoldItem solditem = new SoldItem(stockitem, 0);
		assertEquals(solditem.getSum(), 0.0, 0.0001 );
		
	}
	
}
