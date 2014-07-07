package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.User;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * Mit dieser Aktivität kann sich ein Nutzer registrieren.
 */
public class RegisterActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	/**
	 * Einen Nutzer auf Basis der eingegebenen Daten registrieren.
	 * 
	 * @param view
	 *            View, in der die Nutzerdaten eingegeben wurden.
	 */
	public void register(View view) {

		// Daten aus View auslesen und in Datenbank speichern
		LocalDatabase database = new LocalDatabase(getApplicationContext());
		EditText mail = (EditText) findViewById(R.id.register_mail_input);
		User user = database.getUser(mail.getText().toString());
		if (user == null) {
			// E-Mail Adresse noch nicht verwendet
			user = new User();
			user.setMail(mail.getText().toString());
			EditText firstName = (EditText) findViewById(R.id.register_firstName_input);
			user.setFirstName(firstName.getText().toString());
			EditText lastName = (EditText) findViewById(R.id.register_lastName_input);
			user.setLastName(lastName.getText().toString());
			// Passwort zunächst hashen
			EditText pw = (EditText) findViewById(R.id.register_password_input);
			String hashedPassword = SessionManager.hashPassword(pw.getText()
					.toString());
			user.setHashedPassword(hashedPassword);
			database.insert(user);

			// Login durchführen
			SessionManager sessionManager = new SessionManager(
					getApplicationContext());
			sessionManager.login(user.getMail(), pw.getText().toString());

			// nach erfolgreicher Registrierung zurück zu Haupt-Aktivität
			// wechseln
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		} else {
			// E-Mail Adresse bereits verwendet.
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.register_popup_text);
			builder.setPositiveButton(R.string.register_popup_ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

}
