// Generated by view binder compiler. Do not edit!
package com.google.mediapipe.examples.gesturerecognizer.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.mediapipe.examples.gesturerecognizer.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentLightcontrolBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView RvNoLights;

  @NonNull
  public final TextView lightcontroltitle;

  @NonNull
  public final RecyclerView lightrecyclerview;

  @NonNull
  public final ImageView loadinggifImg;

  @NonNull
  public final CardView togglelightcard;

  private FragmentLightcontrolBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView RvNoLights, @NonNull TextView lightcontroltitle,
      @NonNull RecyclerView lightrecyclerview, @NonNull ImageView loadinggifImg,
      @NonNull CardView togglelightcard) {
    this.rootView = rootView;
    this.RvNoLights = RvNoLights;
    this.lightcontroltitle = lightcontroltitle;
    this.lightrecyclerview = lightrecyclerview;
    this.loadinggifImg = loadinggifImg;
    this.togglelightcard = togglelightcard;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLightcontrolBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLightcontrolBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_lightcontrol, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLightcontrolBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Rv_no_lights;
      TextView RvNoLights = ViewBindings.findChildViewById(rootView, id);
      if (RvNoLights == null) {
        break missingId;
      }

      id = R.id.lightcontroltitle;
      TextView lightcontroltitle = ViewBindings.findChildViewById(rootView, id);
      if (lightcontroltitle == null) {
        break missingId;
      }

      id = R.id.lightrecyclerview;
      RecyclerView lightrecyclerview = ViewBindings.findChildViewById(rootView, id);
      if (lightrecyclerview == null) {
        break missingId;
      }

      id = R.id.loadinggifImg;
      ImageView loadinggifImg = ViewBindings.findChildViewById(rootView, id);
      if (loadinggifImg == null) {
        break missingId;
      }

      id = R.id.togglelightcard;
      CardView togglelightcard = ViewBindings.findChildViewById(rootView, id);
      if (togglelightcard == null) {
        break missingId;
      }

      return new FragmentLightcontrolBinding((ConstraintLayout) rootView, RvNoLights,
          lightcontroltitle, lightrecyclerview, loadinggifImg, togglelightcard);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
