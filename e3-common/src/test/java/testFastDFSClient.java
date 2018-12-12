import cn.e3mall.common.utils.FastDFSClient;
import org.junit.Test;

public class testFastDFSClient {

    @Test
    public void testDelete()throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("E:\\ideaWorkspace\\source\\e3parent\\e3-manager-web\\src\\test\\resources\\client.conf");
        int code = fastDFSClient.deleteFile("group1/M00/00/00/rBIltlwQxhGATYxJAAAMLm-05ao553.png");
        System.out.println(code);
    }
}
