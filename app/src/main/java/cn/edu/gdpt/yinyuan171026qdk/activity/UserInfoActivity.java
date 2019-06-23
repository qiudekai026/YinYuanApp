package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.edu.gdpt.yinyuan171026qdk.R;
import cn.edu.gdpt.yinyuan171026qdk.UpdateUserInfoReceiver;
import cn.edu.gdpt.yinyuan171026qdk.bean.UserBean;
import cn.edu.gdpt.yinyuan171026qdk.utils.DBUtils;
import cn.edu.gdpt.yinyuan171026qdk.utils.UtilsHelper;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_head_icon;
    private RelativeLayout rl_head,rl_account,rl_nickName,rl_sex,rl_signature;
    private TextView tv_user_name,tv_nickName,tv_sex,tv_signature;
    private String spUserName;
    private static final int CHANGE_NICKNAME=1;
    private static final int CHANGE_SIGNATURE=2;
    private static final int CROP_PHOTO1=3;
    private static final int CROP_PHOTO2=4;
    private static final int SAVE_PHOTO=5;
    private Bitmap head;
    private static String path="/sdcard/TopLine/myHead/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName= UtilsHelper.readLoginUserName(this);
        initView();
        initData();

    }

    private void initData() {
        UserBean bean=null;
        bean= DBUtils.getInstance(this).getUserInfo(spUserName);
        if (bean==null){
            bean=new UserBean();
            bean.setUserName(spUserName);
            bean.setNickName("");
            bean.setSex("男");
            bean.setSignature("");
            iv_head_icon.setImageResource(R.drawable.touxiang);
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }

    private void setValue(UserBean bean) {
        tv_nickName.setText(bean.getNickName());
        tv_user_name.setText(bean.getUserName());
        tv_sex.setText(bean.getSex());
        tv_signature.setText(bean.getSignature());
        Bitmap bt= BitmapFactory.decodeFile(bean.getHead());
        if (bt!=null){
            @SuppressWarnings("deprecation")
            Drawable drawable=new BitmapDrawable(bt);
            iv_head_icon.setImageDrawable(drawable);
        }else {
            iv_head_icon.setImageResource(R.drawable.touxiang);
        }
    }

    private void initView() {
        iv_head_icon = (ImageView) findViewById(R.id.iv_head_icon);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
        rl_account = (RelativeLayout) findViewById(R.id.rl_account);
        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);

        rl_head.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_nickName:
                String neme=tv_nickName.getText().toString();
                Bundle bdName=new Bundle();
                bdName.putString("content",neme);
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bdName);
                break;
            case R.id.rl_sex:
                String sex=tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_signature:
                String signature=tv_signature.getText().toString();
                Bundle bdsignature=new Bundle();
                bdsignature.putString("content",signature);
                bdsignature.putString("title","签名");
                bdsignature.putInt("flag",2);
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bdsignature);
                break;
            case R.id.rl_head:
                showTypeDialog();
                break;
                default:
                    break;
        }
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog dialog=builder.create();
        View view=View.inflate(this,R.layout.dialog_select_photo,null);
        TextView tv_select_gallery=(TextView)view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera=(TextView)view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_PICK,null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent1,3);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),spUserName+"_head.jpg")));
                startActivityForResult(intent2,4);
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    private void sexDialog(final String sex) {
        int sexFlag=0;
        if ("男".equals(sex)){
            sexFlag=0;
        }else if ("女".equals(sex)){
            sexFlag=1;
        }
        final String items[]={"男","女"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }
        });
        builder.create().show();
    }

    private void setSex(String sex){
        tv_sex.setText(sex);
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex",sex,spUserName);
    }

    public void enterActivityForResult(Class<?> to,int requestCode,Bundle b){
        Intent i=new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requestCode);
    }
    private String new_info;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CROP_PHOTO1:
                if (requestCode==RESULT_OK){
                    cropPhoto(data.getData());
                }
                break;
            case CROP_PHOTO2:
                if (requestCode==RESULT_OK){
                    File temp=new File(Environment.getExternalStorageDirectory()+"/"+spUserName+"_head.jpg");
                    cropPhoto(Uri.fromFile(temp));
                }
                break;
            case SAVE_PHOTO:
                if (data!=null){
                    Bundle extras=data.getExtras();
                    head=extras.getParcelable("data");
                    if (head!=null){
                        String fileName=setPicToView(head);
                        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("head",fileName,spUserName);
                        iv_head_icon.setImageBitmap(head);
                        Intent intent=new Intent(UpdateUserInfoReceiver.ACTION.UPDATE_USERINFO);
                        intent.putExtra(UpdateUserInfoReceiver.INTENT_TYPE.TYPE_NAME,UpdateUserInfoReceiver.INTENT_TYPE.UPDATE_HEAD);
                        intent.putExtra("head",fileName);
                        sendBroadcast(intent);
                    }
                }
                break;
            case CHANGE_NICKNAME:
                if (data !=null){
                    new_info=data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tv_nickName.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName",new_info,spUserName);
                }
                break;
            case CHANGE_SIGNATURE:
                if (data !=null){
                    new_info=data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tv_signature.setText(new_info);
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature",new_info,spUserName);
                }
                break;
        }
    }

    private String setPicToView(Bitmap mBitmap) {
        String sdStatus=Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)){
            return "";
        }
        FileOutputStream b=null;
        File file=new File(path);
        file.mkdirs();
        String fileName=path+spUserName+"_head.jpg";
        try {
            b=new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG,100,b);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                b.flush();
                b.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileName;
    }

    private void cropPhoto(Uri uri) {
        Intent intent=new Intent("com.android.camer.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,SAVE_PHOTO);
    }


}
