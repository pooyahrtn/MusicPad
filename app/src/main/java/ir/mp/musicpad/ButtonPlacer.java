package ir.mp.musicpad;

import android.content.res.Resources;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by pooya on 2/12/17.
 */

public class ButtonPlacer {
//    private int rows;
//    private int columns;
//    private SparseArray<View> buttons;
    public static class ViewContainer{
        int height;
        int width;
        FrameLayout mFrameLayout;
        SparseArray<SoundButton> buttons;
        int margin = 60;

        public static int getPixelFromDp(Resources resources,int dp){
            Resources r = resources;
            return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        }
    }

    public static void place(ViewContainer viewContainer,int rows, int columns){
        int size = Math.min((viewContainer.height - (rows ) * viewContainer.margin) / rows,
                (viewContainer.width - (columns ) * viewContainer.margin) / columns) ;
        int shiftFromLeft = (viewContainer.width - ( columns * size + (columns) * viewContainer.margin )) / 2;
        int shiftFromTop = (viewContainer.height - ( rows * size + (rows ) * viewContainer.margin )) / 2;
        if (shiftFromLeft < (viewContainer.margin / 2)){
            shiftFromLeft = viewContainer.margin / 2;
        }
        if (shiftFromTop < (viewContainer.margin / 2 )){
            shiftFromTop = viewContainer.margin / 2;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int index = j + i * columns;
                View button = viewContainer.buttons.get(index);
                button.setLayoutParams(new FrameLayout.LayoutParams(size , size));
                button.setX(shiftFromLeft + j * size + (j ) * viewContainer.margin);
                button.setY(shiftFromTop + i * size + (i )* viewContainer.margin);
            }
        }
    }

}
