import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Handles loading and saving tasks to disk.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage with the given file path.
     *
     * @param filePath storage file path
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Result of loading tasks from storage.
     */
    public static class LoadResult {
        private final TaskList tasks;
        private final int skipped;
        private final boolean truncated;

        /**
         * Creates a load result.
         *
         * @param tasks loaded tasks
         * @param skipped number of skipped corrupted entries
         * @param truncated whether extra tasks were truncated
         */
        public LoadResult(TaskList tasks, int skipped, boolean truncated) {
            this.tasks = tasks;
            this.skipped = skipped;
            this.truncated = truncated;
        }

        /**
         * Returns the loaded task list.
         */
        public TaskList getTasks() {
            return tasks;
        }

        /**
         * Returns number of skipped entries.
         */
        public int getSkipped() {
            return skipped;
        }

        /**
         * Returns true if the load was truncated due to capacity.
         */
        public boolean isTruncated() {
            return truncated;
        }
    }

    /**
     * Ensures the storage file and parent directory exist.
     */
    public void doesFileExist() throws IOException {
        File f = new File(filePath);
        File parent = f.getParentFile();

        if (parent != null && !parent.exists()) {
            boolean createdDir = parent.mkdirs();
            if (!createdDir) {
                throw new IOException("Failed to create directory: " + parent);
            }
        }

        if (!f.exists()) {
            boolean createdFile = f.createNewFile();
            if (!createdFile) {
                throw new IOException("Failed to create file: " + f);
            }
        }
    }

    // Level 7: load on startup
    /**
     * Loads tasks from storage.
     *
     * @return load result with tasks and warnings
     * @throws IOException if file operations fail
     */
    public LoadResult load() throws IOException {
        doesFileExist();

        File f = new File(filePath);
        TaskList tasks = new TaskList();
        int skipped = 0;
        boolean truncated = false;

        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    if (tasks.isFull()) {
                        truncated = true;
                        continue;
                    }
                    Task task = parseLine(line);
                    tasks.addLoaded(task);
                } catch (IllegalArgumentException corrupted) {
                    // skip if file is corrupted
                    skipped++;
                }
            }
        }

        return new LoadResult(tasks, skipped, truncated);
    }

    // Level 7: save whenever list changes
    /**
     * Saves tasks to storage.
     *
     * @param tasks tasks to save
     * @throws IOException if file operations fail
     */
    public void save(TaskList tasks) throws IOException {
        doesFileExist();

        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i).toSaveString());
                fw.write(System.lineSeparator());
            }
        }
    }

    /**
     * Parses a saved task line into a Task.
     *
     * @param line raw saved line
     * @return parsed task
     */
    private Task parseLine(String line) {
        // expected: TYPE | 0/1 | description [| date]
        String[] p = line.split("\\s*\\|\\s*");
        if (p.length < 3) {
            throw new IllegalArgumentException("Bad line");
        }

        String type = p[0];
        if (!p[1].equals("0") && !p[1].equals("1")) {
            throw new IllegalArgumentException("Bad done flag");
        }
        boolean done = p[1].equals("1");
        String description = p[2];

        Task t;
        switch (type) {
        case "T":
            t = new ToDo(description);
            break;
        case "D":
            if (p.length < 4) {
                throw new IllegalArgumentException("Missing deadline date");
            }
            try {
                LocalDate by = LocalDate.parse(p[3]);
                t = new Deadlines(description, by);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Bad deadline date");
            }
            break;
        case "E":
            t = new Events(description);
            break;
        default:
            throw new IllegalArgumentException("Unknown type");
        }

        if (done) {
            t.mark();
        }
        return t;
    }
}
