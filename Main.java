import adaboost.Table;
import adaboost.WeakSorter;

public class Main {
    public static void main(String[] args) {
        var table = new Table(10, targets);

        var so_weather = new MySorter("天气情况", weather);
        var so_company = new MySorter("有同伴", company);
        var so_money = new MySorter("零花钱", money);
        var so_festival = new MySorter("特殊节日", festival);
        var so_moreThan2 = new MySorter("心情大于2", moreThan2);
        table.addRawSorter(so_weather);
        table.addRawSorter(so_company);
        table.addRawSorter(so_money);
        table.addRawSorter(so_festival);
        table.addRawSorter(so_moreThan2);
        table.dump();
        for(var ite = 0; ite < 3; ite++) {
            table.iterateStep();
            table.dump();
        }
    }

    static final boolean[] targets = {
            true, true, true, true, true, true, true, false, false, false
    };

    static final boolean[] weather = {
            true, false, false, false, false, true, true, false, false, false
    };

    static final boolean[] company = {
            false, true, true, true, true, false, false, false, true, false
    };

    static final boolean[] money = {
            true, true, false, false, false, true, true, true, false, false
    };

    static final boolean[] festival = {
            true, true, false, false, false, true, true, true, false, false
    };

    static final boolean[] moreThan2 = {
            true, true, false, true, true, true, true, false, false, true
    };

    static class MySorter implements WeakSorter {
        private String name;
        private boolean[] values;
        private double weight;
        private boolean marked;

        MySorter(String name, boolean[] values) {
            this.name = name;
            this.values = values;
            this.weight = 0.0;
            this.marked = false;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getSize() {
            return this.values.length;
        }

        @Override
        public boolean getAt(int index) {
            return this.values[index];
        }

        @Override
        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public double getWeight() {
            return this.weight;
        }

        @Override
        public void mark(boolean mark) {
            this.marked = mark;
        }

        @Override
        public boolean isMarked() {
            return this.marked;
        }
    }
}