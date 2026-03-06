#!/usr/bin/env bash

cd "$(dirname "$0")" || exit 1

# create bin directory if it doesn't exist
mkdir -p ../bin

# delete output from previous run
rm -f ACTUAL.TXT EXPECTED-UNIX.TXT

# isolate test data file to make test deterministic
DATA_DIR="data"
DATA_FILE="$DATA_DIR/list.txt"
BACKUP_FILE="$DATA_DIR/list.txt.bak.textuitest"

mkdir -p "$DATA_DIR"
rm -f "$BACKUP_FILE"
if [ -e "$DATA_FILE" ]; then
    mv "$DATA_FILE" "$BACKUP_FILE"
fi
: > "$DATA_FILE"

restore_data() {
    rm -f "$DATA_FILE"
    if [ -e "$BACKUP_FILE" ]; then
        mv "$BACKUP_FILE" "$DATA_FILE"
    fi
}
trap restore_data EXIT

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect output to ACTUAL.TXT
java -classpath ../bin Main < input.txt > ACTUAL.TXT

# normalize line endings before compare
cp EXPECTED.TXT EXPECTED-UNIX.TXT
if command -v dos2unix >/dev/null 2>&1
then
    dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT >/dev/null 2>&1
fi

# compare the output to the expected output
if diff ACTUAL.TXT EXPECTED-UNIX.TXT
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
