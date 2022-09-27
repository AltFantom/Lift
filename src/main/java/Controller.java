public class Controller {
    private static int step = 1;
    public static void main(String[] args) {
        Building building = new Building();
        Controller controller = new Controller();
        while (building.isFloorsEmpty() || !(building.getLift().getPeople().isEmpty())){
            controller.upp(building);
            controller.down(building);
        }
    }
    private void upp(Building building){
        building.getLift().setDirect(true);
        for (int i = building.getLift().getFloor(); i < building.getFloors().size(); i++){
            System.out.println("**************** STEP" +  step++ + " ****************" );
            building.getLift().setPeople(building.getLift().leaveFromLift());
            building.getLift().addToLift();
            System.out.println(building);
            building.getLift().setFloor(building.getLift().getFloor() + 1);
        }
    }
    private void down(Building building){
        building.getLift().setDirect(false);
        for (int i = building.getFloors().size(); i > 1; i--){
            System.out.println("**************** STEP" +  step++ + " ****************" );
            building.getLift().setPeople(building.getLift().leaveFromLift());
            building.getLift().addToLift();
            System.out.println(building);
            building.getLift().setFloor(building.getLift().getFloor() - 1);
        }
    }
}
