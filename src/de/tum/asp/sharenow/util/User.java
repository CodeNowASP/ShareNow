package de.tum.asp.sharenow.util;

/**
 * Diese Klasse repräsentiert einen einzelnen User.
 */
public class User {

	private long id;
	private String mail;
	private String hashedPassword;
	private byte[] userImage;
	private String firstName;
	private String lastName;
	private double accountBalance;

	/**
	 * Standard-Konstruktor. Erstellt ein neues User Objekt, das anschließend
	 * mit Werten gefüllt werden kann.
	 */
	public User() {
	}

	/**
	 * Konstruktor, um ein User Objekt zu erstellen, von dem alle Werte bekannt
	 * sind.
	 * 
	 * @param id
	 *            Die ID des Nutzers in der Datenbank.
	 * @param mail
	 *            Die E-Mail Adresse des Nutzers.
	 * @param hashedPassword
	 *            Das gehashte Passwort des Nutzers.
	 * @param userImage
	 *            Das Profilbild des Nutzers als Binärdaten.
	 * @param firstName
	 *            Der Vorname des Nutzers.
	 * @param lastName
	 *            Der Nachname des Nutzers.
	 * @param accountBalance
	 *            Das Guthaben des Nutzers.
	 */
	public User(long id, String mail, String hashedPassword, byte[] userImage,
			String firstName, String lastName, double accountBalance) {
		super();
		this.id = id;
		this.mail = mail;
		this.hashedPassword = hashedPassword;
		this.userImage = userImage;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountBalance = accountBalance;
	}

	/**
	 * Konstruktor, um ein User Objekt zu erstellen, von dem alle Werte außer
	 * der ID bekannt sind.
	 * 
	 * @param mail
	 *            Die E-Mail Adresse des Nutzers.
	 * @param hashedPassword
	 *            Das gehashte Passwort des Nutzers.
	 * @param userImage
	 *            Die URI zum Profilbild des Nutzers.
	 * @param firstName
	 *            Der Vorname des Nutzers.
	 * @param lastName
	 *            Der Nachname des Nutzers.
	 * @param accountBalance
	 *            Das Guthaben des Nutzers.
	 */
	public User(String mail, String hashedPassword, byte[] userImage,
			String firstName, String lastName, double accountBalance) {
		super();
		this.id = -1;
		this.mail = mail;
		this.hashedPassword = hashedPassword;
		this.userImage = userImage;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountBalance = accountBalance;
	}

	/**
	 * @return Die ID des Users in der Datenbank. Gibt -1 zurück wenn dem User
	 *         noch keine ID zugewiesen wurde.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param _id
	 *            Die neue ID des Users in der Datenbank.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Die E-Mail Adresse des Users.
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            Die neue E-Mail Adresse des Users.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return Das gehashte Passwort des Nutzers.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * @param hashedPassword
	 *            Das neue gehashte Passwort des Nutzers.
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * @return Das Profilbild des Nutzers als Binärdaten.
	 */
	public byte[] getUserImage() {
		return userImage;
	}

	/**
	 * @param userImage
	 *            Das neue Profilbild des Nutzers als Binärdaten.
	 */
	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}

	/**
	 * 
	 * @return Der Vorname des Nutzers.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            Der neue Vorname des Nutzers.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return Der Nachname des Nutzers.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            Der neue Nachname des Nutzers.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return Das Guthaben des Nutzers.
	 */
	public double getAccountBalance() {
		return accountBalance;
	}

	/**
	 * @param accountBalance
	 *            Das neue Guthaben des Nutzers.
	 */
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		String s = "User(";
		s += "id:" + id + " ";
		s += "mail:" + mail + " ";
		s += "hashedPassword:" + hashedPassword + " ";
		s += "userImage:" + userImage + " ";
		s += "firstName:" + firstName + " ";
		s += "lastName:" + lastName + " ";
		s += "accountBalance:" + accountBalance;
		s += ")";
		return s;
	}

}
