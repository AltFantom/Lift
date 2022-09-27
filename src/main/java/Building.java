import readers.BuildingPropertiesReader;
import readers.FloorPropertiesReader;
import readers.LiftPropertiesReader;

import java.util.*;

public class Building {
    private final BuildingPropertiesReader reader = new BuildingPropertiesReader();
    private final int minFloor = reader.getMinFloor();
    private final int maxFloor = reader.getMaxFloor();
    private List<Floor> floors;
    private Lift lift;
    private int randomCountFloors;


    public Building() {
        Random random = new Random();
        randomCountFloors = random.nextInt(minFloor, maxFloor + 1);
        floors = new ArrayList<>(randomCountFloors);
        fillFloors();
        lift = new Lift();
    }

    private void fillFloors() {
        for (int i = 1; i <= randomCountFloors; i++) {
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

    public Lift getLift() {
        return lift;
    }

    public boolean isFloorsEmpty() {
        for (Floor floor : floors) {
            if (!floor.queue.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public class Lift {
        private final LiftPropertiesReader reader = new LiftPropertiesReader();
        private final int capacity = reader.getMaxCapacity();
        private final int startFloor = reader.getStartFloor();
        private List<Person> people;
        private int floor = startFloor;
        private boolean direct = true;


        public Lift() {
            people = new ArrayList<>(capacity);
        }

        public List<Person> leaveFromLift() {
            List<Person> newList = new ArrayList<>(capacity);
            for (Person person : people) {
                if (person.needFloor != floor) {
                    newList.add(person);
                } else {
                    floors.get(floor - 1).countDeliveredPeople++;
                }
            }
            return newList;
        }

        public void addToLift() {
            while (people.size() < capacity && !(floors.get(floor - 1).queue.isEmpty())) {
                if (direct && (containsPeopleNeedsUpp(floors.get(floor - 1).queue))) {
                    for (int i = 0; i < floors.get(floor - 1).queue.size(); i++) {
                        if (floors.get(floor - 1).queue.get(i).needFloor > floor) {
                            people.add(floors.get(floor - 1).queue.get(i));
                            floors.get(floor - 1).queue.remove(i);
                            break;
                        }
                    }
                } else if (!direct && containsPeopleNeedsDown(floors.get(floor - 1).queue)) {
                    for (int i = 0; i < floors.get(floor - 1).queue.size(); i++) {
                        if (floors.get(floor - 1).queue.get(i).needFloor < floor) {
                            people.add(floors.get(floor - 1).queue.get(i));
                            floors.get(floor - 1).queue.remove(i);
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder lift = new StringBuilder();
            for (Person person : people) {
                lift.append(person.toString());
            }
            return lift.toString();
        }

        private boolean containsPeopleNeedsUpp(ArrayList<Person> queue) {
            for (Person person :
                    queue) {
                if (person.needFloor > floor) {
                    return true;
                }
            }
            return false;
        }

        private boolean containsPeopleNeedsDown(ArrayList<Person> queue) {
            for (Person person :
                    queue) {
                if (person.needFloor < floor) {
                    return true;
                }
            }
            return false;
        }

        public List<Person> getPeople() {
            return people;
        }

        public void setPeople(List<Person> people) {
            this.people = people;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public void setDirect(boolean direct) {
            this.direct = direct;
        }
    }

    public class Floor {
        private int countDeliveredPeople = 0;
        private final FloorPropertiesReader reader = new FloorPropertiesReader();
        private ArrayList<Person> queue;
        private final int minNumberOfPeopleOnFloor = reader.getMinQueue();
        private final int maxNumberOfPeopleOnFloor = reader.getMaxQueue();
        private int numberOfPeopleOnFloor;
        private final int floor;

        public Floor(int floor) {
            this.floor = floor;
            queue = new ArrayList<>();
            fillQueueOnFloor();
        }

        private void fillQueueOnFloor() {
            Random random = new Random();
            this.numberOfPeopleOnFloor = random.nextInt(minNumberOfPeopleOnFloor, maxNumberOfPeopleOnFloor + 1);
            for (int i = 1; i <= numberOfPeopleOnFloor; i++) {
                int needFloorForPerson = random.nextInt(1, randomCountFloors);
                if (needFloorForPerson != floor) {
                    queue.add(new Person(floor, needFloorForPerson));
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder toBuildFloor = new StringBuilder();
            int liftLength = lift.toString().length();
            toBuildFloor.append("[").append(floor).append("]  ");
            if (floor > 9) {
                toBuildFloor.append("\b");
            }
            toBuildFloor.append(countDeliveredPeople).append("  ");
            if (countDeliveredPeople > 9){
                toBuildFloor.append("\b");
            }
            if (floor == lift.floor) {
                if (lift.direct) {
                    toBuildFloor.append("|^               ")
                            .append(howManyPeopleInLift(liftLength))
                            .append(lift).append("^| ");
                } else {
                    toBuildFloor.append("|v               ")
                            .append(howManyPeopleInLift(liftLength))
                            .append(lift).append("v| ");
                }
            } else {
                toBuildFloor.append("|                 ");
                toBuildFloor.append("| ");
            }
            for (Person person : queue) {
                toBuildFloor.append(person.toString());
            }
            if (!queue.isEmpty()) {
                toBuildFloor.append("\b");
            }
            return toBuildFloor + "\n";
        }

        private String howManyPeopleInLift(int liftLength) {
            StringBuilder removeString = new StringBuilder();
            for (int i = 0; i < liftLength; i++){
                removeString.append("\b");
            }
            return removeString.toString();
        }
    }

    public class Person {
        private final int currentFloor;
        private final int needFloor;

        public Person(int currentFloor, int neededFloor) {
            this.currentFloor = currentFloor;
            this.needFloor = neededFloor;
        }
        @Override
        public String toString() {
            return needFloor + " ";
        }
    }
}
