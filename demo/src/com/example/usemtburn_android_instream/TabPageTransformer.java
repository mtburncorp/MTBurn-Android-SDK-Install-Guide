package com.example.usemtburn_android_instream;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.widget.FrameLayout;

import com.example.usemtburn_android_sdk.R;
import android.support.v4.view.ViewCompat;

public class TabPageTransformer implements PageTransformer {

    public TabPageTransformer() {
        super();
    }

    @Override
    public void transformPage(View page, float position) {
        FrameLayout frameLayout = (FrameLayout) page.findViewById(R.id.card_frame_layout);
        View cardPaperView = frameLayout.findViewById(R.id.card_paper_view);
        View cardShadowView = frameLayout.findViewById(R.id.card_shadow_view);

        int pageWidth = frameLayout.getWidth();
        int cardPaperWidth = cardPaperView.getWidth();
        int cardShadowWidth = cardShadowView.getWidth();

        ViewCompat.setRotation(cardPaperView, 2f);
        ViewCompat.setRotation(cardShadowView, 2f);

        ViewCompat.setAlpha(cardPaperView, 1);
        ViewCompat.setAlpha(cardShadowView, 1);

        // ちょうど中央に表示されている状態
        // 紙も影も透明にする
        if (position == 0) {
            ViewCompat.setAlpha(cardPaperView, 0);
            ViewCompat.setAlpha(cardShadowView, 0);
        // 左端に消えていこうとしている状態
        // 影は透明にする
        } else if (-1 < position && position < 0) {
            ViewCompat.setPivotX(cardPaperView, cardPaperWidth);
            ViewCompat.setScaleX(cardPaperView, -position);
            ViewCompat.setAlpha(cardShadowView, 0);
        // 右端から表示しようとしている状態
        // 紙は透明にする
        } else if (0 < position && position < 1) {
            ViewCompat.setTranslationX(frameLayout, pageWidth * -position);
            ViewCompat.setTranslationX(cardShadowView, cardShadowWidth * position);
            ViewCompat.setAlpha(cardPaperView, 0);
        // 完全に左端または右端に消えている（position は -1 または 1）
        // 紙も影も透明にする
        } else {
            ViewCompat.setAlpha(cardPaperView, 0);
            ViewCompat.setAlpha(cardShadowView, 0);
        }
    }
}
