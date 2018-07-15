#include <jni.h>
#include "windows.h"
#include "Native_WindowInformation.h"

// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT int[] JNICALL Java_HelloJNI_sayHello(JNIEnv *, jobject, jint) {
	cout << "Hello World from C++!" << endl;
   return;
}