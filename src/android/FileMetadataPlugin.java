package com.plugin.filemetadata;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.MediaMetadataRetriever;

public class FileMetadataPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("getFileMetadata".equals(action)) {
            String filePath = args.getString(0);
            try {
                JSONObject metadata = getMetadata(filePath);
                callbackContext.success(metadata);
            } catch (Exception e) {
                callbackContext.error("Error getting file metadata: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    private JSONObject getMetadata(String filePath) throws Exception {
        JSONObject metadataObj = new JSONObject();
        
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);

        String mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
        metadataObj.put("mimeType", mimeType);

        return metadataObj;
    }
}
