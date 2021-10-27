package controllerLogic;

/** Schnittstelle f�r Eingabeeinheiten im Projekt bei einer
* Implementierung mit Regelschleife.
* Die Schnittstelle darf nur in Absprache mit dem Dozenten ver�ndert werden.
*/

public interface CarSensorInput {
	/** es gibt vier Sensoren: FL ist vorne links am Auto angebracht, FR vorne rechts, 
	* BL hinten links und BR hinten rechts.
	*/
	enum Sensor {FL,FR,BL,BR;}
	
	/** 
	* Liefert die aktuell gemessene Entfernung zum naechsten ortbaren Objekt des 
	* Sensors s. Die Ausführung kann bei der Realisierung mit dem NXTUltrasonicSensor
	* einige Zeit in Anspruch nehmen (vgl. Doku von Lejos)
	* @param s der Sensor, der abgefragt werden soll
	* @return die aktuell gemessene Entfernung zum naechsten ortbaren Objekt in cm???
	* @throws CarException, wenn kein gültiger Wert gelesen werden konnte
	*/
	double getDistance(Sensor s) throws CarException, InterruptedException;

	void startThreads();

	void init(Controller controller);
	
}