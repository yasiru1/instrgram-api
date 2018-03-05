package app.test.yasiru.andoridtest.presenter;

import android.content.Context;
import app.test.yasiru.andoridtest.util.Utility;


/**
 * Created by yasiru on 3/1/18.
 */

public class ImagePresenter  extends Presenter<ImagePresenter.View> {

    @Override public void terminate() {
        super.terminate();
        setView(null);
    }



   // to get number of column according to display
   public void CalColumnCount(Context context){
        getView().setColumnCount(Utility.calculateNoOfColumns(context));
    }



    public interface View extends Presenter.View {
        //set number of column
        void setColumnCount(int columnCount);


    }
}
