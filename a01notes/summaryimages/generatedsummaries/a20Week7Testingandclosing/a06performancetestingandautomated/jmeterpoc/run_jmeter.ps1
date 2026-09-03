# =========================================================================
# Run Apache JMeter in Non-GUI CLI Mode with HTML Dashboard Generation
# =========================================================================

$jmeterCandidates = @(
    "F:\software\apache-jmeter-5.6.3\bin\jmeter.bat",
    "$env:JMETER_HOME\bin\jmeter.bat",
    "jmeter"
)

$jmeterExec = $null
foreach ($cand in $jmeterCandidates) {
    if (Test-Path $cand -PathType Leaf) {
        $jmeterExec = $cand
        break
    }
}

if (-not $jmeterExec) {
    $cmd = Get-Command jmeter -ErrorAction SilentlyContinue
    if ($cmd) {
        $jmeterExec = "jmeter"
    }
}

if (-not $jmeterExec) {
    Write-Host "[!] Apache JMeter not found. Please set JMETER_HOME or verify F:\software\apache-jmeter-5.6.3" -ForegroundColor Red
    exit 1
}

Write-Host "[✓] Found JMeter: $jmeterExec" -ForegroundColor Green

# Clean up old reports
if (Test-Path "results.jtl") { Remove-Item "results.jtl" -Force }
if (Test-Path "results_summary.csv") { Remove-Item "results_summary.csv" -Force }
if (Test-Path "html_report") { Remove-Item "html_report" -Recurse -Force }

Write-Host "[*] Executing JMeter Non-GUI Load Test..." -ForegroundColor Cyan
& $jmeterExec -n -t sc_employees_load_test.jmx -l results.jtl -e -o html_report

Write-Host "[✓] JMeter Performance Test Finished!" -ForegroundColor Green
Write-Host "[✓] View interactive dashboard at: html_report\index.html" -ForegroundColor Yellow
