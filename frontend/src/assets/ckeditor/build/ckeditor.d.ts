/**
 * @license Copyright (c) 2014-2023, CKSource Holding sp. z o.o. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */
import { ClassicEditor } from '@ckeditor/ckeditor5-editor-classic';
import { Bold, Italic, Strikethrough, Underline } from '@ckeditor/ckeditor5-basic-styles';
import { Essentials } from '@ckeditor/ckeditor5-essentials';
import { FontBackgroundColor, FontColor, FontSize } from '@ckeditor/ckeditor5-font';
import { AutoImage, Image, ImageResize, ImageUpload } from '@ckeditor/ckeditor5-image';
import { List, ListProperties } from '@ckeditor/ckeditor5-list';
import { Paragraph } from '@ckeditor/ckeditor5-paragraph';
import { SelectAll } from '@ckeditor/ckeditor5-select-all';
import { SimpleUploadAdapter } from '@ckeditor/ckeditor5-upload';
declare class Editor extends ClassicEditor {
    static builtinPlugins: (typeof AutoImage | typeof Bold | typeof Essentials | typeof FontBackgroundColor | typeof FontColor | typeof FontSize | typeof Image | typeof ImageResize | typeof ImageUpload | typeof Italic | typeof List | typeof ListProperties | typeof Paragraph | typeof SelectAll | typeof SimpleUploadAdapter | typeof Strikethrough | typeof Underline)[];
    static defaultConfig: {
        toolbar: {
            items: string[];
        };
        language: string;
    };
}
export default Editor;
