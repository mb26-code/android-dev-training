package fr.umontpellier.hai926i.tp2_app_2;


//just a classic plain old java object for dealing with sensor types
public class SensorType {

    private final int id;
    private final String name;


    public SensorType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
