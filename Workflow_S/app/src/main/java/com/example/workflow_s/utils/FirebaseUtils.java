package com.example.workflow_s.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-11
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class FirebaseUtils {

    public interface UploadImageListener {
        void onFinishedUploadToFirebase(Uri downloadImageURL, int orderContent);
    }

    public static void uploadImageToStorage(byte[] imageByte, final int orderContent, final UploadImageListener callback) {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference baseStorageReference = storage.getReferenceFromUrl("gs://workflows-1559988095103.appspot.com/");

        String imageFileName = "WORKFLOW_JPEG_" + UUID.randomUUID().toString();
        final StorageReference storageReference = baseStorageReference.child("workflow_s/" + imageFileName +".jpg");

//        UploadTask uploadTask = storageReference.putFile(imageURI);
        UploadTask uploadTask = storageReference.putBytes(imageByte);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    callback.onFinishedUploadToFirebase(task.getResult(), orderContent);
                } else {
                    callback.onFinishedUploadToFirebase(null, orderContent);
                }
            }
        });
    }

}
