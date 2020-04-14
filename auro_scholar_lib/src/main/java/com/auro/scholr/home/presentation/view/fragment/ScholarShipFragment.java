package com.auro.scholr.home.presentation.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.auro.scholr.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.disposables.Disposable;

/**
 * Created by dharmaraj on 28/4/17.
 * <p>
 * <p>
 * Home framgment
 */

public class ScholarShipFragment extends Fragment {
    public static final String TAG = "ScholarShipFragment";

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    protected Disposable subscriptionList;
    private WebView webView;
    private WebSettings webSettings;
    private ValueCallback<Uri[]> mUploadMessage;
    private String mCameraPhotoPath = null;
    private long size = 0;

   // private LinearLayout llTopBar;
  //  private ProgressBar pbPageLoading;
   // private AppCompatImageView ivHome;
   // TextView tvTitle;
    private View view;

    public static ScholarShipFragment newInstance() {
        return new ScholarShipFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        if (view == null) {
            view = inflater.inflate(R.layout.card_fragment_layout, container, false);


            webView = (WebView) view.findViewById(R.id.webView);
            webSettings = webView.getSettings();
            webSettings.setAppCacheEnabled(true);
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setDomStorageEnabled(true);
            webView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");

            webSettings.setAllowFileAccess(true);
            //      webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");

            webView.setWebViewClient(new PQClient());
            webView.setWebChromeClient(new PQChromeClient());
            //if SDK version is greater of 19 then activate hardware acceleration otherwise activate software acceleration
            if (Build.VERSION.SDK_INT >= 19) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            //ivHome = view.findViewById(R.id.ivHome);
            //  tvTitle = view.findViewById(R.id.tvTitle);

         /*   ivHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadWebData();
                }
            });
*/
           // loadWebData();

            //llTopBar = view.findViewById(R.id.llTopBar);
            // llTopBar.setVisibility(View.VISIBLE);
            //  pbPageLoading = view.findViewById(R.id.pbPageLoading);

            getUserCommunityProfileData();
        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void getUserCommunityProfileData() {
        loadWebData();
     /*  // if (ProfileManager.isUserLoggedIn()) {
            subscriptionList = ApiClient.get()
                    .getUserCommunityProfile(ProfileManager.getUserProfile().user_id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userCommunityProfileData -> {
                        userCommunityProfile = userCommunityProfileData;
                        String jsonData = new Gson().toJson(userCommunityProfile);
                        PrefManager.getInstance().setCommunityProfile(jsonData);
                        setUserCommunityProfileData();
                        loadWebData();

                    }, throwable -> {
                        LogUtils.e(throwable);
                    });
      } else {
         //   PrefManager.getInstance().setShowQuiz(true);
           // startActivity(AuroScholrWelcomeActivity.Companion.makeIntent());

        }*/

    }

    public void loadWebData() {
        webView.loadUrl(" http://auroscholar.com/api/scholarlogin.php?mobile_no=9759841719&student_class=6&scholr_id=577159&regitration_source=auro-google");

       /* if (userCommunityProfile != null && userCommunityProfile.getProfile() != null) {
            if (userCommunityProfile.getProfile().phone != null && userCommunityProfile.getProfile().grade != null) {
                webView.loadUrl("http://auroscholar.com/api/scholarlogin.php?mobile_no=" + userCommunityProfile.getProfile().phone + "&student_class=" + userCommunityProfile.getProfile().grade + "&scholr_id=" + userCommunityProfile.getProfile().user_id + "&regitration_source=" + userCommunityProfile.getDeviceSourceAuroScholr());
            } else if (userCommunityProfile.getProfile().phone == null) {
                Toast.makeText(getActivity(), "Please enter mobile number for auto-login", Toast.LENGTH_SHORT).show();
                webView.loadUrl("http://auroscholar.com/start_quiz.php?");
            } else if (userCommunityProfile.getProfile().grade == null) {
                Toast.makeText(getActivity(), "Please enter grade for auto-login", Toast.LENGTH_SHORT).show();
                webView.loadUrl("http://auroscholar.com/start_quiz.php?");
            }
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setUserCommunityProfileData() {
        // TODO: 30/1/19 communicate with ruchi(backend dev) for api to show data in daily attendance card and answer now card.
      /*  userCommunityProfile = PrefManager.getInstance().getCommunityProfile();
        if (userCommunityProfile != null && userCommunityProfile.getProfile() != null) {
            if (userCommunityProfile.getProfile().name == null) {
                userCommunityProfile.getProfile().name = "User";
            }
            PrefManager.getInstance().setTotalCoins(userCommunityProfile.getPointsEarned());

            if (userCommunityProfile.getProfile().name == null || userCommunityProfile.getProfile().name.equals("")) {
                userCommunityProfile.getProfile().name = "User";
            }

        } else {
            getUserCommunityProfileData();
        }*/
    }

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mUploadMessage == null) {
            super.onActivityResult(requestCode, resultCode, data);
            mUploadMessage.onReceiveValue(new Uri[]{});
            mUploadMessage = null;
            return;
        }
        try {
            String file_path = mCameraPhotoPath.replace("file:", "");
            File file = new File(file_path);
            size = file.length();

        } catch (Exception e) {
            Log.e("Error!", "Error while opening image file" + e.getLocalizedMessage());
        }

        if (data != null || mCameraPhotoPath != null) {
            Integer count = 0; //fix fby https://github.com/nnian
            ClipData images = null;
            try {
                images = data.getClipData();
            } catch (Exception e) {
                Log.e("Error!", e.getLocalizedMessage());
            }

            if (images == null && data != null && data.getDataString() != null) {
                count = data.getDataString().length();
            } else if (images != null) {
                count = images.getItemCount();
            }
            Uri[] results = new Uri[count];
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (size != 0) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else if (data.getClipData() == null) {
                    results = new Uri[]{Uri.parse(data.getDataString())};
                } else {

                    for (int i = 0; i < images.getItemCount(); i++) {
                        results[i] = images.getItemAt(i).getUri();
                    }
                }
                mUploadMessage.onReceiveValue(results);
                mUploadMessage = null;
            } else {
                mUploadMessage.onReceiveValue(new Uri[]{});
                mUploadMessage = null;
            }

        }
    }

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            mUploadMessage.onReceiveValue(new Uri[]{});
            mUploadMessage = null;
        }
