package appocorrencias.com.appocorrencias.Adapters;

import android.content.Context;
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

public class CustomSwiperAdapter extends PagerAdapter {


    int[] image_resources;
    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwiperAdapter(Context ctx, int[] image_resources){
        this.ctx = ctx ;
        this.image_resources = image_resources;
    }


    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (ConstraintLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView =  (ImageView) item_view.findViewById(R.id.image_view);
        TextView textView = (TextView) item_view.findViewById(R.id.image_count);
        imageView.setImageResource(image_resources[position]);
        textView.setText("");
        container.addView(item_view);
        return item_view;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);

    }
}
