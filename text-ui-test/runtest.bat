@ECHO OFF
SETLOCAL

SET "SCRIPT_DIR=%~dp0"
PUSHD "%SCRIPT_DIR%"

REM create bin directory if it doesn't exist
IF NOT EXIST "..\bin" MKDIR "..\bin"

REM delete output from previous run
IF EXIST "ACTUAL.TXT" DEL /F /Q "ACTUAL.TXT"

REM isolate test data file to make test deterministic
SET "DATA_DIR=data"
SET "DATA_FILE=%DATA_DIR%\list.txt"
SET "BACKUP_FILE=%DATA_DIR%\list.txt.bak.textuitest"

IF NOT EXIST "%DATA_DIR%" MKDIR "%DATA_DIR%"
IF EXIST "%BACKUP_FILE%" DEL /F /Q "%BACKUP_FILE%"
IF EXIST "%DATA_FILE%" MOVE /Y "%DATA_FILE%" "%BACKUP_FILE%" >NUL
TYPE NUL > "%DATA_FILE%"

REM compile the code into the bin folder
JAVAC -cp "..\src\main\java" -Xlint:none -d "..\bin" "..\src\main\java\*.java"
IF ERRORLEVEL 1 GOTO :BUILD_FAIL

REM run the program, feed commands from input.txt and redirect output to ACTUAL.TXT
JAVA -classpath "..\bin" Main < "input.txt" > "ACTUAL.TXT"

REM compare the output to the expected output
FC "ACTUAL.TXT" "EXPECTED.TXT"
SET "TEST_EXIT=%ERRORLEVEL%"
GOTO :RESTORE

:BUILD_FAIL
ECHO ********** BUILD FAILURE **********
SET "TEST_EXIT=1"

:RESTORE
IF EXIST "%DATA_FILE%" DEL /F /Q "%DATA_FILE%"
IF EXIST "%BACKUP_FILE%" MOVE /Y "%BACKUP_FILE%" "%DATA_FILE%" >NUL
POPD
EXIT /B %TEST_EXIT%
