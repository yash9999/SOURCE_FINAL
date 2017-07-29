package com.example.yogeshgarg.source.common.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class FontHelper {

    public enum FontType {


        FONT_LIGHT("fonts/roboto.light.ttf"),
        FONT_Normal("fonts/roboto.regular.ttf"),
        FONT_Semi_Bold("fonts/roboto.semibold.ttf"),
        FONT_Bold("fonts/roboto.bold.ttf");

        private String type;

        FontType(String type) {

            this.type = type;
        }

        public static String fromType(FontType fontType) {
            if (fontType != null) {
                for (FontType typeEnum : FontType.values()) {
                    if (fontType == typeEnum) {
                        return typeEnum.type;

                    }
                }
            }
            return null;
        }
    }

    public static void setFontFace(TextView tv, FontType fontType, Context context) {
        Typeface type = Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType));

        tv.setTypeface(type);
    }

    public static void applyFont(final Context context, final View root, final FontType fontType) {
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    applyFont(context, root, fontType);

                }
            } else if (root instanceof EditText) {
                ((EditText) root).setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
            } else if (root instanceof TextView) {
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
            } else if (root instanceof Button) {
                ((Button) root).setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
            } else if (root instanceof RadioButton) {
                ((RadioButton) root).setTypeface(Typeface.createFromAsset(context.getAssets(), FontType.fromType(fontType)));
            }
        } catch (Exception e) {
            // Log.e("Font error", String.format("Error occuerd when trying to apply %s font for %s view", FontType.fromType(fontType), root));
            e.printStackTrace();
        }
    }


}
