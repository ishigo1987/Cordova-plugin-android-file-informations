# Cordova-plugin-android-file-informations

```javascript

const FileInformation = window.FileInformation;

const fileUri = 'file:///path/to/your/file.txt'; // Replace with the actual file URI.

FileInformation.getFileInfo(
  fileUri,
  function(fileInfo) {
    console.log('File information:', fileInfo);
    // fileInfo.fileName: Name of the file.
    // fileInfo.fileSize: Size of the file in bytes.
    // fileInfo.mimeType: MIME type of the file.
    // fileInfo.lastModified: Last modified timestamp of the file.
  },
  function(error) {
    console.error('Error getting file information:', error);
  }
);
