package com.atguigu.shoppingmall.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.community.fragment.CommunityFragment;
import com.atguigu.shoppingmall.home.fragment.HomeFragment;
import com.atguigu.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.atguigu.shoppingmall.type.fragment.TypeFragment;
import com.atguigu.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;

    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    /**
     * 装多个Fragment的实例集合
     */
    private ArrayList<BaseFragment> fragments;

    private int position = 0;
    /**
     * 缓存的Fragemnt或者上次显示的Fragment
     */
    private Fragment tempFragemnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife和当前Activity绑定
        ButterKnife.bind(this);
        /**
         * 初始化Fragment
         */
        initFragment();
        //设置RadioGroup的监听
        initListener();

    }

    private void initListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        position = 0;
                        break;
                    case R.id.rb_type://分类
                        position = 1;
                        break;
                    case R.id.rb_community://发现
                        position = 2;
                        break;
                    case R.id.rb_cart://购物车
                        position = 3;
                        break;
                    case R.id.rb_user://用户中心
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }

                //根据位置取不同的Fragment
                BaseFragment baseFragment = getFragment(position);

                /**
                 * 第一参数：上次显示的Fragment
                 * 第二参数：当前正要显示的Fragment
                 */
                switchFragment(tempFragemnt, baseFragment);

            }
        });


        rgMain.check(R.id.rb_home);
    }

    /**
     * 添加的时候要按照顺序
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    /**
     * 切换Fragment
     * @param fromFragment
     * @param nextFragment
     */
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragemnt != nextFragment) {
            tempFragemnt = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}
