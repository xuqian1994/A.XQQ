package pf.xqq.unvideo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import pf.xqq.R;

public class MainVideo extends Activity implements
		UniversalVideoView.VideoViewCallback, OnClickListener {

	private static final String TAG = "MainActivity";
	private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
	private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

	UniversalVideoView mVideoView;
	UniversalMediaController mMediaController;

	View mBottomLayout;
	View mVideoLayout;
	TextView mStart;

	private int mSeekPosition;
	private int cachedHeight;
	private boolean isFullscreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_main_video);

//		findViewById(R.id.iv_img_left).setOnClickListener(this);
//		TextView tv_name =(TextView) findViewById(R.id.tv_page_name);
		
		mVideoLayout = findViewById(R.id.video_layout);
		mBottomLayout = findViewById(R.id.flayout_header);
		mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
		mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
		mVideoView.setMediaController(mMediaController);

		// mStart = (TextView) findViewById(R.id.start);

		// mStart.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if (mSeekPosition > 0) {
		// mVideoView.seekTo(mSeekPosition);
		// }
		// mVideoView.start();
		// mMediaController.setTitle("Big Buck Bunny");
		// }
		// });

		String url = VIDEO_URL;//getIntent().getStringExtra("url");
		String name = getIntent().getStringExtra("name");
//		tv_name.setText(name);
		setVideoAreaSize(url);
		mVideoView.setVideoViewCallback(this);
		if (mSeekPosition > 0) {
			mVideoView.seekTo(mSeekPosition);
		}
		mVideoView.start();
		mMediaController.setTitle(name);

		mVideoView
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						Log.d(TAG, "onCompletion ");
					}
				});

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause ");
		if (mVideoView != null && mVideoView.isPlaying()) {
			mSeekPosition = mVideoView.getCurrentPosition();
			Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
			mVideoView.pause();
		}
	}

	/**
	 * 置视频区域大小
	 * 
	 * @param url
	 */
	private void setVideoAreaSize(final String url) {
		mVideoLayout.post(new Runnable() {
			@Override
			public void run() {
				int width = mVideoLayout.getWidth();
				cachedHeight = (int) (width * 405f / 720f);
				// cachedHeight = (int) (width * 3f / 4f);
				// cachedHeight = (int) (width * 9f / 16f);
				ViewGroup.LayoutParams videoLayoutParams = mVideoLayout
						.getLayoutParams();
				videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
				videoLayoutParams.height = cachedHeight;
				mVideoLayout.setLayoutParams(videoLayoutParams);
				mVideoView.setVideoPath(url);
				mVideoView.requestFocus();
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG,
				"onSaveInstanceState Position="
						+ mVideoView.getCurrentPosition());
		outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
	}

	@Override
	protected void onRestoreInstanceState(Bundle outState) {
		super.onRestoreInstanceState(outState);
		mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
		Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
	}

	@Override
	public void onScaleChange(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		if (isFullscreen) {
			ViewGroup.LayoutParams layoutParams = mVideoLayout
					.getLayoutParams();
			layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
			mVideoLayout.setLayoutParams(layoutParams);
			mBottomLayout.setVisibility(View.GONE);

		} else {
			ViewGroup.LayoutParams layoutParams = mVideoLayout
					.getLayoutParams();
			layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.height = this.cachedHeight;
			mVideoLayout.setLayoutParams(layoutParams);
			mBottomLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onPause(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onPause UniversalVideoView callback");
		gone(mediaPlayer.isPlaying());
	}

	@Override
	public void onStart(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onStart UniversalVideoView callback");
		gone(mediaPlayer.isPlaying());
	}

	@Override
	public void onBufferingStart(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onBufferingStart UniversalVideoView callback");
		gone(mediaPlayer.isPlaying());
	}

	@Override
	public void onBufferingEnd(MediaPlayer mediaPlayer) {
		Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
		gone(mediaPlayer.isPlaying());
	}

	@Override
	public void onBackPressed() {
		if (this.isFullscreen) {
			mVideoView.setFullscreen(false);
		} else {
			super.onBackPressed();
		}
	}

	private void gone(boolean b) {
		if (b) {
			// img_text.setVisibility(View.GONE);
			// gif_view.setVisibility(View.GONE);
		}
	}

	private void show() {
		// img_text.setVisibility(View.VISIBLE);
	}

	private void setImg(boolean b) {
		if (b) {
			// img_text.setImageResource(R.drawable.coursepic);
		} else {
			// img_text.setImageResource(R.mipmap.loading);
			// gif_view.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		finish();

	}
}