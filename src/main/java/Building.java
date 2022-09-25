import readers.BuildingPropertiesReader;
import readers.FloorPropertiesReader;
import readers.LiftPropertiesReader;

import java.util.*;

public class Building {
    private final BuildingPropertiesReader reader = new BuildingPropertiesReader();
    private final int minFloor = reader.getMinFloor();
    private final int maxFloor = reader.getMaxFloor();
    private List<Floor> floors;
    Lift lift;

    public Building() {
        Random random = new Random();
        int randomFloor = random.nextInt(minFloor, maxFloor + 1);
        floors = new ArrayList<>(randomFloor);
        fillFloors(randomFloor);
        lift = new Lift();
    }

    private void fillFloors(int randomFloor) {
        for (int i = 1; i <= randomFloor; i++) {
            floors.add(new Floor(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = floors.size() - 1; i >= 0; i--) {
            stringBuilder.append(floors.get(i));
        }
        return stringBuilder.toString();
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public class Lift {
        private final LiftPropertiesReader reader = new LiftPropertiesReader();
        private final int capacity = reader.getMaxCapacity();
        private final int startFloor = reader.getStartFloor();
        private final List<Person> people;
        private int floor = startFloor;
        private boolean direct = true;


        public Lift() {
            people = new ArrayList<>(capacity);
        }

        public void personLeaveToElevator(Person person) {
            people.remove(person);
        }

        @Override
        public String toString() {
            StringBuilder elevator = new StringBuilder();
            for (Person person : people) {
                elevator.append(person.toString());
            }
            return elevator.toString();
        }

        public List<Person> getPeople() {
            return people;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }
    }

    public class Floor {
        private int countDeliveredPeople = 0;
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

        public int getNumberOfPeopleOnFloor() {
            return numberOfPeopleOnFloor;
        }

        public int getFloor() {
            return floor;
        }

        public List<Person> getQueue() {
            return queue;
        }

        @Override
        public String toString() {
            StringBuilder toBuildFloor = new StringBuilder();
            int howManyPeopleInLift = lift.people.size();
            toBuildFloor.append("[").append(floor).append("]  ");
            if (floor > 9){
                toBuildFloor.append("\b");
            }
            toBuildFloor.append(countDeliveredPeople);
            if (floor == lift.floor){
                if (lift.direct){
                    toBuildFloor.append(" |^           ");
                    toBuildFloor.append(howManyPeopleInLift(howManyPeopleInLift))
                            .append(lift).append("\b\b^| ");
                }
                else {
                    toBuildFloor.append(" |v           ")
                            .append(howManyPeopleInLift(howManyPeopleInLift))
                            .append(lift).append("\b\bv| ");
                }
            }
            else {
                toBuildFloor.append(" |           ");
                toBuildFloor.append(lift).append("| ");
            }
            for (Person person : queue) {
                toBuildFloor.append(person.toString());
            }
            if (!queue.isEmpty()){
                toBuildFloor.append("\b");
            }
            return toBuildFloor + "\n";
        }
        private String howManyPeopleInLift(int howManyPeopleInLift){
                return switch (howManyPeopleInLift){
                    case 5 -> "\b\b\b\b\b\b\b\b\b\b";
                    case 4 -> "\b\b\b\b\b\b\b\b";
                    case 3 -> "\b\b\b\b\b\b";
                    case 2 -> "\b\b\b\b";
                    case 1 -> "\b\b";
                    case 0 -> "";
                    default -> throw new RuntimeException();
                };
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
