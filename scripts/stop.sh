# demo-0.0.1-SNAPSHOT.jar 프로세스 ID(PID) 검색
PID=$(ps -ef | grep 'demo-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{ print $2 }')

if [ -z "$PID" ]; then
    echo "demo-0.0.1-SNAPSHOT.jar가 실행되고 있지 않습니다."
else
    echo "demo-0.0.1-SNAPSHOT.jar가 실행 중인 PID: $PID. 이 프로세스를 종료합니다."
    sudo kill -9 $PID
fi