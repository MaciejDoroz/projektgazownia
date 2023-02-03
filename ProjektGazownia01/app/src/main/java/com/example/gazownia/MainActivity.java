package com.example.gazownia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gazownia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ReplaceFragment(new History(),"history");

        binding.flBottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                /*
                case R.id.home:
                    ReplaceFragment(new Home());
                    break;
                 */
                case R.id.history:
                    ReplaceFragment(new History(),"history");
                    break;
                case R.id.profile:
                    ReplaceFragment(new Profile(),"profile");
                    break;


            }


            return true;
        });
    }

    public void ReplaceFragment(Fragment fragment, String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
        );
        fragmentTransaction.replace(R.id.flMain,fragment,tag);
        fragmentTransaction.commit();

    }




    public void OpenEditDialog(String id){
        EditDialog editDialog = new EditDialog(id);
        editDialog.show(getSupportFragmentManager(),"EditDialog");
    }

    public void OpenDeleteDialog(String id){
        DeleteDialog deleteDialog = new DeleteDialog(id);
        deleteDialog.show(getSupportFragmentManager(),"DeleteDialog");
    }




}