package ee.ut.math.tvt.salessystem.domain.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Purchase implements DisplayableItem, Serializable {

	private static final long serialVersionUID = 1L;

	private List<SoldItem> goods;
	private DateFormat dateFormat;

	private Calendar calendar;

	public Purchase(Calendar calendar, List<SoldItem> goods) {
		this.calendar = calendar;
		this.goods = goods;

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	}

	public String getDate() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(calendar.getTime());
	}

	public String getTime() {
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(calendar.getTime());
	}

	public double getOrderSum() {
		double sum = 0;
		for (SoldItem item : goods) {
			sum += item.getPrice() * item.getQuantity();
		}
		return sum;
	}

	public List<SoldItem> getItems() {
		return goods;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
