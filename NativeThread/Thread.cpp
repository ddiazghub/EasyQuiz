/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/cppFiles/file.cc to edit this template
 */

#include <jni.h>
#include <processthreadsapi.h>
#include "Thread.h"

JavaVM *jvm;

struct ThreadParameters {
    jobject thread;
};

DWORD WINAPI threadFunction(LPVOID params)
{
    ThreadParameters *parameters = reinterpret_cast<ThreadParameters *>(params);
    jobject thread = parameters->thread;
    delete parameters;
    
    JNIEnv *env;
    jvm->AttachCurrentThread((void **) &env, NULL);
    
    jclass clazz = env->GetObjectClass(thread);
    jmethodID run = env->GetMethodID(clazz, "run", "()V"); //get the run method function pointer.
    env->CallObjectMethod(thread, run); //call RunnableInterface.run();
    env->DeleteGlobalRef(thread);
    jvm->DetachCurrentThread();
    
    return 0;
}

JNIEXPORT jint JNICALL Java_easyquiz_thread_JThread_create(JNIEnv *env, jobject thread)
{
    if (jvm == nullptr) {
        env->GetJavaVM(&jvm);
    }
    
    ThreadParameters* params = new ThreadParameters(); //create an object to store our parameters.
    params->thread = env->NewGlobalRef(thread); //store the runnable object.

    //create a thread that calls "RunnableThreadProc" and passes it "ptr" as a param.
    CreateThread(NULL, 0, threadFunction, reinterpret_cast<LPVOID>(params), 0, NULL);
    
    return 0;
}