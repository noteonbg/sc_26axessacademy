@echo off
setlocal enabledelayedexpansion

REM =========================================================================
REM Run Apache JMeter in Non-GUI CLI Mode with HTML Dashboard Generation
REM Target Plan: sc_employees_load_test.jmx (50 users load test)
REM =========================================================================

echo =======================================================================
echo          Standard Chartered Axess Academy - JMeter Load Test
echo =======================================================================

REM 1. Detect JMeter Binary
set JMETER_EXEC=
if exist "F:\software\apache-jmeter-5.6.3\bin\jmeter.bat" (
    set "JMETER_EXEC=F:\software\apache-jmeter-5.6.3\bin\jmeter.bat"
) else if defined JMETER_HOME (
    if exist "%JMETER_HOME%\bin\jmeter.bat" (
        set "JMETER_EXEC=%JMETER_HOME%\bin\jmeter.bat"
    )
)

if "%JMETER_EXEC%"=="" (
    where jmeter >nul 2>&1
    if %ERRORLEVEL% equ 0 (
        set "JMETER_EXEC=jmeter"
    )
)

if "%JMETER_EXEC%"=="" (
    echo.
    echo [!] Apache JMeter not found in standard paths or PATH environment variable.
    echo Please install or set JMETER_HOME.
    echo.
    pause
    exit /b 1
)

echo [✓] Using JMeter executable: "%JMETER_EXEC%"

REM 2. Clean previous test runs
if exist results.jtl del /f /q results.jtl
if exist results_summary.csv del /f /q results_summary.csv
if exist html_report rd /s /q html_report

REM 3. Check if server is running on port 3000
netstat -ano | findstr :3000 | findstr LISTENING >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo [!] WARNING: Spring Boot service does not appear to be running on port 3000.
    echo     Please make sure to start Spring Boot: mvn spring-boot:run
    echo     Proceeding with test in 3 seconds...
    timeout /t 3 /nobreak >nul
)

echo.
echo [*] Executing Apache JMeter in Non-GUI mode...
echo [*] Command: "%JMETER_EXEC%" -n -t sc_employees_load_test.jmx -l results.jtl -e -o html_report
echo.

call "%JMETER_EXEC%" -n -t sc_employees_load_test.jmx -l results.jtl -e -o html_report

echo.
echo =======================================================================
echo [✓] JMeter Performance Test Finished!
echo [✓] Results JTL Log: results.jtl
echo [✓] HTML Dashboard : html_report\index.html
echo =======================================================================
echo.
pause
