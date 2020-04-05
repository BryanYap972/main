package seedu.nova.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.nova.commons.core.GuiSettings;
import seedu.nova.model.person.Person;
import seedu.nova.model.plan.StrongTask;
import seedu.nova.model.plan.Task;
import seedu.nova.model.plan.WeakTask;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.util.time.slotlist.DateTimeSlotList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' nova book file path.
     */
    Path getNovaFilePath();

    /**
     * Sets the user prefs' nova book file path.
     */
    void setNovaFilePath(Path addressBookFilePath);

    Nova getNova();

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces nova book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the nova book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the nova book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the nova book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the nova book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the nova book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void commitAddressBook();

    void undoAddressBook();

    boolean canUndoAddressBook();

    boolean canRedoAddressBook();

    void redoAddressBook();

    String viewSchedule(LocalDate date);

    String viewSchedule(int weekNumber);

    boolean isWithinSem(LocalDate date);

    boolean isWithinSem(int weekNumber);

    Mode getMode();

    ProgressTracker getProgressTracker();

    void addEvent(Event e);

    void addLesson(Lesson l);

    DateTimeSlotList getFreeSlotOn(LocalDate date);

    String viewFreeSlot(LocalDate date);

    String deleteEvent(LocalDate date, int index);

    String addNote(String desc, LocalDate date, int index);

    //==============studyplanner=============

    void resetPlan();

    boolean addRoutineTask(StrongTask st);

    boolean addFlexibleTask(WeakTask wt);

    List<Task> getTaskList();

    Task searchTask(String name);

    boolean deleteTask(Task task);

    Event generateTaskEvent(Task task, LocalDate date) throws Exception;

}
