package com.time.memory.util;

import java.io.File;
import java.text.DecimalFormat;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

/** * 本应用数据清除管理器 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class DataCleanManager {

	/** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
	public static void cleanInternalCache(Context context) {
		delAllFile(context.getCacheDir().getAbsolutePath().toString());
	}

	/** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/databases"));
	}

	/**
	 * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
	 * context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
	}

	/** * 按名字清除本应用数据库 * * @param context * @param dbName */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
	 * context
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/**
	 * 清除自定义路径下的文件,使用需小心，请不要误删。而且只支持目录下的文件删除
	 * 
	 * @param filePath
	 */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/** * 清除本应用所有的数据 * * @param context * @param filepath */
	public static void cleanApplicationData(Context context) {
		cleanInternalCache(context);
		cleanDatabases(context);
		cleanFiles(context);
		cleanWebView(context);
		// cleanExternalCache(context);
		// cleanSharedPreference(context);
		/*
		 * for (String filePath : filepath) { cleanCustomCache(filePath); }
		 */
	}

	private static void cleanWebView(Context context) {
		File dir = context.getDir("webview", context.MODE_WORLD_READABLE);
		delAllFile(dir.getAbsolutePath().toString());
	}

	/** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}

	// 清楚webView里面的缓存
	private static int clearCacheFolder(File dir, long numDays) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {

				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, numDays);
					}
					if (child.lastModified() < numDays) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	/**
	 * @Description:获取缓存大小
	 * @param
	 * @return
	 */
	public static String getCacheSize() {
		// 缓存地址
		String cache = "";
		double cacheSize = 0;
		double folderSize = DataCleanManager.getFolderSize(AttPathUtils
				.getInternalDir());
		double webViewSize = DataCleanManager.getFolderSize(AttPathUtils
				.getWebViewDir());
		// double databaseSize = DataCleanManager.getFolderSize(AttPathUtils
		// .getDatabaseDir());
		double totalSize = folderSize + webViewSize;

		// double totalSize =6000000;
		if (totalSize > 1024 * 1024) {
			cacheSize = (float) totalSize / 1024 / 1024;
			cache = String.format("%.2f", cacheSize) + "MB";
		} else if (totalSize > 1024) {
			cacheSize = (float) totalSize / 1024;
			cache = String.format("%.2f", cacheSize) + "KB";
		} else {
			cache = String.format("%.2f",
					Math.floor((double) totalSize / 1024 * 100) / 100)
					+ "KB";
		}
		System.out.println("cache:" + cache);
		return cache;
	}

	/**
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return long
	 */
	public static long getFolderSize(java.io.File file) {
		long size = 0;
		try {
			java.io.File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return size/1048576;
		return size;
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File[] listFiles = file.listFiles();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}