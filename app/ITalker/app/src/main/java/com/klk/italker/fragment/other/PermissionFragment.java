package com.klk.italker.fragment.other;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klk.common.app.MyApplication;
import com.klk.common.widget.view.TransStatusBottomSheetDialog;
import com.klk.italker.R;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class PermissionFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permission, container, false);
        root.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshState(getView());
    }

    public static void show(FragmentManager manager){
        new PermissionFragment().show(manager,PermissionFragment.class.getName());
    }


    /**
     * 刷新我们的布局中的图片的状态
     *
     * @param root 跟布局
     */
    private void refreshState(View root) {
        if (root == null)
            return;
        Context context = getContext();

        root.findViewById(R.id.iv_permission_network)
                .setVisibility(isHaveInternetPermission(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.iv_permission_read)
                .setVisibility(isHaveReadPermission(context)? View.VISIBLE : View.GONE);

        root.findViewById(R.id.iv_permission_write)
                .setVisibility(isHaveWritePermission(context) ? View.VISIBLE : View.GONE);

        root.findViewById(R.id.iv_permission_record_audio)
                .setVisibility(isHaveRecordPermission(context) ? View.VISIBLE : View.GONE);
    }
    /**
     *
     * @return
     */
    public  static boolean isHaveReadPermission(Context context){
        String readPermission = Manifest.permission.READ_EXTERNAL_STORAGE;

        return EasyPermissions.hasPermissions(context, readPermission);
    }

    /**
     *
     * @return
     */
    public static boolean isHaveWritePermission(Context context){
        String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        return EasyPermissions.hasPermissions(context, writePermission) ;
        }

    /**
     *
     * @return
     */
    public static boolean isHaveInternetPermission(Context context){
        String[] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        return EasyPermissions.hasPermissions(context, perms) ;
        }

    /**
     *
     * @return
     */
    public static boolean isHaveRecordPermission(Context context){
        String recordPermission = Manifest.permission.RECORD_AUDIO;
        return EasyPermissions.hasPermissions(context, recordPermission) ;
        }


    /**
     * 检查是否具有所有的权限
     *
     * @param context Context
     * @param manager FragmentManager
     * @return 是否有权限
     */
    public static boolean haveAll(Context context, FragmentManager manager) {
        // 检查是否具有所有的权限
        boolean haveAll = isHaveReadPermission(context)
                && isHaveWritePermission(context)
                &&isHaveInternetPermission(context)
                && isHaveRecordPermission(context);

        // 如果没有则显示当前申请权限的界面
        if (!haveAll) {
            show(manager);
        }

        return haveAll;
    }
    private static final int RC = 0x0100;
    /**
     * 申请全部的权限
     */
    @AfterPermissionGranted(RC)
    public void requestPermission(){
        String [] perms = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO

        };
        if(EasyPermissions.hasPermissions(getContext(), perms)){
            //如果已经申请了所有的权限
            MyApplication.Toast(R.string.label_permission_ok);
            refreshState(getView());

        }else {
            EasyPermissions.requestPermissions(this, getString(R.string.title_assist_permissions), RC,perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

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
        // 传递对应的参数，并且告知接收权限的处理者是我自己
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
