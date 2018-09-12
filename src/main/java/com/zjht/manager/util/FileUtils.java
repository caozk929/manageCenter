package com.zjht.manager.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.StringTokenizer;


public class FileUtils {
    private String message;

    public FileUtils() {

    }

    public static String getFileNameByPath(String pathName) {
        return pathName.substring(pathName.lastIndexOf('/') + 1, pathName.length());
    }

    /**
     * 读取文件
     * @param filePathAndName
     * @param encoding
     * @return
     */
    public static String readTxt(String filePathAndName, String encoding)
            throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    str.append(data + " ");
                }
            } catch (Exception e) {
                str.append(e.toString());
                e.fillInStackTrace();
            }
            st = str.toString();
        } catch (IOException es) {
            es.fillInStackTrace();
            st = "";
        }
        return st;
    }

    /**
     * 读取文件
     * @param fileName
     * @return
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {

            // reader = new BufferedReader(new FileReader(file));
            reader = new BufferedReader(new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "gbk")));
            String tempString = null;
            StringBuffer sb = new StringBuffer();
            int line = 1;

            while ((tempString = reader.readLine()) != null) {

                //System.out.println("line " + line + ": " + tempString);
                //tempString = tempString.replace(",", "??");
                sb.append(tempString + "\r\n");
                line++;
            }

            return sb.toString();

        } catch (IOException e) {


        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return "";

    }

    /**
     * 新建文件夹
     * @param folderPath
     * @return
     */
    public String createFolder(String folderPath) {
        String txt = folderPath;
        try {
            File myFilePath = new File(txt);
            txt = folderPath;
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            message = "";
        }
        return txt;
    }

    /**
     * 新建文件夹
     * @param folderPath
     * @param paths
     * @return
     */
    public String createFolders(String folderPath, String paths) {
        String txts = folderPath;
        try {
            String txt;
            txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths, "|");
            for (int i = 0; st.hasMoreTokens(); i++) {
                txt = st.nextToken().trim();
                if (txts.lastIndexOf("/") != -1) {

                    txts = createFolder(txts + txt);
                } else {
                    txts = createFolder(txts + txt + "/");
                }
            }
        } catch (Exception e) {
            message = "";
        }
        return txts;
    }

    /**
     * 新建文件
     *
     * @param filePathAndName
     * @param fileContent
     * @return
     */
    public void createFile(String filePathAndName, String fileContent) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();
            resultFile.close();
        } catch (Exception e) {
            message = "???????????????";
        }
    }

    /**
     * 新建文件
     * @param filePathAndName
     * @param fileContent
     * @param encoding
     * @return
     */
    public static void createFile(String filePathAndName, String fileContent,
                                  String encoding) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            PrintWriter myFile = new PrintWriter(myFilePath, encoding);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param filePathAndName
     * @return Boolean
     */
    public static boolean delFile(String filePathAndName) {
        boolean bea = false;

        String filePath = filePathAndName;
        File myDelFile = new File(filePath);
        if (myDelFile.exists()) {
            myDelFile.delete();
            bea = true;
        } else {
            bea = false;

        }
        return bea;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     * @return
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //
        } catch (Exception e) {
            message = ("删除文件夹失败");
        }
    }

    /**
     * 删除全部文件
     *
     * @param path
     * @return
     */
    public boolean delAllFile(String path) {
        boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        }
        if (!file.isDirectory()) {
            return bea;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//
                delFolder(path + "/" + tempList[i]);//
                bea = true;
            }
        }
        return bea;
    }

    /**
     * 复制文件
     * @param oldPathFile
     * @param newPathFile
     * @return
     */
    public static void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { // ????????
                InputStream inStream = new FileInputStream(oldPathFile); // ????????

                File filepath = new File(newPathFile.substring(0, newPathFile.lastIndexOf('/')));
                if (!filepath.exists()) {
                    filepath.mkdirs();
                }

                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // ????? ?????��
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件夹
     * @param oldPath
     * @param newPath
     * @return
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath
                            + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// ????????????
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动文件
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    /**
     * 移动文件夹
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public String getMessage() {
        return this.message;
    }

    /*
     * ???????
     */
    public static void downLoadData(HttpServletResponse response, File returnFile, String uploadedFileName) {
        response.setHeader("Content-disposition", "attachment; filename=" + uploadedFileName);
        response.setHeader("Content-Type", "application/octet-stream");

        BufferedInputStream bis = null;//??excel
        BufferedOutputStream bos = null;//???

        try {
            //???excel???
            bis = new BufferedInputStream(new FileInputStream(returnFile));
            //��??response?????????
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];/*???????*/
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
