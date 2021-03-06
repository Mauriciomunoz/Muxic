package msfot.muxic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Mau on 08/08/15.
 */
public class list_adapter extends ArrayAdapter<String> {

    private String[] mStrings;
    private TypedArray mIcons;
    //private LayoutInflater mInflater;
    private int mViewResourceId;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public list_adapter(Context ctx, int viewResourceId,
                        String[] strings) {
        super(ctx, viewResourceId, strings);

        inflater = (LayoutInflater)ctx.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        mStrings = strings;
        //mIcons = icons;

        mViewResourceId = viewResourceId;
    }

    @Override
    public int getCount() {
        return mStrings.length;
    }

    @Override
    public String getItem(int position) {
        return mStrings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(mViewResourceId, null);



        Button btn=(Button)convertView.findViewById(R.id.btnUp);


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontawesome.ttf");

        btn.setTypeface(font);
        btn.setText(new String(Character.toChars(0xf087)));

        String[] strSeparate = mStrings[position].split("##");

        btn.setTag(strSeparate[3]);

        TextView title = (TextView)convertView.findViewById(R.id.cancion);

        title.setText(strSeparate[0]);

        TextView artista = (TextView)convertView.findViewById(R.id.artista);
        artista.setText(strSeparate[2]);

        TextView album=(TextView)convertView.findViewById(R.id.album);
        album.setText(strSeparate[1]);

        ImageView imgAlb=(ImageView)convertView.findViewById((R.id.imgAlbum));

        String encodedURL="";

        try {
            encodedURL = URLDecoder.decode(strSeparate[4], "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        UrlImageViewHelper.setUrlDrawable(imgAlb,"http://i.scdn.co/image/"+strSeparate[4]);


       // Ion.with(imgAlb).load("http://i.scdn.co/image/"+strSeparate[4]);


        /*
        if(strSeparate.length>1){
            TextView title = (TextView)convertView.findViewById(R.id.titulo);
            title.setText(strSeparate[0]);

            TextView subtitle = (TextView)convertView.findViewById(R.id.subtitulo);
            subtitle.setText(strSeparate[1]);

            TextView price=(TextView)convertView.findViewById(R.id.precio);
            price.setText("$"+strSeparate[2]);

        }*/

        return convertView;

    }
}
