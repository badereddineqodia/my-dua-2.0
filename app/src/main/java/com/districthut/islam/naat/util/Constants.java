package com.districthut.islam.naat.util;

import android.os.Environment;

public class Constants {
    private static final String APP_FOLDER = "Epamphlets";
    private static final String APP_FOLDER2 = "Ebooks";
    public static final String QURAN_PATH = (Environment.getExternalStorageDirectory().getPath() + "/" + "DEEN" + "/" + "QURAN");
    public static final String BOOK_PATH = (Environment.getExternalStorageDirectory().getPath() + "/" + "DEEN" + "/" + "BOOK");
    public static final String NAATS_PATH = (Environment.getExternalStorageDirectory().getPath() + "/" + "My Dua" + "/" + "NAATS");
    public static final String TILAWAT_PATH = QURAN_PATH + "/" + "TILAWAT";
    public static final String TRANSLATIONS_PATH = QURAN_PATH + "/" + "TRANSLATIONS";
    public static final String QURANAUDIO_URL = "https://download.quranicaudio.com/quran/";
    public static final String TRANSLATIONS_URL = "http://tanzil.net/trans/";

    public static final String EBOOK_PATH = (Environment.getExternalStorageDirectory().getPath() + "/" + "Tinku" + "/" + APP_FOLDER2);
    public static final String EPAMPHLETS_PATH = (Environment.getExternalStorageDirectory().getPath() + "/" + "MadaniBookLibrary" + "/" + APP_FOLDER);
    public static final String EXTERNAL_DATA_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String IMAGE_DATA_PATH = (EXTERNAL_DATA_PATH + "/" + "Gallery");
    private static final String IMAGE_FOLDER = "Gallery";
    public static final String MEDIA_AVAILABLE = "media_available";
    private static final String MEDIA_FOLDER = "MadaniBookLibrary";
    public static final String MEDIA_NOT_AVAILABLE = "media_not_available";
    public static final String PDF_DATA_PATH = (EXTERNAL_DATA_PATH + "/" + "PDF");
    private static final String PDF_FOLDER = "PDF";
    public static final int RequestPermissionCode = 1;

    public static final String SD_CARD_NOT_AVAILABLE = "sd_card_not_available";
    public static final String STATUS_CANCEL = "status_cancel";
    public static final String STATUS_COMPLETE = "status_complete";
    public static String STATUS_CURRENT = "status_current";
    public static final String STATUS_EMPTY = "status_empty";
    public static final String STATUS_ERROR = "status_error";
    public static final String STATUS_FAILED = "status_failed";
    public static final String STATUS_NO_CONNECTION = "status_no_connection";
    public static final String STATUS_PAUSED = "status_paused";
    public static final String STATUS_PENDING = "status_pending";
    public static final String STATUS_READY = "status_ready";
    public static final String STATUS_RUNNING = "status_running";
    public static final String STATUS_SUCCESSFUL = "status_successful";
    public static final String TABLE_BOOK = "book";
    public static final String TABLE_BOOKMARK = "bookmark";
    public static final String TABLE_BOOK_CATEGORY = "book_category";
    public static final String TABLE_BOOK_IN_LANGUAGE = "book_in_languages";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_CATEGORY_NATIVE = "category_native";
    public static final String TABLE_DETAIL = "book_detail";
    public static final String TABLE_DOWNLOADS = "downloads";
    public static final String TABLE_FAVORITES = "favorites";
    public static final String TABLE_LANGUAGES = "languages";
    public static final String TABLE_PERSONNEL = "personnel";
    public static final String TABLE_PERSONNEL_NATIVE = "personnel_native";
    public static final String THUMBNAIL_FOLDER = "Thumbnail";
}
