package com.example.guardapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.guardapp.R;
import com.example.guardapp.repository.ArticlesRepositoryImpl;
import com.example.guardapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements Preferences {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setSyncPeriod(getPreferredSyncPeriod());
    }

    private int getPreferredSyncPeriod() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return Integer.parseInt(sharedPreferences.getString(getString(R.string.sync_period_key), "30"));
    }

    @Override
    public void setSyncPeriod(int period) {
        ArticlesRepositoryImpl repository = ArticlesRepositoryImpl.getInstance();
        repository.setSyncPeriod(period);
    }

}
