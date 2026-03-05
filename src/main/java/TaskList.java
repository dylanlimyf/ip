/**
 * Fixed-capacity list of tasks with basic operations.
 */
public class TaskList {

    private static final int DEFAULT_CAPACITY = 100;

    private final Task[] tasks;
    private int size;

    /**
     * Creates a task list with default capacity.
     */
    public TaskList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a task list with a custom capacity.
     *
     * @param capacity maximum number of tasks
     */
    public TaskList(int capacity) {
        tasks = new Task[capacity];
        size = 0;
    }

    /**
     * Returns current number of tasks.
     *
     * @return task count
     */
    public int size() {
        return size;
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index
     * @return task at index
     */
    public Task get(int index) {
        return tasks[index];
    }

    /**
     * Adds a task, enforcing capacity.
     *
     * @param task task to add
     * @throws DukeException if the list is full
     */
    public void add(Task task) throws DukeException {
        if (isFull()) {
            throw new DukeException("task list full, delete something first");
        }
        tasks[size++] = task;
    }

    /**
     * Adds a task without throwing on capacity (used during load).
     *
     * @param task task to add
     * @return true if added, false if full
     */
    public boolean addLoaded(Task task) {
        if (isFull()) {
            return false;
        }
        tasks[size++] = task;
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
        Task removed = tasks[index];
        for (int i = index; i < size - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[size - 1] = null;
        size--;
        return removed;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index zero-based index
     * @throws DukeException if index is invalid
     */
    public void mark(int index) throws DukeException {
        checkIndex(index);
        tasks[index].mark();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index zero-based index
     * @throws DukeException if index is invalid
     */
    public void unmark(int index) throws DukeException {
        checkIndex(index);
        tasks[index].unmark();
    }

    /**
     * Returns tasks whose descriptions contain the keyword.
     *
     * @param keyword search term
     * @return list of matching tasks
     */
    public TaskList find(String keyword) {
        TaskList matches = new TaskList(size);
        for (int i = 0; i < size; i++) {
            if (tasks[i].matches(keyword)) {
                matches.addLoaded(tasks[i]);
            }
        }
        return matches;
    }

    /**
     * Returns true if the list is at capacity.
     *
     * @return whether the list is full
     */
    public boolean isFull() {
        return size >= tasks.length;
    }

    /**
     * Validates a zero-based index against current size.
     *
     * @param index index to check
     * @throws DukeException if index is invalid
     */
    private void checkIndex(int index) throws DukeException {
        if (index < 0 || index >= size) {
            throw new DukeException("task number must be between 1 and " + size);
        }
    }
}
