package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.table.AbstractTableModel;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Generic table model implementation suitable for extending.
 */
public abstract class SalesSystemTableModel<T extends DisplayableItem> extends
        AbstractTableModel {

    private static final long serialVersionUID = 1L;

    protected List<T> rows;
    protected final String[] headers;
    private static Session session = HibernateUtil.currentSession();
    private List<T> table; 
	private String tablename;
    public SalesSystemTableModel(final String[] headers, String tablename) {
        this.headers = headers;
        this.tablename = tablename;
        try {
        	this.table = session.createQuery("from "+tablename).list();
        }
        catch (Exception e){
        	this.table = new ArrayList<T>();
        }
        
    }

    /**
     * @param item
     *            item describing selected row
     * @param columnIndex
     *            selected column index
     * @return value displayed in column with specified index
     */
    protected abstract Object getColumnValue(T item, int columnIndex);

    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return headers[columnIndex];
    }

    public int getRowCount() {
        return table.size();
    }

    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return getColumnValue(table.get(rowIndex), columnIndex);
    }

    // search for item with the specified id
    public T getItemById(final long id) {
        for (final T item : table) {
            if (item.getId() == id)
                return item;
        }
        throw new NoSuchElementException();
    }

    public List<T> getTableRows() {
        return table;
    }

    public void clear() {
        table = new ArrayList<T>();
        fireTableDataChanged();
    }
    
    public void addTableItem(T item){
    	session.beginTransaction();
    	session.save(item);
    	session.getTransaction().commit();
	}

    public void populateWithData(final List<T> data) {
        table.clear();
        table.addAll(data);
        fireTableDataChanged();
    }
    
    
}
