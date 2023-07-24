const exec = require('cordova/exec');

const FileInformations = {
  getFileInfo: function(fileUri, successCallback, errorCallback) {
        if (typeof fileUri !== 'string' || fileUri.trim() === '') {

            return errorCallback('Invalid file URI');
        
        }

        return exec(
        successCallback,
        errorCallback,
        'FileInformation',
        'getFileInfo',
        [fileUri]
        );
  }
};

module.exports = FileInformations;

