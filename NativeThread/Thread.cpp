/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/cppFiles/file.cc to edit this template
 */

#include <jni.h>
#include <processthreadsapi.h>
#include "Thread.h"

JavaVM *jvm;

// Parámetros que se le pasan al hilo, solamente se le pasa el hilo
struct ThreadParameters {
    jobject thread;
};

// Función que el hilo ejecutará
DWORD WINAPI threadFunction(LPVOID params)
{
    // Se obtiene el objeto que representa al hilo en java
    ThreadParameters *parameters = reinterpret_cast<ThreadParameters *>(params);
    jobject thread = parameters->thread;
    delete parameters;
    
    // Se añade el hilo actual a la máquina virtual de java para que pueda modificar al programa de java.
    JNIEnv *env;
    jvm->AttachCurrentThread((void **) &env, NULL);
    
    // Se busca al método "run" del objeto y se ejecuta.
    jclass clazz = env->GetObjectClass(thread);
    jmethodID run = env->GetMethodID(clazz, "run", "()V"); //get the run method function pointer.
    env->CallObjectMethod(thread, run); //call RunnableInterface.run();
    
    // Cuando termina el hilo, se libera la memoria reservada
    env->DeleteGlobalRef(thread);
    jvm->DetachCurrentThread();
    
    return 0;
}

// Se crea un nuevo hilo basado en un objeto de java suministrado
JNIEXPORT jint JNICALL Java_easyquiz_thread_JThread_create(JNIEnv *env, jobject thread)
{
    // Se guarda una referencia a la máquina virtual de java
    if (jvm == nullptr) {
        env->GetJavaVM(&jvm);
    }
    
    ThreadParameters* params = new ThreadParameters(); // Se crea un objeto para guardar el hilo de java.
    params->thread = env->NewGlobalRef(thread);

    // Se crea el hilo y se pasa el objeto de java como parámetro
    CreateThread(NULL, 0, threadFunction, reinterpret_cast<LPVOID>(params), 0, NULL);
    
    return 0;
}