package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {
	
	public Session session = HibernateUtil.currentSession();

    /**
     * Load the current state of the warehouse.
     * 
     * @return List of ${link
     *         ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
     */

    
    /**
     * Load the history.
     * 
     * @return List of ${link
     *         ee.ut.math.tvt.salessystem.domain.data.Purchase}s.
     */
    public List<Purchase> getHistoryState();
    
    public List<StockItem> getStockState();

    // business processes
    /**
     * Initiate new business transaction - purchase of the goods.
     * 
     * @throws VerificationFailedException
     */
    public void startNewPurchase() throws VerificationFailedException;
    
    public void endSession();

    
    /**
     * Rollback business transaction - purchase of goods.
     * 
     * @throws VerificationFailedException
     */
    public void cancelCurrentPurchase() throws VerificationFailedException;

    /**
     * Commit business transaction - purchsae of goods.
     * 
     * @param goods
     *            Goods that the buyer has chosen to buy.
     * @throws VerificationFailedException
     */
    public void editStockItem(StockItem editable) throws VerificationFailedException;
    public void submitCurrentPurchase(List<SoldItem> goods)
            throws VerificationFailedException;   
    public void submitNewStockItem(StockItem newitem) 
    		throws VerificationFailedException;
}
