public class Controller {
    private static int step = 1;
    public static void main(String[] args) {
        Building building = new Building();
        for (int i = building.lift.getFloor(); i <= building.getFloors().size(); i++){
            System.out.println("**************** STEP" +  step++ + " ****************" );
            System.out.println(building);
            building.lift.setFloor(building.lift.getFloor() + 1);
        }
    }
}
