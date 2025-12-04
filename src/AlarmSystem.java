public class AlarmSystem {

    // Singleton pattern — single alarm instance for the mansion
    private static final AlarmSystem instance = new AlarmSystem();

    private boolean alarmActive = false;

    private AlarmSystem() {}

    public static AlarmSystem getInstance() {
        return instance;
    }

    public void activateAlarm(String reason) {
        alarmActive = true;
        System.out.println("Alarm Activated: " + reason);
    }

    public void deactivateAlarm() {
        alarmActive = false;
        System.out.println("Alarm Deactivated");
    }

    public boolean isAlarmActive() {
        return alarmActive;
    }
}
