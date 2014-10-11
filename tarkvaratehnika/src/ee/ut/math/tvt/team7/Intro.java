package ee.ut.math.tvt.team7;
import org.apache.log4j.Logger;
import java.io.IOException;

public class Intro {
	static Logger log = Logger.getLogger(
            Intro.class.getName());
	
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			IntroUI ui = new IntroUI();
			
            public void run() {
                try {
					ui.createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                log.info("1255 5 5 ");
            }
        });

	}

}
