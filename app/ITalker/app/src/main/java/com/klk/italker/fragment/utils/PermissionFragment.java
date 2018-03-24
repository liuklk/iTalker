package com.klk.italker.fragment.utils;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klk.italker.R;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class PermissionFragment extends Fragment implements EasyPermissions.PermissionCallbacks{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission, container, false);
    }

    /**
     *
     * @return
     */
    public  boolean isHaveReadPermission(){
        String readPermission = Manifest.permission.READ_EXTERNAL_STORAGE;

        return EasyPermissions.hasPermissions(getContext(), readPermission);
    }

    /**
     *
     * @return
     */
    public boolean isHaveWritePermission(){
        String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        return EasyPermissions.hasPermissions(getContext(), writePermission) ;
        }

    /**
     *
     * @return
     */
    public boolean isHaveInternetPermission(){
        String internetPermission = Manifest.permission.INTERNET;
        String accessNetworkState = Manifest.permission.ACCESS_NETWORK_STATE;
        return EasyPermissions.hasPermissions(getContext(), internetPermission,accessNetworkState) ;
        }

    /**
     *
     * @return
     */
    public boolean isHaveRecordPermission(){
        String recordPermission = Manifest.permission.RECORD_AUDIO;
        return EasyPermissions.hasPermissions(getContext(), recordPermission) ;
        }

    private static final int RC = 0x0100;
    /**
     * 申请全部的权限
     */
    @AfterPermissionGranted(RC)
    public void requestPermission(){
        String [] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.RECORD_AUDIO

        };
        if(EasyPermissions.hasPermissions(getContext(), perms)){
            //如果已经申请了所有的权限
        }else {

            EasyPermissions.requestPermissions(this, getString(R.string.label_permission_ok), RC,perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //权限申请成功
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //权限有的申请失败
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }

    /**
     *权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
