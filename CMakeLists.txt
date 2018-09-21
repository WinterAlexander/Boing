cmake_minimum_required(VERSION 3.7)
project(Boing)

find_package(Threads REQUIRED)

if(CMAKE_COMPILER_IS_GNUCXX)
    add_definitions(-Wall -ansi -Wno-deprecated -pthread)
endif()

set(CMAKE_CXX_STANDARD 17)

include_directories(include)
file(GLOB SOURCE_FILES
        ${PROJECT_SOURCE_DIR}/src/main/cpp/*.cpp
        ${PROJECT_SOURCE_DIR}/include/*.h
        ${PROJECT_SOURCE_DIR}/src/main/cpp/*/*.cpp
        ${PROJECT_SOURCE_DIR}/include/*/*.h
        ${PROJECT_SOURCE_DIR}/src/main/cpp/*/*/*.cpp
        ${PROJECT_SOURCE_DIR}/include/*/*/*.h)

add_library(Boing_lib ${SOURCE_FILES})
add_executable(Boing ${SOURCE_FILES})


add_subdirectory(lib/gtest)
enable_testing()
include_directories(${GTEST_INCLUDE_DIRS} include)


file(GLOB TEST_SRC_FILES ${PROJECT_SOURCE_DIR}/src/test/cpp/*.cpp)
add_executable(Boing_tests ${TEST_SRC_FILES})
add_dependencies(Boing_tests googletest Boing_lib)


if(NOT WIN32 OR MINGW)
    target_link_libraries(Boing_tests
            ${GTEST_LIBS_DIR}/libgtest.a
            ${GTEST_LIBS_DIR}/libgtest_main.a
            )
else()
    target_link_libraries(Boing_tests
            debug ${GTEST_LIBS_DIR}/DebugLibs/${CMAKE_FIND_LIBRARY_PREFIXES}gtest${CMAKE_FIND_LIBRARY_SUFFIXES}
            optimized ${GTEST_LIBS_DIR}/ReleaseLibs/${CMAKE_FIND_LIBRARY_PREFIXES}gtest${CMAKE_FIND_LIBRARY_SUFFIXES}
            )
    target_link_libraries(Boing_tests
            debug ${GTEST_LIBS_DIR}/DebugLibs/${CMAKE_FIND_LIBRARY_PREFIXES}gtest_main${CMAKE_FIND_LIBRARY_SUFFIXES}
            optimized ${GTEST_LIBS_DIR}/ReleaseLibs/${CMAKE_FIND_LIBRARY_PREFIXES}gtest_main${CMAKE_FIND_LIBRARY_SUFFIXES}
            )
endif()

target_link_libraries(Boing_tests ${CMAKE_THREAD_LIBS_INIT} Boing_lib)
