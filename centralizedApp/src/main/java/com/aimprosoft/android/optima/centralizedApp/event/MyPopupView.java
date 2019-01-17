package com.aimprosoft.android.optima.centralizedApp.event;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aimprosoft.android.optima.centralizedApp.R;

public class MyPopupView {

    private static final String PARAM_STATUS_BAR_HEIGHT = "status_bar_height";
    private static final String PARAM_DIMEN = "dimen";
    private static final String PARAM_ANDROID = "android";

    private static final int X_INDEX = 0;
    private static final int Y_INDEX = 1;

    private Context context;

    private PopupWindow popupWindow;
    private RelativeLayout bottomRootLayout;

    public MyPopupView(Context context, int animationStyle, RelativeLayout bottomRootLayout) {
        this.context = context;
        this.bottomRootLayout = bottomRootLayout;

        initPopupWindow(animationStyle);
    }

    private void initPopupWindow(int animationStyle) {
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(animationStyle);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                } else return false;
            }
        });
    }

    public void setMaxHeightResource(int heightResource) {
        int maxHeight = context.getResources().getDimensionPixelSize(heightResource);
        popupWindow.setHeight(maxHeight);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void dismiss() {
        popupWindow.dismiss();
    }

    public void show(View anchor, String text) {
        try {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);

            Rect anchorRect = new Rect(location[X_INDEX], location[Y_INDEX],
                    location[X_INDEX] + anchor.getWidth() / 2, location[Y_INDEX]);

            boolean onBottom = true;

            if (bottomRootLayout.getLayoutParams() == null) bottomRootLayout.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            bottomRootLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            int rootHeight = bottomRootLayout.getMeasuredHeight();
//            int rootWidth = bottomRootLayout.getMeasuredWidth();

//            int x = calculateHorizontalPosition(anchor, anchorRect, rootWidth, screenWidth);
            int x = anchorRect.left;
            int y = calculateVerticalPosition(anchorRect, rootHeight, onBottom);

            TextView textView = (TextView) bottomRootLayout.findViewById(R.id.contentText);
            textView.setText(text);

            popupWindow.setContentView(bottomRootLayout);
            popupWindow.dismiss();
            popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int calculateHorizontalPosition(View anchor, Rect anchorRect, int rootWidth, int screenWidth) {
        return anchorRect.left;
    }

    @SuppressWarnings("ConstantConditions")
    private int calculateVerticalPosition(Rect anchorRect, int rootHeight, boolean onBottom) {
        int y;

        if (onBottom) {
            y = anchorRect.bottom + rootHeight/2;
        } else {
            y = anchorRect.top - rootHeight;
        }

        return y;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(PARAM_STATUS_BAR_HEIGHT, PARAM_DIMEN, PARAM_ANDROID);
        if (resourceId > 0) result = context.getResources().getDimensionPixelSize(resourceId);

        return result;
    }

}