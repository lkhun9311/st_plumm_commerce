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

#YML_FILE=$(ls /home/ec2-user/action/src/main/resources/application-dev.yml)
#YML_NAME=$(basename $YML_FILE)
DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "$DEPLOY_JAR" >> /home/ec2-user/action/deploy.log
echo ">>> DEPLOY_JAR 배포" >> /home/ec2-user/action/deploy.log

#nohup java -jar $DEPLOY_JAR --spring.config.name=${ YML_NAME } --spring.config.location=/home/ec2-user/action/src/main/resources/ >> /home/ec2-user/action/deploy.log 2>/home/ec2-user/action/deploy_err.log &
nohup java -Dspring.profiles.active=dev -jar demo-0.0.1-SNAPSHOT.jar >> /home/ec2-user/action/deploy.log 2>/home/ec2-user/action/deploy_err.log &