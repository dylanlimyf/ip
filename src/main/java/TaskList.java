import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * List of tasks with basic operations.
 */
public class TaskList {

    private static final int DEFAULT_CAPACITY = 100;

    private final ArrayList<Task> tasks;

    /**
     * Creates a task list with default initial capacity.
     */
    public TaskList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a task list with a custom initial capacity.
     *
     * @param capacity initial capacity for the list
     */
    public TaskList(int capacity) {
        tasks = new ArrayList<>(capacity);
    }

    /**
     * Returns current number of tasks.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index
     * @return task at index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task.
     *
     * @param task task to add
     */
    public void add(Task task) throws DukeException {
        tasks.add(task);
    }

    /**
     * Adds a task without throwing (used during load).
     *
     * @param task task to add
     * @return true if added (always true for ArrayList)
     */
    public boolean addLoaded(Task task) {
        tasks.add(task);
        return true;
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index zero-based index
     * @return removed task
     * @throws DukeException if index is invalid
     */
    public Task remove(int index) throws DukeException {
        checkIndex(index);
        return tasks.remove(index);
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index zero-based index
     * @throws DukeException if index is invalid
     */
    public void mark(int index) throws DukeException {
        checkIndex(index);
        tasks.get(index).mark();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index zero-based index
     * @throws DukeException if index is invalid
     */
    public void unmark(int index) throws DukeException {
        checkIndex(index);
        tasks.get(index).unmark();
    }

    /**
     * Returns tasks whose descriptions contain the keyword.
     *
     * @param keyword search term
     * @return list of matching tasks
     */
    public TaskList find(String keyword) {
        TaskList matches = new TaskList(tasks.size());
        find(keyword, (index, task) -> matches.addLoaded(task));
        return matches;
    }

    /**
     * Finds tasks that match the keyword and sends each match to the callback.
     *
     * @param keyword search term
     * @param onMatch callback invoked with 1-based match index and task
     * @return number of matches
     */
    public int find(String keyword, BiConsumer<Integer, Task> onMatch) {
        if (keyword == null || keyword.isBlank()) {
            return 0;
        }
        int matchCount = 0;
        for (Task task : tasks) {
            if (task.matches(keyword)) {
                matchCount++;
                if (onMatch != null) {
                    onMatch.accept(matchCount, task);
                }
            }
        }
        return matchCount;
    }

    /**
     * Validates a zero-based index against current size.
     *
     * @param index index to check
     * @throws DukeException if index is invalid
     */
    private void checkIndex(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("task number must be between 1 and " + tasks.size());
        }
    }
}
