package com.example.gazownia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.gazownia.databinding.ActivityMainBinding;

import java.net.NoRouteToHostException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ReplaceFragment(new Home());

        binding.flBottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.home:
                    ReplaceFragment(new Home());
                    break;
                case R.id.history:
                    ReplaceFragment(new History());
                    break;
                case R.id.profile:
                    ReplaceFragment(new Profile());
                    break;
                case R.id.settings:
                    ReplaceFragment(new Settings());
                    break;

            }


            return true;
        });
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flMain,fragment);
        fragmentTransaction.commit();
    }


}