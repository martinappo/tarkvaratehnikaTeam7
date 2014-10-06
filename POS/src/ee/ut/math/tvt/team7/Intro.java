package ee.ut.math.tvt.team7;

import java.io.IOException;

public class Intro {

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
            }
        });

	}

}
