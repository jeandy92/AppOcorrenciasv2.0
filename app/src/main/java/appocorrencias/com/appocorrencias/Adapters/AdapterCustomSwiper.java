package appocorrencias.com.appocorrencias.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.R;

/**
 * Created by Robson on 26/04/2017.
 */

public class AdapterCustomSwiper extends PagerAdapter {


    int[] imageResources;
    private Context ctx;
    private LayoutInflater layoutInflater;
    Bitmap [] images;

    public AdapterCustomSwiper(Context ctx, Bitmap [] images){
        this.ctx = ctx ;
        this.imageResources = imageResources;
        this.images= images;
    }


    @Override
    public int getCount() {
        return images.length;
        //return imageResources.length;
        //return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (ConstraintLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater       =  (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View      itemView   =  layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView  =  (ImageView) itemView.findViewById(R.id.image_view);
        TextView  textView   =  (TextView) itemView.findViewById(R.id.image_count);

        imageView.setImageBitmap(images[position]);

        textView.setText("");

        container.addView(itemView);

        return itemView;
    }


    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);

    }
}
