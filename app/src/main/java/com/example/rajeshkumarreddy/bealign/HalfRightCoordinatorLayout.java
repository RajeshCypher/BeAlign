package com.example.rajeshkumarreddy.bealign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xenione.libs.swipemaker.AbsCoordinatorLayout;
import com.xenione.libs.swipemaker.SwipeLayout;

/**
 * Created by Rajeshkumar Reddy on 30-11-2017.
 */

public class HalfRightCoordinatorLayout extends AbsCoordinatorLayout {

    private View mBackgroundView;
    private SwipeLayout mForegroundView;

    public HalfRightCoordinatorLayout(Context context) {
        super(context);
    }

    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HalfRightCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void doInitialViewsLocation() {
        mForegroundView=(SwipeLayout)findViewById(R.id.foregroundView);
        mBackgroundView=findViewById(R.id.backgroundView);
        mForegroundView.anchor(mBackgroundView.getRight(),mBackgroundView.getLeft());

        final ImageButton delete=(ImageButton) findViewById(R.id.backgroundView);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //ShakeAnimation ba=new ShakeAnimation(delete);
                //ba.animate();
                Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onTranslateChange(float globalPercent, int index, float relativePercent) {

    }

}
