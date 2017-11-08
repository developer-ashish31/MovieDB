package com.intigral.moviedb._property;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intigral.moviedb.R;


/**
 * Created by AKM on 02,February,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public class AlertDialogView {

   // public static Context mContext;
    public final  void showAlertMessage(final String title,

                                        final String message, final int[] buttonTypes, final String[] buttonTexts,
                                        final DialogInterface.OnClickListener[] buttonListeners , final Context mContext, final Activity activity) {
            //mContext=mContext;
        try {
            if (activity != null)
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        showCustomMessage(title, message, buttonTypes, buttonTexts,
                                buttonListeners, mContext, activity);
                    }
                });
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public synchronized final void showSimpleAlert(final String title,
                                                   final String message, final DialogInterface.OnClickListener listener, final Context mContext , final Activity activity) {
        if (null == message) {
            return;
        }
        if (lDialog != null && lDialog.isShowing()) {
            return;
        }
		/*
		 * runOnUiThread(new Runnable() { public void run() { if
		 * (!isFinishing()) { alertDialog = new
		 * AlertDialogView.Builder(UIActivityManager.this).create(); if (null !=
		 * title) { alertDialog.setTitle(title); }
		 * alertDialog.setMessage(message);
		 * alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK",
		 * listener); alertDialog.setOnKeyListener(SEARCH_KEY_HANDLER);
		 * alertDialog.setCancelable(false); alertDialog.show(); } } });
		 */
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showCustomMessage(title, message, null, null, null,mContext, activity);
            }
        });

    }

    public Dialog lDialog = null;
    Button button;
    int[] buttonTypesNew;
    DialogInterface.OnClickListener[] buttonListenersNew = null;

    public void showCustomMessage(final String pTitle, final String pMsg,
                                  final int[] buttonTypes, final String[] buttonTexts,
                                  final DialogInterface.OnClickListener[] buttonListeners, final Context mContext , final Activity activity) {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    buttonListenersNew = buttonListeners;
                    buttonTypesNew = buttonTypes;
                    if (lDialog != null && lDialog.isShowing()) {
                        lDialog.dismiss();
                        lDialog = null;
                    }
                    if (lDialog != null)
                        lDialog = null;

                    try {
                        // getParent()
                        lDialog = new Dialog(
                                mContext,
                                android.R.style.Theme_Translucent_NoTitleBar) {
                            public void onBackPressed() {
                                if (null == buttonTypes)
                                    if (lDialog == null && lDialog.isShowing())
                                        lDialog.dismiss();
                            }

                            ;
                        };
                        lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    } catch (Exception e) {
                        // TODO: handle exception
                        if (lDialog == null) {
                            lDialog = new Dialog(
                                    mContext,
                                    android.R.style.Theme_Translucent_NoTitleBar) {
                                public void onBackPressed() {
                                    if (null == buttonTypes)
                                        if (lDialog == null
                                                && lDialog.isShowing())
                                            lDialog.dismiss();
                                }

                                ;
                            };
                        }

                        e.printStackTrace();
                        // lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    }
                    // lDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    WindowManager.LayoutParams wp = lDialog.getWindow()
                            .getAttributes();
                    wp.dimAmount = 0.50f;
                    lDialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    lDialog.getWindow().addFlags(
                            WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    lDialog.setContentView(R.layout.r_okcanceldialogview);
                    ((TextView) lDialog.findViewById(R.id.dialog_title))
                            .setText(pTitle);
                    ((TextView) lDialog.findViewById(R.id.dialog_message))
                            .setText(pMsg);
                    LinearLayout buttonContainer = ((LinearLayout) lDialog
                            .findViewById(R.id.buttonContainer));// .setText(pMsg);
                    LinearLayout con = ((LinearLayout) lDialog
                            .findViewById(R.id.con));



                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            0, (int) UtilitesData.convertDpToPixel(40,
                            activity), 0.5f);
                    param.leftMargin = (int) UtilitesData.convertDpToPixel(5,
                            activity);
                    param.rightMargin = (int) UtilitesData.convertDpToPixel(5,
                            activity);


                    if (null != buttonTypes) {
                        for (int i = 0; i < buttonTypes.length; i++) {
                            // lDialog.setButton(buttonTypes[i], buttonTexts[i],
                            // buttonListeners[i]);

                            button = new Button(mContext);
                            button.setLayoutParams(param);
                            button.setText(buttonTexts[i]);
                            button.setTextColor(0xffffffff);
                            button.setId(i);
                            // button.setPadding((int)
                            // Utilities.convertDpToPixel(10,
                            // this), 0, 0, 0);
                            button.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(final View v) {
                                    activity.runOnUiThread(new Runnable() {
                                        public void run() {

                                            lDialog.dismiss();
                                            int btnIndex = v.getId();
                                            try {
                                                // if(buttonTypes[v.getId()] !=
                                                // 0)
                                                btnIndex = buttonTypes[v
                                                        .getId()];
                                            } catch (Exception e) {

                                                // TODO: handle exception
                                            }
                                            if (buttonListeners[v.getId()] != null)
                                                buttonListeners[v.getId()]
                                                        .onClick(null, btnIndex);

                                        }
                                    });

                                }
                            });
                            if (i == 0)
                                button.setBackgroundResource(R.drawable.greencolorbutton);
                            else
                                button.setBackgroundResource(R.drawable.custombutton2);
                            buttonContainer.addView(button);
                        }
                    } else {
                        button = new Button(mContext);
                        button.setLayoutParams(param);
                        button.setText(mContext.getString(R.string.ok));
                        button.setTextColor(0xffffffff);

                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                if (lDialog != null && lDialog.isShowing())
                                    lDialog.dismiss();
                            }
                        });
                        button.setBackgroundResource(R.drawable.greencolorbutton);
                        buttonContainer.addView(button);

                    }

                    UtilitesData.startAnimition(
                            mContext, con,
                            R.anim.grow_from_midddle);
                    if (!((Activity) mContext)
                            .isFinishing())
                        lDialog.show();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

    }
}
