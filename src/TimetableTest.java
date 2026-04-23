import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TimetableTest {
    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        timetable.addNewTrainingSession(new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0)));

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0)));

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size());
        assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours());
        assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours());
    }

    @Test
    void testGetCountByCoachesSorting() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Петров", "Петр", "Петрович");
        Coach coach2 = new Coach("Сидоров", "Сидор", "Сидорович");
        Group group = new Group("Йога", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        assertEquals("Сидоров", result.get(0).getCoach().getSurname());
        assertEquals(2, result.get(0).getCount());
    }
}