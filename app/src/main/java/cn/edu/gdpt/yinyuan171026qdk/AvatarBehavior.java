package cn.edu.gdpt.yinyuan171026qdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

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

//    public AvatarBehavior(Context context, AttributeSet attrs){
//        super(context,attrs);
//        mContext=context;
//        mMoveYInterpolator=new DecelerateInterpolator();
//        mMoveXInterpolator=new AccelerateInterpolator();
//        if (attrs!=null){
//            TypedArray a=mContext.obtainStyledAttributes(attrs,R.styleable.A)
//        }
//    }
}
