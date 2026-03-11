# 개발 규칙
* 반드시 아래의 내용을 준수한다.
* 이 파일은 임의로 수정하지 않는다.

## 버전 정보
- java 17.0.18
- spring 3.5.11

## 경로 및 명명 규칙
### 패키지
- com.vercel.gwangho로 한다.
### 컨트롤러 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/{분류}/controller
- 파일은 {분류}Controller.kt로 만든다.
- ex) TestController.kt
### 서비스 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/{분류}/service
- 파일은 {분류}Service.kt로 만든다.
- ex) TestService.kt
### DTO 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/{분류}/dto
- 컨트롤러가 받는 dto는 {분류}Request로 data class를 만든다.
- 컨트롤러가 반환하는 dto는 {분류}Response로 data class를 만든다.
### 유틸 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/utils
### 설정 경로 및 명명 규칙
- src/main/kotlin/app/vercel/gwangho/config

## endpoint 규칙
### RESTAPI
- /{분류 폴더명} 으로 시작
- 요청은 /{분류 폴더명}/{method}{function}으로 제작
- /api부분은 vercel 앱에서 rewrite를 해서 무조건 들어가므로 RESTAPI 명명 규칙에서 제외

## 데이터 저장
- 데이터 저장 경로는 resources/static에 저장한다.
- 모든 데이터는 json 형태로 저장하고 받아온다.
- 데이터는 반드시 utils에 정의된 jsonSave, jsonLoad 함수를 사용해서 저장 및 로드한다.