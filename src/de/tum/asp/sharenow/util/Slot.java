package de.tum.asp.sharenow.util;

import java.sql.Timestamp;

/**
 * Diese Klasse repräsentiert einen einzelnen Zeitslot eines Parkplatzes.
 */
public class Slot {

	private long id;
	private long placeId;
	private Timestamp dateStart;
	private Timestamp dateEnd;
	private boolean reserved;

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
	 * @param dateStart
	 *            Das Start-Datum des Slots.
	 * @param dateEnd
	 *            Das End-Datum des Slots.
	 * @param reserved
	 *            Wahr, wenn der Slot belegt ist.
	 */
	public Slot(long id, long placeId, Timestamp dateStart, Timestamp dateEnd,
			boolean reserved) {
		super();
		this.id = id;
		this.placeId = placeId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.reserved = reserved;
	}

	/**
	 * Konstruktor, um ein Slot Objekt zu erstellen, von dem alle Werte außer
	 * der ID bekannt sind.
	 * 
	 * @param placeId
	 *            Die ID des Parkplatzes in der Datenbank, zu dem dieser Slot
	 *            gehört.
	 * @param dateStart
	 *            Das Start-Datum des Slots.
	 * @param dateEnd
	 *            Das End-Datum des Slots.
	 * @param reserved
	 *            Wahr, wenn der Slot belegt ist.
	 */
	public Slot(long placeId, Timestamp dateStart, Timestamp dateEnd,
			boolean reserved) {
		super();
		this.placeId = placeId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.reserved = reserved;
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

	@Override
	public String toString() {
		String s = "Slot(";
		s += "id:" + id + " ";
		s += "placeId:" + placeId + " ";
		s += "dateStart:" + dateStart + " ";
		s += "dateEnd:" + dateEnd + ", ";
		s += "reserved:" + reserved;
		s += ")";
		return s;
	}

}
