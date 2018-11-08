package demoapp.lotter.com.hellodemo;

public  class BaseResultEntity<T> {

    public int Code;

    public String Msg;

    public T Data;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }



}
