# open jdk 17 버전의 환경을 구성
FROM eclipse-temurin:17-jdk-alpine

# 노출할 포트를 설정
EXPOSE 80

# build가 되는 시점에 JAR_FILE이라는 변수 명에 build/libs/*.jar 선언
# build/libs - gradle로 빌드했을 때 jar 파일이 생성되는 경로
ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} app.jar

# 운영 및 개발에서 사용되는 환경 설정을 분리
ENTRYPOINT ["nohup", "java", "-jar", "-Dspring.profiles.active=prod", "/app.jar", ">", "nohup-dev.out", "2>&1", "&"]