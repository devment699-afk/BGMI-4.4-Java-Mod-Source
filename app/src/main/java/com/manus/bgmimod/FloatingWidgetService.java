package com.manus.bgmimod;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FloatingWidgetService extends Service {

    private WindowManager mWindowManager;
    private View mFloatingWidgetView;
    private LinearLayout mExpandedMenu;

    public FloatingWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Inflate the floating widget layout we defined in floating_menu.xml
        mFloatingWidgetView = LayoutInflater.from(this).inflate(R.layout.floating_menu, null);

        // Add the view to the window. 
        final WindowManager.LayoutParams params;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        // Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT; // Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        // Get window manager service
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingWidgetView, params);

        // The root layout of the floating widget (the menu itself)
        mExpandedMenu = mFloatingWidgetView.findViewById(R.id.floating_menu_root);

        // Set the close button
        Button closeButton = mFloatingWidgetView.findViewById(R.id.close_menu);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf(); // Stop the service, which removes the floating widget
            }
        });

        // Initialize CheckBoxes and set listeners
        setupCheckBoxListeners();

        // Make the floating widget draggable
        mFloatingWidgetView.findViewById(R.id.floating_menu_root).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        // If the user taps without dragging, consider it a click to toggle menu visibility
                        if (Math.abs(event.getRawX() - initialTouchX) < 5 && Math.abs(event.getRawY() - initialTouchY) < 5) {
                            // Handle click if needed, e.g., minimize/maximize
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingWidgetView, params);
                        return true;
                }
                return false;
            }
        });
    }

    private void setupCheckBoxListeners() {
        // Visuals
        CheckBox espToggle = mFloatingWidgetView.findViewById(R.id.esp_toggle);
        espToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "ESP: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual ESP logic here
        });

        CheckBox hideEspToggle = mFloatingWidgetView.findViewById(R.id.hide_esp_toggle);
        hideEspToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Hide ESP: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Hide ESP logic here
        });

        CheckBox sketchLineToggle = mFloatingWidgetView.findViewById(R.id.sketch_line_toggle);
        sketchLineToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Sketch Line: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Sketch Line logic here
        });

        // Combat
        CheckBox aimbotToggle = mFloatingWidgetView.findViewById(R.id.aimbot_toggle);
        aimbotToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Aimbot: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Aimbot logic here
        });

        CheckBox bulletTrackToggle = mFloatingWidgetView.findViewById(R.id.bullet_track_toggle);
        bulletTrackToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Bullet Track: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Bullet Track logic here
        });

        CheckBox magicBulletToggle = mFloatingWidgetView.findViewById(R.id.magic_bullet_toggle);
        magicBulletToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Magic Bullet: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Magic Bullet logic here
        });

        // Skins
        CheckBox xsuitToggle = mFloatingWidgetView.findViewById(R.id.xsuit_toggle);
        xsuitToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "X-Suits Skins: " + (isChecked ? "Enabled" : "Disabled" ), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual X-Suits Skins logic here
        });

        CheckBox gunSkinToggle = mFloatingWidgetView.findViewById(R.id.gun_skin_toggle);
        gunSkinToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Gun Skins: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Gun Skins logic here
        });

        CheckBox carSkinToggle = mFloatingWidgetView.findViewById(R.id.car_skin_toggle);
        carSkinToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Car Skins: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Car Skins logic here
        });

        CheckBox lootBoxToggle = mFloatingWidgetView.findViewById(R.id.loot_box_toggle);
        lootBoxToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Loot Box Skins: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Loot Box Skins logic here
        });

        CheckBox killMsgToggle = mFloatingWidgetView.findViewById(R.id.kill_msg_toggle);
        killMsgToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Kill Message Skins: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Kill Message Skins logic here
        });

        // Security
        CheckBox antibanToggle = mFloatingWidgetView.findViewById(R.id.antiban_toggle);
        antibanToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(FloatingWidgetService.this, "Antiban: " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            // TODO: Implement actual Antiban logic here
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingWidgetView != null) {
            mWindowManager.removeView(mFloatingWidgetView);
        }
    }
}
