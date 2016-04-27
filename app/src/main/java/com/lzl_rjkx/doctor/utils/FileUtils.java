package com.lzl_rjkx.doctor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/Photo_LJ/";
    private static File f;

    public static void saveBitmap(Bitmap bm, String picName) {
        try {
            File tempf = createSDDir("");
            if (!tempf.exists()) {
                tempf.mkdir();
            }

            f = new File(SDPATH, picName);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从手机或者sd卡获取Bitmap
     *
     * @return
     */
    public static Bitmap getBitmap(String fileName) {
        return BitmapFactory.decodeFile(SDPATH + fileName);
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
        }
        return dir;
    }

    /**
     * 获取图片的名称
     */
    public static String getFileName(String url) {
        //http://test.waikegj.com/SurgicalKeeper/dataupload/adv/2016-03-15/ad_5079225952202404.jpg
        if (url == null) {
            return null;
        }
        String[] strs = url.split("/");
        return strs[strs.length - 1];
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        return new File(SDPATH + fileName).exists();
    }

    /**
     * 获取文件的大小
     *
     * @param fileName
     */
    public static long getFileSize(String fileName) {
        return new File(SDPATH + fileName).length();
    }

    /**
     * 删除SD卡或者手机的缓存图片和目录
     *
     * @param fileName
     */

    public static void delFile(String fileName) {
        File dirFile = new File(SDPATH + fileName);
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (int i = 0; i < children.length; i++) {
                new File(dirFile, children[i]).delete();
            }
        }
        dirFile.delete();
    }

    public static Bitmap createVideoThumbnail(String filePath) {
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}