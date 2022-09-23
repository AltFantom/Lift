package readers;

public class FloorPropertiesReader extends PropertiesReader{
    private int minQueue;
    private int maxQueue;
    public FloorPropertiesReader(){
        minQueue = Integer.parseInt(properties.getProperty("building.floor.min_queue"));
        maxQueue = Integer.parseInt(properties.getProperty("building.floor.max_queue"));
    }

    public int getMinQueue() {
        return minQueue;
    }

    public int getMaxQueue() {
        return maxQueue;
    }
}
