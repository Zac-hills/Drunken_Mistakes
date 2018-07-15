#include <jni.h>
#include "windows.h"
#include "Native_WindowInformation.h"

struct handle_data {
	unsigned long process_id;
	HWND window_handle;
};

BOOL is_main_window(HWND handle)
{
	return GetWindow(handle, GW_OWNER) == (HWND)0 && IsWindowVisible(handle);
}

BOOL CALLBACK enum_windows_callback(HWND handle, LPARAM lParam)
{
	handle_data& data = *(handle_data*)lParam;
	unsigned long process_id = 0;
	GetWindowThreadProcessId(handle, &process_id);
	if (data.process_id != process_id || !is_main_window(handle))
		return TRUE;
	data.window_handle = handle;
	return FALSE;
}

HWND find_main_window(unsigned long process_id)
{
	handle_data data;
	data.process_id = process_id;
	data.window_handle = 0;
	EnumWindows(enum_windows_callback, (LPARAM)&data);
	return data.window_handle;
}

// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT jintArray JNICALL Java_Native_WindowInformation_GetNativeWindowBounds(JNIEnv *a_JNIEnvironment, jobject, jint a_PID) {
	jintArray l_Result;
	l_Result=a_JNIEnvironment->NewIntArray(4);
	HWND hWnd = NULL;
	hWnd = find_main_window(a_PID);
	LPRECT l_Buffer = new RECT();
	ShowWindow(hWnd, 3);
	GetWindowRect(hWnd, l_Buffer);
	jint temp[4];
	temp[0] = l_Buffer->left;
	temp[1] = l_Buffer->top;
	temp[2] = l_Buffer->right;
	temp[3] = l_Buffer->bottom;
	a_JNIEnvironment->SetIntArrayRegion(l_Result, 0, 4, temp);
	return l_Result;
}