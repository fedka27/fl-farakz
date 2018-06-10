package kz.terah.projectfarakzm.popular;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kz.terah.projectfarakzm.R;
import kz.terah.projectfarakzm.models.Popular;

public class PopularFragment extends Fragment {
    private static final String TAG = PopularFragment.class.getSimpleName();
    private static final String EXTRA_POPULAR = TAG + "_EXTRA_POPULAR";

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    public static PopularFragment newInstance(Popular popular) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_POPULAR, popular);
        PopularFragment fragment = new PopularFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView = view.findViewById(R.id.title_text_view);
        descriptionTextView = view.findViewById(R.id.description_text_view);
        imageView = view.findViewById(R.id.image_view);

        initData((Popular) getArguments().getSerializable(EXTRA_POPULAR));
    }

    private void initData(Popular popular) {
        titleTextView.setText(popular.getTitle());
        descriptionTextView.setText(popular.getDescription());
        imageView.setImageResource(popular.getDrawableRes());
    }
}
