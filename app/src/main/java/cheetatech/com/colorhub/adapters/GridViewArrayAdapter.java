package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.Util;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorInfo;
import layout.ColorPicker1;


public class GridViewArrayAdapter extends ArrayAdapter<ColorInfo>  {
    private Context context = null;
    private int resource;
    private List<ColorInfo> colorInfos;
    public static int HeaderIndex = -1;
    public static boolean isMaterial = false;
    private ColorPicker1.OnColorListener mListener = null;

    public GridViewArrayAdapter(Context context, int resource, List<ColorInfo> objects, ColorPicker1.OnColorListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.colorInfos = objects;
        this.mListener = listener;
    }

    static class ViewHolder{
        @BindView(R.id.colorName)
        TextView textColorName;
        @BindView(R.id.colorCode)
        TextView textColorCode;
        @BindView(R.id.buttonCopy)
        ImageButton btnCopy;

        @BindView(R.id.rootColorLayout)
        RelativeLayout mLayout;

        @BindView(R.id.borderLayout)
        RelativeLayout mBorderLayout;


        @BindView(R.id.add_color_image_button)
        ImageView mColorAddButton;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    static class ViewHolderAds{
        public ViewHolderAds(View view){
            ButterKnife.bind(this,view);
        }
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder = null;
        View view = convertView;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(this.resource, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Clicked Image Button Erkan Erkan " + position);
                BoardEditor.getInstance().copyToClipBoard(colorInfos.get(position).getColorCode());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colorInfos.get(position).getColorCode() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });

        if(holder.mLayout != null){
            GradientDrawable gd = (GradientDrawable) holder.mLayout.getBackground().getCurrent();
            if(gd != null){
                gd.setColor(Color.parseColor(colorInfos.get(position).getColorCode()));
            }
        }

        if(holder.mBorderLayout != null){
            GradientDrawable gd = (GradientDrawable) holder.mBorderLayout.getBackground().getCurrent();
            if(gd != null){
                gd.setStroke(Util.dpToPx(2),Color.parseColor(colorInfos.get(position).getColorCode()));
            }
        }

        holder.textColorCode.setText(colorInfos.get(position).getColorCode());
        holder.textColorName.setText(colorInfos.get(position).getColorName());
        holder.mColorAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorInfo colorInfo = colorInfos.get(position);
                Log.e("TAG", "onClick: colorInfos " + colorInfo.getColorCode() +  " : " + colorInfo.getColorName());
                if(mListener != null){
                    mListener.onAddColor(colorInfo.getColorCode());
                }
            }
        });

        return view;
    }

}
