package cn.e3mall.common.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;

public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;

    public FastDFSClient(String conf) throws Exception {
        if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
            //将win10，win8系统路径中用%20表示的空格符替换成正常的空格符
            conf = conf.replace("%20", " ");
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileContent 文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metas       文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

        String result = storageClient.upload_file1(fileContent, extName, metas);
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 从fastDFS中删除iamge
     * @param filePath 文件路径
     * @return -1失败 0 成功 2 找不到文件
     * @throws Exception
     */
    public int deleteFile(String filePath) throws Exception {
        return storageClient.delete_file1(filePath);
    }



    /**
     * 释放资源
     */
    public void closeServer() {
        try {
            if (null != storageServer) {
                storageServer.close();
            }
            if (null != trackerServer) {
                trackerServer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeServer();
    }
}
