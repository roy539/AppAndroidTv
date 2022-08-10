package com.example.androidtvapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.leanback.app.BrowseFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;

import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;

import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends BrowseFragment {


    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    @SuppressLint("StaticFieldLeak")
    private static PicassoBackgroundManager picassoBackgroundManager = null;
    String description = "";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        loadRows();

        setupEventListeners();

        picassoBackgroundManager = new PicassoBackgroundManager(getActivity());
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is clicked, code inside here will be executed.
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                getActivity().startActivity(intent);
            } else if (item instanceof String) {
                if (item == "ErrorFragment") {
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private static final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is selected, code inside here will be executed.
                if (item instanceof Movie) {
                // CardPresenter
                picassoBackgroundManager.updateBackgroundWithDelay("https://i.pinimg.com/originals/4a/64/76/4a6476ee0a749a891cc05b2ae9093f69.jpg");

            }
        }
    }

    private void setupUIElements() {
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.background_gradient_start));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.fastlane_background));
    }

    private void loadRows() {
        ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());


        /* CardPresenter */
        HeaderItem cardPresenterHeader = new HeaderItem(1, "Series");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();

            if (i == 0) {
                movie.setCardImageUrl("https://i.ytimg.com/vi/dVp0k6PV3Hk/maxresdefault.jpg");
                movie.setTitle("Game Of Thrones");
                movie.setStudio("HBOmax");
                description = "Es la descripción de dos familias poderosas, reyes y reinas, caballeros y renegados, hombres falsos y honestos, haciendo parte de un juego mortal por el control de los Siete Reinados de Westeros y por sentarse en el trono más alto. ";

            } else if (i == 1) {
                movie.setCardImageUrl("http://gritaradio.com/wp-content/uploads/2022/01/peaky-blinders-trailer-1.jpg");
                movie.setTitle("Peaky Blinders");
                movie.setStudio("Netflix");
                description = "En Birmingham, una pandilla de gánsters callejeros asciende hasta convertirse en los reyes de la clase obrera.";

            } else if (i == 2) {
                movie.setCardImageUrl("https://i0.wp.com/eltiempolatino.com/wp-content/uploads/2022/04/foto-grande-AL-DIA-1_6.png?fit=1280%2C730&ssl=1");
                movie.setTitle("Palpito");
                movie.setStudio("Netflix");
                description = "La esposa de Simón es asesinada para quitarle el corazón y trasplantarlo a la esposa de un poderoso millonario. En busca de venganza, Simón se lanza al peligroso mundo del tráfico de órganos.";
            } else if (i == 3) {
                movie.setCardImageUrl("https://los40es00.epimg.net/los40/imagenes/2021/04/06/cinetv/1617698534_521932_1617698632_noticia_normal_amp.jpg");
                movie.setTitle("The Walking Dead");
                movie.setStudio("Netflix");
                description = "Este drama crudo describe las vidas de un grupo de sobrevivientes que está siempre en movimiento en busca de un hogar seguro durante las semanas y meses de un apocalipsis zombi.";
            } else {
                movie.setCardImageUrl("https://i0.wp.com/cuatrobastardos.com/wp-content/uploads/2016/09/narcos-s2.jpg?resize=1200%2C675&ssl=1");
                movie.setTitle("Narcos");
                movie.setStudio("Netflix");
                description = "Netflix lleva al cartel de Medellín en \" Narcos\", que sigue el ascenso y caída del capo colombiano Pablo Escobar, y a los agentes de la DEA que lucharon para atraparlo.";
            }




            movie.setDescription(description);
            cardRowAdapter.add(movie);
        }

        mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));

        /* Set */
        setAdapter(mRowsAdapter);
    }


    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }
}