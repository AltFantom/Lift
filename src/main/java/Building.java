import readers.BuildingPropertiesReader;
import readers.FloorPropertiesReader;
import readers.LiftPropertiesReader;

import java.util.*;

public class Building {
    private final BuildingPropertiesReader reader = new BuildingPropertiesReader();
    private final int minFloor = reader.getMinFloor();
    private final int maxFloor = reader.getMaxFloor();
    private List<Floor> floors;
    Elevator elevator;

    public Building() {
        Random random = new Random();
        int randomFloor = random.nextInt(minFloor, maxFloor + 1);
        floors = new ArrayList<>(randomFloor);
        System.out.println(floors.size());
        fillFloors(randomFloor);
        elevator = new Elevator();
    }

    private void fillFloors(int randomFloor) {
        for (int i = 1; i <= randomFloor; i++) {
            floors.add(new Floor(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Floor floor :
                floors) {
            stringBuilder.append(floor.toString());
        }
        return stringBuilder.toString();
    }


    public class Elevator {
        private final List<Person> people;
        private final LiftPropertiesReader reader = new LiftPropertiesReader();
        private final int capacity = reader.getMaxCapacity();
        private final int startFloor = reader.getStartFloor();

        public Elevator() {
            people = new ArrayList<>(capacity);
        }

        public void personLeaveToElevator(Person person) {
            people.remove(person);
        }

        @Override
        public String toString() {
            return "| " + people.toString() + " |";
        }
    }

    public class Floor {
        private final FloorPropertiesReader reader = new FloorPropertiesReader();
        private List<Person> queue;
        private final int minNumberOfPeopleOnFloor = reader.getMinQueue();
        private final int maxNumberOfPeopleOnFloor = reader.getMaxQueue();
        private int numberOfPeopleOnFloor;
        private final int floor;

        public Floor(int floor) {
            this.floor = floor;
            queue = new LinkedList<>();
            fillQueueOnFloor();
        }

        private void fillQueueOnFloor() {
            Random random = new Random();
            this.numberOfPeopleOnFloor = random.nextInt(minNumberOfPeopleOnFloor, maxNumberOfPeopleOnFloor + 1);
            for (int i = 1; i <= numberOfPeopleOnFloor; i++) {
                int needFloorForPerson = random.nextInt(1, 11);
                if (needFloorForPerson != floor) {
                    queue.add(new Person(floor, needFloorForPerson));
                }
            }
        }

        public void personEnterToElevator(Person person) {
            queue.remove(person);
        }

        @Override
        public String toString() {
            StringBuilder toBuildFloor = new StringBuilder();
            toBuildFloor.append("[").append(floor).append("] ");
            for (Person person : queue) {
                toBuildFloor.append(person.toString());
            }
            return toBuildFloor + "\b\n";
        }
    }

    public class Person {
        private int currentFloor;
        private int needFloor;
        public Person(int currentFloor, int neededFloor) {
            this.currentFloor = currentFloor;
            this.needFloor = neededFloor;
        }
        @Override
        public String toString() {
            return needFloor + " ";
        }
        public int getCurrentFloor() {
            return currentFloor;
        }

        public int getNeededFloor() {
            return needFloor;
        }
    }
}
