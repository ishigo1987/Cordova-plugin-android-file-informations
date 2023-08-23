package com.plugin.filemetadata;

import android.util.Log;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.webkit.MimeTypeMap; // Import MimeTypeMap

public class FileMetadataPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("getFileMetadata".equals(action)) {
            try {
                String filePath = args.getString(0);
                JSONObject metadata = getFileMetadata(filePath);
                callbackContext.success(metadata);
            } catch (Exception e) {
                callbackContext.error("Error getting file metadata: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    private JSONObject getFileMetadata(String filePath) throws JSONException {
        JSONObject metadata = new JSONObject();

        try {
            CordovaResourceApi resourceApi = webView.getResourceApi();
            Uri fileUri = resourceApi.remapUri(Uri.parse(filePath));
            String extension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString());
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

            CordovaResourceApi.OpenForReadResult result = resourceApi.openForRead(fileUri);
            long fileSize = result.length;

            metadata.put("filename", fileUri.getLastPathSegment());
            metadata.put("filesize", fileSize);
            metadata.put("mimetype", mimeType);

            Log.d("FileMetadataPlugin", "File Name: " + fileUri.getLastPathSegment());
            Log.d("FileMetadataPlugin", "File Size: " + fileSize);
            Log.d("FileMetadataPlugin", "MIME Type: " + mimeType);

        } catch (Exception e) {
            Log.e("FileMetadataPlugin", "Error retrieving metadata: " + e.getMessage());
        }

        return metadata;
    }
}
