package com.mrd.flutterwangmasteryijia.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Jack
 * luodijack@163.com
 * https://github.com/LuodiJackShen
 * 2018.02.11 下午8:52.
 */

public class ImageUtil {
    public static String compressImage(String filePath, String dstDir) throws Exception {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("filePath is empty or null !");
        }

        Bitmap bitmap = compressImageSize(filePath, 720, 1080);
        bitmap = compressImageQuantity(bitmap);

        File dirFile = new File(dstDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        String fileName = "crash_feedback" + System.currentTimeMillis() + ".jpg";
        File dstFile = new File(dirFile, fileName);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dstFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            try {
                out.close();
                bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dstFile.getAbsolutePath();
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImageQuantity(Bitmap image) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到out中
        image.compress(Bitmap.CompressFormat.JPEG, 50, out);
        int options = 90;

//         循环判断如果压缩后图片是否大于60kb,大于继续压缩
        while (out.toByteArray().length / 1024 > 60) {
            out.reset(); // 重置out即清空out
            image.compress(Bitmap.CompressFormat.JPEG, options, out);
            if (options <= 10) {
                break;
            }
            options -= 10;// 每次都减少10
        }
        // 把压缩后的数据out存放到ByteArrayInputStream中
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(in, null, null);
    }

    /***
     * 尺寸压缩
     * @param picPath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap compressImageSize(String picPath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picPath, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options, width, height);
        return BitmapFactory.decodeFile(picPath, options);
    }

    private static int calculateSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
