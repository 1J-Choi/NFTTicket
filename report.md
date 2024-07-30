NFTTicket 개발 일지
=======
해당 문서에서는 날짜에 따라 NFTTicket의 개발 진행사항 및 발생하였던
오류&문제 사항들에 대한 로그를 정리하였습니다.

웹서비스 특성상 웹개발과 SC개발이 동시에 진행되어
SC개발 관련 일자는 빨간색으로 표기하였습니다.

추가로 프로젝트를 진행하면서 발생한 문제의 경우 파란색으로 표기하였습니다.

개발일지 목록
---

*2024-03-18*

- NFT를 활용한 웹사이트 아이디어 서칭 및 공유
  + NFT(Smart Contract)를 접목시킬 아이디어를 선정하는 과정으로 브레인스토밍 방식을 채택했다.

*<span style="color:red;">2024-03-18</span>*

- NFT관련 1차 보고서 작성
  + [NFT보고서_1](src/main/resources/img/NFT_report_1.pdf)

*2024-03-21*

- 웹서비스의 아이디어 선정, 프로토타입 제작
  + 여러가지 의견 가운데 NFTTicket으로 선정되었으며 프로토타입을 제작해 개발의 방향을 빠르게 잡고자 하였다.

*<span style="color:red;">2024-03-26</span>*

- NFT관련 2차 보고서 작성
  + [NFT보고서_2](src/main/resources/img/NFT_report_2.pdf)

*2024-03-27*
- 프로토타입 피드백 및 요구사항, API, DB구조 정의
  + 프로토타입을 통해 가감할 부분을 논의하고, 개발 이전에 요구사항, API, DB구조를 정의하여 구성원과의 효율을 높이고자 하였다.

*2024-04-01*
- DB구조 수정 및 기술스택 논의
  + DB 설계를 완료하고 웹서비스를 개발하는데 필요한 기술 스택을 논의하였다.

*<span style="color:red;">2024-04-2</span>*

- NFT관련 2차 보고서 작성
  + [NFT보고서_3](src/main/resources/img/NFT_report_3.pdf)

*2024-04-03*
- 깃허브 설정 및 DB연동
  + 깃허브 관리 방안을 논의하였다.
  + DB구조를 통해 설계한 DB를 연결하였다.

*2024-04-08*
- 로그인 기능 구현
  + 기본적인 웹서비스 형태를 제작하고 로그인 기능을 구현 및 테스트하였다.

*<span style="color:red;">2024-04-17</span>*
- 웹서비스에 필요한 Solidity 개발사항 논의
  + 프로젝트에서 여러 개의 행사에 각자 다른 NFT가 다르게 생성되어야 하며, Ticket limit가 있어야 한다는 결과를 도출하였다.

*<span style="color:red;">2024-04-18</span>*
- Solidity 제헌된 개수의 NFT 발행 기능 구현
  + hardhat을 활용하여 Smart Contract가 정해진 갯수만큼 NFT를 발행하고 이를 초과하면 기능이 작동되지 않는 코드를 구현하였다.

*2023-04-29*
- 로그인 기능, My page기능 기초 개발
  + 로그인 기능을 완료하고 My page기능의 기초를 개발하였다. 

*<span style="color:red;">2024-05-08</span>*
- Contract 주소, 지갑주소, Max_token값 받아오는 기능 구현
  + Contract 주소, 지갑주소, Max_token값을 입력받을 수 있는 형태로 구현하여 웹서비스에 맞게 활용할 수 있도록 하였다.

*2023-05-08*
- Event list 구현, Event Register 구현
  + Event Register를 통해 Owner가 행사를 등록하고, Event list에서 등록된 행사를 열람할 수 있게 구현하였다.

*<span style="color:red;">2024-05-10</span>*
- Pinata API 연동
  + 기존의 SC 프로젝트에 Pinata API를 연동하여 IPFS 관련 기능을 구현하고자 하였다.


*2023-05-20*
- Event Regist에 이미지를 등록하는 기능 구현
  + 기존의 Regist방식에 이미지를 넣는 기능을 추가하여 행사마다 고유의 이미지를 활용할 수 있도록 하였다.

*<span style="color:blue;">2024-05-27</span>*
- Solidity프로젝트와 MAVEN프로젝트 연동 문제
  + 두 프로젝트가 어느정도 구성된 상태에서 연동과 관련된 문제가 제기되었다. 
  + 이를 통해 프로젝트를 따로 두고 통신하는 방향과 Solidity 프로젝트를 java로 변환하는 방향으로 논의가 이루어졌다.

*2024-06-03*
- 권한별 Mypage 구현 시작
  + 3가지 권한과 관련된 기능 구현을 완료하여 각 권한별 Mypage에서 보여주어야 할 사항을 정리 및 구현하기 시작하였다.

*2024-06-10*
- Admin 권한의 Mypage 구현 완료
  + User가 신청한 Ticket 컨펌 후 노출, Owner가 Mint신청 목록 노출 기능을 구현하였다.

*<span style="color:blue;">2024-06-12</span>*
- CSRF오류 문제
  + 웹서비스를 테스트하면서 CSRF문제로 페이지간 이동에 불편함이 생기는 것을 인지하였다. 관련문제가 복잡하여 전체 웹개발을 완료한 이후 수정하는 것으로 계획하였다.

*2024-06-20*
- 웹서비스 개발 최종 보고서 작성
  + 웹서비스와 관련된 전체 내용을 보고서로 작성하였다.
   + [최종보고서 파일](src/main/resources/img/NFTTicket_finalreport.pdf)

*2024-07-01*
- Owner 권한의 Mypage 구현 완료, Ticket 개발
  + Owner가 본인이 등록한 Event list확인 가능하도록 구현하였다.
  + 한 User가 여러개의 Ticket을 Mint하는 것을 막도록 해야한다는 의견을 통해 개발이 진행되었다.

*2024-07-08*
- Ticket 개발 완료, 기타 UI 수정
  + Event Entity에서 safeMint완료한 인원을 속성으로 가지고 있게 하여 카운트하면서 제한하도록 하였다.
  + 인원수 표기 기능, 프로필 및 메타마스크 UI화면을 수정하였다.

*2024-07-12*
- Ticket 로직 수정
  + Ticket 인원수 증가 적용 부분을 Admin이 Ticket을 Confirm할 때로 수정하였다.

*2024-07-18*
- User Mypage 구현 완료 및 CSRF관련 문제 논의
  + User Mypage에서 승인전 신청 Ticket과 승인 후 Mint된 Ticket을 확인할 수 있도록 하였다.
  + CSRF문제의 겨우 시큐리티 자체의 난이도가 높아 하나 하나 CSRF를 삽입하여 하드코딩하거나 CSRF를 완전히 제거하는 방식 중 하나를 택하기로 하였다.

*2024-07-28*
- 웹개발 완료
  + 3가지 권한이 유기적으로 작동하는 것을 확인하면서 웹개발을 마무리지었다.