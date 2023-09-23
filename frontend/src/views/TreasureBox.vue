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

    <div class="side-headline mt-4 mb-3">
      <div class="d-flex justify-content-between align-items-end">
        <p class="mb-0">총 <span class="fw-bold">3</span>건</p>
        <div>
          <select class="form-select w-100" id="sortList" aria-label="Default select example">
            <option value="1">최신순</option>
            <option value="2">오래된순</option>
          </select>
        </div>
      </div>
    </div>

    <div>
      <table class="container-lg table table-hover mt-1">
        <thead class="table-light">
        <tr class="text-center">
          <th class="col-7 text-wrap">제목</th>
          <th class="col-2 text-wrap">작성자</th>
          <th class="col-2 text-wrap">작성일</th>
          <th class="col-1 text-wrap">조회수</th>
        </tr>
        </thead>
        <tbody>
        <tr class="text-center align-middle">
          <td class="col-7 text-wrap">
            <span>892827</span>
            <span>광활한 우주 속 티끌, 인간을 생각한다:베르크손의 「창조적 진화」 읽기</span>
            <span>사진</span>
            <span>[1]</span>
          </td>
          <td class="col-2 text-wrap">스위트자몽</td>
          <td class="col-2 text-wrap">2023.09.13</td>
          <td class="col-1 text-wrap">50</td>
        </tr>
        </tbody>
      </table>
    </div>


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