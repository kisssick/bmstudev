# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

# Declares and names the project.

project("myapplication")

add_library( # Sets the name of the library.
        myapplication

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp)

MESSAGE(${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/spdlog/spdlog/include)
target_include_directories(myapplication PUBLIC
        ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/spdlog/spdlog/include
        ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/mbedtls/mbedtls/mbedtls/include
        )

add_library(spdlog STATIC IMPORTED)
message(${ANDROID_ABI})
set_target_properties(spdlog PROPERTIES IMPORTED_LOCATION
        ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/spdlog/build/x86_64/libspdlog.a)

add_library(mbedcrypto SHARED IMPORTED)
set_target_properties(mbedcrypto PROPERTIES IMPORTED_LOCATION
        ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/mbedtls/mbedtls/build/x86_64/library/libmbedcrypto.so)


find_library( # Sets the name of the path variable.
        log-lib

        log)

target_link_libraries( # Specifies the target library.
        myapplication
        spdlog
        mbedcrypto
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})
