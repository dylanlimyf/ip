public class TaskList {

    private static final int DEFAULT_CAPACITY = 100;

    private final Task[] tasks;
    private int size;

    public TaskList() {
        this(DEFAULT_CAPACITY);
    }

    public TaskList(int capacity) {
        tasks = new Task[capacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    public Task get(int index) {
        return tasks[index];
    }

    public void add(Task task) throws DukeException {
        if (isFull()) {
            throw new DukeException("task list full, delete something first");
        }
        tasks[size++] = task;
    }

    public boolean addLoaded(Task task) {
        if (isFull()) {
            return false;
        }
        tasks[size++] = task;
        return true;
    }

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

    public void mark(int index) throws DukeException {
        checkIndex(index);
        tasks[index].mark();
    }

    public void unmark(int index) throws DukeException {
        checkIndex(index);
        tasks[index].unmark();
    }

    public TaskList find(String keyword) {
        TaskList matches = new TaskList(size);
        for (int i = 0; i < size; i++) {
            if (tasks[i].matches(keyword)) {
                matches.addLoaded(tasks[i]);
            }
        }
        return matches;
    }

    public boolean isFull() {
        return size >= tasks.length;
    }

    private void checkIndex(int index) throws DukeException {
        if (index < 0 || index >= size) {
            throw new DukeException("task number must be between 1 and " + size);
        }
    }
}
