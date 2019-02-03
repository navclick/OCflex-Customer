package com.example.fightersarena.ocflex_costumer.Helpers;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.fightersarena.ocflex_costumer.R;

public class ProgressLoader extends DialogFragment {

    private static final String TAG = "ProgressLoader";
    private static ProgressLoader loader;
    public ProgressLoader() {
    }

    public static ProgressLoader getProgressLoader(){
        if(loader ==null){
            loader = new ProgressLoader();

        }
        return loader;

    }

    public static void hideProgress(){

        if(loader!=null){

            loader.dismissAllowingStateLoss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());


        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.transparent));
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View parent = inflater.inflate(R.layout.custom_loader, null);
        parent.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        dialog.setContentView(parent);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }

    @Override
    public void show(FragmentManager manager, String tag) {

        try {
            if (this.isAdded()) {
                return; //or return false/true, based on where you are calling from
            }
            FragmentTransaction ft = manager.beginTransaction();
            try {
                ft.add(this, TAG);
            } catch (Exception e) {
                e.printStackTrace();
            }

        /*try catch is used to tackle the crash
         when user close the app and some thing
         running in background of app*/

            ft.commitAllowingStateLoss();


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }

    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        }
        catch (IllegalStateException e)
        {
            super.dismissAllowingStateLoss();
        }
    }
}
