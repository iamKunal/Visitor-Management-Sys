@echo off
echo Setting Up environment...
PATH %PATH%;%cd%
PATH %PATH%;%cd%\Python36-32
PATH %PATH%;%cd%\Java\bin
echo Starting MySQL
"C:\Windows\SysWOW64\WindowsPowerShell\v1.0\powershell.exe" -windowstyle hidden -nologo -noprofile -executionpolicy bypass -command "start-process -verb 'runas' -filepath start_sql.bat
echo Starting the System...
start Java\bin\javaw -jar VisitorManagement.jar 0