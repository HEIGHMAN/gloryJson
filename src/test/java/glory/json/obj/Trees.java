package glory.json.obj;

import java.util.List;

public class Trees{
    private int height;
    List<?> data;
    public Trees(){}

    public Trees(int height,List<?> data){
        this.height = height;
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
