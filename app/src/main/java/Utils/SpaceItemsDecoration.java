package Utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

//add padding around RecyclerView thumbnail images. The padding-right will be added to all the thumbnail images but not to the last item in the list.

public class SpaceItemsDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemsDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            outRect.left = space;
            outRect.right = 0;
        }else{
            outRect.right = space;
            outRect.left = 0;
        }
    }
}