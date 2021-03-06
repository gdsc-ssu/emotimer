# Project. Timer

- 프로젝트 개요 : 웹소켓을 이용한 다중기기 동시 타이머

## Commmon
 
### Domain dictionary 

- User : 하나의 사용자를 의미합니다. 소셜 로그인을 통해 가입/로그인한 
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

### Contribute guide

1. 모든 작업은 Notion에 있는 To-Do 이슈 태스크를 구현하는 형태로 진행합니다.
2. 모든 작업은 Github Flow를 따라 진행합니다.
  1. 작업할 내용에 대한 branch를 main에서 분기하고 작업 후 PR를 생성합니다.
  2. 논의가 필요하거나 리뷰가 필요한 내용에 대해서는 PR에서 의사소통합니다.
  3. 리뷰가 늦어지거나 별도의 리뷰가 필요가 없다면 Merge 합니다. 
  4. 가급적 Squash Merge를 사용하나, 중첩된 브랜치가 있거나, 단일 커밋 브랜치의 경우에는 Rebase merge / Fast-forward merge를 사용합니다. 

