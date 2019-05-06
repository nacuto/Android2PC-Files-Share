@echo off

set file=%userprofile%\Desktop\openFtp.bat

echo %%1 start mshta vbscript:createobject("wscript.shell").run("""%%~0"" ::",0)(window.close)^&^&exit >%file%
echo explorer ftp://%3:%4@%1:%2 >>%file%



REM pause >nul