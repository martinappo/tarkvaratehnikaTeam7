package ee.ut.math.tvt.salessystem.domain.data;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PurchaseTest {
	
	private Purchase purchase;
	private List list;
	private StockItem stockitem;
	private Date date;
	
  @Before
  public void setUp() {
	date = new Date(System.currentTimeMillis());
	stockitem = new StockItem(1l,"lauaviin", "viin", 4.0, 3);
	list = new ArrayList();    
  }
  
  @Test
  public void testAddSoldItem(){
	  
  }
  @Test
  public void testGetSumWithNoItems(){
	  purchase = new Purchase(date, list);
	  assertEquals(purchase.getOrderSum(), 0.0, 0.0001);
  }
  @Test
  public void testGetSumWithOneItem(){
	  SoldItem solditem = new SoldItem(stockitem, 1);	
	  list.add(solditem);
	  purchase = new Purchase(date, list);
	  assertEquals(purchase.getOrderSum(), 4.0, 0.0001);
  }
  @Test
  public void testGetSumWithMultipleItems(){
	  SoldItem solditem = new SoldItem(stockitem, 3);	
	  list.add(solditem);
	  purchase = new Purchase(date, list);
	  assertEquals(purchase.getOrderSum(), 12.0, 0.0001);
  }
  
  
}
