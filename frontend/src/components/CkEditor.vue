<template>
  <div id="editor">
    <!-- CKEditor가 이곳에 초기화됩니다. -->
  </div>
</template>

<script>
import CustomEditor from '@/assets/ckeditor/build/ckeditor';

export default {
  data() {
    return {
      editor: null,
      editorData: '<p>Hello, world!</p>'
    };
  },
  mounted() {
    CustomEditor.create(document.querySelector('#editor'))
        .then(editor => {
          this.editor = editor;

          // 에디터의 데이터를 초기화합니다.
          editor.setData(this.editorData);

          // 에디터의 변화를 감지하여, 컴포넌트의 데이터를 업데이트합니다.
          editor.model.document.on('change:data', () => {
            this.editorData = editor.getData();
          });
        })
        .catch(error => console.error(error));
  },
  beforeDestroy() {
    // 컴포넌트가 소멸되기 전에 에디터 인스턴스를 소멸시킵니다.
    this.editor.destroy();
  }
}
</script>