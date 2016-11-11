package com.grability.apps.App;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grability.apps.R;

import java.io.InputStream;
import java.util.List;

/**
 * Created by ana on 09/11/2016.
 */

public class AppListAdapter  extends ArrayAdapter<App> {

    public AppListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AppListAdapter(Context context, int resource, List<App> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        App app = getItem(position);

        if (convertView == null) {
            // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_list_item, null);
        }

        if (app != null) {
            TextView textView1 = (TextView)convertView.findViewById(R.id.text);
            textView1.setText(app.getLabel());

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imagen);
            DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
            downloadImageTask.execute(app.getImagen());
        }
        // Return the completed view to render on screen
        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
