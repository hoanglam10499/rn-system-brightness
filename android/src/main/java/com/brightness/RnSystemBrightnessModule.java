package com.brightness;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RnSystemBrightnessModule extends ReactContextBaseJavaModule {

    private static ReactApplicationContext reactContext;
    private static final String PERMISSION_EVENT_NAME = "RNSystemBrightnessPermission";
    private static final int BRIGHTNESS_MIN = 0;

    public RnSystemBrightnessModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNSystemBrightness";
    }

    private int maxBrightness() {
        int brightness = 255;
        try {
            Resources system = Resources.getSystem();
            int resId = system.getIdentifier("config_screenBrightnessSettingMaximum", "integer", "android");
            if (resId != 0) {
                brightness = system.getInteger(resId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return brightness;
    }


    @ReactMethod
    public void getMaxBrightness(final Promise promise) {
        promise.resolve(maxBrightness());
    }


    /**
     * Called by the main activity when the ACTION_MANAGE_WRITE_SETTINGS result is received.
     * Emits the result into the JS context.
     */
    public void onPermissionResult() {
        WritableMap payload = new WritableNativeMap();
        boolean hasPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                Settings.System.canWrite(getReactApplicationContext());

        payload.putBoolean("hasPermission", hasPermission);

        getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(PERMISSION_EVENT_NAME, payload);
    }

    /**
     * Returns whether the device has granted the application permission to write settings.
     *
     * @return True if WRITE_SETTINGS are granted.
     */
    private boolean hasSettingsPermission() {
        // By default, Android versions earlier than 6 have permission.
        boolean hasPermission = true;

        // Check for permisions if >= Android 6
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasPermission = Settings.System.canWrite(getReactApplicationContext());
        }

        return hasPermission;
    }

    /**
     * Invokes the request permission activity to request access for WRITE_SETTINGS.
     */
    private void requestSettingsPermission() {
        ReactApplicationContext application = getReactApplicationContext();

        if (!hasSettingsPermission()) {
            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + application.getPackageName())
            );
//            application.startActivityForResult(intent, 0, null);
            application.startActivity(intent, null);
        }
    }

    /**
     * Gets the brightness level of the device settings.
     *
     * @return The brightness level.
     */
    private Integer getSystemBrightness() {
        Integer brightness;
        try {
            brightness = Settings.System.getInt(
                    getReactApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
            );
        } catch (Settings.SettingNotFoundException e) {
            brightness = null;
        }
        return brightness;
    }

    /**
     * Sets the brightness level to the device settings.
     *
     * @param brightness The brightness level between 0-255.
     * @return True if the brightness has been set.
     */
    private boolean setSystemBrightness(int brightness) {
        if (hasSettingsPermission()) {
            // ensure brightness is bound between range 0-255
            brightness = Math.max(BRIGHTNESS_MIN, brightness);
            Settings.System.putInt(
                    getReactApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    brightness
            );
            return true;
        }
        return false;
    }

    /**
     * Updates the device brightness.
     * This method is callable from the JS context.
     *
     * @param brightness The brightness level between 0-1.
     * @param promise    A promise resolving if the brightness was updated.
     */
    @ReactMethod
    private void setSystemBrightness(float brightness, final Promise promise) {
        if (setSystemBrightness((int) (brightness))) {
            promise.resolve(brightness);
        } else {
            promise.reject(new Error("Unable to set system brightness"));
        }
    }

    /**
     * Determines if the application has been granted WRITE_SETTINGS permissions.
     * This method is callable from the JS context.
     *
     * @param promise A promise resolving if the application has WRITE_SETTINGS granted.
     */
    @ReactMethod
    public void hasPermission(final Promise promise) {
        promise.resolve(hasSettingsPermission());
    }

    /**
     * Invokes the permission request flow.
     * This method is callable from the JS context.
     */
    @ReactMethod
    public void requestPermission() {
        requestSettingsPermission();
    }

    /**
     * Gets the brightness level of the device.
     * This method is callable from the JS context.
     *
     * @param promise A promise resolving the brightness level.
     * @deprecated Use {@link #getSystemBrightness()} instead.
     */
    @ReactMethod
    public void getBrightness(final Promise promise) {
        promise.resolve(getSystemBrightness());
    }

    /**
     * Updates the device brightness.
     * This method is callable from the JS context.
     *
     * @param brightness The brightness level between 0-1.
     * @param promise    A promise resolving if the brightness was updated.
     * @deprecated Use {@link #setSystemBrightness(int)} instead.
     */
    @ReactMethod
    public void setBrightness(float brightness, final Promise promise) {
        setSystemBrightness(brightness, promise);
    }
}
