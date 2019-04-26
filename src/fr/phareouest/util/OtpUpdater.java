package fr.phareouest.util;

import fr.phareouest.AuthentiBBScreen;
import net.rim.device.api.ui.UiApplication;

public class OtpUpdater extends Thread {
	AuthentiBBScreen mainScreen;
	public OtpUpdater(AuthentiBBScreen mainScreen) {
		this.mainScreen = mainScreen;
	}
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
			}
			// Queue a new task on the event thread
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					mainScreen.appendLabelText();
				}
			});
		}
	}
}
