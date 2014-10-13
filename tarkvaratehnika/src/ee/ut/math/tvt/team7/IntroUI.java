package ee.ut.math.tvt.team7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
	String teamName;
	String teamLeader;
	String leaderEmail;
	String teamMembers;
	String teamLogo;
	String ver;
	static Logger log = Logger.getLogger(
            Intro.class.getName());

	public void createAndShowGUI() throws IOException {
		BasicConfigurator.configure();
		
		log.info("Intro window opened");
		
		Properties application = new Properties();
		Properties version = new Properties();
    	InputStream input = null;
    	InputStream input2 = null;
    	 
 
    	try {
    		String filename2 = "version.properties";
    		String filename = "application.properties";
    		input = new FileInputStream(filename);
    		input2 = new FileInputStream(filename2);
    		
 
    		//load a properties file from class path, inside static method
    		application.load(input);
    		version.load(input2);
 
                //get the property value and print it out
    		teamName = application.getProperty("teamName");
    		teamLeader = application.getProperty("teamLeader");
    		leaderEmail = application.getProperty("leaderEmail");
    		teamMembers = application.getProperty("teamMembers");
    		teamLogo= application.getProperty("teamLogo");
    		ver = version.getProperty("build.number");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} 
        		catch (IOException e) {
				e.printStackTrace();
			}
        	}
        	if(input2 !=null){
        		try {
				input2.close();
			} 
        		catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
		
        JLabel label1 = new JLabel("Team name: "+ teamName);
        JLabel label2 = new JLabel("Leader: " + teamLeader);
        JLabel label3 = new JLabel("Leader email: " + leaderEmail);
        JLabel label4 = new JLabel("Team members: " + teamMembers);
        JLabel label5 = new JLabel("Version number: " + ver);
        JLabel label6 = new JLabel("Logo: ");
        
        BufferedImage myPicture = ImageIO.read(new File(teamLogo));
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
