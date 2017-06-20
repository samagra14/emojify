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
    public static final String LOG_TAG = "class Emojifier";
    private static final double SMILING_PROB_THRESHOLD = .15;
    private static final double EYE_OPEN_PROB_THRESHOLD = .5;

    public static void detectFaces(Context context, Bitmap bitmap){


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
        else{
            for (int i = 0; i < faces.size(); i++) {
                Face face = faces.valueAt(i);
                whichEmoji(face);
            }
        }

        //release the detector object
        detector.release();
    }

    public static void whichEmoji(Face face){
        Log.e(LOG_TAG,"whichEmoji: smilingProb = " + face.getIsSmilingProbability());
        Log.e(LOG_TAG,"whichEmoji: leftEyeOpenProb = " + face.getIsLeftEyeOpenProbability());
        Log.e(LOG_TAG,"whichEmoji: rightEyeOpenProb = " + face.getIsRightEyeOpenProbability());

        boolean smiling = face.getIsSmilingProbability() > SMILING_PROB_THRESHOLD;
        boolean leftEyeClosed = face.getIsLeftEyeOpenProbability() < EYE_OPEN_PROB_THRESHOLD;
        boolean rightEyeClosed = face.getIsRightEyeOpenProbability() < EYE_OPEN_PROB_THRESHOLD;

        // Determine and log the appropriate emoji
        Emoji emoji;
        if(smiling){
            if(leftEyeClosed&&!rightEyeClosed)
                emoji = Emoji.LEFT_WINK;
            else if(rightEyeClosed&&!leftEyeClosed)
                emoji = Emoji.RIGHT_WINK;
            else if (leftEyeClosed)
                emoji = Emoji.CLOSED_EYE_SMILE;
            else
                emoji = Emoji.SMILE;
        }
        else {
            if(leftEyeClosed&&!rightEyeClosed)
                emoji = Emoji.LEFT_WINK_FROWN;
            else if(rightEyeClosed&&!leftEyeClosed)
                emoji = Emoji.RIGHT_WINK_FROWN;
            else if (leftEyeClosed)
                emoji = Emoji.CLOSED_EYE_FROWN;
            else
                emoji = Emoji.FROWN;
        }

        //Log the chosen emoji
        Log.e(LOG_TAG,"whichEmoji: " + emoji.name());
    }

    private enum Emoji{
        SMILE,
        FROWN,
        LEFT_WINK,
        RIGHT_WINK,
        LEFT_WINK_FROWN,
        RIGHT_WINK_FROWN,
        CLOSED_EYE_SMILE,
        CLOSED_EYE_FROWN;
    }
}
