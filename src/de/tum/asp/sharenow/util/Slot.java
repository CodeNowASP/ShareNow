package de.tum.asp.sharenow.util;

import java.sql.Timestamp;

/**
 * Diese Klasse repräsentiert einen einzelnen Zeitslot eines Parkplatzes.
 */
public class Slot {

	private long id;
	private long placeId;
	private long userId;
	private Timestamp dateStart;
	private Timestamp dateEnd;
	private boolean reserved;
	private boolean weekly;

	/**
	 * Standard-Konstruktor. Erstellt ein neues Slot Objekt, das anschließend
	 * mit Werten gefüllt werden kann.
	 */
	public Slot() {
	}

	/**
	 * Konstruktor, um ein Slot Objekt zu erstellen, von dem alle Werte bekannt
	 * sind.
	 * 
	 * @param id
	 *            Die ID des Slots in der Datenbank.
	 * @param placeId
	 *            Die ID des Parkplatzes in der Datenbank, zu dem dieser Slot
	 *            gehört.
	 * @param userId
	 *            Die ID des Nutzers in der Datenbank, zu dem dieser Slot
	 *            gehört.
	 * @param dateStart
	 *            Das Start-Datum des Slots.
	 * @param dateEnd
	 *            Das End-Datum des Slots.
	 * @param reserved
	 *            Wahr, wenn der Slot belegt ist.
	 * @param weekly
	 *            Wahr, wenn der Slot wöchentlich wiederholt werden soll.
	 */
	public Slot(long id, long placeId, long userId, Timestamp dateStart,
			Timestamp dateEnd, boolean reserved, boolean weekly) {
		super();
		this.id = id;
		this.placeId = placeId;
		this.userId = userId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.reserved = reserved;
		this.weekly = weekly;
	}

	/**
	 * Konstruktor, um ein Slot Objekt zu erstellen, von dem alle Werte außer
	 * der ID bekannt sind.
	 * 
	 * @param placeId
	 *            Die ID des Parkplatzes in der Datenbank, zu dem dieser Slot
	 *            gehört.
	 * @param userId
	 *            Die ID des Nutzers in der Datenbank, zu dem dieser Slot
	 *            gehört.
	 * @param dateStart
	 *            Das Start-Datum des Slots.
	 * @param dateEnd
	 *            Das End-Datum des Slots.
	 * @param reserved
	 *            Wahr, wenn der Slot belegt ist.
	 * @param weekly
	 *            Wahr, wenn der Slot wöchentlich wiederholt werden soll.
	 */
	public Slot(long placeId, long userId, Timestamp dateStart,
			Timestamp dateEnd, boolean reserved, boolean weekly) {
		super();
		this.placeId = placeId;
		this.userId = userId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.reserved = reserved;
		this.weekly = weekly;
	}

	/**
	 * @return Die ID des Slots in der Datenbank.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            Die neue ID des Slots in der Datenbank.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Die ID des Parkplatzes in der Datenbank, zu dem dieser Slot
	 *         gehört.
	 */
	public long getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId
	 *            Die neue ID des Parkplatzes in der Datenbank, zu dem dieser
	 *            Slot gehört.
	 */
	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}

	/**
	 * @return Die ID des Nutzers in der Datenbank, zu dem dieser Slot gehört.
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            Die neue ID des Nutzers in der Datenbank, zu dem dieser Slot
	 *            gehört.
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return Das Start-Datum des Slots.
	 */
	public Timestamp getDateStart() {
		return dateStart;
	}

	/**
	 * @param dateStart
	 *            Das neue Start-Datum des Slots.
	 */
	public void setDateStart(Timestamp dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * @return Das End-Datum des Slots.
	 */
	public Timestamp getDateEnd() {
		return dateEnd;
	}

	/**
	 * @param dateEnd
	 *            Das neue End-Datum des Slots.
	 */
	public void setDateEnd(Timestamp dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * @return Wahr wenn der Zeitslot reserviert ist.
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * @param reserved
	 *            Wahr wenn der Zeitslot reserviert ist.
	 */
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	/**
	 * @return Wahr wenn der Zeitslot wöchentlich ist.
	 */
	public boolean isWeekly() {
		return weekly;
	}

	/**
	 * @param weekly
	 *            Wahr wenn der Zeitslot wöchentlich ist.
	 */
	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}

	@Override
	public String toString() {
		String s = "Slot(";
		s += "id:" + id + " ";
		s += "placeId:" + placeId + " ";
		s += "userId:" + userId + " ";
		s += "dateStart:" + dateStart + " ";
		s += "dateEnd:" + dateEnd + ", ";
		s += "reserved:" + reserved + ", ";
		s += "weekly:" + weekly;
		s += ")";
		return s;
	}

	/**
	 * Überprüfen, ob der Slot mit einem Zeitabschnitt kollidiert.
	 * 
	 * @param start
	 *            Start des Zeitabschnitts.
	 * @param end
	 *            Ende des Zeitabschnitts.
	 * @return Wahr wenn sich der Slot und der Zeitabschnitt überschneiden.
	 */
	public boolean overlapsWith(Timestamp start, Timestamp end) {
		boolean otherIsBefore = end.before(this.dateStart);
		boolean otherIsAfter = start.after(this.dateEnd);
		return !(otherIsBefore || otherIsAfter);
	}

}
