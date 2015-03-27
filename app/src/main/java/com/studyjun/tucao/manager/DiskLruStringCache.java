package com.studyjun.tucao.manager;

import android.content.Context;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by studyjun on 2015/2/5.
 *
 * @TODO
 */
public class DiskLruStringCache implements StringCache {

    private static final String TAG = DiskLruStringCache.class.getSimpleName();

    private DiskLruCache mDiskCache;

    private static int IO_BUFFER_SIZE = 8*1024 ;

    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;

    public DiskLruStringCache(Context context, String uniqueName, long diskCacheSize) {
        try {
            final File diskCacheDir = getDiskCacheDir(context, uniqueName);
            mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeStringToFile(String str, DiskLruCache.Editor editor)
            throws IOException, FileNotFoundException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
            out.write(str.getBytes("UTF-8"));
            return true;

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {

        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) ?
                context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getPath()
                : context.getFilesDir().getPath();
        // final String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    @Override
    public void putString(String key, String value) {

        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskCache.edit(createKey(key));
            if (editor == null) {
                return;
            }

            if (writeStringToFile(value, editor)) {
                mDiskCache.flush();
                editor.commit();

            } else {
                editor.abort();

            }
        } catch (IOException e) {

            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }

    }

    @Override
    public String getString(String key) {


        String value = null;
        DiskLruCache.Snapshot snapshot = null;
        try {

            snapshot = mDiskCache.get(createKey(key));
            if (snapshot == null) {
                return null;
            }
            final InputStream in = snapshot.getInputStream(0);
            if (in != null) {
                final BufferedInputStream buffIn =
                        new BufferedInputStream(in, IO_BUFFER_SIZE);
                byte[] contents = new byte[1024];

                int bytesRead = 0;
                StringBuffer strFileContents = new StringBuffer();
                while ((bytesRead = buffIn.read(contents)) != -1) {

                    strFileContents.append(new String(contents, 0, bytesRead));

                }
                value = strFileContents.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }


        return value;

    }

    public boolean containsKey(String key) {

        boolean contained = false;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(createKey(key));
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return contained;

    }

    public void clearCache() {

        try {
            mDiskCache.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return mDiskCache.getDirectory();
    }

    public String createKey(String key){
        return String.valueOf(key.hashCode());
    }
}
