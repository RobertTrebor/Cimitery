package de.cimitery.android.cimitery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Finisher {
	
	public static void finishCimitery(final Activity activity) {
		AlertDialog.Builder exitDialogBuilder = new AlertDialog.Builder(activity.getApplicationContext());
		 
		// set title
		exitDialogBuilder.setTitle("exit");

		// set dialog message
		exitDialogBuilder
			.setMessage("Would you like to exit cimitery?")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					activity.finish();
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});

			AlertDialog alertDialog = exitDialogBuilder.create();

			alertDialog.show();
	}

}
