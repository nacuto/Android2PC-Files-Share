@echo off

set ip=%1
set port=%2

REM set ip=192.168.43.206
REM set port=8090

set file=%userprofile%\Desktop\openFtp.bat

:check
REM 排除手机没连上Wifi的情况（使用ping能节省脚本运行时间）
ping %ip% -n 1 | findstr /i "TTL" > nul
if ERRORLEVEL 1 (
	echo %time:~0,2%:%time:~3,2%:%time:~6,2%: ping fail
	if exist %file% (
		echo %time:~0,2%:%time:~3,2%:%time:~6,2%: del file
		del %file% > nul
	)
	goto check
)
REM 判断Ftp是否开启
echo open %ip% %port% >ftpCMD
echo bye >> ftpCMD
echo bye >> ftpCMD
echo bye >> ftpCMD
ftp -s:ftpCMD >ftpRes 2>&1
findstr "220" ftpRes >nul

if ERRORLEVEL 1 (
	echo %time:~0,2%:%time:~3,2%:%time:~6,2%: Ftp connect fail
	if exist %file% (
		echo %time:~0,2%:%time:~3,2%:%time:~6,2%: del file
		del %file% > nul
	)
)else (
	echo %time:~0,2%:%time:~3,2%:%time:~6,2%: connect success
	if not exist %file% (
		echo %time:~0,2%:%time:~3,2%:%time:~6,2%: create file
		call createFtp.bat %ip% %port% %3 %4
	)
)
del ftpCMD
del ftpRes

goto check
pause > nul
