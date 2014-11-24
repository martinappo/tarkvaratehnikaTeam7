package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryTableModelTest {
	private StockItem pilsner;
	private SoldItem myyk1;
	private HistoryTableModel history;
	private Purchase purchase;
	private Date date;
	private List list;

	@Before
	public void setUp() {
		history = new HistoryTableModel();
		date = new Date(System.currentTimeMillis());
		pilsner = new StockItem(1l, "pilsner", "olu", 2.0, 8);
		

	}

	@Test
	public void testGetSum() {
		myyk1 = new SoldItem(pilsner, 3);
		purchase = new Purchase();
		list = new ArrayList();
		list.add(myyk1);
		purchase = new Purchase(date, list);
		assertEquals((Double) history.getColumnValue(purchase, 2), 6.0, 0.0001);
	}
	@Test
	public void testGetSumWithZeroQuantity() {
		myyk1 = new SoldItem(pilsner, 0);
		purchase = new Purchase();
		list = new ArrayList();
		list.add(myyk1);
		purchase = new Purchase(date, list);
		assertEquals((Double) history.getColumnValue(purchase, 2), 0.0, 0.0001);
	}

}
