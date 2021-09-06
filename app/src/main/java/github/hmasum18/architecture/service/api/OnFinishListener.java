package github.hmasum18.architecture.service.api;

public interface OnFinishListener<T>{
    void onSuccess(T t);
    void onFailure(Exception e);
}
