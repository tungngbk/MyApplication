package model;

import android.graphics.Bitmap;

public class ResultPredict {
    private Bitmap bitmap;
    private record record;

    public ResultPredict(Bitmap bitmap, model.record record) {
        this.bitmap = bitmap;
        this.record = record;
    }
    public ResultPredict() {

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public model.record getRecord() {
        return record;
    }

    public void setRecord(model.record record) {
        this.record = record;
    }
}
