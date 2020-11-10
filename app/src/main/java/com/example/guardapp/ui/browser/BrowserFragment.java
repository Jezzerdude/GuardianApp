package com.example.guardapp.ui.browser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.guardapp.R;
import com.example.guardapp.databinding.FragmentBrowserBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

import static android.net.ConnectivityManager.NetworkCallback;
import static com.example.guardapp.ui.browser.common.FragmentsLabels.DISCOVER_FRAGMENT;
import static com.example.guardapp.ui.browser.common.FragmentsLabels.NO_CONNECTION_FRAGMENT;

public class BrowserFragment extends Fragment  {
    private BehaviorSubject<Boolean> isNetworkConnected = BehaviorSubject.createDefault(false);
    NavController navController;
    BehaviorSubject<List<String>> currentDestination = BehaviorSubject.createDefault(new ArrayList<>());
    NavController.OnDestinationChangedListener destinationChangedListener;
    NetworkCallback networkCallback;
    private CompositeDisposable disposables;

    public BrowserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables = new CompositeDisposable();
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(@NonNull Network network) {
                isNetworkConnected.onNext(false);
            }

            @Override
            public void onUnavailable() {
                isNetworkConnected.onNext(false);
            }

            @Override
            public void onAvailable(@NonNull Network network) {
                isNetworkConnected.onNext(true);
            }

        };
        registerNetworkCallback();
    }

    @Override
    public void onResume() {
        super.onResume();
        destinationChangedListener = (controller, destination, arguments) -> {
            currentDestination.getValue().add(Objects.requireNonNull(
                    destination
                            .getLabel())
                    .toString());
            currentDestination.onNext(currentDestination.getValue());
            Log.d("DestinationHistory", currentDestination.getValue().toString());
        };
        navController.addOnDestinationChangedListener(destinationChangedListener);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBrowserBinding binding = FragmentBrowserBinding.inflate(inflater, container, false);
        disposables.add(isNetworkConnected
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((isConnected) -> {
                  //  changeColorBasedOnConnectivity(binding, isConnected);
                }));
        disposables.add(
                currentDestination
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                //region Handle Animation based on currentDestination
                                (currentHistory) -> {
                                    if (currentHistory.size() > 1) {
                                        String currentDestination = currentHistory
                                                .get(currentHistory
                                                        .size() - 1);
                                        switch (currentHistory
                                                .get(currentHistory
                                                        .size() - 2)) {
                                            case DISCOVER_FRAGMENT:
                                                if (currentDestination
                                                        .equals(NO_CONNECTION_FRAGMENT)) {
                                                    binding.appBarChildren.transitionToStart();
                                                } else if (!currentDestination
                                                        .equals(DISCOVER_FRAGMENT)) {
                                                    binding.appBarChildren.transitionToEnd();
                                                }

                                            case NO_CONNECTION_FRAGMENT:
                                                if (currentDestination
                                                        .equals(DISCOVER_FRAGMENT)) {
                                                    binding.appBarChildren.transitionToStart();
                                                } else if (currentDestination.equals(NO_CONNECTION_FRAGMENT)) {
                                                    binding.appBarChildren.transitionToEnd();
                                                }
                                        }
                                    }
                                }
                                //endregion
                        ));

        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void navigateToDestinationWhenCurrentIs(
            String currentFragment,
            NavDirections destination) {
        if (currentDestination
                .getValue()
                .get(currentDestination
                        .getValue()
                        .size() - 1)
                .equals(currentFragment)) {
            navController
                    .navigate(destination);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment
                .findNavController(Objects.requireNonNull(getChildFragmentManager()
                        .findFragmentById(R.id.browser_nav_host_fragment)));
        navController.restoreState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        navController.saveState();
    }

    public void registerNetworkCallback() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Objects.requireNonNull(connectivityManager)
                    .registerDefaultNetworkCallback(networkCallback);
        } catch (Exception e) {
            isNetworkConnected.onNext(false);
        }
    }


    @Override
    public void onPause() {
        navController
                .removeOnDestinationChangedListener(destinationChangedListener);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Objects.requireNonNull(connectivityManager)
                .unregisterNetworkCallback(networkCallback);
        super.onDestroy();
    }
}
