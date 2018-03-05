

package app.test.yasiru.andoridtest.presenter;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
// base presenter
abstract class Presenter<T extends Presenter.View> {

  private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

  private T mView;

  public T getView() {
    return mView;
  }

  public void setView(T view) {
    this.mView = view;
  }

  public void initialize() {

  }

  public void terminate() {
    dispose();
  }

  void addDisposableObserver(Disposable disposable) {
    mCompositeDisposable.add(disposable);
  }

  private void dispose() {
    if (!mCompositeDisposable.isDisposed()) {
      mCompositeDisposable.dispose();
    }
  }

  public interface View {

    Context context();
  }
}