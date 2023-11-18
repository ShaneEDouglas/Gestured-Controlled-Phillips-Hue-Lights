// Generated by view binder compiler. Do not edit!
package com.google.mediapipe.examples.gesturerecognizer.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import com.google.mediapipe.examples.gesturerecognizer.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class InfoBottomSheetBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final NestedScrollView bottomSheetLayout;

  private InfoBottomSheetBinding(@NonNull NestedScrollView rootView,
      @NonNull NestedScrollView bottomSheetLayout) {
    this.rootView = rootView;
    this.bottomSheetLayout = bottomSheetLayout;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static InfoBottomSheetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static InfoBottomSheetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.info_bottom_sheet, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static InfoBottomSheetBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    NestedScrollView bottomSheetLayout = (NestedScrollView) rootView;

    return new InfoBottomSheetBinding((NestedScrollView) rootView, bottomSheetLayout);
  }
}