const exec = require('cordova/exec');

const FileInformations = {
  getFileInfos: function(fileUri, successCallback, errorCallback) {
        if (typeof fileUri !== 'string' || fileUri.trim() === '') {

            return errorCallback('Invalid file URI');
        
        }

        return exec(
        successCallback,
        errorCallback,
        'FileInformation',
        'getFileInfos',
        [fileUri]
        );
  }
};

module.exports = FileInformations;

