package seedu.nova.model.progresstracker;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

/**
 * Handles list related methods for tasks
 */
public class PtTaskList {
    private final String listHeader = "Tasks for the week: \n";

    private ArrayList<PtTask> list;

    /**
     * Creates PtTaskList object
     */
    public PtTaskList() {
        list = new ArrayList<>();
    }

    public int getNumTask() {
        return list.size();
    }

    public ArrayList<PtTask> getList() {
        return list;
    }

    public PtTask getTask(int taskNum) {
        return list.get(taskNum - 1);
    }

    /**
     * Lists the tasks in the task list
     * @return String listing out the tasks
     */
    public String listTasks() {
        String str = "";

        for (int i = 0; i < list.size(); i++) {
            PtTask task = list.get(i);
            String note = task.getNote().toString() + "\n";

            str = str + "  " + (i + 1) + ") " + task.toString() + "\n"
                    + "       Note: " + note;
        }

        return str;
    }

    /**
     * Adds task to the list
     * @param task to be added
     */
    public void addTask(PtTask task) {
        requireNonNull(task);
        list.add(task);
    }

    public void deleteTask(int taskNum) {
        list.remove(taskNum - 1);
    }

    /**
     * Calculates the progress for a week
     * @return a double representing the percentage of tasks done in a week
     */
    public double getProgressTasks() {
        double numDone = 0;
        double totalTask = getNumTask();

        for (int i = 0; i < totalTask; i++) {
            PtTask task = list.get(i);

            if (task.isDone()) {
                numDone++;
            }
        }

        if (totalTask == 0) {
            return 0;
        } else {
            return numDone / totalTask;
        }
    }
}
