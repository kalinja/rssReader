package cz.fit.ctu.rssreader.background;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import cz.fit.ctu.rssreader.R;

/**
 * Created by Jakub on 24. 4. 2015.
 */
public class RefreshAnimator {
    public interface RefreshAnimationCallbacks {
        public void onAnimationRepeat(Animation animation);
    }

    private RefreshAnimationCallbacks callbacks;
    private Animation refreshAnimation;

    public RefreshAnimator(RefreshAnimationCallbacks callbacks){
        this.callbacks = callbacks;
    }

    public void initAnimation(Context context){
        refreshAnimation = AnimationUtils.loadAnimation(context, R.anim.refresh_rotation);
        refreshAnimation.setRepeatCount(Animation.INFINITE);
        refreshAnimation.setAnimationListener(animationListener);
    }

    public Animation getRefreshAnimation() {
        return refreshAnimation;
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            if (callbacks != null) {
                callbacks.onAnimationRepeat(animation);
            }
        }
    };
}
