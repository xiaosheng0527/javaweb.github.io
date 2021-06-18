
public class GenericArray<T> {  // 为了在实例化对象可以使用泛型，故类在定义的时候也需要定义为泛型
    /**
     * 1) 模拟Java中的ArrayList自动扩容机制
     * 注意：实例方法的this我都进行省略了
     * generic：通用的
     * 在Java里数组的length只是数组定义时的长度，
     * 而要获取数组中实际已赋值元素个数，要循环判断，没有现成的方法。
     *
     * 2) 这里的一个亮点在于，自己实现异常的判断，这样让度这段代码的人更清晰
     * Author: shengxiao
     *
     * 注意：一般我添加和删除元素，基本都是基于index来进行for循环判断，写移动操作
     */

    // 声明数组，其数据类型是泛型的，其延展性好
    private T[] array ;

    // 定义数组的当前长度
    private int realSize ;

    // 定义数组的容量
    // 这个已经由刚开始传入的参数决定了，故这里不需要定义这个实例变量
    //private int capacity ;


    // 有参构造方法，根据传入的容量来构造array
    public GenericArray(int capacity){
        this.array = (T[]) new Object[capacity];    // 注意：数组默认初始化为null
        this.realSize = 0 ;
    }

    // 无参构造方法，默认数组容量为10
    public GenericArray(){
        this(10);
    }

    // 获取数组的容量
    public int getCapacity(){
        return array.length ;
    }

    // 下面是有些业务代码
    // 获取数组的当前长度，即元素个数
    public int getCount(){
        // 注意：有时候代码可以精简，就不需要写很多行
        return realSize ;
    }

    // 判断数组是否为空
    public boolean isEmpty(){
        return realSize == 0 ;
    }

    // 修改指定index位置的元素
    public void set(int index, T e){
        this.JudgeOfIndex(index) ;  // 若不满足条件，则抛出异常
        array[index] = e ;
    }

    // 获取对应index位置的元素
    public T get(int index){
        this.JudgeOfIndex(index) ;
        return array[index] ;
    }

    // 查看数组是否包含元素e
    public boolean contains(T e){
        for(int i = 0 ; i < realSize ; ++i){
            if(array[i].equals(e)) return true ;
        }
        return false ;
    }

    // 获取元素对应的下标,未找到返回-1
    public int find(T e) {
        for ( int i = 0; i < realSize; i++) {
            if (array[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

     /**在指定的index位置插入元素e，平均时间复杂度为O(m+n)
        为什么不是普通的O(n)，因为涉及到数据的扩容，
        我们无法事先评估 m 和 n 谁的量级大，所以我们在表示复杂度的时候，
        就不能简单地利用加法法则，省略掉其中一个。所以，上面代码的时间复杂度就是 O(m+n)
        eg：此处的m代表我们的初始容量，这个是可以变化的
            n代表经过扩容后增加的容量
      */
    // 在指定的index位置插入元素e，平均时间复杂度为O(m+n)
    public void insert(int index,T e){
        JudgeOfIndexForAdd(index) ;
        // 如果当前元素个数等于数组容量，则将数组扩容为原来的2倍
        if(realSize == array.length){
            this.resize(2*array.length) ;
        }
        // 此时就可以插入元素

        for(int i = realSize ; i > index ; --i){
            array[i] = array[i - 1] ;
        }
        array[index] = e ;
        ++realSize ;
    }
    // 向数组头部插入元素
    public void addFirst(T e){
        insert(0,e) ;
    }

    // 向数组尾部插入元素
    public void addLast(T e){
        // 此处的realSize刚好相当于下一个元素的索引值
        insert(realSize,e) ;
    }

    // 删除index位置的元素，并返回此元素
    public T delete(int index){
        JudgeOfIndexForDelete(index) ;

        T ret = array[index] ;
        for(int i = index ; i < realSize - 1 ; ++i){
            array[i] = array[i+1] ;
        }
        --realSize ;
        // what???
        // 此时刚好realSize往前挪了一位，而最后空着的索引位置要清空为null，
        // 切记：不能清空为0，因为数组默认初始化为null
        array[realSize] = null ;

        // 缩小容量
        // 条件：已用容量仅仅只占总容量的1/4,且总容量不能为0
        if(realSize == array.length/4 && array.length / 2 != 0){
            this.resize(array.length / 2) ;
        }

        return ret ;
    }

    // 删除第一个元素
    public T deleteFirst(){
        return delete(0) ;
    }

    // 删除末尾元素
    public T deleteLast(){
        return this.delete(realSize - 1) ;
    }

    // 从数组中删除指定元素
    public void deleteElement(T e){
        int index = this.find(e) ;
        if(index != -1){
            this.delete(index) ;
        }
        System.out.println("无法删除") ;
    }

    // 用于最后的输出表示
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();    // 定义一个StringBuilder用来字符串拼接,且StringBuffer效率更高
        builder.append(String.format("Array size = %d, capacity = %d \n", realSize, array.length));
        builder.append('[');
        for (int i = 0; i < realSize ; ++i) {
            builder.append(array[i]);
            if (i != realSize - 1) {
                builder.append(", ");   // 目的：添加逗号
            }
        }
        builder.append(']');
        return builder.toString();
    }

    // 扩容方法，时间复杂度O(n)
    // 因为，扩充时，涉及到一个初始容量的拷贝，相当于进行了n次操作
    public void resize(int capacity){
        T[] newArray = (T[]) new Object[capacity] ;

        for(int i = 0 ; i < realSize ; ++i){
            newArray[i] = array[i] ;
        }
        array = newArray ;  // 此时array的引用指向了新创建的数组对象
    }

    // 通过下标添加元素异常判断
    public void JudgeOfIndex(int index){
        if(index < 0 || index >= realSize)
            // 这个太low了
            //System.out.println("Add failed! Require index >=0 and index < size.") ;
            throw new IllegalArgumentException("GET/SET failed! Require index >=0 and index < size.");
    }

    // 通过下标添加元素异常判断
    // index >= realSize : 若是刚开始添加，则会出现index=realSize，故会抛异常
    // 故判断条件只能为index > realSize
    public void JudgeOfIndexForAdd(int index){
        if(index < 0 || index > realSize) {
            throw new IllegalArgumentException("Add failed! Require index >=0 and index < size.");
        }
    }

    public void JudgeOfIndexForDelete(int index){
        if(index < 0 || index >= realSize) {
            throw new IllegalArgumentException("Remove failed! Require index >=0 and index < size.");
        }
    }

    public static void main(String args[]) {
        // 注意：虽然我们不想用到int的包装类Integer，这样效率比较低，但是为了符合泛型的设计思想，故只能用引用数据类型
        GenericArray<Integer> array = new GenericArray<>() ;
        array.insert(0,3);
        array.insert(0, 4);
        array.insert(0, 5);
        array.addFirst(6);
        array.addLast(7);
        array.addLast(8);
        array.addLast(9);
        array.addLast(10);
        array.addLast(11);
        array.addLast(12);
        array.addLast(13);
        System.out.println(array.toString());

        array.delete(0);
        array.delete(0) ;
        System.out.println(array.toString());

    }
}

