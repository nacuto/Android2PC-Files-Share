@echo off
set file=%userprofile%\Desktop\openFtp.bat
if exist %file% (
		del %file% > nul
)
