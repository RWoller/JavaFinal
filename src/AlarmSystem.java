public class AlarmSystem {

    // ** Singleton pattern ** to have one alarm system for the mansion
    private static final AlarmSystem instance = new AlarmSystem();

    private boolean alarmActive = false;

    // Constructor to make sure that no other instances can be created
    private AlarmSystem() {
    }
    // Allows other classes to get this shared instance
    public static AlarmSystem getInstance() {
        return instance;
    }

    public void ActivateAlarm(String reason) {
        alarmActive = true;
        System.out.println("Alarm Activated: " + reason);
    }
    public void DeactivateAlarm() {
        alarmActive = false;
        System.out.println("Alarm Deactivated");
    }

    public boolean isAlarmActive() {
        return alarmActive;
    }
}
