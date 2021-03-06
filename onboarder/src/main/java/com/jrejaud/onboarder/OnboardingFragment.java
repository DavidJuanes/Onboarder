package com.jrejaud.onboarder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class OnboardingFragment extends Fragment implements Serializable {

    // the fragment initialization parameters
    private static final String TITLE = "TITLE";
    private final static String TITLE_TEXT_COLOR = "TITLE_TEXT_COLOR";
    private static final String BODY_TEXT = "BODY_TEXT";
    private final static String BODY_TEXT_COLOR = "BODY_TEXT_COLOR";
    private static final String IMAGE_RESOURCE_ID = "IMAGE_RESOURCE_ID";
    private static final String POSITION = "POSITION";
    private static final String  BUTTON_TEXT = "BUTTON_TEXT";
    private static final String MAX_IMAGE_HEIGHT = "MAX_IMAGE_HEIGHT";

    public OnboardingFragment() {
        // Required empty public constructor
    }

    public static OnboardingFragment newInstance(OnboardingPage onboardingPage, int position) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, onboardingPage.getTitle());
        args.putString(BODY_TEXT, onboardingPage.getBodyText());
        args.putInt(IMAGE_RESOURCE_ID, onboardingPage.getImageResId());
        args.putInt(POSITION, position);
        args.putString(BUTTON_TEXT, onboardingPage.getButtonText());
        args.putInt(TITLE_TEXT_COLOR, onboardingPage.getTitleTextColor());
        args.putInt(BODY_TEXT_COLOR,onboardingPage.getBodyTextColor());
        args.putInt(MAX_IMAGE_HEIGHT, onboardingPage.getMaxImageHeight());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);

        // Inflate the layout for this fragment
        Bundle bundle = getArguments();

        /* The title which is displayed at the top of the fragment */
        String title = bundle.getString(TITLE, null);
        /* The body text which is displayed in the middle of the fragment */
        String bodyText = bundle.getString(BODY_TEXT, null);
        /* The image resource which is displayed in the middle of the fragment, above the text */
        int imageResId = bundle.getInt(IMAGE_RESOURCE_ID, -1);
        /* The position that the fragment is in adapter */
        final int position = bundle.getInt(POSITION, 0);
        /* The button text (if the user set any) */
        String buttonText = bundle.getString(BUTTON_TEXT, null);
        /* Max height for the image (if not, it just scales it up) */
        int maxImageHeight = bundle.getInt(MAX_IMAGE_HEIGHT,-1);

        @ColorRes int titleTextColor = bundle.getInt(TITLE_TEXT_COLOR, -1);
        @ColorRes int bodyTextColor = bundle.getInt(BODY_TEXT_COLOR,-1);

        TextView titleTextView = (TextView) view.findViewById(R.id.onboarding_fragment_title);
        TextView bodyTextView = (TextView) view.findViewById(R.id.onboarding_fragment_body_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.onboarding_fragment_image);
        Button button = (Button) view.findViewById(R.id.onboarding_fragment_button);

        //Set the title
        if (title !=null) {
            titleTextView.setText(title);
            //Set the body text color
            if (titleTextColor!=-1) {
                titleTextView.setTextColor(getResources().getColor(titleTextColor));
            }
        } else {
            titleTextView.setVisibility(View.GONE);
        }

        //Set the body text
        if (bodyText !=null) {
            bodyTextView.setText(bodyText);
            //Set the body text color
            if (bodyTextColor!=-1) {
                bodyTextView.setTextColor(getResources().getColor(bodyTextColor));
            }
        } else {
            bodyTextView.setVisibility(View.GONE);
        }

        //Set the image
        if (imageResId !=-1) {
            imageView.setImageResource(imageResId);
            //Set the max image height (if it was set)
            if (maxImageHeight !=-1) {
                imageView.setMaxHeight(maxImageHeight);
            }
        } else {
            imageView.setVisibility(View.GONE);
        }

        //Set the button text and link it to the method
        if (buttonText!=null) {
            button.setText(buttonText);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickOnboardingButton(position);
                }
            });
        } else {
            button.setVisibility(View.GONE);
        }

        return view;
    }

    private onOnboardingButtonClickListener buttonClickListener;

    private void clickOnboardingButton(int position) {
        buttonClickListener.onOnboardingClick(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        buttonClickListener = (onOnboardingButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        buttonClickListener = null;
    }

    public interface onOnboardingButtonClickListener {
        void onOnboardingClick(int position);
    }
}
