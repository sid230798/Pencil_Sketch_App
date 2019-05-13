------------------------------------------
Dependencies : 

USER:~PATH$ sudo apt-get install ant

* Download zip for Opencv in java

USER:~PATH$ git clone https://github.com/Itseez/opencv.git

*Extract the zip in home folder and run below commands 

*First, create a directory for the build in the cloned directory.

USER:~PATH$ mkdir build
USER:~PATH$ cd build
USER:~PATH cmake -D BUILD_SHARED_LIBS=ON -D BUILD_LIBPROTOBUF_FROM_SOURCES=ON ..
USER:~PATH make -j4


For Compilation and Running :

USER:~PATH$ javac -cp JARFILE_JAVA_PATH Trial.java
USER:~PATH$ java -Djava.library.path=/home/USERNAME/opencv/build/lib -cp .:JARFILE_JAVA Main IMAGE_PATH

--------------------------------------------

Sample Run :

USER:~PATH$ java -Djava.library.path=/home/USERNAME/opencv/build/lib -cp .:opencv-400.jar Main Chinese.jpg

--------------------------------------------


