package com.example.jaielalondon.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class articlesAdapter extends ArrayAdapter<Article> {

    // the context
    private Context mContext;

    /** List of articles */
    private List<Article> mArticles;

    public articlesAdapter(@NonNull Context context, int resource, List<Article> articlesList) {
        super(context, resource, articlesList);
        mContext = context;
        mArticles = articlesList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get list item view
        View listItem = convertView;

        // if the view listItem is null, inflate a new list item
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent,false);

        }

        // Get current article at the current position
        final Article currentArticle = (Article) getItem(position);

        // Find resource for titleTextView and set the current article's title as its text
        TextView titleTextView = (TextView) listItem.findViewById(R.id.title_text_view);
        titleTextView.setText(currentArticle.getTitle());

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.getUrl()));
                mContext.startActivity(intent);
            }
        });

        // Return the list item
        return listItem;

    }

}
