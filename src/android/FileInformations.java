package com.app.fileinformations; // Replace with your actual package name

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;

public class FileInformations extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("getFileInfos".equals(action)) {
            String fileUri = args.optString(0);
            if (fileUri == null || fileUri.trim().isEmpty()) {
                callbackContext.error("Invalid file URI");
                return false;
            }
            getFileInfos(fileUri, callbackContext);
            return true;
        }
        return false;
    }

    private void getFileInfos(String fileUri, CallbackContext callbackContext) {
        try {
            Context context = cordova.getContext();
            Uri uri = Uri.parse(fileUri);
            File file = new File(uri.getPath());

            String fileName = file.getName();
            long fileSize = file.length();
            String mimeType = getMimeType(uri, context);
            long lastModified = file.lastModified();

            JSONObject fileInfo = new JSONObject();
            fileInfo.put("fileName", fileName);
            fileInfo.put("fileSize", fileSize);
            fileInfo.put("mimeType", mimeType);
            fileInfo.put("lastModified", lastModified);

            callbackContext.success(fileInfo);
        } catch (Exception e) {
            callbackContext.error("Error getting file information: " + e.getMessage());
        }
    }

    private String getMimeType(Uri uri, Context context) {
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
    }
}
