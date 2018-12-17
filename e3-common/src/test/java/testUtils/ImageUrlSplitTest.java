package testUtils;

import cn.e3mall.common.utils.ImageUrlSplit;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageUrlSplitTest {

    @Test
    public void testGetMulUrl() {
        ImageUrlSplit imageUrlSplit = new ImageUrlSplit();
        String[] mulUrl = imageUrlSplit.getMulUrl("http://47.106.180.153:22123/group1/M00/00/00/rBIltlwQ0jyAMkCxAACq0IosQNI213.jpg,http://47.106.180.153:22123/group1/M00/00/00/rBIltlwQ0j2Adw5CAAD2gnDM0r043.jpeg,http://47.106.180.153:22123/group1/M00/00/00/rBIltlwQ0jyAMkCxAACq0IosQNI213.jpg,http://47.106.180.153:22123/group1/M00/00/00/rBIltlwQ0j2Adw5CAAD2gnDM0r043.jpeg");
        for (String url : mulUrl) {
            System.out.println(url);
        }
    }

}