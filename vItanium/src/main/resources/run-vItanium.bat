@ECHO off
SETLOCAL

SET JAVA="%JAVA_HOME%/bin/java.exe"
ECHO Found JAVA at %JAVA%

SET JAVA_OPTS=

SET ENV_PROPERTIES=
rem === Uncomment next line to enable tracing of the vItanium emulator execution ===
rem SET ENV_PROPERTIES=-DvItanium.debug=true
rem === Uncomment next line to enable tracing of the user program execution ===
rem SET ENV_PROPERTIES=%ENV_PROPERTIES% -DvItanium.execution.debug
rem === Uncomment for user-defined logging configuration. if not set - default is used ===
rem SET ENV_PROPERTIES=%ENV_PROPERTIES% -Dlog4j.configuration=path/to/log4j/properties/file

SET VITANIUM_ARGS=--execute sample001.vit
rem === Uncomment next line to prevent long-running or faulty programs from running indefinitely ===
rem SET VITANIUM_ARGS=%VITANIUM_ARGS% --instructionlimit 100000

ECHO Executing %JAVA% %JAVA_OPTS% %ENV_PROPERTIES% -jar vItanium.jar %VITANIUM_ARGS% ...

%JAVA% %JAVA_OPTS% %ENV_PROPERTIES% -jar vItanium.jar %VITANIUM_ARGS%

ENDLOCAL

PAUSE