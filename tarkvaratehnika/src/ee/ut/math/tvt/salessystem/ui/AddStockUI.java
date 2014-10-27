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
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class AddStockUI extends JFrame {
	private static final Logger log = Logger.getLogger(StockTableModel.class);
	private static final long serialVersionUID = 1L;
	private JTextField id;
	private JTextField name;
	private JTextField description;
	private JTextField price;
	private JTextField quantity;
	private JButton cancelBtn;
	private JButton addBtn;

	SalesDomainController domainController;
	StockTableModel StockTableModel;

	public AddStockUI(SalesDomainController domainController,
			StockTableModel StockTableModel) {
		this.domainController = domainController;
		this.StockTableModel = StockTableModel;
		setTitle("Add stock item");
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());

		} catch (UnsupportedLookAndFeelException e1) {
			log.warn(e1.getMessage());
		}
		id = new JTextField(40);
		name = new JTextField(40);
		description = new JTextField(40);
		price = new JTextField(40);
		quantity = new JTextField(40);
		;

		initButtons();

		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory
				.createTitledBorder("Add products to stock"));
		JPanel actions = new JPanel();
		actions.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 0, 5);
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		actions.add(new JLabel("Insert product id: "), c);
		c.gridx = 1;
		c.gridy = 0;
		actions.add(id, c);
		c.gridx = 0;
		c.gridy = 1;
		actions.add(new JLabel("Insert product name: "), c);
		c.gridx = 1;
		c.gridy = 1;
		actions.add(name, c);
		c.gridx = 0;
		c.gridy = 2;
		actions.add(new JLabel("Insert product description: "), c);
		c.gridx = 1;
		c.gridy = 2;
		actions.add(description, c);
		c.gridx = 0;
		c.gridy = 3;
		actions.add(new JLabel("Insert product price: "), c);
		c.gridx = 1;
		c.gridy = 3;
		actions.add(price, c);
		c.gridx = 0;
		c.gridy = 4;
		actions.add(new JLabel("Insert product quantity: "), c);
		c.gridx = 1;
		c.gridy = 4;
		actions.add(quantity, c);
		c.gridx = 0;
		c.gridy = 5;
		actions.add(addBtn, c);
		c.gridx = 1;
		c.gridy = 5;
		actions.add(cancelBtn, c);
		c.gridx = 1;
		c.gridy = 0;
		basketPane.add(actions, c);

		add(basketPane);

		int width = 300;
		int height = 250;
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
		addBtn = new JButton("Add/edit product");

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		addBtn.addActionListener(new ActionListener() {
			//
			@Override
			public void actionPerformed(ActionEvent e) {
				StockItem uus = new StockItem(Long.parseLong(id.getText()),
						name.getText(), description.getText(), Double
								.parseDouble(price.getText()), Integer
								.parseInt(quantity.getText()));
				log.info("Added product " + uus.toString());
				try {
					StockItem item = StockTableModel.getItemById(uus.getId());
					item.setQuantity(item.getQuantity() + uus.getQuantity());
					item.setPrice(uus.getPrice());
					log.debug("Found existing item " + uus.getName()
							+ " increased quantity by " + uus.getQuantity());
					domainController.submitNewStockItem(item);
				} catch (NoSuchElementException e1) {
					try {
						domainController.submitNewStockItem(uus);
						log.debug("Added " + uus.getName() + " quantity of "
								+ uus.getQuantity());
					} catch (VerificationFailedException e2) {
						e2.printStackTrace();
					}
				} catch (VerificationFailedException e1) {
					e1.printStackTrace();
				}
				StockTableModel.populateWithData(domainController
						.getStockState());
				dispose();
			}
		});
	}

}
