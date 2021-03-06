package ee.ut.math.tvt.salessystem.domain.data;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PURCHASE")
public class Purchase implements DisplayableItem, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(mappedBy="saleId")
	private List<SoldItem> goods;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="time")
	private String time;
	
	@Column(name="summary")
	private double summary;
	
	@Transient
	private DateFormat dateFormat;
	//private Calendar calendar;
	
	public Purchase() {}

	public Purchase(Date calendar, List<SoldItem> solditems) {
		for (int i = 0 ; i < solditems.size() ; i++) {
			StockItem stockitem = solditems.get(i).getStockItem();
			if(stockitem.getQuantity() < solditems.get(i).getQuantity()){
				throw new IllegalArgumentException("Invalid quantity!");
			}
		}
		this.goods = solditems;
		this.date = calendar;
		dateFormat = new SimpleDateFormat("HH:mm:ss");					
		this.time  = dateFormat.format(calendar.getTime());
		this.summary = getOrderSum();

	}

	public String getDate() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date.getTime());
	}

	public String getTime() {
		return time;
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
		return id;
	}

}
