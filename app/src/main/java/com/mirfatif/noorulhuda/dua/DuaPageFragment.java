package com.mirfatif.noorulhuda.dua;

import static com.mirfatif.noorulhuda.dua.DuaPageAdapter.DUA_TYPE;
import static com.mirfatif.noorulhuda.dua.DuaPageAdapter.DUA_TYPE_MASNOON;
import static com.mirfatif.noorulhuda.dua.DuaPageAdapter.DUA_TYPE_OCCASIONS;
import static com.mirfatif.noorulhuda.dua.DuaPageAdapter.DUA_TYPE_QURANIC;
import static com.mirfatif.noorulhuda.prefs.MySettings.SETTINGS;
import static com.mirfatif.noorulhuda.util.Utils.setTooltip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import com.mirfatif.noorulhuda.App;
import com.mirfatif.noorulhuda.R;
import com.mirfatif.noorulhuda.databinding.AayahContextMenuBinding;
import com.mirfatif.noorulhuda.databinding.RecyclerViewBinding;
import com.mirfatif.noorulhuda.dua.DuasAdapter.Dua;
import com.mirfatif.noorulhuda.dua.DuasAdapter.DuaLongClickListener;
import com.mirfatif.noorulhuda.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class DuaPageFragment extends Fragment {

  private DuaActivity mA;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mA = (DuaActivity) getActivity();
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mB = RecyclerViewBinding.inflate(inflater, container, false);
    return mB.getRoot();
  }

  private RecyclerViewBinding mB;
  private LinearLayoutManager mLayoutManager;
  private DuasAdapter mAdapter;
  private int mDuaType;

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mDuaType = requireArguments().getInt(DUA_TYPE);

    mLayoutManager = new LinearLayoutManager(mA);
    mB.recyclerV.setLayoutManager(mLayoutManager);

    mAdapter = new DuasAdapter(new DuaLongClickImpl());
    Utils.runBg(this::submitList);

    mB.recyclerV.setAdapter(mAdapter);
    mB.recyclerV.addItemDecoration(new DividerItemDecoration(mA, DividerItemDecoration.VERTICAL));
    mB.recyclerV.addOnItemTouchListener(new RvTouchListener());
    mB.recyclerV.addOnScrollListener(new ScrollListener());
  }

  private void submitList() {
    String[] titles = null, texts, trans;
    int lastDua;

    if (mDuaType == DUA_TYPE_QURANIC) {
      texts = App.getRes().getStringArray(R.array.quranic_duas);
      trans = SETTINGS.getQuranicDuaTrans();
      lastDua = SETTINGS.getLastQuranicDua();
    } else if (mDuaType == DUA_TYPE_MASNOON) {
      texts = App.getRes().getStringArray(R.array.masnoon_duas);
      trans = SETTINGS.getMasnoonDuaTrans();
      lastDua = SETTINGS.getLastMasnoonDua();
    } else {
      titles = SETTINGS.getDuaTitles();
      texts = App.getRes().getStringArray(R.array.occasion_duas);
      trans = SETTINGS.getOccasionsDuaTrans();
      lastDua = SETTINGS.getLastOccasionsDua();
    }

    List<Dua> duas = new ArrayList<>();
    for (int i = 0; i < texts.length; i++) {
      String text = texts[i];
      Dua dua = new Dua();
      String[] splitText = text.split("\\|");
      dua.text = splitText[0];
      if (mDuaType == DUA_TYPE_QURANIC) {
        dua.surahNum = Integer.parseInt(splitText[1]);
        dua.aayahNum = Integer.parseInt(splitText[2]);
        dua.ref = getString(R.string.surah_name, SETTINGS.getMetaDb().getSurahName(dua.surahNum));
      } else {
        dua.ref = splitText[1];
      }
      if (titles != null && mDuaType == DUA_TYPE_OCCASIONS) {
        dua.title = titles[i];
      }
      if (trans != null) {
        dua.trans = trans[i];
      }
      duas.add(dua);
    }
    Utils.runUi(this, () -> submitList(duas, lastDua));
  }

  private void submitList(List<Dua> duas, int lastDua) {
    mAdapter.submitList(duas);
    mLayoutManager.scrollToPositionWithOffset(lastDua, 0);
    mB.recyclerV.addOnScrollListener(
        new OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView rv, int newState) {
            mA.setCurrentDua(mLayoutManager.findFirstVisibleItemPosition(), mDuaType);
          }
        });
  }

  boolean onBackPressed() {
    if (mPopup != null) {
      mPopup.dismiss();
      return true;
    }
    return false;
  }

  //////////////////////////////////////////////////////////////////
  /////////////////////////// LONG CLICK ///////////////////////////
  //////////////////////////////////////////////////////////////////

  private static final int POPUP_WIDTH = 150, POPUP_HEIGHT = 100;
  private PopupWindow mPopup;
  private int mTapPosX, mTapPosY;

  private class DuaLongClickImpl implements DuaLongClickListener {

    @Override
    public void onLongClick(Dua dua, View view) {
      AayahContextMenuBinding b = AayahContextMenuBinding.inflate(getLayoutInflater());
      setTooltip(b.copyButton);
      setTooltip(b.shareButton);
      setTooltip(b.gotoButton);

      b.bookmarkButton.setVisibility(View.GONE);
      b.addTagButton.setVisibility(View.GONE);

      b.copyButton.setOnClickListener(v -> shareDua(dua, true));
      b.shareButton.setOnClickListener(v -> shareDua(dua, false));

      int popupWidth = POPUP_WIDTH;

      if (mDuaType == DUA_TYPE_QURANIC) {
        b.gotoButton.setVisibility(View.VISIBLE);
        b.gotoButton.setOnClickListener(
            v -> {
              mPopup.dismiss();
              mA.goTo(dua);
            });
      } else {
        popupWidth -= popupWidth / 3;
      }

      if (mPopup != null) {
        mPopup.dismiss();
      }
      mPopup = new PopupWindow(b.getRoot(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
      mPopup.setElevation(500);
      mPopup.setOverlapAnchor(true);
      mPopup.setOutsideTouchable(true); // Dismiss on outside touch.

      view.setBackgroundColor(Utils.getAttrColor(mA, R.attr.accentTrans3));
      mPopup.setOnDismissListener(
          () -> {
            view.setBackgroundColor(Color.TRANSPARENT);
            mPopup = null;
          });

      int xOff = mTapPosX - Utils.toPx(popupWidth);
      int yOff = mTapPosY - Utils.toPx(POPUP_HEIGHT);
      if (xOff < 0) {
        xOff = mTapPosX + Utils.toPx(popupWidth / 4);
      }
      if (yOff < 0) {
        yOff = mTapPosY + Utils.toPx(POPUP_HEIGHT / 2);
      }

      mPopup.showAsDropDown(mB.recyclerV, xOff, yOff);
    }
  }

  private void shareDua(Dua dua, boolean toClipboard) {
    mPopup.dismiss();

    StringBuilder string = new StringBuilder();
    if (dua.title != null) {
      string.append(dua.title).append("\n\n");
    }
    string.append(dua.text).append("\n\n");
    if (dua.trans != null) {
      string.append(dua.trans).append("\n\n");
    }
    string.append(dua.ref);

    if (toClipboard) {
      ClipboardManager clipboard =
          (ClipboardManager) App.getCxt().getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData data = ClipData.newPlainText("dua", string.toString());
      clipboard.setPrimaryClip(data);
      Utils.showShortToast(R.string.copied);
    } else {
      Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain");
      intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
      startActivity(
          Intent.createChooser(intent.putExtra(Intent.EXTRA_TEXT, string.toString()), null));
    }
  }

  private class RvTouchListener implements OnItemTouchListener {

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
      if (e.getAction() == MotionEvent.ACTION_DOWN) {
        mScrolling = false;
      } else if (e.getAction() == MotionEvent.ACTION_UP
          && !mScrolling
          && (mPopup == null || !mPopup.isShowing())
          && SystemClock.uptimeMillis() - e.getDownTime() < 200) {
        mA.toggleFullScreen(null);
      }

      mTapPosX = (int) e.getX();
      mTapPosY = (int) e.getY();

      return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
  }

  private boolean mScrolling = false;

  private class ScrollListener extends OnScrollListener {

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
      mScrolling = true;
      if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
        mA.toggleFullScreen(true);
      }
    }
  }
}
