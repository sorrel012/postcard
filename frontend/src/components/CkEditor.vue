<template>
  <ckeditor :editor="editor"
            v-model="editorData"
            :config="editorConfig"
  />
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'
import CKEditor from '@ckeditor/ckeditor5-vue'

export default {
  name: 'CKEditor',
  components: {
    ckeditor: CKEditor.component
  },
  data: () => ({
    editor: ClassicEditor,
    editorData: '<p>Content of the editor.</p>',
    editorConfig: {
      // The configuration of the editor.
      height: '500px',
      language: 'ko'
    }
  })
}
</script>
