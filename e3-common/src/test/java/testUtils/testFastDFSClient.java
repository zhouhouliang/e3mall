package testUtils;

import cn.e3mall.common.utils.FastDFSClient;
import org.junit.Test;

public class testFastDFSClient {

    @Test
    public void testDelete()throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("E:\\ideaWorkspace\\source\\e3parent\\e3-manager-web\\src\\test\\resources\\client.conf");
        int code = fastDFSClient.deleteFile("group1/M00/00/00/rBIltlwR8JGAchPIAAD2gnDM0r059.jpeg");
        System.out.println(code);
    }
}
