package com.aoi.springbootmall.constant;

public class MyTest {

    public static void main(String[] args) {

        //name() and valueOf() 使用頻率很高
        //Category 的值存放的是 ProductCategory.FOOD
        ProductCategory Category = ProductCategory.FOOD;
        //將 Category 的值轉成 String
        String s = Category.name();
        //輸出結果為 FOOD
        System.out.println(s);

        //用 String 尋找 enum 內的值是否有符合
        String s2 = "CAR";
        ProductCategory Category2 = ProductCategory.valueOf(s2);
    }
}
