<template>
  <div class="container-fluid row d-lg-flex align-items-sm-start p-5 pt-4">

    <my-page-navbar/>
    <my-page-sidebar/>

    <div class="col-9 container-lg">
      <div class="container-lg mb-4 p-0">
        <h3 class="h3 mb-3 pb-2 fw-bold border-bottom border-2 border-dark">프로필</h3>
      </div>
      <form id="editform1" method="POST" class="form-control p-4 bg-secondary bg-opacity-10" action="/user_profile_update.do">
        <input type="hidden" id="b_pw" name="m_id" value="${dto.m_id}">
        <div class="fw-bold d-lg-flex align-items-center">
          <div class="w-50 me-5 a-email">
            <p class="m-0 mb-2 w-auto">이메일</p>
            <input class="form-control" type="email" id="email" name="m_email" value="${dto.m_email}">
          </div>
          <div class="w-50">
            <p class="m-0 mt-sm-2 mt-md-2 mt-lg-0 mb-2 w-auto">주소</p>
            <div class="d-flex btn-group">
              <input class="form-control" id="sample6_address" name="m_address" style="border-top-right-radius: 0px; border-bottom-right-radius: 0px;" type="text" value="${dto.m_address}" readonly>
              <input type="button" class="btn w-25 search-btn" value="주소 검색" onclick="sample6_execDaumPostcode()">
            </div>
            <div class="d-flex btn-group a-detail">
              <input class="form-control mt-2" id="address_detail" name="m_address_detail"
                     style="border-top-right-radius: 0px; border-bottom-right-radius: 0px;"
                     type="text" value="${dto.m_address_detail}" required onchange="checkFields()">
            </div>
          </div>
        </div>
        <div class="fw-bold d-lg-flex align-items-center mt-4">
          <div class="w-50 me-5">
            <p class="m-0 mb-2 w-auto">연락처</p>
            <input class="form-control" type="text" id="phone" name="m_tel" value="${dto.m_tel}">
          </div>
          <div class="w-50">
            <p class="m-0 mt-sm-2 mt-md-2 mt-lg-0 mb-2 w-auto">생년월일</p>
            <input class="form-control" type="date" value="${dto.m_birth}" readonly>
          </div>
        </div>
        <div class="mt-4 text-end">
          <button type="button" class="btn" id="editbtn2" style="width: 80px;" disabled onclick="showConfirmationModal2()">수정</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
      </form>
      <form id="editform2" method="POST" class="form-control p-4 bg-secondary bg-opacity-10 border-top-0" action="/user_profile_updatepw.do">
        <div class="fw-bold mt-4">
          <div class="w-100">
            <p class="m-0 mb-2 w-auto">비밀번호 변경</p>
            <input class="form-control mb-2" type="password" placeholder="현재 비밀번호" id="nowpw">
            <input class="form-control mb-2" type="password" placeholder="새 비밀번호" id="newpw">
            <input class="form-control mb-2" type="password" name="new_pw" placeholder="새 비밀번호 확인" id="newpw2">
            <p id="status-message"></p>
            <p id="status-message2"></p>
          </div>
        </div>
        <div class="mt-4 text-end">
          <button type="button" class="btn" id="editbtn" style="width: 80px;" disabled onclick="showConfirmationModal()">수정</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
      </form>
    </div>

    <!-- 모달 창 1 -->
    <div class="modal fade" id="confirmationModal" tabindex="-1"
         aria-labelledby="confirmationModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="confirmationModalLabel">수정 확인</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"
                    aria-label="Close"></button>
          </div>
          <div class="modal-body">수정을 진행하시겠습니까?</div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary btn-border"
                    data-bs-dismiss="modal">취소</button>
            <button type="submit" class="btn btn-primary btn-border"
                    onclick="submitForm()">확인</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 모달 창 2 -->
    <div class="modal fade" id="confirmationModal2" tabindex="-1"
         aria-labelledby="confirmationModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="confirmationModalLabel">수정 확인</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"
                    aria-label="Close"></button>
          </div>
          <div class="modal-body">수정을 진행하시겠습니까?</div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary btn-border"
                    data-bs-dismiss="modal">취소</button>
            <button type="submit" class="btn btn-primary btn-border"
                    onclick="submitForm2()">확인</button>
          </div>
        </div>
      </div>
    </div>

  </div>

</template>

<script>
import MyPageSidebar from '@/components/MypageSidebar.vue';
import MyPageNavbar from "@/components/MyPageNavbar.vue";

export default {
  name: 'MyProfile',
  components: {
    MyPageSidebar,
    MyPageNavbar
  }
}

</script>

<style >

</style>