package cn.edu.gdpt.yinyuan171026qdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class AvatarBehavior extends CoordinatorLayout.Behavior<CircleImageView> {
    private static final float ANIM_CHANGE_POINT=0.2f;
    private Context mContext;
    private int mTotalScrollRange;
    private int mAppBarHeight;
    private int mAppBarWidth;
    private int mOriginalSize;
    private int mFinalSize;
    private float mScaleSize;
    private float mOriginalX;
    private float mFinalX;
    private float mOriginalY;
    private float mFinalY;
    private int mToolBarHeight;
    private float mAppbarStartY;
    private float mPercent;
    private DecelerateInterpolator mMoveYInterpolator;
    private AccelerateInterpolator mMoveXInterpolator;
    private CircleImageView mFinalView;
    private int mFinalViewMarginBottom;

    public AvatarBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext=context;
        mMoveYInterpolator=new DecelerateInterpolator();
        mMoveXInterpolator=new AccelerateInterpolator();
        if (attrs!=null){
            TypedArray a=mContext.obtainStyledAttributes(attrs,R.styleable.AvatarImageBehavior);
            mFinalSize=(int)a.getDimension(R.styleable.AvatarImageBehavior_finalSize,0);
            mFinalX=a.getDimension(R.styleable.AvatarImageBehavior_finalX,0);
            mToolBarHeight=(int)a.getDimension(R.styleable.AvatarImageBehavior_toolBarHeight,0);
            a.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull CircleImageView child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull CircleImageView child, @NonNull View dependency) {
        if (dependency instanceof AppBarLayout){
            _initVariables(child,dependency);
            mPercent=(mAppbarStartY - dependency.getY()) * 1.0f/mTotalScrollRange;
            float percentY=mMoveYInterpolator.getInterpolation(mPercent);
            AnimHelper.setViewY(child,mOriginalY,mFinalY-mScaleSize,percentY);
            if (mPercent>ANIM_CHANGE_POINT){
                float scalePercent=(mPercent-ANIM_CHANGE_POINT)/(1-ANIM_CHANGE_POINT);
                float percentX=mMoveXInterpolator.getInterpolation(scalePercent);
                AnimHelper.scaleView(child,mOriginalSize,mFinalSize,scalePercent);
                AnimHelper.setViewX(child,mOriginalX,mFinalX-mScaleSize,percentX);
            }else {
                AnimHelper.scaleView(child,mOriginalSize,mFinalSize,0);
                AnimHelper.setViewX(child,mOriginalX,mFinalX-mScaleSize,0);
            }
            if (mFinalView != null){
                if (percentY==1.0f){
                    mFinalView.setVisibility(View.VISIBLE);
                }else {
                    mFinalView.setVisibility(View.GONE);
                }
            }
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP&&dependency instanceof CollapsingToolbarLayout){
            if (mFinalView==null&&mFinalSize!=0&&mFinalX!=0&&mFinalViewMarginBottom!=0){
                mFinalView=new CircleImageView(mContext);
                mFinalView.setVisibility(View.GONE);
                ((CollapsingToolbarLayout) dependency).addView(mFinalView);
                FrameLayout.LayoutParams params=(FrameLayout.LayoutParams) mFinalView.getLayoutParams();
                params.width=mFinalSize;
                params.height=mFinalSize;
                params.gravity= Gravity.BOTTOM;
                params.leftMargin=(int)mFinalX;
                params.bottomMargin=mFinalViewMarginBottom;
                mFinalView.setLayoutParams(params);
                mFinalView.setImageDrawable(child.getDrawable());
                mFinalView.setBorderColor(child.getBorderColor());
                int borderWidth=(int) ((mFinalSize*1.0f/mOriginalSize)*child.getBorderWidth());
                mFinalView.setBorderWidth(borderWidth);
            }else {
                mFinalView.setImageDrawable(child.getDrawable());
                mFinalView.setBorderColor(child.getBorderColor());
                int borderWidth=(int)((mFinalSize*1.0f/mOriginalSize)*child.getBorderWidth());
                mFinalView.setBorderWidth(borderWidth);
            }
        }
        return true;
    }
    private void _initVariables(CircleImageView child,View dependency){
        if (mAppBarHeight==0){
            mAppBarHeight=dependency.getHeight();
            mAppbarStartY=dependency.getY();
        }
        if (mTotalScrollRange==0){
            mTotalScrollRange=((AppBarLayout) dependency).getTotalScrollRange();
        }
        if (mOriginalSize==0){
            mOriginalSize=child.getWidth();
        }
        if (mFinalSize==0){
            mFinalSize=mContext.getResources().getDimensionPixelSize(R.dimen.avatar_final_size);
        }
        if (mAppBarWidth==0){
            mAppBarWidth=dependency.getWidth();
        }
        if (mOriginalX==0){
            mOriginalX=child.getX();
        }
        if (mFinalX==0){
            mFinalX=mContext.getResources().getDimensionPixelSize(R.dimen.avatar_final_x);
        }
        if (mOriginalY==0){
            mOriginalY=child.getY();
        }
        if (mFinalY==0){
            if (mToolBarHeight==0){
                mToolBarHeight=mContext.getResources().getDimensionPixelSize(R.dimen.toolbar_height);
            }
            mFinalY=(mToolBarHeight-mFinalSize)+mAppbarStartY;
        }
        if (mScaleSize==0){
            mScaleSize=(mOriginalSize-mFinalSize)*1.0f;
        }
        if (mFinalViewMarginBottom==0){
            mFinalViewMarginBottom=(mToolBarHeight-mFinalSize);
        }
    }

    private static class AnimHelper {
        public AnimHelper() {
            throw new RuntimeException(" AnimHelper cannot be initialized!");
        }
        public static void setViewX(View view,float originalX,float finalX,float percent){
            float calcX=(finalX-originalX)*percent+originalX;
            view.setX(calcX);
        }
        public static void setViewY(View view,float originalY,float finalY,float percent){
            float calcY=(finalY-originalY)*percent+originalY;
            view.setY(calcY);
        }
        public static void scaleView(View view,float originalSize,float finalSize,float percent){
            float calcSize=(finalSize-originalSize)*percent+originalSize;
            float calcScale=calcSize/originalSize;
            view.setScaleX(calcScale);
            view.setScaleY(calcScale);
        }
    }
}
