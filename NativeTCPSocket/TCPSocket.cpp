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

// Se inicializa la librería winsock del sistema operativo windows
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_winsockInit(JNIEnv *env, jclass clazz)
{
    // Se carga el respectivo dll
    if (WSAStartup(MAKEWORD(2,2), &winsock) != 0)
    {
        std::cerr << "Could not initialize winsock. Error code: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    // Al finalizar el programa se libera la memoria
    atexit((void (*)()) WSACleanup);
    
    return 0;
}

// Se crea un nuevo socket
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_socket(JNIEnv *env, jclass clazz)
{
    // Se crea el socket con protocolo ip y protocolo TCP
    SOCKET s = socket(AF_INET, SOCK_STREAM, 6);
    
    // Si no se logró crear el socket se muestra un error
    if(s == INVALID_SOCKET)
    {
        std::cerr << "Could not create socket: " << WSAGetLastError() << std::endl;
        return -1;
    }

    // Se retorna el descriptor del socket
    return (jint) s;
}

// Se conecta el socket a un servidor
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_connect(JNIEnv *env, jclass clazz, jint handle, jstring ip, jint port)
{
    // Dirección ip del servidor
    const char *ipAddress = env->GetStringUTFChars(ip, nullptr);
    
    // Dirección ip y puerto del servidor
    struct sockaddr_in server;
    
    server.sin_family = AF_INET;
    server.sin_addr.S_un.S_addr = inet_addr(ipAddress);
    server.sin_port = htons(port);
    
    // Se establece la conexión
    if (connect(handle, (struct sockaddr*) &server, sizeof(server)) == SOCKET_ERROR)
    {
        std::cerr << "Could not connect to server: " << WSAGetLastError() << std::endl;
        env->ReleaseStringUTFChars(ip, ipAddress);
        
        return -1;
    }

    env->ReleaseStringUTFChars(ip, ipAddress);
    
    return 0;
}

// Se asigna el socket a una dirección local
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_bind(JNIEnv *env, jclass clazz, jint handle, jint port)
{   
    // Dirección IP y puerto local en el cual estará el socket
    struct sockaddr_in address;
    
    address.sin_family = AF_INET;
    address.sin_addr.S_un.S_addr = inet_addr("0.0.0.0");
    address.sin_port = htons(port);
    
    // Se asigna el socket a la dirección establecida
    if (bind(handle, (struct sockaddr *) &address, sizeof(address)) == SOCKET_ERROR)
    {
        std::cerr << "Could not bind socket. Error code: " << WSAGetLastError() << std::endl;

        return -1;
    }
    
    return 0;
}

// Se pone al servidor en modo de escucha para aceptar conexiones incidentes
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_listen(JNIEnv *env, jclass clazz, jint handle)
{
    // Se pone el servidor en modo de escucha
    if (listen(handle, SOMAXCONN) == SOCKET_ERROR)
    {
        std::cerr << "Could start listening on socket " << handle << ". Error code: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    return 0;
}

// Se accepta una conexión incidente
JNIEXPORT jobject JNICALL Java_easyquiz_tcpsocket_TCPSocket_accept(JNIEnv *env, jclass clazz, jint handle)
{   
    // Información del socket que se está conectando
    struct sockaddr_in client;
    int size = sizeof(client);
    
    // Se acepta la conexión
    int clientHandle = accept(handle, (struct sockaddr *) &client, &size);
    
    if (clientHandle == INVALID_SOCKET)
    {
        std::cerr << "Could not accept connection. Error code: " << WSAGetLastError() << std::endl;
        return env->NewGlobalRef(NULL);
    }
    
    // Se busca el constructor de la clase TCPSocket
    jclass klazz = env->FindClass("easyquiz/tcpsocket/TCPSocket");
    jmethodID method = env->GetMethodID(klazz, "<init>", "(ILjava/lang/String;I)V");
    
    // Se retorna una instancia de la clase TCPSocket con los datos del nuevo socket cliente
    return env->NewObject(klazz, method, clientHandle, env->NewStringUTF(inet_ntoa(client.sin_addr)), client.sin_port);
}

// Se envían datos mediante el socket
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_send(JNIEnv *env, jclass clazz, jint handle, jbyteArray data)
{
    // Se convierten los datos a enviar en una cadena de caracteres
    int length = env->GetArrayLength(data);
    jbyte *bytes = env->GetByteArrayElements(data, nullptr);
    std::string message(reinterpret_cast<char *>(bytes), length);
    
    // Se envían los datos
    if (send(handle, message.c_str(), length, 0) == SOCKET_ERROR)
    {
        std::cerr << "Could not send from socket " << handle << ". Error Code: " << WSAGetLastError() << std::endl;
        return -1;
    }

    env->ReleaseByteArrayElements(data, bytes, 0);
    
    return 0;
}

// El hilo espera hasta recibir datos y los retorna
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_receive(JNIEnv *env, jclass clazz, jint handle, jbyteArray buffer)
{
    // Se reciben hasta 1024 bytes.
    char receiveBuffer[1024];
    int received = recv(handle, receiveBuffer, 1024, 0);
    
    if (received == SOCKET_ERROR)
    {
        std::cerr << "Could not receive in socket " << handle << ". Error Code: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    // Se llena un arreglo de bytes suministrado por java con los bytes recibidos
    env->SetByteArrayRegion(buffer, 0, received, (jbyte *) receiveBuffer);
    
    // Se retorna el número de bytes recibidos mediante el socket
    return received;
}

// Se cierra el hilo
JNIEXPORT jint JNICALL Java_easyquiz_tcpsocket_TCPSocket_close(JNIEnv *env, jclass clazz, jint handle)
{
    if(closesocket((SOCKET) handle) == SOCKET_ERROR)
    {
        std::cerr << "Could not close socket: " << WSAGetLastError() << std::endl;
        return -1;
    }
    
    return 0;
}