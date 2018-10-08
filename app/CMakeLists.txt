# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(distribution_DIR ../../../../libs)

add_library( native-lib
             SHARED
             src/main/cpp/native-lib.cpp

              )
add_library( avcodec-56
             SHARED
             IMPORTED)
set_target_properties( avcodec-56
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/armeabi-v7a/libavcodec.so)
add_library( avfilter-5
             SHARED
             IMPORTED)
set_target_properties( avfilter-5
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/armeabi-v7a/libavfilter.so)

add_library( avformat-56
             SHARED
             IMPORTED)
set_target_properties( avformat-56
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/armeabi-v7a/libavformat.so)

add_library( avutil-54
             SHARED
             IMPORTED)
set_target_properties( avutil-54
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/armeabi-v7a/libavutil.so)

add_library( swresample-1
             SHARED
             IMPORTED)
set_target_properties( swresample-1
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/armeabi-v7a/libswresample.so)

add_library( swscale-3
             SHARED
             IMPORTED)
set_target_properties( swscale-3
                       PROPERTIES IMPORTED_LOCATION
                       ${distribution_DIR}/armeabi-v7a/libswscale.so)
include_directories(libs/include)
target_link_libraries( native-lib
                       avcodec-56
                       avfilter-5
                       avformat-56
                       avutil-54
                       swresample-1
                       swscale-3
                       log
                       android
                       OpenSLES
                        )