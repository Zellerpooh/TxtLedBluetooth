package com.example.txtledbluetooth.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.txtledbluetooth.R;
import com.example.txtledbluetooth.bean.Lighting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by KomoriWu
 * on 2017-04-18.
 */

public class Utils {
    public static final String ITEM_RIGHT_TEXT = "item_right_text";
    public static final String AUDIO_PROMPTS_DEFAULT_MODEL = "Voice and Tones";

    public static DisplayImageOptions getImageOptions(int defaultIconId) {
        return getImageOptions(defaultIconId, 0);
    }

    public static DisplayImageOptions getImageOptions(int defaultIconId, int cornerRadiusPixels) {
        return new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels))
                .showImageOnLoading(defaultIconId)
                .showImageOnFail(defaultIconId)
                .showImageForEmptyUri(defaultIconId)
                .cacheInMemory(true)
                .cacheOnDisc()
                .build();
    }

    public static void showAlertDialog(Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setMessage(message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    public static ArrayList<Lighting> getLightList(Context context) {
        String[] lightNames = context.getResources().getStringArray(R.array.lighting_name);
        int[] lightIcons = {R.mipmap.icon_moon_light, R.mipmap.icon_fireworks,
                R.mipmap.icon_blue_skies, R.mipmap.icon_rainbow, R.mipmap.icon_pulsate,
                R.mipmap.icon_glow, R.mipmap.icon_monochrome};
        ArrayList<Lighting> lightingList = new ArrayList<>();
        boolean isEdit;
        for (int i = 0; i < lightIcons.length; i++) {
            if (i == 5) {
                isEdit = true;
            } else {
                isEdit = false;
            }
            lightingList.add(i, new Lighting(lightNames[i], lightIcons[i], isEdit));
        }
        return lightingList;
    }

}
