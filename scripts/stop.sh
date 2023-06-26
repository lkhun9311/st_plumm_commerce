# demo-0.0.1-SNAPSHOT.jar 프로세스 ID(PID) 검색
PID=$(ps -ef | grep 'demo-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{ print $2 }')
sudo kill -9 $PID