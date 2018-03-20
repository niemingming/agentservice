rem start mysql...
for /f "skip=3 tokens=4" %%i in ('sc query MySQL') do set "zt=%%i" &goto :next
:next
if /i "%zt%"=="RUNNING" (
    rem 已经启动
) else (
   net start MySQL
)
rem start webview
start cmd /c java -jar E:\workspace\agentservices\webview\target\webview-1.0-SNAPSHOT.jar
rem start author
start cmd /c java -jar E:\workspace\agentservices\author\target\author-1.0-SNAPSHOT.jar
rem start search
start cmd /c java -jar E:\workspace\agentservices\search\target\search-1.0-SNAPSHOT.jar