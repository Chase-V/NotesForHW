package com.tashev.notesforhw;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(int containerViewId, Fragment fragment, boolean useBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        if (useBackStack){
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction.commit();
    }

}
