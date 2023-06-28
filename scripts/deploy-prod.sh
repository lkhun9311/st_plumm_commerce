echo ">>> action 디렉토리로 이동" >> /home/ec2-user/action/deploy.log
cd /home/ec2-user/action

BUILD_JAR=$(ls ./build/libs/demo-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo ">>> 빌드 파일명: $JAR_NAME" >> /home/ec2-user/action/deploy.log

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

echo ">>> DEPLOY_JAR 배포" >> /home/ec2-user/action/deploy.log

# 로그 파일 생성 및 실행 로그 저장
LOG_FILE=./application.log
touch $LOG_FILE
nohup java -Dspring.profiles.active=prod -jar $BUILD_JAR >> $LOG_FILE 2>&1 &

# 로그 파일 위치 출력
echo ">>> 로그 파일 위치: $LOG_FILE" >> /home/ec2-user/action/deploy.log

# AWS CLI 를 사용하여 인스턴스를 비활성화
AWS_REGION="ap-northeast-2"
ELB_NAME="alb.lkhun.store"

# 로드밸런서에서 모든 인스턴스의 상태 확인
INSTANCE_IDS=$(aws elb describe-instance-health --region $AWS_REGION --load-balancer-name $ELB_NAME --query 'InstanceStates[*].InstanceId' --output text)

# 각 인스턴스를 차례대로 비활성화
for INSTANCE_ID in $INSTANCE_IDS
do
  aws elb deregister-instances-from-load-balancer --region $AWS_REGION --load-balancer-name $ELB_NAME --instances $INSTANCE_ID
done
