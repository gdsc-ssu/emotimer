<h1 align="center">Emotimer ⏰</h1>

<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
</p>

> #### 웹소켓을 이용한 다중기기 동시 타이머

&nbsp;

## 기술 스택
<p>
  <img src="https://img.shields.io/badge/-SpringBoot-blue"/>&nbsp
  <img src="https://img.shields.io/badge/-JPA-red"/>&nbsp
  <img src="https://img.shields.io/badge/-MySQL-yellow"/>&nbsp
  <img src="https://img.shields.io/badge/-Stomp-orange"/>&nbsp
  <img src="https://img.shields.io/badge/-EC2-orange"/>&nbsp
  <img src="https://img.shields.io/badge/-RDS-orange"/>&nbsp
  <img src="https://img.shields.io/badge/-SpringSecurity-green"/>&nbsp
  <img src="https://img.shields.io/badge/-JWT-blue"/>&nbsp
  <img src="https://img.shields.io/badge/-Querydsl-violet"/>&nbsp
</p>

## 사용 화면
<img src="https://user-images.githubusercontent.com/71378447/212466970-315d93e9-f75e-4ed7-b57b-7faf9f7cc852.png" width="1000" height="300" />

 
## Domain

- User
  - 속성
    - Email
    - Name
  - 행위
    - SignIn
    - SignUp
- Timer
  - 속성
    - Status : Ready / Running / Paused
    - StartedAt : DateTime
    - RemainedSeconds : Long
    - TotalSeconds : Long
    - Emoji : EmojiId
  - 행위
    - Start
    - Pause
    - Reset
- Timer History
  - 속성
    - RecordedAt : DateTime
    - TotalSeconds : Long
    - RemainedSeconds : Long
    - Status : Failed / Succeeded
    - Emoji : EmojiId
    
- EMOJI(enum)


## Usage

```sh
gradlew clean build
```


## Contribute guide

1. 모든 작업은 Notion에 있는 To-Do 이슈 태스크를 구현하는 형태로 진행합니다.
2. 모든 작업은 Github Flow를 따라 진행합니다.
3. 작업할 내용에 대한 branch를 main에서 분기하고 작업 후 PR를 생성합니다.
4. 논의가 필요하거나 리뷰가 필요한 내용에 대해서는 PR에서 의사소통합니다.
5. 리뷰가 늦어지거나 별도의 리뷰가 필요가 없다면 Merge 합니다.
  
  
## Code Contributors

👤 **jinho**

* Github: [@ohjinhokor](https://github.com/ohjinhokor)

👤 **jinseoung**

* Github: [@enif-lee](https://github.com/enif-lee)


👤 **seoyeon**

* Github: [@yeonnex](https://github.com/yeonnex)

## Show your support

Give a ⭐️ if this project helped you!

***
_This README was generated with ❤️ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_
~ 
