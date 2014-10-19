package ee.ut.math.tvt.salessystem.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;

public class AddInfoUI extends JFrame {

	private static final Logger log = Logger.getLogger(SalesSystemUI.class);
	private static final long serialVersionUID = 1L;
	private JLabel sumField;
	private JTextField payAmount;
	private JLabel changeAmount;
	private double sum;
	private double change;

	private JButton cancelBtn;
	private JButton acceptBtn;

	PurchaseInfoTableModel currentPurchaseInfoTableModel;
	HistoryTableModel historyTableModel;

	SalesDomainController domainController;

	PurchaseTab pTab;

	public AddInfoUI(PurchaseInfoTableModel currentPurchaseInfoTableModel,
			HistoryTableModel historyTableModel,
			SalesDomainController domainController, PurchaseTab pTab) {

		this.currentPurchaseInfoTableModel = currentPurchaseInfoTableModel;
		this.historyTableModel = historyTableModel;
		this.domainController = domainController;
		this.pTab = pTab;

		setTitle("Purchase Info");

		// set L&F to the nice Windows style
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());

		} catch (UnsupportedLookAndFeelException e1) {
			log.warn(e1.getMessage());
		}

		List<SoldItem> items = currentPurchaseInfoTableModel.getTableRows();

		for (SoldItem item : items) {
			sum += item.getPrice() * item.getQuantity();
		}

		initButtons();
		payAmount = new JTextField("0.0");
		changeAmount = new JLabel("    0.0");

		payAmount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateChange();
			}
		});

		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory
				.createTitledBorder("Product details"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(currentPurchaseInfoTableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		JPanel actions = new JPanel();
		actions.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 0, 5);

		sumField = new JLabel("Amount to pay:");
		c.gridx = 0;
		c.gridy = 0;
		actions.add(sumField, c);
		c.gridx = 1;
		c.gridy = 0;
		actions.add(new JLabel("    " + sum), c);
		c.gridx = 0;
		c.gridy = 1;
		actions.add(new JLabel("Payment amount:"), c);
		c.gridx = 1;
		c.gridy = 1;
		actions.add(payAmount, c);
		c.gridx = 0;
		c.gridy = 2;
		actions.add(new JLabel("Change:"), c);
		c.gridx = 1;
		c.gridy = 2;
		actions.add(changeAmount, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		actions.add(acceptBtn, c);
		c.gridx = 0;
		c.gridy = 4;
		actions.add(cancelBtn, c);

		c.gridx = 1;
		c.gridy = 0;
		basketPane.add(actions, c);
		add(basketPane);

		int width = 450;
		int height = 200;
		setSize(width, height);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - width) / 2, (screen.height - height) / 2);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	private void initButtons() {
		cancelBtn = new JButton("Cancel");
		acceptBtn = new JButton("Accept");

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		acceptBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					log.debug("Contents of the current basket:\n"
							+ currentPurchaseInfoTableModel);
					domainController
							.submitCurrentPurchase(currentPurchaseInfoTableModel
									.getTableRows());
					log.info("Sale complete");
					historyTableModel.populateWithData(domainController.getHistoryState());
					pTab.endSale();
					currentPurchaseInfoTableModel.clear();
					dispose();
				} catch (VerificationFailedException e1) {
					log.error(e1.getMessage());
				}
			}
		});
	}

	private void calculateChange() {
		change = Double.parseDouble(payAmount.getText()) - sum;
		changeAmount.setText("    " + change);
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

}