//        else {
//            webView.evaluateJavascript("grantedPermission();", null);
//        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    public class PQChromeClient extends WebChromeClient {

        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            String[] requestedResources = request.getResources();
            ArrayList<String> permissions = new ArrayList<>();
            ArrayList<String> grantedPermissions = new ArrayList<String>();
            for (int i = 0; i < requestedResources.length; i++) {
                if (requestedResources[i].equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                    permissions.add(Manifest.permission.RECORD_AUDIO);
                } else if (requestedResources[i].equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                    permissions.add(Manifest.permission.CAMERA);
                }
                // TODO: RESOURCE_MIDI_SYSEX, RESOURCE_PROTECTED_MEDIA_ID.
            }

            for (int i = 0; i < permissions.size(); i++) {
                if (ContextCompat.checkSelfPermission(getActivity(), permissions.get(i)) != PackageManager.PERMISSION_GRANTED) {
                    continue;
                }
                if (permissions.get(i).equals(Manifest.permission.RECORD_AUDIO)) {
                    grantedPermissions.add(PermissionRequest.RESOURCE_AUDIO_CAPTURE);
                } else if (permissions.get(i).equals(Manifest.permission.CAMERA)) {
                    grantedPermissions.add(PermissionRequest.RESOURCE_VIDEO_CAPTURE);
                }
            }

            if (grantedPermissions.isEmpty()) {
                request.deny();
            } else {
                String[] grantedPermissionsArray = new String[grantedPermissions.size()];
                grantedPermissionsArray = grantedPermissions.toArray(grantedPermissionsArray);
                request.grant(grantedPermissionsArray);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
           // pbPageLoading.setProgress(newProgress);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = filePath;
//            Log.e("FileCooserParams => ", filePath.toString());
//
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                // Create the File where the photo should go
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
//                } catch (IOException ex) {
//                    // Error occurred while creating the File
//                    Log.e("error", "Unable to create Image File", ex);
//                }
//
//                // Continue only if the File was successfully created
//                if (photoFile != null) {
//                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                } else {
//                    takePictureIntent = null;
//                }
//            }
//
//            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//            contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            contentSelectionIntent.setType("image/*");
//
//            Intent[] intentArray;
//            if (takePictureIntent != null) {
//                intentArray = new Intent[]{takePictureIntent};
//            } else {
//                intentArray = new Intent[2];
//            }
//
//            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
//            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
//            startActivityForResult(Intent.createChooser(chooserIntent, "Select images"), 1);

            int writePermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int cameraPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

            if (!(writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED)) {

                try {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    galleryintent.setType("image/*");

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e("error", "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        cameraIntent.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile)
                        );

                    }

                    Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                    chooser.putExtra(Intent.EXTRA_INTENT, galleryintent);
                    chooser.putExtra(Intent.EXTRA_TITLE, "Select from:");

                    Intent[] intentArray = {cameraIntent};
                    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    // startActivityForResult(chooser, REQUEST_PIC);
                    startActivityForResult(chooser, INPUT_FILE_REQUEST_CODE);
                } catch (Exception e) {
                    // TODO: when open file chooser failed
                }
            }

            return true;

        }
    }

    public class PQClient extends WebViewClient {
        ProgressDialog progressDialog;

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // If url contains mailto link then open Mail Intent
            if (url.contains("http://auroscholar.com/dashboard.php")) {
             //   tvTitle.setVisibility(View.VISIBLE);
            //    ivHome.setVisibility(View.GONE);
            } else {
                //tvTitle.setVisibility(View.GONE);
              //  ivHome.setVisibility(View.VISIBLE);
            }
            return false;
        }

        //Show loader on url load
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // Then show progress  Dialog
            // in standard case YourActivity.this
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.hide();
            }
        }

        // Called when all page resources loaded
        public void onPageFinished(WebView view, String url) {
//            webView.loadUrl("javascript:(function(){ " +
//                                    "document.getElementById('android-app').style.display='none';})()");

            try {
                // Close progressDialog
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscriptionList != null && !subscriptionList.isDisposed()) {
            subscriptionList.dispose();
        }
    }

    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void askCamera() {
            verifyStoragePermissions(getActivity());
        }
    }


}