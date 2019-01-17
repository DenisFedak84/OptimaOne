package com.aimprosoft.android.optima.centralizedApp.app.savefiledialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveFileDialog extends Dialog implements View.OnClickListener {
    private static final String FILE_KEY = "filename";
    private static final String IMAGE_KEY = "fileimage";
    private static final String TAG = "SaveFileDialog";
    private File currentDir;
    private File newFile = null;
    private ListView view = null;
    private EditText edit = null;
    private FilenameFilter filter = null;
    private OnNewFileSelectedListener onNewFileSelectedListener = null;

    public SaveFileDialog(Context context, String dir, String[] fileExt, OnNewFileSelectedListener listener) {
        super(context);
        init(dir, fileExt, listener);
    }

    private void init(String dir, String[] fileExt, OnNewFileSelectedListener listener) {
        onNewFileSelectedListener = listener;

        currentDir = new File(Environment.getExternalStorageDirectory().toString());

        if (dir != null && new File(dir).exists()) {
            currentDir = new File(dir);
        }

        prepareFileFilter(fileExt);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sfd_layout);
        setTitle(R.string.sfd_title);

        edit = (EditText) findViewById(R.id.sfd_file_name);

        view = (ListView) findViewById(R.id.sfd_list);
        browseTo(currentDir);

        view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Map<String, String> listItem = (Map<String, String>) a.getItemAtPosition(position);
                        String text = listItem.get(FILE_KEY);
                        File file = new File(currentDir.getAbsolutePath() + File.separator + text);
                        if (!browseTo(file)) {
                            edit.setText(text);
                        }
                    }
                });

        Button upButton = (Button) findViewById(R.id.sfd_go_up);
        upButton.setOnClickListener(this);

        Button newDirButton = (Button) findViewById(R.id.sfd_new_dir);
        newDirButton.setOnClickListener(this);

        Button saveButton = (Button) findViewById(R.id.sfd_save);
        saveButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == findViewById(R.id.sfd_go_up)) {
            browseUp();
        } else if (v == findViewById(R.id.sfd_new_dir)) {
            createNewDirectory(edit.getText().toString());
        } else if (v == findViewById(R.id.sfd_save)) {
            String fileName = String.valueOf(edit.getText());
            if (!fileName.contains(Constants.SAVED_FILE_EXTENSION))
                fileName += Constants.SAVED_FILE_EXTENSION;
            File f = createNewFile(fileName);
            if (f != null && onNewFileSelectedListener != null) {
                returnNewFile(f);
            }
        }
    }

    private void prepareFileFilter(final String[] ext) {
        if (ext == null)
            return;

        filter = new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                if (new File(dir + File.separator + filename).isDirectory())
                    return true;
                for (String e : ext) {
                    if (filename.endsWith(e))
                        return true;
                }
                return false;
            }
        };
    }

    private boolean browseTo(File dir) {
        if (!dir.isDirectory()) {
            return false;
        } else {
            if (!fillListView(dir))
                return true;
            currentDir = dir;
            TextView pathView = (TextView) findViewById(R.id.sfd_current_path);
            pathView.setText(currentDir.getAbsolutePath());
            return true;
        }
    }

    private void browseUp() {
        if (currentDir.getParentFile() != null)
            browseTo(currentDir.getParentFile());
    }

    private boolean fillListView(File dir) {
        List<Map<String, ?>> list = new ArrayList<>();
        String[] files = null;
        try {
            files = filter != null ? dir.list(filter) : dir.list();
        } catch (SecurityException e) {
            handleException(e);
        }

        if (files == null)
            return false;

        for (String file : files) {
            Map<String, Object> item = new HashMap<>();
            item.put(FILE_KEY, file);
            int imageId = new File(dir.getAbsolutePath() + File.separator + file).isDirectory()
                    ? R.drawable.ic_osdialogs_dir
                    : R.drawable.file;
            item.put(IMAGE_KEY, imageId);
            list.add(item);
        }

        Collections.sort(list, new Comparator<Map<String, ?>>() {
            @Override
            public int compare(Map<String, ?> lhs, Map<String, ?> rhs) {
                String name1 = lhs.get(FILE_KEY).toString().toLowerCase();
                String name2 = rhs.get(FILE_KEY).toString().toLowerCase();
                return name1.compareTo(name2);
            }
        });

        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                list,
                R.layout.sfd_list_item,
                new String[]{FILE_KEY, IMAGE_KEY},
                new int[]{R.id.sfd_item_text, R.id.sfd_item_image});
        view.setAdapter(adapter);
        return true;
    }

    private void createNewDirectory(String directoryName) {
        if (directoryName == null)
            return;

        File newDir = new File(currentDir + File.separator + directoryName);
        if (!newDir.exists()) {
            if (newDir.mkdirs()) {
                edit.setText(null);
                browseTo(newDir);
            }
        }
    }

    private File createNewFile(String name) {
        newFile = new File(currentDir + File.separator + name);
        if (!newFile.exists()) {
            try {
                if (!newFile.createNewFile()) {
                    throw new IOException("can't create file - " + newFile.toString());
                }
                return newFile;
            } catch (Exception e) {
                handleException(e);
                return null;
            }
        } else {
           /* new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.sfd_confirmation_title)
                    .setMessage(R.string.sfd_confirmation_message)
                    .setPositiveButton(R.string.sfd_btn_yes,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    returnNewFile(newFile);
                                }

                            })
                    .setNegativeButton(R.string.sfd_btn_no, null)
                    .show();*/
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.rewrite_file_dialog);
            dialog.findViewById(R.id.rewrite_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnNewFile(newFile);
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.rewrite_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        return null;
    }

    private void returnNewFile(File file) {
        if (onNewFileSelectedListener != null) {
            onNewFileSelectedListener.onNewFileSelected(file);
        }
        dismiss();
    }

    private void handleException(Throwable ex) {
        Log.e(TAG, ex.getMessage(), ex);
    }


}
