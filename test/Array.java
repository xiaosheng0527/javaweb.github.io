public class Array {
    /**
     * 1) 数组的插入、删除、按照下标随机访问操作；
     * 2）数组中的数据是int类型的；
     * 注意：实例方法的this我都进行省略了
     *
     * Author: shengxiao
     */
    // 定义数组
    private int[] array ;

    // 定义数组长度
    private int maxSize ;

    // 定义数组的实际长度,刚开始还没有添加，默认为0，但最好在构造过程中也进行赋值
    private int realSize ;

    // 数组的创建
    public Array(int maxSize){
        this.maxSize = maxSize ;
        this.array = new int[maxSize] ;
        this.realSize = 0 ;

    }

    public int find(int index){
       /* for(int i = 0 ; i < realSize ; i++){
            if(array[index])
        }*/
        // 上面的代码太繁琐,下面的代码就简化了
        if(index < 0 || index > realSize) return -1 ;
        return array[index] ;
    }

    // 数组元素在指定位置的插入,包括头部插入，尾部插入，中间插入
    // 但从尾部插入效率更高
    // 这里只需要使用一般性的插入算法，使其适用于三种情况的插入
    public boolean insert(int index,int value){

        // 注意：要先判断临界情况，例如，数组已满，
        if(realSize == maxSize) {
            System.out.println("数组已满，无法插入元素");
            return false ;
        }

        // 判断下标小于0以及如果插入的位置在实际长度的后面
        if(index < 0 || index > realSize){
            System.out.println("插入的位置不合法");
            return  false ;
        }

        // array[realSize] = value ;
        // ++realSize ;    // 比realSize++效率更高，减少临时变量的性能消耗

        // 这个是争哥写的
/*        for( int i = count; i > index; --i){
            data[i] = data[i - 1];
        }*/
        // realSize代表真实长度，和索引值不同，realSize-1=index，不要理解错了
        // 向争哥一样，这样写的会比较清晰，不会糊涂+

/*        for(int i = realSize ; i >= index ; --i){
            array[i+1] = array[i] ;
        刚才如果这样写的话，就会造成下标越界，出现异常
        }*/
        for(int i = realSize ; i > index ; --i){
            array[i] = array[i-1] ;
        }
        array[index] = value ;
        ++realSize ;
        return true ;

    }

    // 数组元素的删除[删除指定位置的元素],可以从头部删除，中间删除，尾部删除
    // 但从尾部删除效率更高
    // 按照一般情况下删除
    public boolean remove(int index){

        // 判断临界情况，如果数组为空
        if(realSize == 0){
            System.out.println("数组为空，无法删除");
            return false ;
        }
        //若删除的位置不在相应的索引值，也会报错
        if(index < 0 || index > realSize){
            System.out.println("无效操作");
        }
        for(int i = index ; i < realSize ; ++i){
            array[i] = array[i+1];
        }
        --realSize ;
        return true ;
    }

    public void printArray(){
/*        for (int i = 0; i < realSize ; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.println();*/
        // 注意：使用foreach来遍历时，迭代类型不一定就是引用类型
        // 如果是二维数组的遍历，则foreach嵌套采用下面的类型
        // 因为二维数组的值就是一维数组，故数据类型即为int[]
        /**
         for (int val[]: brr) {
         for (int value: val) {
         System.out.println(value);
         }
         }
         */
        for(int elem : array){
            System.out.print(elem + " ");
        }
        System.out.println();
    }

    public static void main(String args[]) {
        Array array = new Array(5) ;
        array.printArray();
        array.insert(0, 3);
        array.insert(0, 4);
        array.insert(1, 5);
        array.insert(3, 9);
        array.insert(3, 10);
        //array.insert(3, 11);
        array.printArray();
    }
}