/*
 * Copyright (c) 2021. Drakeet Xu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * tributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mordecai.base.ui.component.drawerlayout;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;
import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Drakeet Xu
 */
public class FullDraggableContainer extends FrameLayout implements com.mordecai.base.ui.component.drawerlayout.FullDraggableHelper.Callback {

  @NonNull
  private final com.mordecai.base.ui.component.drawerlayout.FullDraggableHelper helper;

  private DrawerLayout drawerLayout;

  public FullDraggableContainer(@NonNull Context context) {
    this(context, null);
  }

  public FullDraggableContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FullDraggableContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    helper = new com.mordecai.base.ui.component.drawerlayout.FullDraggableHelper(context, this);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ensureDrawerLayout();
  }

  private void ensureDrawerLayout() {
    ViewParent parent = getParent();
    if (!(parent instanceof DrawerLayout)) {
      throw new IllegalStateException("This " + this + " must be added to a DrawerLayout");
    }
    drawerLayout = (DrawerLayout) parent;
  }
  float lastX = 0f;
  float lastY = 0f;
  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
//    super.onInterceptTouchEvent(event);
//    getLayoutDirection());
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        lastX = event.getX();
        lastY = event.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        float dx = event.getX() - lastX;
        float dy = event.getY() - lastY;
        double angle = toDegrees(atan2(dy, dx));
        if (angle < 0) {
          angle += 360;
        }
        if (this.getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
          if (angle > 25 && angle < 335) {
            return false;
          }
        } else {
          if (angle < 155 || angle > 205) {
            return false;
          }
        }
        // 这里的 angle 就是移动的角度，你可以根据需要使用它
        break;
    }
    return helper.onInterceptTouchEvent(event);
  }
  float initialMotionX = 0f;
  float initialMotionY = 0f;
  float lastMotionX = 0f;



  @Override
  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouchEvent(MotionEvent event) {
//    mListener.onTouch(event);
    return helper.onTouchEvent(event);
  }

  @NonNull
  @Override
  public View getDrawerMainContainer() {
    return this;
  }

  @Override
  public boolean isDrawerOpen(int gravity) {
    return drawerLayout.isDrawerOpen(gravity);
  }

  @Override
  public boolean hasEnabledDrawer(int gravity) {
    return drawerLayout.getDrawerLockMode(gravity) == DrawerLayout.LOCK_MODE_UNLOCKED
      && findDrawerWithGravity(gravity) != null;
  }

  @Override
  public void offsetDrawer(int gravity, float offset) {
    setDrawerToOffset(gravity, offset);
    drawerLayout.invalidate();
  }

  @Override
  public void smoothOpenDrawer(int gravity) {
    drawerLayout.openDrawer(gravity, true);
  }

  @Override
  public void smoothCloseDrawer(int gravity) {
    drawerLayout.closeDrawer(gravity, true);
  }

  @Override
  public void onDrawerDragging() {
    List<DrawerLayout.DrawerListener> drawerListeners = getDrawerListeners();
    if (drawerListeners != null) {
      int listenerCount = drawerListeners.size();
      for (int i = listenerCount - 1; i >= 0; --i) {
        drawerListeners.get(i).onDrawerStateChanged(DrawerLayout.STATE_DRAGGING);
      }
    }
  }

  @Nullable
  protected List<DrawerLayout.DrawerListener> getDrawerListeners() {
    try {
      Field field = DrawerLayout.class.getDeclaredField("mListeners");
      field.setAccessible(true);
      // noinspection unchecked
      return (List<DrawerLayout.DrawerListener>) field.get(drawerLayout);
    } catch (Exception e) {
      // throw to let developer know the api is changed
      throw new RuntimeException(e);
    }
  }

  protected void setDrawerToOffset(int gravity, float offset) {
    View drawerView = findDrawerWithGravity(gravity);
    float slideOffsetPercent = MathUtils.clamp(offset / requireNonNull(drawerView).getWidth(), 0f, 1f);
    try {
      Method method = DrawerLayout.class.getDeclaredMethod("moveDrawerToOffset", View.class, float.class);
      method.setAccessible(true);
      method.invoke(drawerLayout, drawerView, slideOffsetPercent);
      drawerView.setVisibility(VISIBLE);
    } catch (Exception e) {
      // throw to let developer know the api is changed
      throw new RuntimeException(e);
    }
  }

  // Copied from DrawerLayout
  @Nullable
  private View findDrawerWithGravity(int gravity) {
    final int absHorizontalGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(drawerLayout)) & Gravity.HORIZONTAL_GRAVITY_MASK;
    final int childCount = drawerLayout.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = drawerLayout.getChildAt(i);
      final int childAbsGravity = getDrawerViewAbsoluteGravity(child);
      if ((childAbsGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == absHorizontalGravity) {
        return child;
      }
    }
    return null;
  }

  // Copied from DrawerLayout
  private int getDrawerViewAbsoluteGravity(View drawerView) {
    final int gravity = ((DrawerLayout.LayoutParams) drawerView.getLayoutParams()).gravity;
    return GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(drawerLayout));
  }
}