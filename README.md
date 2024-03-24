# AdaboostSIM For BUAAers

> 用于模拟计算**人工智能**课程第一次作业最后一道计算题目

**注意**！模拟过程**仅为个人理解**，不一定能保证正确性和准确性，仅供参考学习！

---



## 类：

**Table**： 用于存储“训练数据”以及“属性”。均为`boolean`

- 构造方法：

  ```java
  public Table(int dataSize, boolean[] targets);
  /*dataSize 指的是数据集大小（题目中为10），targets是取值数组，例如规定“出去玩”为true，反之为false*/
  ```

- 重要方法：

  ```java
  public void addRawSorter(WeakSorter sorter);
  ```

  为表格添加一个弱分类器（注意，`sorter`的数据集大小必须与`Table`对照相同，否则会抛出`IllegalArgumentException`

  

  ```java
  public void dump();
  ```

  以一定格式输出当前的模拟状态

  

  ```java
  public void iterateStep();
  ```

  模拟一个迭代循环



**WeakSorter**：弱分类器接口（用于存储一列属性）

- 需要实现：

  ```java
  public String getName();
  ```

  获得当前弱分类器的注释名称（即显示名称）

  ```java
  public int getSize();
  ```

  获得该分类器对应的数据列的长度

  ```java
  public boolean getAt(int index);
  ```

  获得在`index`行，分类器的预测值

  ```java
  public void setWeight(double weight);
  ```

  设置分类器的权重

  ```java
  public double getWeight();
  ```

  获得分类器的权重

  ```java
  public void mark(boolean value);
  ```

  标记此分类器是否已经被选中

  ```java
  public boolean isMarked();
  ```

  获得此分类器是否已经被选中



这是一个十分简单的接口，实现起来难度不大，如果您有相关疑问，可查看`Main`当中的示例实现

