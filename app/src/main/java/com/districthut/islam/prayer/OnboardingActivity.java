package com.districthut.islam.prayer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mirfatif.noorulhuda.R;
import com.districthut.islam.prayer.fragments.OnOnboardingOptionSelectedListener;
import com.districthut.islam.prayer.fragments.OnboardingAdjustmentHighLatitudesFragment;
import com.districthut.islam.prayer.fragments.OnboardingAsrCalculationMethodFragment;
import com.districthut.islam.prayer.fragments.OnboardingCalculationMethodFragment;
import com.districthut.islam.prayer.fragments.OnboardingTimeFormatFragment;
import com.districthut.islam.prayer.util.AppSettings;
import com.districthut.islam.prayer.util.ScreenUtils;
import com.districthut.islam.prayer.widget.FragmentStatePagerAdapter;

public class OnboardingActivity extends AppCompatActivity implements OnOnboardingOptionSelectedListener {

  public static final String EXTRA_CARD_INDEX = "card_index";

  private ViewPager mPager;
  private PagerAdapter mPagerAdapter;

  private int mCardIndex = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ScreenUtils.lockOrientation(this);
    setContentView(R.layout.activity_onboarding);

    // Instantiate a ViewPager and a PagerAdapter.
    Intent intent = getIntent();
    mCardIndex = intent.getIntExtra(EXTRA_CARD_INDEX, 0);
    mPager = (ViewPager) findViewById(R.id.pager);
    mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager(), mCardIndex);
    mPager.setAdapter(mPagerAdapter);
  }


  @Override
  public void onBackPressed() {
    if (mPager.getCurrentItem() == 0) {
      // If the user is currently looking at the first step, allow the system to handle the
      // Back button. This calls finish() on this activity and pops the back stack.
      super.onBackPressed();
    } else {
      // Otherwise, select the previous step.
      mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }
  }


  @Override
  public void onOptionSelected() {
    if (mPager.getCurrentItem() + 1 == mPagerAdapter.getCount()) {
      AppSettings.getInstance(this).set(AppSettings.Key.HAS_DEFAULT_SET, true);
      Intent data = new Intent();
      if (getParent() == null) {
        setResult(RESULT_OK, data);
      } else {
        getParent().setResult(RESULT_OK, data);
      }
      finish();
    } else {
      mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }
  }


  private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    int mCardIndex = 0;

    public ScreenSlidePagerAdapter(FragmentManager fm, int cardIndex) {
      super(fm);
      mCardIndex = cardIndex;
    }


    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return OnboardingCalculationMethodFragment.newInstance(mCardIndex);
        case 1:
          return OnboardingAsrCalculationMethodFragment.newInstance(mCardIndex);
        case 2:
          return OnboardingAdjustmentHighLatitudesFragment.newInstance(mCardIndex);
        case 3:
          return OnboardingTimeFormatFragment.newInstance(mCardIndex);
      }
      return null;
    }

    @Override
    public int getCount() {
      return 4;
    }
  }
}
