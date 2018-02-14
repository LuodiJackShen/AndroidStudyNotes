package com.jack.fasthelp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Jack
 * luodijack@163.com
 * https://github.com/LuodiJackShen
 * 2018.02.11 下午8:52.
 */

public class ImageUtil {
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImageQuantity(Bitmap image) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到out中
        image.compress(Bitmap.CompressFormat.JPEG, 100, out);
        int options = 90;

        // 循环判断如果压缩后图片是否大于400kb,大于继续压缩
        while (out.toByteArray().length / 1024 > 400) {
            out.reset(); // 重置out即清空out
            // 这里压缩options%，把压缩后的数据存放到out中
            image.compress(Bitmap.CompressFormat.JPEG, options, out);
            options -= 10;// 每次都减少10
            if (options <= 20) {
                break;
            }
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
