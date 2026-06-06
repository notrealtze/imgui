package imgui.example.android

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OverlayService : Service() {

    private var windowManager: WindowManager? = null
    private var glSurfaceView: OverlayGLSurfaceView? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        glSurfaceView = OverlayGLSurfaceView(this)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
        }

        windowManager?.addView(glSurfaceView, params)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        glSurfaceView?.let { windowManager?.removeView(it) }
        glSurfaceView?.onPause()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
