import java.util.Objects;

public class Person {
    private int id;
    private int currentFloor;
    private int neededFloor;
    public Person(int id, int currentFloor, int neededFloor){
        this.id = id;
        this.currentFloor = currentFloor;
        this.neededFloor = neededFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && currentFloor == person.currentFloor && neededFloor == person.neededFloor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentFloor, neededFloor);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", neededFloor=" + neededFloor +
                '}';
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getNeededFloor() {
        return neededFloor;
    }

    public void setNeededFloor(int neededFloor) {
        this.neededFloor = neededFloor;
    }
}
