package ee.ut.math.tvt.salessystem;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

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
  void testAddSoldItem(){
  	
  }
  @Test
  void testGetSumWithNoItems(){
		//SoldItem solditem = new SoldItem(stockitem, 0);	
		//list.add(solditem);
		purchase = new Purchase(date, list);
		assertEquals(purchase.getOrderSum(), 0.0, 0.0001);
	  
  }
  @Test
  void testGetSumWithOneItem(){
	  
  }
  @Test
  void testGetSumWithMultipleItems(){
	  
  }
  
  
}
