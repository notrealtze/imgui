package imgui.example.android

import android.content.res.AssetManager

object ImGuiNative {
    external fun init(assetManager: AssetManager?)
    external fun resize(width: Int, height: Int)
    external fun render()
    external fun onTouch(x: Float, y: Float, pressed: Boolean)

    init {
        System.loadLibrary("ImGuiExample")
    }
}
