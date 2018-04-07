package myguruh.com.booklend;

/**
 * Created by god on 06-04-2018.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapters extends ArrayAdapter {

    private final Activity context;
    private final ArrayList maintitle;
    private final ArrayList subtitle;
    private final ArrayList imgid;

    public MyListAdapters(Activity context, ArrayList maintitle, ArrayList subtitle, ArrayList imgid) {
        super(context, R.layout.coustom, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.coustom, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.editText);
       ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.textView2);

        titleText.setText(maintitle.get(position).toString());
        imageView.setImageResource(Integer.parseInt(imgid.get(position).toString()));
        subtitleText.setText(subtitle.get(position).toString());

        return rowView;

    }
}