package com.nt.sorm.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileUtil {


    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }


    /**
     * 创建文件
     *
     * @param fileName
     * @return
     */
    public static boolean createFile(File fileName) throws Exception {
        boolean flag = false;
        try {
            if (!fileName.exists()) {
                fileName.createNewFile();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 读TXT文件内容
     *
     * @param fileName
     * @return
     */
    public static String readTxtFile(File fileName) throws Exception {
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            try {
                String read = null;
                while ((read = bufferedReader.readLine()) != null) {
                    result = read + "\r\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
        System.out.println("读取出来的文件内容是：" + "\r\n" + result);
        return result;
    }

    public static boolean writeTxtFile(String content, File fileName) throws Exception {
        RandomAccessFile mm = null;
        boolean flag = false;
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
            // mm=new RandomAccessFile(fileName,"rw");
            // mm.writeBytes(content);
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (mm != null) {
                mm.close();
            }
        }
        return flag;
    }

    public static void contentToTxt(String filePath, String content) {
        String str = new String(); // 原有txt内容
        String s1 = new String();// 内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 指定图片宽度和高度和压缩比例对图片进行压缩
     *
     * @param imgsrc     源图片地址
     * @param imgdist    目标图片地址
     * @param widthdist  压缩后图片的宽度
     * @param heightdist 压缩后图片的高度
     */
    public static void reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist) {
        try {
            Float rate = 0.9f;
            File srcfile = new File(imgsrc);
            // 检查图片文件是否存在
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
            }
            long filesize = srcfile.length() / 1024;
            if (filesize >= 6000) {
                rate = 0.2f;
            } else if (filesize >= 5000 && filesize < 6000) {
                rate = 0.3f;
            } else if (filesize >= 3000 && filesize < 5000) {
                rate = 0.5f;
            } else if (filesize >= 2000 && filesize < 3000) {
                rate = 0.6f;
            } else if (filesize >= 1000 && filesize < 2000) {
                rate = 0.7f;
            } else if (filesize >= 700 && filesize < 1000) {
                rate = 0.8f;
            }
            //判断文件大小，大于1M进行缩小
            if (filesize >= 300) {
                // 如果比例不为空则说明是按比例压缩
                if (rate != null && rate > 0) {
                    //获得源图片的宽高存入数组中
                    int[] results = getImgWidthHeight(srcfile);
                    if (results == null || results[0] == 0 || results[1] == 0) {
                        return;
                    } else {
                        //特殊情况，图片宽》3000，但是文件大小小于2000k
                        if (results[0] > 3000 && filesize < 2000) {
                            rate = 0.4f;
                        }
                        //按比例缩放或扩大图片大小，将浮点型转为整型
                        widthdist = (int) (results[0] * rate);
                        heightdist = (int) (results[1] * rate);
                    }
                }
                // 开始读取文件并进行压缩
                Image src = ImageIO.read(srcfile);

                // 构造一个类型为预定义图像类型之一的 BufferedImage
                BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);

                //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
                //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
                tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);


            /*    //以下代码，在jdk1.8中过期失效，更改为下面的方法
                //创建文件输出流
                FileOutputStream out = new FileOutputStream(imgdist);
                //将图片按JPEG压缩，保存到out中
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);*/
                //关闭文件输出流
                //out.close();
                String formatName = imgdist.substring(imgdist.lastIndexOf(".") + 1);
                ImageIO.write(tag, /*"GIF"*/ formatName /* format desired */, new File(imgdist) /* target */);

                //删除源文件
                srcfile.delete();
            } else {
                //重命名
                srcfile.renameTo(new File(imgdist));
            }
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }


    /**
     * 获取图片宽度和高度
     *
     * @param
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            // 获得文件输入流
            is = new FileInputStream(file);
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            // 得到源图片宽
            result[0] = src.getWidth(null);
            // 得到源图片高
            result[1] = src.getHeight(null);
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return result;
    }

}
