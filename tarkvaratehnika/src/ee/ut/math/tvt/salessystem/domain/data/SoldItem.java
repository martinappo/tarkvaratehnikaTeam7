package ee.ut.math.tvt.salessystem.domain.data;

import java.io.Serializable;
import java.util.NoSuchElementException;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
@Entity
@Table(name="SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "sale_id", nullable = false)
	private Purchase saleId;
	
	@Column(name = "stockitem_id", nullable = false)
    private Long stockItemId;
    
	@Column(name="name")
    private String name;
	
	@Column(name="quantity")
    private Integer quantity;
	
	@Column(name="itemprice")
    private double price;
	
	@Transient
	private StockItem stockItem;
	
	public SoldItem() {}
    
    public SoldItem(StockItem stockItem, int quantity) {
    	if (quantity > stockItem.getQuantity()){
    		throw new IllegalArgumentException();
    	}
    	this.stockItemId = stockItem.getId();
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
    }
    
    public void setPurchase(Purchase purchase){
    	this.saleId = purchase;    	
    }

	public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
}
