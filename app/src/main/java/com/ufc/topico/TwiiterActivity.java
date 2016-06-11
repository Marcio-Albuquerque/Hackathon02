package com.ufc.topico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class TwiiterActivity extends ListActivity {

    private static final String SEARCHES = "searches";
    private EditText queryEditText;
    private EditText tagEditText;
    private SharedPreferences savedSearches;
    private ArrayList<String> tags;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twiiter);

        savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);

        Intent intent = getIntent();
        String local2 = intent.getStringExtra("Citie2");

        tags = new ArrayList<>(savedSearches.getAll().keySet());
        Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
        adapter = new ArrayAdapter<>(this, R.layout.list_item, tags);
        setListAdapter(adapter);


        getListView().setOnItemClickListener(itemClickListener);
        getListView().setOnItemLongClickListener(itemLongClickListener);

        addTaggedSearch(local2, local2);
    }


    private void addTaggedSearch(String query, String tag){
        SharedPreferences.Editor preferencesEditor = savedSearches.edit();
        preferencesEditor.putString(tag, query);
        preferencesEditor.apply();
        if (!tags.contains(tag)) {
            tags.add(tag);
            Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
            adapter.notifyDataSetChanged();
        }
    }

    OnItemClickListener itemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String tag = ((TextView) view).getText().toString();
            String urlString = getString(R.string.searchURL) + Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            startActivity(webIntent);
        }
    };

    //Slide 07
    OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            final String tag = ((TextView) view).getText().toString();
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(TwiiterActivity.this);
            builder.setTitle(getString(R.string.shareEditDeleteTitle, tag));
            builder.setItems(R.array.dialog_items,
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            switch (which)
                            {
                                case 0:
                                    shareSearch(tag); break;
                                case 1:
                                    deleteSearch(tag); break;
                            }
                        }
                    }
            );

            //Slide 08
            builder.setNegativeButton(getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }
            );
            builder.create().show(); return true;
        }
    };

    private void shareSearch(String tag)
    {
        String urlString = getString(R.string.searchURL) +
                Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareSubject));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareMessage, urlString));
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.shareSearch)));
    }

    //Slide 09
    private void deleteSearch(final String tag)
    {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);
        confirmBuilder.setMessage(getString(R.string.confirmMessage, tag));
        confirmBuilder.setNegativeButton( getString(R.string.cancel),
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                }
        );
        confirmBuilder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        tags.remove(tag);
                        SharedPreferences.Editor preferencesEditor = savedSearches.edit();
                        preferencesEditor.remove(tag);
                        preferencesEditor.apply();
                        adapter.notifyDataSetChanged();
                    } }
        );
        confirmBuilder.create().show();
    }
}



