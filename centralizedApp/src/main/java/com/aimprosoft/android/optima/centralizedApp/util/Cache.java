package com.aimprosoft.android.optima.centralizedApp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Cache {
    Context context;
    final String A = "content://"
            + CacheContentProvider.AUTHORITY + "/" + CacheContentProvider.CACHE_PATH;
    final Uri CONTACT_URI = Uri.parse(A);

    public Cache(Context context) {
        this.context = context;
    }

    public void save(String key, Object value) {
        if (value instanceof ContentValues) {
            HashMap<String, Object> temp = new HashMap<>();

            for (String tempKey : ((ContentValues) value).keySet()) {
                temp.put(tempKey, ((ContentValues) value).get(tempKey));
            }
            value = temp;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(value);
            byte[] bytes = bos.toByteArray();
            ContentValues cv = new ContentValues();
            cv.put("key", key);
            cv.put("value", bytes);
            Cursor query = context.getContentResolver().query(CONTACT_URI, null, "key = ?", new String[]{key}, null);
            assert query != null;
            int count = query.getCount();
            if (count == 0) {
                context.getContentResolver().insert(CONTACT_URI, cv);
            } else {
                context.getContentResolver().update(CONTACT_URI, cv, "key = ?", new String[]{key});
            }
            query.close();
        } catch (IOException ignored) {

        } finally {
            try {
                assert out != null;
                out.close();
                bos.close();
            } catch (IOException ignored) {

            }
        }
    }

    public int update(String key, Object value) {
        ContentValues cv = new ContentValues();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(value);
            byte[] bytes = bos.toByteArray();
            cv.put("value", bytes);
            Uri parse = (key != null) ? Uri.parse(A + "/" + key) : CONTACT_URI;
            return context.getContentResolver().update(parse, cv, CacheContentProvider.ID + " = ?", new String[]{key});
        } catch (IOException ignored) {

        } finally {
            try {
                assert out != null;
                out.close();
                bos.close();
            } catch (IOException ignored) {
            }
        }
        return -1;
    }

    public int delete(String key) {
        Uri parse = (key != null) ? Uri.parse(A + "/" + key) : CONTACT_URI;
        int result;
        if (key == null) {
            result = context.getContentResolver().delete(parse, null, null);
        } else {
            result = context.getContentResolver().delete(parse, CacheContentProvider.ID + " = ?", new String[]{key});
        }
        return result;
    }

    public Object get(String key) {
        Uri parse = (key != null) ? Uri.parse(A + "/" + key) : Uri.parse(A + "/");

        Cursor query = context.getContentResolver().query(parse, null, null, null, null);
        assert query != null;
        Object o = null;
        if (query.moveToFirst()) {
            byte[] blob = query.getBlob(0);
            ByteArrayInputStream bis = new ByteArrayInputStream(blob);
            ObjectInput in = null;
            try {

                in = new ObjectInputStream(bis);
                o = in.readObject();
            } catch (IOException | ClassNotFoundException ignored) {
            } finally {
                try {
                    bis.close();
                    assert in != null;
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        query.close();
        return o;
    }
}
