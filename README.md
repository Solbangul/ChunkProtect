### Minecraft 1.20.1

## 명령어
 - /청크보호 생성 : 청크 보호 구역을 생성합니다. 사용한 유저의 땅이 되며 아무도 건들 수 없습니다.
 - /청크보호 제거 : 기존에 있던 청크 보호 구역을 삭제합니다.
 - /청크보호 화이트리스트 추가 <닉네임> : 자신의 청크를 건들 수 있는 사람을 추가합니다.
 - /청크보호 화이트리스트 삭제 <닉네임> : 자신의 청크를 건들 수 잇던 사람을 삭제합니다.
 - /청크보호 리로드 : 콘피그 파일을 리로드합니다.
 - /청크보호 관리자모드 : OP 명령어이며, 스위치처럼 껐다 켰다 할 수 있습니다. 보호 구역 상관없이 모두 건들 수 있습니다.
 - /청크보호 강제제거 : OP 명령어이며, 그 청크보호 구역을 강제로 제거합니다.

## 부가 설명
블록을 부술수도 설치하고 상호작용 조차 불가한 보호 구역을 만듭니다.
최대 청크 보호 구역 객수는 10개입니다.
최대 청크 보호 구역을 늘리는 방법은 config.yml에서 max-chunk-count의 숫자를 조정한 후
마인크래프트에서 "/청크보호 리로드"를 하시면 됩니다.
