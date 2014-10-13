package ee.ut.math.tvt.team7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class IntroUI {
	static Logger log = Logger.getLogger(
            Intro.class.getName());

	public void createAndShowGUI() throws IOException {
		BasicConfigurator.configure();
		
		log.info("Intro window opened");
		
        JLabel label1 = new JLabel("Team name: team7");
        JLabel label2 = new JLabel("Leader: Martin Appo");
        JLabel label3 = new JLabel("Leader email: martinappo@gmail.com");
        JLabel label4 = new JLabel("Team members: Handre Elias, Argo Neumann, Martin Appo");
        JLabel label5 = new JLabel("Version number: ");
        JLabel label6 = new JLabel("Logo: ");
        
        BufferedImage myPicture = ImageIO.read(new File("res/ic_menu_search.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture), SwingConstants.LEFT);
       

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(30, //top
            30, //left
            10, //bottom
            30) //right
            );
        pane.setLayout(new GridLayout(0, 1));
        pane.add(label1);
        pane.add(label2);
        pane.add(label3);
        pane.add(label4);
        pane.add(label5);
        pane.add(label6);
        pane.add(picLabel);
        JFrame frame = new JFrame("Team7");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.pack();
        //Display the window.
        frame.setVisible(true);
	}

}
