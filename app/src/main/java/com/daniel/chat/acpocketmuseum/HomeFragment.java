package com.daniel.chat.acpocketmuseum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        buildButtonListeners(rootView);

        return rootView;
    }

    public void buildButtonListeners(final View rootView) {
        Button goToFishButton = rootView.findViewById(R.id.goToFishButton);
        Button goToInsectButton = rootView.findViewById(R.id.goToInsectButton);
        Button goToFossilButton = rootView.findViewById(R.id.goToFossilButton);

        goToFishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action =
                        HomeFragmentDirections.actionHomeToFishFragment();
                Navigation.findNavController(rootView).navigate(action);
            }
        });

        goToInsectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action =
                        HomeFragmentDirections.actionHomeToInsectFragment();
                Navigation.findNavController(rootView).navigate(action);
            }
        });

        goToFossilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action =
                        HomeFragmentDirections.actionHomeToFossilFragment();
                Navigation.findNavController(rootView).navigate(action);
            }
        });
    }
}