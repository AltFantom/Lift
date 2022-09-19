import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Floor {
    Queue<Person> queue;
    final int minNumberOfPeopleOnFloor = 0;
    final int maxNumberOfPeopleOnFloor = 10;
    int numberOfPeopleOnFloor;

    public Floor() {
        queue = new LinkedList<>();
        fillQueueOnFloor();

    }
    private void fillQueueOnFloor(){
        Random random = new Random();
        this.numberOfPeopleOnFloor = random.nextInt(minNumberOfPeopleOnFloor, maxNumberOfPeopleOnFloor + 1);
        for (int i = 0; i < numberOfPeopleOnFloor - 1; i++) {
            queue.add(new Person(random.nextInt(),));
        }
    }

    public void personEnterToElevator() {

    }

    public void personLeaveFromElevator() {

    }
}
