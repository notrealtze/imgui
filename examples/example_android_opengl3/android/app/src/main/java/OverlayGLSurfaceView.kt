package imgui.example.android

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OverlayGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer = OverlayRenderer()

    init {
        setEGLContextClientVersion(3)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(android.graphics.PixelFormat.TRANSLUCENT)
        setZOrderOnTop(true)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                queueEvent {
                    ImGuiNative.onTouch(event.x, event.y, true)
                }
            }
            MotionEvent.ACTION_UP -> {
                queueEvent {
                    ImGuiNative.onTouch(event.x, event.y, false)
                }
            }
        }
        return true
    }
}

class OverlayRenderer : GLSurfaceView.Renderer {

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        ImGuiNative.init(null)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        ImGuiNative.resize(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        ImGuiNative.render()
    }
}
