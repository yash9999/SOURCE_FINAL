package com.example.yogeshgarg.source.mvp.profile;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Created by yogeshgarg on 12/08/17.
 */

public interface ProfilePresenter {

    public void callingProfileApi();

    public void callingUploadProfilePicApi(File filePath);

    public void callingUpdateProfileInfo(String firstName,String lastName,String email,String phone);
}
