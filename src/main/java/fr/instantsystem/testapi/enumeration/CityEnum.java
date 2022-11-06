package fr.instantsystem.testapi.enumeration;

public enum CityEnum {
    POITIERS("poitiers");

    private final String label;
    CityEnum(String label) {
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
