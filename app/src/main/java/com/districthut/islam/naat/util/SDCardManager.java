package com.districthut.islam.naat.util;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

public class SDCardManager {
    public static boolean isSdCardReadable() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state) || "mounted_ro".equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isSdCardWritable() {
        boolean mExternalStorageAvailable;
        String state = Environment.getExternalStorageState();
        boolean mExternalStorageWritable;
        if ("mounted".equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWritable = true;
        } else if ("mounted_ro".equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
        } else {
            mExternalStorageAvailable = false;
            mExternalStorageWritable = false;
        }
        if (mExternalStorageAvailable && mExternalStorageWritable) {
            return true;
        }
        return false;
    }

    private static String getFormattedTitle(String title) {
        return title.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public static String getThumbnailPath(Context context, String photoId) {
        String folder = context.getExternalFilesDir(null).getAbsolutePath() + "/" + Constants.THUMBNAIL_FOLDER;
        makeDirs(folder);
        return folder + "/thm_" + photoId + ".thm";
    }

    public static String getmediastaus(String title) {
        String folder = Constants.EBOOK_PATH;
        makeDirs(folder);
        return folder + "/" + title;
    }

    public static String getmediastaus1(String title) {
        String folder = Constants.EPAMPHLETS_PATH;
        makeDirs(folder);
        return folder + "/" + title;
    }

    public static String getImagePath(String photoId) {
        String folder = Constants.IMAGE_DATA_PATH;
        makeDirs(folder);
        return folder + "/" + photoId + ".jpg";
    }

    public static String getPdfStatus(String title) {
        return getStatus(Constants.EPAMPHLETS_PATH + "/" + title + ".pdf");
    }

    public static String getPdfStatus1(String title) {
        return getStatus(Constants.EBOOK_PATH + "/" + title + ".pdf");
    }

    public static String getPdfStatus12(String title) {
        return getStatus(Constants.EPAMPHLETS_PATH + "/" + title);
    }

    public static String getPdfStatus11(String title) {
        return getStatus(Constants.EBOOK_PATH + "/" + title);
    }

    public static String getImageStatus(String photoId) {
        return getStatus(Constants.IMAGE_DATA_PATH + "/" + photoId + ".jpg");
    }

    public static ArrayList<String> getDownloadsList() {
        ArrayList<String> list = new ArrayList();
        String path = Constants.EPAMPHLETS_PATH;
        makeDirs(path);
        File folder = new File(path);
        String[] files = folder.list();
        if (files == null || files.length <= 0) {
            list.clear();
        } else {
            for (String fileName : files) {
                if (fileName.endsWith(".pdf")) {
                    list.add(fileName);
                } else {
                    deleteDirs(new String(folder + "/" + fileName));
                }
            }
        }
        return list;
    }

    public static String getStatus(String path) {
        if (!isSdCardWritable()) {
            return Constants.SD_CARD_NOT_AVAILABLE;
        }

        if (new File(path).isFile()) {
            return Constants.MEDIA_AVAILABLE;
        }

        if (new File(path).isDirectory()) {
            return Constants.MEDIA_AVAILABLE;
        }

        return Constants.MEDIA_NOT_AVAILABLE;
    }

    public static void CreateFolders(String path){
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public static void makeDirs(String path) {
        File folder = new File(path);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
    }

    public static boolean deleteDirs(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            String[] children = file.list();
            for (String str : children) {
                if (!deleteDirs(new String(file + "/" + str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}
