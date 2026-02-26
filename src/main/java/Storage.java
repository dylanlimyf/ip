import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public static class ReadResult {
        private final int count;
        private final int skipped;
        private final boolean truncated;

        public ReadResult(int count, int skipped, boolean truncated) {
            this.count = count;
            this.skipped = skipped;
            this.truncated = truncated;
        }

        public int getCount() {
            return count;
        }

        public int getSkipped() {
            return skipped;
        }

        public boolean isTruncated() {
            return truncated;
        }
    }

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
    public ReadResult readFile(Task[] tasks) throws IOException {
        doesFileExist();

        File f = new File(filePath);
        int count = 0;
        int skipped = 0;
        boolean truncated = false;

        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) continue;

                try {
                    if (count >= tasks.length) {
                        truncated = true;
                        continue;
                    }
                    tasks[count++] = parseLine(line);
                } catch (IllegalArgumentException corrupted) {
                    // skip if file is corrupted
                    skipped++;
                }
            }
        }

        return new ReadResult(count, skipped, truncated);
    }

    // Level 7: save whenever list changes
    public void writeFile(Task[] tasks, int taskCount) throws IOException {
        doesFileExist();

        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < taskCount; i++) {
                fw.write(tasks[i].toSaveString());
                fw.write(System.lineSeparator());
            }
        }
    }

    private Task parseLine(String line) {
        // expected: TYPE | 0/1 | taskName
        String[] p = line.split("\\s*\\|\\s*", 3);
        if (p.length != 3) {
            throw new IllegalArgumentException("Bad line");
        }

        String type = p[0];
        if (!p[1].equals("0") && !p[1].equals("1")) {
            throw new IllegalArgumentException("Bad done flag");
        }
        boolean done = p[1].equals("1");
        String taskName = p[2];

        Task t;
        switch (type) {
        case "T":
            t = new ToDo(taskName);
            break;
        case "D":
            t = new Deadlines(taskName);
            break;
        case "E":
            t = new Events(taskName);
            break;
        default:
            throw new IllegalArgumentException("Unknown type");
        }

        if (done) t.mark();
        return t;
    }
}
