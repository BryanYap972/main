package seedu.nova.model.schedule.event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import seedu.nova.model.plan.Task;
import seedu.nova.model.plan.TaskFreq;
import seedu.nova.model.util.time.TimeUtil;
import seedu.nova.model.util.time.duration.DateTimeDuration;

/**
 * Event generated by task
 */
public class WeakEvent extends Event {
    private static final String EVENT_TYPE = "weak";
    private Task origin;

    public WeakEvent(String name, DateTimeDuration dtd, Task origin) {
        super(name, null, dtd.getStartTime(), dtd.getEndTime(), dtd.getStartDate());
        this.origin = origin;
    }

    public Task getOriginTask() {
        return origin;
    }

    public void destroy() {
        origin.deleteEvent(this);
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    @Override
    public void setDate(LocalDate date) {
        origin.deleteEvent(this);
        this.date = date;
        origin.addEvent(this);
    }

    @Override
    public String toString() {
        return "Description: " + desc + "\n"
                + "Venue: " + venue + "\n"
                + "Time: " + getDayOfWeek() + ", "
                + startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                + " - " + endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WeakEvent) {
            if (origin.getTaskFreq() == TaskFreq.WEEKLY) {
                return TimeUtil.getMondayOfWeek(date).equals(TimeUtil.getMondayOfWeek(((WeakEvent) o).getDate()))
                        && getStartTime().equals(((WeakEvent) o).getStartTime())
                        && getEndTime().equals(((WeakEvent) o).getEndTime())
                        && desc.equals(((WeakEvent) o).desc);
            } else {
                return super.equals(o);
            }
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        if (origin.getTaskFreq() == TaskFreq.WEEKLY) {
            return Objects.hash(desc, TimeUtil.getMondayOfWeek(date), startTime, endTime);
        } else {
            return Objects.hash(desc, date, startTime, endTime);
        }
    }
}
