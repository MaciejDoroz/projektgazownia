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
                case R.id.settings:
                    ReplaceFragment(new Settings(),"settings");
                    break;

            }


            return true;
        });
    }

    public void ReplaceFragment(Fragment fragment, String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flMain,fragment,tag);
        fragmentTransaction.commit();
    }


}