package de.tum.asp.sharenow.app;

import de.tum.asp.sharenow.R;
import de.tum.asp.sharenow.database.LocalDatabase;
import de.tum.asp.sharenow.util.User;
import android.support.v7.app.ActionBarActivity;
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
		User user = new User();
		EditText mail = (EditText) findViewById(R.id.register_mail_input);
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

		// nach erfolgreicher Registrierung zurück zu Haupt-Aktivität wechseln
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
