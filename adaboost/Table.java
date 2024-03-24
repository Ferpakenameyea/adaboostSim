package adaboost;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Target> dataset = new ArrayList<>();
    private List<WeakSorter> rawSorters = new ArrayList<>();
    private List<WeakSorter> selectedSorters = new ArrayList<>();

    public Table(int dataSize, boolean[] targets) {
        if (targets == null || targets.length != dataSize) {
            throw new IllegalArgumentException();
        }

        for(var b : targets) {
            this.dataset.add(new Target(1.0 / targets.length, b));
        }
    }

    public void addRawSorter(WeakSorter sorter) {
        if (sorter == null || this.rawSorters.contains(sorter)) 
            throw new IllegalArgumentException();

        this.rawSorters.add(sorter);
    }

    private WeakSorter select() {
        WeakSorter sorter = null;
        var minErr = Integer.MAX_VALUE;
        for(var attr : this.rawSorters) {
            if(attr.isMarked()) {
                continue;
            }
            var err = 0;
            for(int i = 0; i < dataset.size(); i++) {
                if(attr.getAt(i) != this.dataset.get(i).value) {
                    err++;
                }
            }
            if(err < minErr) {
                sorter = attr;
                minErr = err;
            }
        }
        return sorter;
    }

    private double getErr(WeakSorter attribute) {
        var err = 0.0;
        for(var i = 0; i < this.dataset.size(); i++) {
            err += this.dataset.get(i).weight * 
                (attribute.getAt(i) == this.dataset.get(i).value ? 0.0 : 1.0);
        }
        return err;
    }

    private double getSorterWeight(double err, WeakSorter sorter) {
        return 0.5 * Math.log((1 - err) / err);
    }

    private double getZ(WeakSorter sorter) {
        var val = 0.0;
        for(var i = 0; i < this.dataset.size(); i++) {
            val += this.dataset.get(i).weight * Math.exp(
                -sorter.getWeight() 
                * (this.dataset.get(i).value ? 1.0 : -1.0)
                * (sorter.getAt(i) ? 1.0 : -1.0)
            );
        }
        return val;
    }

    private void refreshWeight(double z, WeakSorter sorter) {
        for(var i = 0; i < this.dataset.size(); i++) {
            this.dataset.get(i).weight = 
                (dataset.get(i).weight * Math.exp(
                    -sorter.getWeight() *
                    (this.dataset.get(i).value ? 1.0 : -1.0) *
                    (sorter.getAt(i) ? 1.0 : -1.0)
                )) / z;
        }
    }

    public void iterateStep() {
        var sorter = this.select();
        sorter.mark(true);
        var err = getErr(sorter);
        var alpha = getSorterWeight(err, sorter);
        sorter.setWeight(alpha);
        var z = getZ(sorter);
        this.refreshWeight(z, sorter);
        this.selectedSorters.add(sorter);
    }

    public void dump() {
        System.out.println("============\nIterate steps:" + this.selectedSorters.size());
        System.out.print("dataset weights:\n");
        for (var i = 0; i < this.dataset.size(); i++) {
            System.out.println(i + "\t" + this.dataset.get(i).weight);
        }
        System.out.println("sorters\t\tweight");
        for (var st : this.selectedSorters) {
            System.err.println(st.getName() + "\t\t" + st.getWeight());
        }
        System.out.println("============");
    }

    private class Target {
        double weight;
        boolean value;

        Target(double weight, boolean value) {
            this.weight = weight;
            this.value = value;
        }
    }
}