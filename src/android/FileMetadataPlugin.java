package com.plugin.filemetadata;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class FileMetadataPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if ("getFileMetadata".equals(action)) {
            final String filePath = args.getString(0);
            
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject metadata = getMetadata(filePath);
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, metadata));
                    } catch (Exception e) {
                        callbackContext.error("Error getting file metadata: " + e.getMessage());
                    }
                }
            });
            
            return true;
        }
        return false;
    }

    private JSONObject getMetadata(String filePath) throws Exception {
        File file = new File(filePath);
        
        String mimeType = getMimeType(file);
        String fileName = file.getName();
        long fileSize = file.length();
        
        JSONObject metadata = new JSONObject();
        metadata.put("mimeType", mimeType);
        metadata.put("fileName", fileName);
        metadata.put("fileSize", fileSize);
        return metadata;
    }
    
    private String getMimeType(File file) {
        String mimeType = null;
        String extension = getFileExtension(file.getName());
        if (extension != null) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mimeType;
    }
    
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }
}
