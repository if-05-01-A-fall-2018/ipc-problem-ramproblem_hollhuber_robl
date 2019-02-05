package sample;

public class Ram {
    private Boolean dirty = true;
    private Integer id;

    public Ram(Integer i){
        this.id = i;
    }

    @Override
    public String toString() {
        return "ram{id=" + this.id +" dirty=" + dirty.toString()+"}";
    }

    public Boolean isDirty(){return dirty;}

    void clean() {
        dirty = false;
    }

    void makeDirty()
    {
        dirty = true;
    }
}
