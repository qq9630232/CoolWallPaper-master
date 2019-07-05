package com.coolwallpaper.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.coolwallpaper.R;


/**
 * Created by SDC on 2018/11/6.
 */

public class CommonDialogUtils {

    private static AlertDialog dialog;

    /*生成弹框*/
    public static Dialog createCornerDialog(final Context context, String title, String message, String leftText, final String rightText, final View.OnClickListener leftListener, final View.OnClickListener rightListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.elita_common_dialog);
        window.getAttributes().width = (int) (DimensUtils.getScreenWidth(context.getApplicationContext()) * 0.8);
        window.setGravity(Gravity.CENTER);
        ((TextView) window.findViewById(R.id.title)).setText(title);
        ((TextView) window.findViewById(R.id.context)).setText(message);
        ((TextView) window.findViewById(R.id.cancel)).setText(leftText);
        if (!TextUtils.isEmpty(rightText)) {
            ((TextView) window.findViewById(R.id.confirm)).setText(rightText);
            window.findViewById(R.id.confirm).setVisibility(View.VISIBLE);
        } else {
            window.findViewById(R.id.confirm).setVisibility(View.GONE);
        }
        final long timeStamp = System.currentTimeMillis();

        ((TextView) window.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) {
//                    ElitaBurialPointFactory.sendBurialPoint(ELITA_GOSCENE_CHANGE_CATSTYLE7005);
                    leftListener.onClick(v);

                } else {
                    if(dialog!=null){

                    }

                    dialog.dismiss();
                    dialog=null;

                }
            }
        });

        ((TextView) window.findViewById(R.id.confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightListener != null) {
//                    ElitaBurialPointFactory.sendBurialPoint(ELITA_GOSCENE_CHANGE_CATSTYLE7006);

                    rightListener.onClick(v);
                } else {
                    if(dialog!=null){

                    }
                    dialog.dismiss();
                    dialog=null;

                }
            }
        });

        return dialog;
    }
    public static void dismiss(){
        if(dialog!=null){

            dialog.dismiss();
            dialog=null;
        }

    }


}
