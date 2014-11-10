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
	
	@Transient
	private DateFormat dateFormat;

	//private Calendar calendar;

	public Purchase(Date calendar, List<SoldItem> goods) {
		this.date = (Date) calendar;
		this.goods = goods;

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	}

	public String getDate() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date.getTime());
	}

	public String getTime() {
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date.getTime());
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
