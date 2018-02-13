package com.sadi.sreda.utils;

import android.content.Context;
import android.support.v4.app.Fragment;


public abstract class BaseFragment extends Fragment {
    protected OnFragmentInteractionListener mListener;
    private boolean openMenuOnBackPress=false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean openMenuOnBackPress() {
        return openMenuOnBackPress;
    }

    public void setOpenMenuOnBackPress(boolean openMenuOnBackPress) {
        this.openMenuOnBackPress = openMenuOnBackPress;
    }




}
