package de.cimitery.android.cimitery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Finisher {

	Activity activity;

	public Finisher(Activity activity) {

		this.activity = activity;
	}

	public void finishCimitery() {
		
		AlertDialog.Builder exitDialogBuilder = new AlertDialog.Builder(
				activity);
				//activity.getApplicationContext());

		// set title
		exitDialogBuilder.setTitle("exit");

		// set dialog message

		exitDialogBuilder.setMessage("Would you like to exit cimitery?")
				.setCancelable(false)
				.setPositiveButton("Yes", new FinishConfirmed())
				.setNegativeButton("No", new Continue());

		AlertDialog alertDialog = exitDialogBuilder.create();

		alertDialog.show();
	}

	private final class Continue implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}

	}

	private final class FinishConfirmed implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			activity.finish();
		}
	}
}
