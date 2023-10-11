<template>
  <div>
    <div class="editor-container">
      <div id="editor">{{ initialData }}</div>
    </div>
  </div>
</template>

<script>
import CustomEditor from '@/assets/ckeditor/build/ckeditor.js';

export default {
  name:'CKEditor',
  data() {
    return {
      editor: null,
      initialData: '',
    };
  },
  mounted() {
    CustomEditor.create(document.querySelector('#editor'), {
      simpleUpload: {
        uploadUrl: '/treasure-image',
      },
    })
        .then(editor => {
          this.editor = editor;

          // 초기 데이터 설정
          editor.setData(this.initialData);

          // 데이터 변경을 감지하여 변수를 업데이트합니다.
          editor.model.document.on('change:data', () => {
            this.initialData = editor.getData();

            //상위 컴포넌트로 이벤트 발생
            this.$emit('write', this.initialData);

          });
        })
        .catch(error => {
          console.error('There was a problem initializing the editor.', error)
        });
  },
  beforeDestroy() {
    // 에디터 인스턴스 제거
    if (this.editor) {
      this.editor.destroy();
    }
  },
}
</script>
