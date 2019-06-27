package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.edu.gdpt.yinyuan171026qdk.MainActivity;
import cn.edu.gdpt.yinyuan171026qdk.R;

public class GuidePageActivity extends AppCompatActivity {
    Unbinder unbinder;
    @BindView(R.id.cb_test)
    ConvenientBanner cbTest;
    @BindView(R.id.btn_test)
    Button btnTest;

    private ArrayList<Integer> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        unbinder = ButterKnife.bind(this);
        initView();
        initGuidePage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        arrayList = new ArrayList<>();
        arrayList.add(R.mipmap.b1);
        arrayList.add(R.mipmap.b2);
        arrayList.add(R.mipmap.b3);
    }

    private void initGuidePage() {
        cbTest.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_guide_page;
            }

        }, arrayList)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(true)
                .setCanLoop(false)
                .setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    }

                    @Override
                    public void onPageSelected(int index) {
                        if (index == 2) {
                            btnTest.setVisibility(View.VISIBLE);
                            cbTest.setPointViewVisible(false);
                        } else {
                            btnTest.setVisibility(View.GONE);
                            cbTest.setPointViewVisible(true);

                        }
                    }
                });
    }

    @OnClick(R.id.btn_test)
    public void onViewClicked() {
        Intent intent = new Intent(GuidePageActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public class LocalImageHolderView extends Holder<Integer> {
        private ImageView mImageView;

        public LocalImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mImageView = itemView.findViewById(R.id.iv_guide_page);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        @Override
        public void updateUI(Integer data) {
            mImageView.setImageResource(data);
        }
    }
}
