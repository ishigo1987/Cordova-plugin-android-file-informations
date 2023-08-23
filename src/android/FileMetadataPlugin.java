package com.plugin.filemetadata;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

public class FileMetadataPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getFileMetadata")) {
            String filePath = args.getString(0);
            JSONObject metadata = getFileMetadata(filePath);
            
            if (metadata != null) {
                callbackContext.success(metadata);
            } else {
                callbackContext.error("Error getting file metadata");
            }
            
            return true;
        }
        
        return false;
    }

    private JSONObject getFileMetadata(String filePath) {
        JSONObject metadata = new JSONObject();

        try {
            ContentResolver contentResolver = this.cordova.getActivity().getContentResolver();
            Uri fileUri = Uri.parse(filePath);

            // Get file name and size
            Cursor cursor = contentResolver.query(fileUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                
                if (nameIndex != -1) {
                    String fileName = cursor.getString(nameIndex);
                    metadata.put("name", fileName);
                }
                
                if (sizeIndex != -1) {
                    long fileSize = cursor.getLong(sizeIndex);
                    metadata.put("size", fileSize);
                }
                
                cursor.close();
            }
            
            // Get mime type
            String extension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString());
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            
            if (mimeType != null) {
                metadata.put("mime_type", mimeType);
            }
            
            return metadata;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

