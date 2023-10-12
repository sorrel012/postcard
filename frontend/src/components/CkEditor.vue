<template>
  <div>
    <div class="editor-container">
      <div id="editor">{{ initialData }}</div>
    </div>
  </div>
</template>

<script>
import CustomEditor from '@/assets/ckeditor/build/ckeditor.js';
import axios from 'axios';

class MyUploadAdapter {
  constructor(loader, uploadUrl, component) {
    this.loader = loader;
    this.uploadUrl = uploadUrl;
    this.component = component;
  }

  upload() {
    return new Promise(async (resolve, reject) => {
      const data = new FormData();
      const file = await this.loader.file;
      data.append('upload', file);

      axios.post(this.uploadUrl, data)
          .then(response => {
            this.component.images.push(response.data.url);
            resolve({
              default: response.data.url
            });
          })
          .catch(error => {
            console.error('File upload error:', error);
            reject(error);
          });
    });
  }
}

export default {
  name: 'CKEditor',
  data() {
    return {
      editor: null,
      initialData: '',
      images: [],
    };
  },
  mounted() {
    const component = this;
    const uploadUrl = this.$store.state.url + 'treasure/image';

    CustomEditor.create(document.querySelector('#editor'), {})
        .then(editor => {
          component.editor = editor;

          editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            return new MyUploadAdapter(loader, uploadUrl, component);
          };

          editor.setData(component.initialData);

          editor.model.document.on('change:data', () => {
            component.initialData = editor.getData();
            component.$emit('write', component.initialData);
            component.$emit('images', component.images);
          });

        })
        .catch(error => {
          console.error('There was a problem initializing the editor.', error);
        });
  },
  beforeDestroy() {
    if (this.editor) {
      this.editor.destroy();
    }
  },
}
</script>

<style>
.ck-editor__editable_inline {
  height: 60vh;
  overflow-y: auto;
}
</style>
