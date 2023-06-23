#!/bin/bash

BUILD_JAR=$(ls /home/ec2-user/action/build/libs/demo-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo ">>> 빌드 파일명: $JAR_NAME" >> /home/ec2-user/action/deploy.log

echo ">>> 빌드 파일 복사" >> /home/ec2-user/action/deploy.log
DEPLOY_PATH=/home/ec2-user/action/
cp $BUILD_JAR $DEPLOY_PATH

echo ">>> 현재 실행 중인 애플리케이션 PID 확인" >> /home/ec2-user/action/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo ">>> 현재 실행 중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/action/deploy.log
else
  echo ">>> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "$DEPLOY_JAR" >> /home/ec2-user/action/deploy.log
echo ">>> DEPLOY_JAR 배포" >> /home/ec2-user/action/deploy.log

# 로그 파일 생성 및 실행 로그 저장
LOG_FILE=/home/ec2-user/application.log
touch $LOG_FILE
nohup java -Dspring.profiles.active=dev -jar $DEPLOY_JAR >> $LOG_FILE 2>&1 &

# 로그 파일 위치 출력
echo ">>> 로그 파일 위치: $LOG_FILE" >> /home/ec2-user/action/deploy.log