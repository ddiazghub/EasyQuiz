/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/cppFiles/file.cc to edit this template
 */

#include <jni.h>
#include <iostream>
#include <winsock2.h>
#include <string>
#include <cstring>
#include "TCPSocket.h"

WSADATA winsock;

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_winsockInit(JNIEnv *env, jclass clazz)
{
    if (WSAStartup(MAKEWORD(2,2), &winsock) != 0)
    {
        std::cerr << "Could not initialize winsock. Error code: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    atexit((void (*)()) WSACleanup);
    return 0;
}

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_socket(JNIEnv *env, jclass clazz)
{
    SOCKET s = socket(AF_INET, SOCK_STREAM, 6);
    
    if(s == INVALID_SOCKET)
    {
        std::cerr << "Could not create socket: " << WSAGetLastError() << std::endl;
        return -1;
    }

    return (jint) s;
}

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_connect(JNIEnv *env, jclass clazz, jint handle, jstring ip, jint port)
{
    const char *ipAddress = env->GetStringUTFChars(ip, nullptr);
    
    struct sockaddr_in server;
    
    server.sin_family = AF_INET;
    server.sin_addr.S_un.S_addr = inet_addr(ipAddress);
    server.sin_port = htons(port);
    
    if (connect(handle, (struct sockaddr*) &server, sizeof(server)) == SOCKET_ERROR)
    {
        std::cerr << "Could not connect to server: " << WSAGetLastError() << std::endl;
        env->ReleaseStringUTFChars(ip, ipAddress);
        
        return -1;
    }

    env->ReleaseStringUTFChars(ip, ipAddress);
    
    return 0;
}

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_bind(JNIEnv *env, jclass clazz, jint handle, jint port)
{   
    struct sockaddr_in address;
    
    address.sin_family = AF_INET;
    address.sin_addr.S_un.S_addr = inet_addr("127.0.0.1");
    address.sin_port = htons(port);
    
    if (bind(handle, (struct sockaddr *) &address, sizeof(address)) == SOCKET_ERROR)
    {
        std::cerr << "Could not bind socket. Error code: " << WSAGetLastError() << std::endl;

        return -1;
    }
    
    return 0;
}

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_listen(JNIEnv *env, jclass clazz, jint handle)
{
    if (listen(handle, SOMAXCONN) == SOCKET_ERROR)
    {
        std::cerr << "Could start listening on socket " << handle << ". Error code: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    return 0;
}

JNIEXPORT jobject JNICALL Java_easyquiz_tcpsocket_TCPSocket_accept(JNIEnv *env, jclass clazz, jint handle)
{   
    struct sockaddr_in client;
    int size = sizeof(client);
    int clientHandle = accept(handle, (struct sockaddr *) &client, &size);
    
    if (clientHandle == INVALID_SOCKET)
    {
        std::cerr << "Could not accept connection. Error code: " << WSAGetLastError() << std::endl;
        return env->NewGlobalRef(NULL);
    }
    
    jclass klazz = env->FindClass("easyquiz/tcpsocket/TCPSocket");
    jmethodID method = env->GetMethodID(klazz, "<init>", "(ILjava/lang/String;I)V");
    
    return env->NewObject(klazz, method, clientHandle, env->NewStringUTF(inet_ntoa(client.sin_addr)), client.sin_port);
}

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_send(JNIEnv *env, jclass clazz, jint handle, jbyteArray data)
{
    int length = env->GetArrayLength(data);
    jbyte *bytes = env->GetByteArrayElements(data, nullptr);
    std::string message(reinterpret_cast<char *>(bytes), length);
    
    if (send(handle, message.c_str(), length, 0) == SOCKET_ERROR)
    {
        std::cerr << "Could not send from socket " << handle << ". Error Code: " << WSAGetLastError() << std::endl;
        return -1;
    }

    env->ReleaseByteArrayElements(data, bytes, 0);
    
    return 0;
}

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_receive(JNIEnv *env, jclass clazz, jint handle, jbyteArray buffer)
{
    char receiveBuffer[128];
    int received = recv(handle, receiveBuffer, 128, 0);
    std::cout << "Received " << received << " bytes. Message: " << std::string(receiveBuffer) << std::endl;
    
    if (received == SOCKET_ERROR)
    {
        std::cerr << "Could not receive in socket " << handle << ". Error Code: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    env->SetByteArrayRegion(buffer, 0, received, (jbyte *) receiveBuffer);
    return received;
}

JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_close(JNIEnv *env, jclass clazz, jint handle)
{
    if(closesocket((SOCKET) handle) == SOCKET_ERROR)
    {
        std::cerr << "Could not close socket: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    return 0;
}