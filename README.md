# 💌 소중한 사람에게 마음을 전하는 롤링페이퍼 💌
### 학창 시절 작성하던 롤링페이퍼를 인터넷에서 간편하게 작성할 수 있는 웹 사이트 <br><br>
![image](https://github.com/sorrel012/postcard/assets/115568532/246d6632-c170-4e6c-b4a3-d7d80c141c0b)

<br>

## 📆 개발 기간
- 2023.08.08 - 2023.11.03

<br>

## 🖥 개발 환경
### ✔ 사용 언어
- JAVA(JDK 11), SQL, HTML, CSS, JavaScript
### ✔ 사용 기술
- SpringBoot(Maven), Vue2, Aws
### ✔ 사용 라이브러리
- CKEditor, Sweetalert, Bootstrap, Fontawesome, axios, vue-router, vuex
### ✔ IDE
- Webstorm, IntelliJ, DBeaver, ERDCloud
### ✔ OS
- Window

<br>

## 📁 ERD 
![rollingpaper📧](https://github.com/sorrel012/postcard/assets/115568532/2d0ff8e2-4b58-4870-96b5-43f472eee15a)

<br>
  
## 🖥 주요 기능
- 도화지 생성<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/2e353c6a-f71f-4881-91cb-c852d0748dcd' style='width: 500px; height: 500px'/>
  - 지인들이 쪽지를 남길 수 있는 도화지를 생성한다.
  - 제목, 배경 색상, 쪽지의 모양, 색상 등의 스타일은 양방향 바인딩하여 사용자가 실시간으로 확인하며 커스텀할 수 있다.
  - 도화지 생성 시 중복되지 않는 6자리 난수의 고유코드가 발급되며, 고유코드를 전달하여 자신에게 쪽지를 남기도록 할 수 있다.<br /><br />

- 마음 전하기<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/74309a0c-c549-42aa-8e82-0f2c924cd9d0' style='width: 500px; height: 200px'/>  
  <img src='https://github.com/sorrel012/postcard/assets/115568532/adcbf8e0-3af0-4997-9773-1d26f0a36289' style='width: 500px; height: 200px'/>
  - 전달받은 고유코드를 입력하여 일치할 경우 해당 도화지로 이동하여 익명으로 쪽지를 남길 수 있다.
  - 고유코드의 일치 여부는 SweetAlert2 라이브러리의 preConfirm을 활용하여 판단한다.<br /><br />

- 보물상자(게시글 목록)<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/73ec0934-9bd0-4a7b-95c5-638ea564224b' style='width: 500px; height: 350px'/>  
  - 선물과 관련된 정보를 공유하고 후기를 남길 수 있는 게시판 목록.
  - 검색어와 정렬값을 전달하여 조건에 따라 게시글 목록을 받아온다.
  - 게시글 목록은 기본 table로 구성되어 있다. <br /><br />
  
- 보물상자(게시글 상세)<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/fa25f6c5-16d0-409f-b273-81bc02b7f6a1' style='width: 500px; height: 450px'/>
  - 기본적인 CRUD와 댓글 기능을 구현하였다.
  - 글쓰기는 CKEditor5 라이브러리를 활용하였다.
  - 본문 이미지는 AWS의 S3에 업로드하여 데이터를 관리한다.
  - 본문은 HTML 형태로 입력되는데, XSS를 방지하기 위해 CKEditor와 HtmlPolicyBuilder를 사용하여 클라이언트와 서버 이중으로 허용되지 않은 태그를 필터링한다.<br /><br />
  
- 로그인<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/238afb84-0a01-48dd-a851-826ff8952e3c' style='width: 500px; height: 350px'/>
  - 기본적인 로그인과 카카오, 네이버, 구글 소셜 로그인 기능을 구현하였다.
  - 클라이언트에서 access token을 받아온 후 인가 코드를 발급받는다. 그 후 인가 코드를 서버로 넘겨 회원 정보를 받아오고, 회원 여부를 판별한다.
  - 회원인 경우 로그인을 완료하고, 회원이 아닌 경우 회원가입 페이지로 이동한다.<br /><br />  
  
- 회원가입<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/544d7d2f-648a-4722-ba7f-e88804d57e0d' style='width: 500px; height: 500px'/>
  - 주소는 Daum Postcode Service API를 활용하였다.
  - watch를 사용하여 비밀번호와 비밀번호 확인이 일치하는지 검증한다.
  - NaverCloud Platfom을 활용하여 입력한 연락처로 인증번호를 전송하여 본인인증 절차를 거친다.<br /><br />
  
- 회원정보 수정<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/586c3add-c901-46a3-af6d-45957193205f' style='width: 500px; height: 450px'/>
  - 회원정보를 수정하거나 회원탈퇴를 할 수 있다.
  - 연락처를 수정할 경우 다시 본인인증 절차를 거쳐야 한다.
  - 비밀번호를 변경할 때는 새 비밀번호와 새 비밀번호 확인이 일치하는지, 현재 비밀번호가 정확한지, 현재 비밀번호와 새 비밀번호가 다른지 검증 후 변경한다.<br /><br />  
  
- 생성한 도화지, 작성한 보물상자 게시글, 댓글 목록<br /><br />
  <img src='https://github.com/sorrel012/postcard/assets/115568532/e8b314d3-7d38-4522-bd91-cfc2b2f6c143' style='width: 500px; height: 230px'/>
  <img src='https://github.com/sorrel012/postcard/assets/115568532/09c68450-b26d-4f5f-a165-e9af0c51eaad' style='width: 500px; height: 180px'/>
  <img src='https://github.com/sorrel012/postcard/assets/115568532/3ca574c4-23e7-4ec1-8b1e-548809cc27e0' style='width: 500px; height: 180px'/>
  - 각 도화지 목록에서는 도화지의 고유 번호를 확인할 수 있으며, 도화지·게시글·댓글을 직접 삭제할 수 있다.
  - 사용자가 특정 도화지·게시글·댓글을 클릭하면, GET 방식을 통해 URL의 'seq' 파라미터로 해당 항목의 고유번호를 넘겨주면서 해당 페이지로 이동시킨다.<br /><br />
