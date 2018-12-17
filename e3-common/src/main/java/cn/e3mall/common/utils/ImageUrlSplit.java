package cn.e3mall.common.utils;

public class ImageUrlSplit {


    /**
     *用正则表达式取出image path
     * @param imagePath 数据库中输入的iamge的地址，中间用 , 分隔
     * @return 分返解析后取出的各个url ( 例： group1/M00/00/00/rBIltlwQ0jyAMkCxAACq0IosQNI213.jpg ）
     */
    public static  String[] getMulUrl(String imagePath) {
        String regex=",?\\w{4,5}:\\/\\/\\d+.\\d+.\\d+.\\d+:\\d+\\/";
        String[] split = imagePath.split(regex);
        return split;
    }

}
