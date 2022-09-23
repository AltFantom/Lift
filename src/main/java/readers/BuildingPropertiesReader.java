package readers;

public class BuildingPropertiesReader extends PropertiesReader{
    private int minFloor;
    private int maxFloor;
    public BuildingPropertiesReader(){
        minFloor = Integer.parseInt(properties.getProperty("building.min_floor"));
        maxFloor = Integer.parseInt(properties.getProperty("building.max_floor"));
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }
}
