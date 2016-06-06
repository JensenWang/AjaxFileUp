package com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入文件上传Servlet");
        // 创建一个文件上传的磁盘工厂
        FileItemFactory factory = new DiskFileItemFactory();
        // 创建容器的文件上传类
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置字符编码(如果不设置可能导致存储的文件乱码)
        upload.setHeaderEncoding("utf-8");
        // 解析请求中的文件
        List<FileItem> fileItems;
        StringBuffer result = new StringBuffer();   // result用来存储文件路径
        try {
            //使用的parseRequest（HttpServletRequest的），以获得与给定的HTML部件关联FileItems的列表
            fileItems = upload.parseRequest(request);
            if (fileItems != null) {
                // 指定上传目录
                // String uploadFolder = request.getServletContext().getRealPath("upload");
                // servlet3.0的写法
                String uploadFolder = request.getSession().getServletContext().getRealPath("upload");
                //System.out.println("uploadFolder=" + uploadFolder);
                for (FileItem fileItem : fileItems) {
                    fileItem.write(new File(uploadFolder, fileItem.getName()));
                    if (result.length() > 0) {
                        result.append(",");
                    }
                    // 将上传的文件路径添加到result中   'upload/文件名'
                    result.append("'upload/" + fileItem.getName() + "'");
                }
            }
            String tmp = "var filePaths = [" + result.toString() + "]";
            System.out.println(tmp);
            // 设置response字符编码
            response.setContentType("text/html;charset=utf-8");
            // 将路径打印到前台
            response.getWriter().print(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
