package wiredprogrammers.toastcatcher;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class ToastCatcherService extends AccessibilityService {
    public ToastCatcherService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
            return; // event is not a notification

        String sourcePackageName = (String) event.getPackageName();

        Parcelable parcelable = event.getParcelableData();
        if (!(parcelable instanceof Notification) && !sourcePackageName.contains("toast")) {
            // something else, e.g. a Toast message
            String log = "Message: " + event.getText().get(0)
                    + " [Source: " + sourcePackageName + "]";
            Toast.makeText(this, log, Toast.LENGTH_LONG).show();
            Log.e("New Event", log);
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(this, "Access granted", Toast.LENGTH_SHORT).show();
    }
}
