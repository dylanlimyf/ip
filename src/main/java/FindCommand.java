/**
 * Finds tasks containing a keyword.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Creates a find command.
     *
     * @param keyword keyword to match
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showFindResults(tasks, keyword);
    }
}
