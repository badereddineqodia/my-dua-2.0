package com.districthut.islam.naat.util;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.File;

@TargetApi(11)
public class MediaDownloadManager {
    private static DownloadManager downloadManager;

    public static void init(Context context) {
        downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
    }

    public static long startDownload(String title, String url, String path) {
        Uri source = Uri.parse(url);
        Uri destination = Uri.fromFile(new File(path));
        Request request = new Request(source);
        request.setAllowedNetworkTypes(3);
        request.setAllowedOverRoaming(false);
        request.setVisibleInDownloadsUi(true);
        request.setTitle(title);
        request.setDestinationUri(destination);
        request.setNotificationVisibility(1);
        return downloadManager.enqueue(request);
    }

    public static void displayAllDownloads() {
    }

    public static String checkStatus(long downloadReference) {
        String status = Constants.STATUS_EMPTY;
        Query myDownloadQuery = new Query();
        myDownloadQuery.setFilterById(new long[]{downloadReference});
        Cursor cursor = downloadManager.query(myDownloadQuery);
        if (cursor != null && cursor.moveToFirst()) {
            status = checkStatus(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
        return status;
    }

    public static void cancelDownload(long downloadReference) {
        downloadManager.remove(new long[]{downloadReference});
    }

    private static String checkStatus(Cursor cursor) {
        int status = cursor.getInt(cursor.getColumnIndex("status"));
        int reason = cursor.getInt(cursor.getColumnIndex("reason"));
        String filename = cursor.getString(cursor.getColumnIndex("local_uri"));
        String statusText = "";
        String reasonText = "";
        switch (status) {
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch (reason) {
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        return reasonText = "ERROR_CANNOT_RESUME";
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        return reasonText = "ERROR_DEVICE_NOT_FOUND";
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        return reasonText = "ERROR_FILE_ALREADY_EXISTS";
                    case DownloadManager.ERROR_FILE_ERROR:
                        return reasonText = "ERROR_FILE_ERROR";
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        return reasonText = "ERROR_HTTP_DATA_ERROR";
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        return reasonText = "ERROR_INSUFFICIENT_SPACE";
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        return reasonText = "ERROR_TOO_MANY_REDIRECTS";
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        return reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                    case DownloadManager.ERROR_UNKNOWN:
                        return reasonText = "ERROR_UNKNOWN";
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch (reason) {
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        return reasonText = "PAUSED_QUEUED_FOR_WIFI";
                    case DownloadManager.PAUSED_UNKNOWN:
                        return  reasonText = "PAUSED_UNKNOWN";
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        return reasonText = "PAUSED_WAITING_FOR_NETWORK";
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        return  reasonText = "PAUSED_WAITING_TO_RETRY";
                }
                break;
            case DownloadManager.STATUS_PENDING:
                return statusText = "STATUS_PENDING";
            case DownloadManager.STATUS_RUNNING:
                return statusText = "STATUS_RUNNING";
            case DownloadManager.STATUS_SUCCESSFUL:
                return  statusText = "STATUS_SUCCESSFUL";
            //return reasonText = "Filename:\n" + filename;
        }
        return statusText;
    }
}

