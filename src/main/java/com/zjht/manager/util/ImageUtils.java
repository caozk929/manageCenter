package com.zjht.manager.util;
 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
/**
 * 图片处理工具类，对图片压缩剪裁
 * @author caozk
 *
 */
public class ImageUtils {
 
    /**
     * 读取文件中的图片
     * 
     * @param ImagePath
     * @return
     */
    private static BufferedImage getImage(String ImagePath) {
        BufferedImage srcImage = null;
        try {
            File file = new File(ImagePath);
            srcImage = javax.imageio.ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取图片文件出错！", e);
        }
        return srcImage;
    }
    /**
     * 从流中读取图片
     * 
     * @param ImagePath
     * @return
     */
    private static BufferedImage getImage(InputStream input) {
        BufferedImage srcImage = null;
        try {
        	srcImage = javax.imageio.ImageIO.read(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取图片文件出错！", e);
        }
        return srcImage;
    } 
    /**
     * 将图片按照指定的图片尺寸、图片质量压缩
     * @param srcImgPath
     * @param outImgPath
     * @param new_w
     * @param new_h
     */
    public static void compress(String srcImgPath, String outImgPath,int new_w, int new_h, String format,float quality) {
        // 得到图片
        BufferedImage src = getImage(srcImgPath);
        BufferedImage newImg = getNewImage(new_w, new_h, src);
        // 调用方法输出图片文件
        outImage(outImgPath, newImg, format,quality);
    }
    /**
     * 根据输入流压缩图片
     * @param input 输入流
     * @param outImgPath 图片保存路径
     * @param new_w 新图片宽
     * @param new_h 新图片高
     * @param format 图片保存格式
     * @param quality 质量系数（0-1）
     */
    public static void compress(InputStream input, String outImgPath,int new_w, int new_h, String format,float quality) {
        // 得到图片
        BufferedImage src = getImage(input);
        BufferedImage newImg = getNewImage(new_w, new_h, src);
        // 调用方法输出图片文件
        outImage(outImgPath, newImg, format,quality);
    }
    /** 
     * 将图片按照指定的尺寸比例、图片质量压缩 
     * @param srcImgPath  :源图片路径 
     * @param outImgPath :输出的压缩图片的路径 
     * @param ratio :压缩后的图片尺寸比例 
     * @param quality :压缩质量0-1
     */ 
    public static void compress(String srcImgPath, String outImgPath, float ratio, String format,float quality) {  
        // 得到图片  
        BufferedImage src = getImage(srcImgPath);  
        BufferedImage newImg = getNewImage(ratio, src);  
        // 调用方法输出图片文件
        outImage(outImgPath, newImg,format,quality);  
    } 
    
    public static void compress(InputStream input, String outImgPath, float ratio, String format,float quality) {  
        // 得到图片  
        BufferedImage src = getImage(input);  
        BufferedImage newImg = getNewImage(ratio, src);  
        // 调用方法输出图片文件
        outImage(outImgPath, newImg,format,quality);  
    }
    /**
     * 根据压缩比，得到新图片
     * @param ratio 压缩比率
     * @param src原图片
     * @return
     */
	private static BufferedImage getNewImage(float ratio, BufferedImage src) {
		int old_w = src.getWidth();  
        // 得到源图宽  
        int old_h = src.getHeight();  
        // 得到源图长  
        int new_w = 0;  
        // 新图的宽  
        int new_h = 0;  
        // 新图的长  
        BufferedImage tempImg = new BufferedImage(old_w, old_h,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = tempImg.createGraphics();  
        g.setColor(Color.white);  
        // 从原图上取颜色绘制新图g.fillRect(0, 0, old_w, old_h);  
        g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);  
        g.dispose();  
        new_w = (int) Math.round(old_w * ratio);  
        new_h = (int) Math.round(old_h * ratio);  
        BufferedImage newImg = new BufferedImage(new_w, new_h,  
                BufferedImage.TYPE_INT_RGB);  
        newImg.getGraphics().drawImage(  
                tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,  
                0, null);
		return newImg;
	} 
    /**
     * 根据新图片要求尺寸，得到新图片
     * @param new_w 新图片宽
     * @param new_h 新图片高
     * @param src 原图片
     * @return
     */
	private static BufferedImage getNewImage(int new_w, int new_h, BufferedImage src) {
		int old_w = src.getWidth();
        // 得到源图宽
        int old_h = src.getHeight();
        // 得到源图长
        // 根据原图的大小生成空白画布
        BufferedImage tempImg = new BufferedImage(old_w, old_h,BufferedImage.TYPE_INT_RGB);
        // 在新的画布上生成原图的缩略图
        Graphics2D g = tempImg.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, old_w, old_h);
        g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
        g.dispose();
        BufferedImage newImg = new BufferedImage(new_w, new_h,
        BufferedImage.TYPE_INT_RGB);
        newImg.getGraphics().drawImage(
        tempImg.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,0, null);
		return newImg;
	}
    /** 
     * * 将图片文件输出到指定的路径
     * @param outImgPath 
     * @param newImg
     */ 
    private static void outImage(String outImgPath, BufferedImage newImg, String format,float quality) {  
        // 判断输出的文件夹路径是否存在，不存在则创建  
        File file = new File(outImgPath);  
        if (!file.getParentFile().exists()) {  
            file.getParentFile().mkdirs();  
        }
        // 输出到文件流
        FileOutputStream fos = null;
        try {  
            fos = new FileOutputStream(outImgPath); 
            // JPEGImageEncoder可适用于其他图片类型的转换   
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);   
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(newImg); 
            //压缩质量 
            jep.setQuality(quality, true); 
            encoder.encode(newImg, jep);
        } catch (Exception e) { 
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
     
    public static void main(String[]args){
    	long begin = System.currentTimeMillis();
        compress("C://Users/caozhaokui/Desktop/psb.jpg","C://Users/caozhaokui/Desktop/psb6.jpg",0.25f,"jpg", 1f);
//        compress("C://Users/caozhaokui/Desktop/psb.jpg", "C://Users/caozhaokui/Desktop/psb4.jpg", 588, 784, "jpg");
        System.out.println(System.currentTimeMillis() - begin);
    }
}