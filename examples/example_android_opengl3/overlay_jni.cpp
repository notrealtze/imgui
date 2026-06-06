#include <jni.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <GLES3/gl3.h>
#include "imgui.h"
#include "imgui_impl_opengl3.h"

static bool g_Initialized = false;
static int g_Width = 0;
static int g_Height = 0;
static float g_TouchX = 0;
static float g_TouchY = 0;
static bool g_TouchPressed = false;

extern "C" {

JNIEXPORT void JNICALL
Java_imgui_example_android_ImGuiNative_init(JNIEnv* env, jobject, jobject assetManager) {
    if (g_Initialized) return;

    IMGUI_CHECKVERSION();
    ImGui::CreateContext();
    ImGuiIO& io = ImGui::GetIO();
    io.IniFilename = nullptr;
    ImGui::StyleColorsDark();
    ImGui_ImplOpenGL3_Init("#version 300 es");
    g_Initialized = true;
}

JNIEXPORT void JNICALL
Java_imgui_example_android_ImGuiNative_resize(JNIEnv*, jobject, jint width, jint height) {
    g_Width = width;
    g_Height = height;
    glViewport(0, 0, width, height);
}

JNIEXPORT void JNICALL
Java_imgui_example_android_ImGuiNative_render(JNIEnv*, jobject) {
    if (!g_Initialized) return;

    ImGuiIO& io = ImGui::GetIO();
    io.DisplaySize = ImVec2((float)g_Width, (float)g_Height);
    io.MousePos = ImVec2(g_TouchX, g_TouchY);
    io.MouseDown[0] = g_TouchPressed;

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);

    ImGui_ImplOpenGL3_NewFrame();
    ImGui::NewFrame();

    // Your overlay UI here
    ImGui::SetNextWindowPos(ImVec2(10, 10), ImGuiCond_Always);
    ImGui::SetNextWindowBgAlpha(0.75f);
    ImGui::Begin("Overlay", nullptr,
        ImGuiWindowFlags_NoDecoration |
        ImGuiWindowFlags_AlwaysAutoResize |
        ImGuiWindowFlags_NoSavedSettings);
    ImGui::Text("ImGui Overlay");
    ImGui::Text("FPS: %.1f", io.Framerate);
    ImGui::End();

    ImGui::Render();
    ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());
}

JNIEXPORT void JNICALL
Java_imgui_example_android_ImGuiNative_onTouch(JNIEnv*, jobject, jfloat x, jfloat y, jboolean pressed) {
    g_TouchX = x;
    g_TouchY = y;
    g_TouchPressed = pressed;
}

}
