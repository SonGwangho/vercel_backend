## 버전 정보
- java 17.0.18
- spring 3.5.11

## 경로 및 명명 규칙
### 컨트롤러 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/{분류}/controller
- 파일은 {분류}Controller.kt로 만든다.
- ex) TestController.kt
### 서비스 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/{분류}/service
- 파일은 {분류}Service.kt로 만든다.
- ex) TestService.kt
### 유틸 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/utils
### 설정 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/config

## endpoint 규칙
### RESTAPI
- /api/{분류 폴더명} 으로 시작
- 요청은 /api/{분류 폴더명}/{method}{function}으로 제작
