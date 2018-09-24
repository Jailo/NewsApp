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
import android.widget.ImageView;
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

        // Find resource for section_text_view and set the current article's section as its text
        TextView sectionTextView = (TextView) listItem.findViewById(R.id.section_text_view);
        sectionTextView.setText(currentArticle.getSection());

        // Find resource for date_text_view and set the current article's datePublished as its text
        TextView dateTextView = (TextView) listItem.findViewById(R.id.date_text_view);
        dateTextView.setText(currentArticle.getDatePublished().substring(0, 10));

        // Find resource for author_text_view
        TextView authorTextView = (TextView) listItem.findViewById(R.id.author_text_view);

        // if the curent articles auther is not an empty string
        // set the current article's author as its text
        if (!currentArticle.getAuthor().equals("")) {
            authorTextView.setText(currentArticle.getAuthor());
        } else {
            // set author text view visibility to be gone
            authorTextView.setVisibility(View.GONE);
        }

        // Find resource for image view and set the current article's image as its text
        ImageView imageView = (ImageView) listItem.findViewById(R.id.image);
        imageView.setImageBitmap(currentArticle.getPicture());

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
