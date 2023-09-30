<template>
  <div id="app">
    <ckeditor
        :editor="editor"
        v-model="editorData"
        @ready="onReady"
        :config="editorConfig"
    ></ckeditor>
    <button @click="getData">get Data</button>
  </div>
</template>

<script>
import DecoupledEditor from "@ckeditor/ckeditor5-editor-decoupled/src/decouplededitor";
import EssentialsPlugin from "@ckeditor/ckeditor5-essentials/src/essentials";
import BoldPlugin from "@ckeditor/ckeditor5-basic-styles/src/bold";
import ItalicPlugin from "@ckeditor/ckeditor5-basic-styles/src/italic";
import LinkPlugin from "@ckeditor/ckeditor5-link/src/link";
import ParagraphPlugin from "@ckeditor/ckeditor5-paragraph/src/paragraph";
import SimpleUploadAdapter from "@ckeditor/ckeditor5-upload/src/adapters/simpleuploadadapter";
import Base64UploadAdapter from "@ckeditor/ckeditor5-upload/src/adapters/base64uploadadapter";

import Image from "@ckeditor/ckeditor5-image/src/image";
import ImageInsert from "@ckeditor/ckeditor5-image/src/imageinsert";
import ImageToolbar from "@ckeditor/ckeditor5-image/src/imagetoolbar";
import ImageCaption from "@ckeditor/ckeditor5-image/src/imagecaption";
import ImageStyle from "@ckeditor/ckeditor5-image/src/imagestyle";
import ImageResize from "@ckeditor/ckeditor5-image/src/imageresize";
import LinkImage from "@ckeditor/ckeditor5-link/src/linkimage";

export default {
  name: "app",
  data() {
    return {
      editor: DecoupledEditor,
      editorData: "<p>Content of the editor.</p>",
      editorConfig: {
        plugins: [
          EssentialsPlugin,
          BoldPlugin,
          ItalicPlugin,
          LinkPlugin,
          ParagraphPlugin,
          Image,
          ImageToolbar,
          ImageCaption,
          ImageStyle,
          ImageResize,
          LinkImage,
          ImageInsert,
          // SimpleUploadAdapter
          Base64UploadAdapter,
        ],
        image: {
          toolbar: [
            "imageStyle:inline",
            "imageStyle:wrapText",
            "imageStyle:breakText",
            "|",
            "toggleImageCaption",
            "imageTextAlternative",
          ],
        },
        toolbar: {
          items: ["bold", "italic", "link", "undo", "redo", "insertImage"],
        },
        simpleUpload: {
          // The URL that the images are uploaded to.
          uploadUrl: "http://example.com",

          // Enable the XMLHttpRequest.withCredentials property.
          withCredentials: true,

          // Headers sent along with the XMLHttpRequest to the upload server.
          headers: {
            "X-CSRF-TOKEN": "CSRF-Token",
            Authorization: "Bearer <JSON Web Token>",
          },
        },
      },
    };
  },
  methods: {
    onReady(editor) {
      editor.ui
          .getEditableElement()
          .parentElement.insertBefore(
          editor.ui.view.toolbar.element,
          editor.ui.getEditableElement()
      );
    },
    getData() {
      console.log(this.editorData);
    },
  },
};
</script>