package kz.terah.projectfarakzm.models;

import android.support.annotation.DrawableRes;

import java.io.Serializable;

public class Popular implements Serializable{
    @DrawableRes
    private int drawableRes;
    private String title;
    private String description;

    public Popular(@DrawableRes int drawableRes, String title, String description) {
        this.drawableRes = drawableRes;
        this.title = title;
        this.description = description;
    }

    @DrawableRes
    public int getDrawableRes() {
        return drawableRes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
