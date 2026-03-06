# Duke User Guide

Slave is a task chatbot that helps you track to-dos, deadlines, and events from a single command line.

## Quick start

1. Run the `Main` class in your IDE.
2. Type commands in the prompt and press Enter.
3. Use `bye` to exit.

## Command summary

| Action | Command format | Example |
| --- | --- | --- |
| List tasks | `list` | `list` |
| Add todo | `todo <description>` | `todo read book` |
| Add deadline | `deadline <description> /by <yyyy-mm-dd>` | `deadline return book /by 2026-03-10` |
| Add event | `event <description> /from <start> /to <end>` | `event project meeting /from 2pm /to 4pm` |
| Mark done | `mark <task number>` | `mark 2` |
| Unmark | `unmark <task number>` | `unmark 2` |
| Delete | `delete <task number>` | `delete 3` |
| Find | `find <keyword>` | `find book` |
| Exit | `bye` | `bye` |

## Features

### List tasks

Shows all saved tasks with their numbers.

Format: `list`

### Add a todo

Adds a simple task without a date.

Format: `todo <description>`

Example:
```
todo read book
```

### Add a deadline

Adds a task that must be done by a specific date.

Format: `deadline <description> /by <yyyy-mm-dd>`

Example:
```
deadline return book /by 2026-03-10
```

Notes:
- The date must be in `yyyy-mm-dd`.
- The date cannot be before today.

### Add an event

Adds an event with a start and end time. The time fields are stored as text, so you can use any wording.

Format: `event <description> /from <start> /to <end>`

Example:
```
event project meeting /from 2pm /to 4pm
```

### Mark a task as done

Format: `mark <task number>`

Example:
```
mark 1
```

### Unmark a task

Format: `unmark <task number>`

Example:
```
unmark 1
```

### Delete a task

Format: `delete <task number>`

Example:
```
delete 1
```

### Find tasks by keyword

Finds tasks whose descriptions contain the keyword.

Format: `find <keyword>`

Example:
```
find book
```

Notes:
- Matching is case-sensitive.

### Exit

Format: `bye`

## Data storage

Duke saves tasks automatically after add, delete, mark, and unmark operations. Tasks are loaded on startup from:

`data/list.txt`

## FAQ

**Q: What task number should I use?**  
A: Use the number shown in the `list` output. Numbers start at 1.

