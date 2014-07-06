package de.tum.asp.sharenow.util;

/**
 * Diese Klasse repräsentiert einen einzelnen Parkplatz.
 */
public class Place {

	private long id;
	private long userId;
	private String description;
	private String address;
	private double locationLat;
	private double locationLong;
	private double pricePerHour;
	private int numberOfBookings;
	private double rating;

	/**
	 * Standard-Konstruktor. Erstellt ein neues Place Objekt, das anschließend
	 * mit Werten gefüllt werden kann.
	 */
	public Place() {
	}

	/**
	 * Konstruktor, um ein Place Objekt zu erstellen, von dem alle Werte bekannt
	 * sind.
	 * 
	 * @param id
	 *            Die ID des Parkplatzes in der Datenbank.
	 * @param userId
	 *            Die ID des Nutzers in der Datenbank, dem dieser Parkplatz
	 *            gehört.
	 * @param description
	 *            Die Beschreibung des Parkplatzes.
	 * @param address
	 *            Die Adresse des Parkplatzes.
	 * @param locationLat
	 *            Der Breitengrad des Parkplatzes.
	 * @param locationLong
	 *            Der Längengrad des Parkplatzes.
	 * @param pricePerHour
	 *            Der Preis des Parkplatzes pro Stunde.
	 * @param numberOfBookings
	 *            Die bisherige Anzahl Buchungen des Parkplatzes.
	 * @param rating
	 *            Die Bewertung des Parkplatzes.
	 */
	public Place(long id, long userId, String description, String address,
			double locationLat, double locationLong, double pricePerHour,
			int numberOfBookings, double rating) {
		super();
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.address = address;
		this.locationLat = locationLat;
		this.locationLong = locationLong;
		this.pricePerHour = pricePerHour;
		this.numberOfBookings = numberOfBookings;
		this.rating = rating;
	}

	/**
	 * Konstruktor, um ein Place Objekt zu erstellen, von dem alle Werte außer
	 * der ID bekannt sind.
	 * 
	 * @param userId
	 *            Die ID des Nutzers in der Datenbank, dem dieser Parkplatz
	 *            gehört.
	 * @param description
	 *            Die Beschreibung des Parkplatzes.
	 * @param address
	 *            Die Adresse des Parkplatzes.
	 * @param locationLat
	 *            Der Breitengrad des Parkplatzes.
	 * @param locationLong
	 *            Der Längengrad des Parkplatzes.
	 * @param pricePerHour
	 *            Der Preis des Parkplatzes pro Stunde.
	 * @param numberOfBookings
	 *            Die bisherige Anzahl Buchungen des Parkplatzes.
	 * @param rating
	 *            Die Bewertung des Parkplatzes.
	 */
	public Place(long userId, String description, String address,
			double locationLat, double locationLong, double pricePerHour,
			int numberOfBookings, double rating) {
		super();
		this.userId = userId;
		this.description = description;
		this.address = address;
		this.locationLat = locationLat;
		this.locationLong = locationLong;
		this.pricePerHour = pricePerHour;
		this.numberOfBookings = numberOfBookings;
		this.rating = rating;
	}

	/**
	 * @return Die ID des Parkplatzes in der Datenbank.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            Die neue ID des Parkplatzes in der Datenbank.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Die ID des Nutzers in der Datenbank, dem dieser Parkplatz gehört.
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            Die neue ID des Nutzers in der Datenbank, dem dieser Parkplatz
	 *            gehört.
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return Die Beschreibung des Parkplatzes.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            Die neue Beschreibung des Parkplatzes.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Die Adresse des Parkplatzes.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            Die neue Adresse des Parkplatzes.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Der Breitengrad des Parkplatzes.
	 */
	public double getLocationLat() {
		return locationLat;
	}

	/**
	 * @param locationLat
	 *            Der neue Breitengrad des Parkplatzes.
	 */
	public void setLocationLat(double locationLat) {
		this.locationLat = locationLat;
	}

	/**
	 * @return Der Längengrad des Parkplatzes.
	 */
	public double getLocationLong() {
		return locationLong;
	}

	/**
	 * @param locationLong
	 *            Der neue Längengrad des Parkplatzes.
	 */
	public void setLocationLong(double locationLong) {
		this.locationLong = locationLong;
	}

	/**
	 * @return Der Preis des Parkplatzes pro Stunde.
	 */
	public double getPricePerHour() {
		return pricePerHour;
	}

	/**
	 * @param pricePerHour
	 *            Der neue Preis des Parkplatzes pro Stunde.
	 */
	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	/**
	 * @return Die bisherige Anzahl Buchungen des Parkplatzes.
	 */
	public int getNumberOfBookings() {
		return numberOfBookings;
	}

	/**
	 * @param numberOfBookings
	 *            Die neue bisherige Anzahl Buchungen des Parkplatzes.
	 */
	public void setNumberOfBookings(int numberOfBookings) {
		this.numberOfBookings = numberOfBookings;
	}

	/**
	 * @return Die Bewertung des Parkplatzes.
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            Die neue Bewertung des Parkplatzes.
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		String s = "Place(";
		s += "id:" + id + " ";
		s += "userId:" + userId + " ";
		s += "description:" + description + " ";
		s += "address:" + address + " ";
		s += "locationLat:" + locationLat + " ";
		s += "locationLong:" + locationLong + " ";
		s += "pricePerHour:" + pricePerHour + " ";
		s += "numberOfBookings:" + numberOfBookings + " ";
		s += "rating:" + rating;
		s += ")";
		return s;
	}

}
