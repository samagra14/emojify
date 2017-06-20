package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by samagra on 20/6/17.
 */

public class Emojifier {

    public static void detectFaces(Context context, Bitmap bitmap){

         final String LOG_TAG = "class Emojifier";
        //Creating the face detector object
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        // build the frame
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<Face> faces = detector.detect(frame);

        //Log the number of faces
        Log.e(LOG_TAG,"Number of faces: = "+ faces.size());

        //if there are no faces show a toast message
        if(faces.size()==0)
            Toast.makeText(context, "There are no faces in the picture", Toast.LENGTH_SHORT).show();

        //release the detector object
        detector.release();
    }
}
