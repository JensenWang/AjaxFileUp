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
        System.out.println("�����ļ��ϴ�Servlet");
        // ����һ���ļ��ϴ��Ĵ��̹���
        FileItemFactory factory = new DiskFileItemFactory();
        // �����������ļ��ϴ���
        ServletFileUpload upload = new ServletFileUpload(factory);
        // �����ַ�����(��������ÿ��ܵ��´洢���ļ�����)
        upload.setHeaderEncoding("utf-8");
        // ���������е��ļ�
        List<FileItem> fileItems;
        StringBuffer result = new StringBuffer();   // result�����洢�ļ�·��
        try {
            //ʹ�õ�parseRequest��HttpServletRequest�ģ����Ի���������HTML��������FileItems���б�
            fileItems = upload.parseRequest(request);
            if (fileItems != null) {
                // ָ���ϴ�Ŀ¼
                // String uploadFolder = request.getServletContext().getRealPath("upload");
                // servlet3.0��д��
                String uploadFolder = request.getSession().getServletContext().getRealPath("upload");
                //System.out.println("uploadFolder=" + uploadFolder);
                for (FileItem fileItem : fileItems) {
                    fileItem.write(new File(uploadFolder, fileItem.getName()));
                    if (result.length() > 0) {
                        result.append(",");
                    }
                    // ���ϴ����ļ�·����ӵ�result��   'upload/�ļ���'
                    result.append("'upload/" + fileItem.getName() + "'");
                }
            }
            String tmp = "var filePaths = [" + result.toString() + "]";
            System.out.println(tmp);
            // ����response�ַ�����
            response.setContentType("text/html;charset=utf-8");
            // ��·����ӡ��ǰ̨
            response.getWriter().print(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
