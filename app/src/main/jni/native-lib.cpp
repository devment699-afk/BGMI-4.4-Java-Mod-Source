#include <jni.h>
#include <string>
#include "offsets.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_manus_bgmimod_FloatingWidgetService_getOffsetsInfo(
        JNIEnv* env,
        jobject /* this */) {
    std::string info = "Offsets Loaded: GEngine=" + std::to_string(GEngine_Offset);
    return env->NewStringUTF(info.c_str());
}
