package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;

public class HistoryTableModel extends SalesSystemTableModel<Purchase> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HistoryTableModel() {
		super(new String[] {"Date", "Time", "Order summary"}, "History");
	}

	@Override
	protected Object getColumnValue(Purchase item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getOrderSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}


}
