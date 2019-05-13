/*
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

class Hello {
   public static void main( String[] args ) {
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
      System.out.println("mat = " + mat.dump());
   }
}*/

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;


class Main {


   private static int Rows,Cols;     

   
   private static float Grad_Left(Mat obj,int index1,int index2){
   
        float maxG = 0;
        
        for(int i =0 ;i<index2;i++){
        
                double [] first = obj.get(index1,index2);
                double [] second = obj.get(index1,i);
                float g = (float)(first[0]-second[0])/(index2-i);
                
                if(Math.abs(g) >= Math.abs(maxG))
                        maxG = g;
        
        
        }
        
        return maxG;
   
   
   
   
   }
   private static float Grad_Right(Mat obj,int index1,int index2){
   
        float maxG = 0;
        
        for(int i =index2+1 ;i<obj.cols();i++){
        
                double [] first = obj.get(index1,index2);
                double [] second = obj.get(index1,i);
                float g = (float)(first[0]-second[0])/(i-index2);
                
                if(Math.abs(g) >= Math.abs(maxG))
                        maxG = g;
        
        
        }
        
        return maxG;
   
   
   
   
   }
   private static float Grad_Up(Mat obj,int index1,int index2){
   
        float maxG = 0;
        
        for(int i =0 ;i<index1;i++){
        
                double [] first = obj.get(index1,index2);
                double [] second = obj.get(i,index2);
                float g = (float)(first[0]-second[0])/(index1-i);
                
                if(Math.abs(g) >= Math.abs(maxG))
                        maxG = g;
        
        
        }
        
        return maxG;
   
   
   
   
   }
   private static float Grad_Down(Mat obj,int index1,int index2){
   
        float maxG = 0;
        
        for(int i =index1+1 ;i<obj.rows();i++){
        
        
                double [] first = obj.get(index1,index2);
                double [] second = obj.get(i,index2);
                float g = (float)(first[0]-second[0])/(i-index1);
                
                if(Math.abs(g) >= Math.abs(maxG))
                        maxG = g;
        
        
        }
        
        return maxG;
   
   
   
   
   }    


   public static void main( String[] args ){
   
      try {
         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
         
         
         Mat source = Imgcodecs.imread(args[0],
         Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
         
         
         //float a[][] = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};
         //System.out.println(s.type());
         /*
         Mat source = new Mat(3,3,CvType.CV_32FC1);

         
                        source.put(0,0,120);
                        source.put(0,1,56);
                        source.put(0,2,80);
                        
                        source.put(1,0,201);
                        source.put(1,1,10);
                        source.put(1,2,3);
                        
                        source.put(2,0,235);
                        source.put(2,1,225);
                        source.put(2,2,255);
         
          System.out.println("Source : "+source.dump());
          */
         //System.out.println(source.type());
         /*Smoothing the image by Guassian filter
           21 is for CV_32FC3
         */
         
         Mat destination = new Mat(source.rows(),source.cols(),5);
         //destination = source;
         System.out.println(source.rows()*source.cols());
         Imgproc.GaussianBlur(source, destination,new Size(5,5), 0);
         
         destination.convertTo(destination,CvType.CV_32FC1);
         Imgcodecs.imwrite("Guassian.jpg", destination);
         System.out.println(destination.type());
         Mat kernel = new Mat(3,3,5);
         
         kernel.put(0,0,0);
         kernel.put(0,1,-1);
         kernel.put(0,2,0);
         kernel.put(1,0,-1);
         kernel.put(1,1,4);
         kernel.put(1,2,-1);
         kernel.put(2,0,0);
         kernel.put(2,1,-1);
         kernel.put(2,2,0);
         //System.out.println("Estimator : "+kernel.dump());
         Mat Guas_Estimator = new Mat(source.rows(),source.cols(),5);
         Imgproc.filter2D(destination, Guas_Estimator, -1, kernel);
        int count = 0;
         for(int i = 0;i < source.rows();i++){
         
                for(int j =0 ;j < source.cols();j++){
                        
                        double[] val =   Guas_Estimator.get(i,j);
                        
                        if(val[0] < 0)
                                count++;
                        if(Math.abs(val[0]) < 4){
                               
                                Guas_Estimator.put(i,j,0);
                        }else
                                Guas_Estimator.put(i,j,val[0]);
                
                }
                
         }
         
                  
         System.out.println(count);
      
         
         //Imgcodecs.imwrite("Gaussian5.jpg", destination);
         
         //System.out.println("Guassian : "+destination.dump());
         Rows = source.rows();
         Cols = source.cols();
         
         /*Mat guassian estimater*/
         
         
         //Mat Guas_Estimator = new Mat(source.rows(),source.cols(),5);
         //System.out.println(source.type());
         //System.out.println(Mat.CV_32FC3);
         
         /*
         for(int i = 0;i < source.rows();i++){
         
                for(int j =0 ;j < source.cols();j++){
                
                         float left = Grad_Left(destination,i,j);
                         float right = Grad_Right(destination,i,j);
                         float down = Grad_Down(destination,i,j);             
                         float up = Grad_Up(destination,i,j);
                         
                         float grad_x = left+right;
                         float grad_y = up+down;
                         
                         float max = (Math.abs(grad_x) >= Math.abs(grad_y))?grad_x:grad_y;
                         //double [] data = {max};
                         
                         /*
                         if(max > 0)
                                count++;
                         
                         
                         if(Math.abs(max) < 4)
                                Guas_Estimator.put(i,j,0);
                         else
                         
                                Guas_Estimator.put(i,j,max);       
                                //System.out.println(Guas_Estimator.get(i,j)[0]);
                
                }
         
         
         }*/
         
         for(int i = 0;i < source.rows();i++){
         
                for(int j =0 ;j < source.cols();j++){
                
                        double[] val =   Guas_Estimator.get(i,j);
                        if(val[0] < 0){
                        
                                Guas_Estimator.put(i,j,120-val[0]);
                        }else
                                Guas_Estimator.put(i,j,255);     
                
                }
         }
         
         Imgcodecs.imwrite("output.jpg", Guas_Estimator);
         Mat Output = new Mat(source.rows(),source.cols(),5);
         Mat kernel1 = new Mat(3,3,5);
         
         kernel1.put(0,0,0.0625);
         kernel1.put(0,1,0.125);
         kernel1.put(0,2,0.0625);
         kernel1.put(1,0,0.125);
         kernel1.put(1,1,0.25);
         kernel1.put(1,2,0.125);
         kernel1.put(2,0,0.0625);
         kernel1.put(2,1,0.125);
         kernel1.put(2,2,0.0625);
         Imgproc.filter2D(Guas_Estimator,Output, -1, kernel1);
         //Imgproc.GaussianBlur(Guas_Estimator, Output,new Size(7,7), 0);
         
         //System.out.println("Estimator : "+Guas_Estimator.dump());
         //System.out.println(Guas_Estimator.dump());
         //System.out.println(count);
         //Imgcodecs.imwrite("output.jpg", Guas_Estimator);
         //Imgproc.filter2D(destination, Guas_Estimator, -1, kernel);
         //System.out.println("Estimator : "+Guas_Estimator.dump());
         Imgcodecs.imwrite("output.jpg", Output);
         
         
      } catch (Exception e) {
         System.out.println("Error: " + e.getMessage());
      }
   }
}
