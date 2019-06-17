package com.shangtao.smsmonitor.ui.fragment;

import android.Manifest;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mula.base.dialog.MessageDialog;
import com.mula.base.fragment.BaseFragment;
import com.mula.base.tools.permission.PermissionHelper;
import com.mula.base.tools.text.TextUtil;
import com.mula.base.view.MulaTitleBar;
import com.shangtao.smsmonitor.R;
import com.shangtao.smsmonitor.SMSManager;
import com.shangtao.smsmonitor.model.CommonValues;
import com.shangtao.smsmonitor.presenter.SettingPresenter;
import com.shangtao.smsmonitor.service.ForegroundService;
import com.shangtao.smsmonitor.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingPresenter.SettingView {

    @BindView(R.id.mtb_title_bar)
    MulaTitleBar mtbTitleBar;
    @BindView(R.id.et_service_host)
    EditText etServiceHost;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.et_bank_zhi)
    EditText etBankZhi;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private boolean isGrantedPermission;//是否授权
    private boolean isConnected;
    private static final String USERINFO = "user_info ";
    private PermissionHelper permissionHelper;
    public static final int INIT_PERMISSION = 201;

    @Override
    public int getLayoutId() {
        return R.layout.layout_setting;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!permissionHelper.isGrantedPermission()){
            permissionHelper.showGrantFailureDialog();
        }
    }

    @Override
    protected void initView() {
        etServiceHost.setText(CommonValues.SERVICE_HOST);
        updateOnLineStatus();
        checkSMSPermission();
    }

    private void initInfo() {
        String content = SharedPreferencesUtil.getContent(USERINFO);
        if (!TextUtils.isEmpty(content)) {
            JsonObject jobj = new Gson().fromJson(content,JsonObject.class);
            String card = jobj.get("card").toString().replace("\"","");
            String phone = jobj.get("phone").toString().replace("\"","");
            String name = jobj.get("name").toString().replace("\"","");
            String bank = jobj.get("bank").toString().replace("\"","");
            String bankzhi = jobj.get("bankzhi").toString().replace("\"","");

            Map<String,String> userParam = new HashMap<>();
            userParam.put("card",card);
            userParam.put("phone",phone);
            userParam.put("name",name);
            userParam.put("host",CommonValues.SERVICE_HOST);
            userParam.put("bank",bank);
            userParam.put("bankzhi",bankzhi);
            SMSManager.getInstance().setUserParam(userParam);

            etIdCard.setText(card);
            etPhone.setText(phone);
            etUsername.setText(name);
            etBank.setText(bank);
            etBankZhi.setText(bankzhi);

            if(isGrantedPermission&&ServiceUtils.isServiceRunning(ForegroundService.class)){
                doAction();
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                mActivity.finish();
                break;
            case R.id.tv_confirm:
                startMonitor();
                break;
        }
    }

    private void startMonitor() {
        if(TextUtil.isEmpty(mActivity,etServiceHost,etUsername,etIdCard,etBank,etBankZhi,etPhone)){
            return;
        }
        new MessageDialog(mActivity).
                setTitleHide().
                setMessageContent(isConnected?"确定要关闭监听？":"确定要开启监听？").
                setBottonClickListener((MessageDialog.OnClickListener<Boolean>) result -> {
            if(result){
                doAction();
            }
        }).show();
    }

    private void doAction() {
        if(!isConnected){
            //缓存配置信息
            Map<String,String> userParam = new HashMap<>();
            userParam.put("card",etIdCard.getText().toString());
            userParam.put("phone",etPhone.getText().toString());
            userParam.put("name",etUsername.getText().toString());
            userParam.put("host",etServiceHost.getText().toString());
            userParam.put("bank",etBank.getText().toString());
            userParam.put("bankzhi",etBankZhi.getText().toString());
            SMSManager.getInstance().setUserParam(userParam);

            SharedPreferencesUtil.putContent(USERINFO,new Gson().toJson(userParam));
        }
        if(!isGrantedPermission){
            permissionHelper.showFailedDialog("无接收短信权限，请到设置页面打开权限");
            return;
        }
        if (isConnected) {
            tvConfirm.setText("启动监听");
            disableEditText(true);

            //关闭监听服务
            ForegroundService.stop();
        } else {
            tvConfirm.setText("断开监听");
            disableEditText(false);

            //开启监听服务
            ForegroundService.start();
        }
        isConnected = !isConnected;
        updateOnLineStatus();
    }

    private void disableEditText(boolean isEnable) {
        etUsername.setEnabled(isEnable);
        etIdCard.setEnabled(isEnable);
        etBank.setEnabled(isEnable);
        etBankZhi.setEnabled(isEnable);
        etPhone.setEnabled(isEnable);
    }

    private void updateOnLineStatus(){
        TextView textView = mtbTitleBar.getTitleText();
        if(isConnected){
            textView.setCompoundDrawablesWithIntrinsicBounds( null,null, getResources().getDrawable(R.mipmap.icon_chat_connected), null);
        }else{
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(R.mipmap.icon_chat_disconnected), null);
        }
        textView.setCompoundDrawablePadding(ScreenUtils.dip2px(5));
    }

    @Override
    public SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    /**
     * 检查读取短信权限
     */
    private void checkSMSPermission() {
        permissionHelper = new PermissionHelper(mActivity, INIT_PERMISSION, new String[]{
                Manifest.permission.RECEIVE_SMS,
        }, (grantedList, deniedList) -> {
            if (grantedList.contains(Manifest.permission.RECEIVE_SMS)) {
                isGrantedPermission = true;
            } else {
                isGrantedPermission = false;
                permissionHelper.showFailedDialog("获取接收短信权限失败，请到设置页面打开权限");
            }
            initInfo();
        });
        permissionHelper.request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == INIT_PERMISSION) {
            permissionHelper.result();
        }
    }
}
