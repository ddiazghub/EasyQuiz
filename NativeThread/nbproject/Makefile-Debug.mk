#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=MinGW_1-Windows
CND_DLIB_EXT=dll
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/a31bd810/Thread.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=-m64
CXXFLAGS=-m64

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ../EasyQuiz/lib/thread.${CND_DLIB_EXT}

../EasyQuiz/lib/thread.${CND_DLIB_EXT}: ${OBJECTFILES}
	${MKDIR} -p ../EasyQuiz/lib
	${LINK.cc} -o ../EasyQuiz/lib/thread.${CND_DLIB_EXT} ${OBJECTFILES} ${LDLIBSOPTIONS} -shared

${OBJECTDIR}/_ext/a31bd810/Thread.o: /C/Users/david/code/uninorte/22-10/ds2/java/easyquiz/NativeThread/Thread.cpp
	${MKDIR} -p ${OBJECTDIR}/_ext/a31bd810
	${RM} "$@.d"
	$(COMPILE.cc) -g -I/C/Program\ Files/Java/jdk1.8.0_201/include -I/C/Program\ Files/Java/jdk1.8.0_201/include/win32 -std=c++11  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a31bd810/Thread.o /C/Users/david/code/uninorte/22-10/ds2/java/easyquiz/NativeThread/Thread.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc