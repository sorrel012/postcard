<template>

  <section class="container-md mt-5 mb-5 pt-5">
    <div class="d-flex justify-content-between">
      <div class="headline text-start">
        <h2 class="fw-bold">보물상자🎁</h2>
      </div>
      <div class="btn-group mb-3">
        <input type="text" class="form-control" placeholder="검색어를 입력해주세요.">
        <button type="button" class="btn btn-primary text-nowrap" @click="search()"><font-awesome-icon :icon="['fas', 'magnifying-glass']" beat style="color: #ffffff;" /></button>
      </div>
    </div>

    <div class="side-headline mb-1">
      <div class="d-flex justify-content-between">
        <p style="text-align:left" class="fs-5">총 <span class="fw-bold">3</span>건</p>
        <div class="me-3">
          <select class="form-select w-100" id="sortList" aria-label="Default select example">
            <option value="1">최신순</option>
            <option value="2">오래된순</option>
          </select>
        </div>

      </div>
    </div>

    <c:if test="${count != 0}">
      <div
          class="container-sm border border-1 border-dark border-opacity-25 rounded h-100">


        <c:forEach items="${list}" var="dto">

          <div class="container-fluid list p-2 border-bottom " onclick="location.href='/listendetail.do?seq=${dto.hr_seq}'">
            <div class="d-flex align-items-center mb-2">
              <img class="profile" src="/resources/img/로고.png">
              <div class="profile-detail">
                <p class="name">${dto.m_name}(${dto.m_id})</p>
                <p class="time">${dto.hr_regdate}</p>
              </div>
            </div>
            <div class="d-flex flex-column">
              <h4 class="fs-3 fw-bold title">
                ${dto.hr_subject}</h4>
            </div>
            <div class="d-flex align-items-center pb-2" >
              <i class="bi bi-chat-left-dots me-2"></i> <span
                style="margin-right: 20px;">${dto.cnt}</span>
              <i class="bi bi-eye me-2">
                <span class="fst-normal"style="margin-right: 20px;">${dto.hr_hit}</span>
              </i>



              <div class="bg-danger bg-opacity-25 px-3">
                ${dto.fd_name}
              </div>

            </div>

          </div>

        </c:forEach>
      </div>
    </c:if>


<!--    <c:if test="${count == 0}">-->

<!--      <hr>-->
<!--      <div>-->
<!--        <div class="text-center my-5 fs-2 fw-bold">검색 결과가 존재하지 않습니다.</div>-->
<!--        <div class="w-100 d-flex justify-content-center" >-->
<!--          <button type="button" class="btn btn-light btn-lg mb-5 text-dark" style="background-color : #ededed;" onclick="location.href='/listenlist.do'">전체 리스트 보기</button>-->
<!--        </div>-->


<!--      </div>-->
<!--    </c:if>-->

    <div class="d-flex justify-content-center mt-4">
      <div id="pagination-buttons">
        <button class="btn w-10" id="previous-button">&lt;&lt;</button>
      </div>
    </div>
    <button type="button" class="btn btn-primary" style="width: 100px; height: 40px;" onclick="location.href='/listenadd.do'">글쓰기</button>

  </section>

</template>

<script>

</script>

<style>

</style>