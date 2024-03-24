package adaboost;

public interface WeakSorter {
    public String getName();
    public int getSize();
    public boolean getAt(int index);
    public void setWeight(double weight);
    public double getWeight();
    public void mark(boolean mark);
    public boolean isMarked();
}
