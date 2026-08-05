@ECHO OFF
REM Fresher-friendly run script for Windows
REM 1) Put postgresql jar in lib folder
REM 2) Change password in DBConnection.java
REM 3) Run schema.sql in pgAdmin

SET LIB=lib\*
SET SRC=src

ECHO Compiling...
javac -cp "%LIB%" -d out %SRC%\bank\*.java
IF ERRORLEVEL 1 (
  ECHO Compile failed.
  EXIT /B 1
)

ECHO.
ECHO Running OOP Demo first...
java -cp "out;%LIB%" bank.OOPDemo

ECHO.
ECHO Running Menu App...
java -cp "out;%LIB%" bank.App
