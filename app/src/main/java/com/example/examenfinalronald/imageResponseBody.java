package com.example.examenfinalronald;

public class imageResponseBody {

    boolean success;
    int status;
    data data;



    public imageResponseBody() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public com.example.examenfinalronald.data getData() {
        return data;
    }

    public void setData(com.example.examenfinalronald.data data) {
        this.data = data;
    }
}
