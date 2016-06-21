package com.jacky14.pl.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PlistData {




    public final static String DICT = "dict";
    public final static String KEY = "key";
    public final static String FRAMES = "frames";
    public final static String METADATA = "metadata";

    public final static String FRAME = "frame";
    public final static String OFFSET = "offset";
    public final static String ROTATED = "rotated";
    public final static String SOURCECOLORRECT = "sourceColorRect";
    public final static String SOURCESIZE = "sourceSize";

    public final static String FORMAT = "format";
    public final static String REALTEXTUREFILENAME = "realTextureFileName";
    public final static String SIZE = "size";
    public final static String SMARTUPDATE = "smartupdate";
    public final static String TEXTUREFILENAME = "textureFileName";



    public static void main(String[] args) throws Exception {
      /*  PlistData pd = new PlistData("C:\\Users\\Administrator\\Desktop\\par.plist");

        System.out.println(pd);*/

        float t = -4.234f;
        float sas = -0.8f;
        float ewr = 3.4f;

        System.out.println(Math.abs(t)- ((int)Math.abs(t)) );
        System.out.println(Math.abs(sas)- ((int)Math.abs(sas)) );
        System.out.println(Math.abs(ewr)- ((int)Math.abs(ewr)) );
    }
    /**
     * 纹理坐标转换
     * @param key 对应的纹理贴图名称
     * @param psource 原纹理坐标  0==s ,1==t
     * @return 返回转换后纹理坐标 0==s，1==t
     */
    public float[] transition(String key,float [] psource){
        Frame fm =  frames.get(key);
        
        if(fm==null){
        	return null;
        }
        
        float[] fd = new float[2];

        //先计算出该uv坐标在原图中对应的像素点坐标为了精确使用float（fm.sourceSize[0] * psource[0]），
        // 在+上在新大图中起点位置
        /*if( psource[0]<0|| psource[0]>1){
            psource[0] = Math.abs(psource[0])- ((int)Math.abs(psource[0]));
        }
        if( psource[1]<0|| psource[1]>1){
            psource[1] = Math.abs(psource[1])- ((int)Math.abs(psource[1]));
        }*/

        //测试
       /* fd[0] = psource[0];
        fd[1] = psource[1];*/



        float dx = fm.sourceSize[0] * psource[0] + fm.frame[0][0];
        float dy = fm.sourceSize[1] * psource[1] + fm.frame[0][1];

        //由上面得到当前点在大图中位置计算新的 uv 坐标
        fd[0] = dx / size[0];
        fd[1] =dy / size[1] ;



        return fd;
    }
    public PlistData(String PlistFile_PathName) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(PlistFile_PathName), "utf-8"));

        StringBuffer plstsb = new StringBuffer();
        String str;
        int skipline = 0;//跳过plist 前两行文本，dom4j解析xml必须有根节点
        while ((str = bf.readLine()) != null) {
            if (skipline < 2) {
                skipline++;
            } else {
                plstsb.append(str);
            }
        }
        bf.close();

        Document document = DocumentHelper.parseText(plstsb.toString());

        Element root = document.getRootElement();

        //获得根节点
        Element rootDict = root.element(DICT);

        boolean IsFrames = false;
        boolean IsMetadata = false;

        for (Iterator<?> it = rootDict.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();
            if (IsFrames) {
                if (DICT.equals(element.getName())) {
                    //处理帧数据
                    for (Iterator<?> itc = element.elementIterator(); itc.hasNext(); ) {
                        Element c = (Element) itc.next();
                        if (KEY.equals(c.getName())) {
                            lastKey = c.getText();
                            Frame frametmp = new Frame(lastKey);
                            frames.put(lastKey,frametmp);

                            //this.frames.add(new Frame(c.getText()));

                        } else if (DICT.equals(c.getName())) {
                            Frame fm = this.frames.get(lastKey);

                            for (Iterator<?> itcc = c.elementIterator(); itcc.hasNext(); ) {
                                Element cc = (Element) itcc.next();
                                if (KEY.equals(cc.getName())) {
                                    String keyS = cc.getText();

                                    Element next = (Element) itcc.next();

                                    if (FRAME.equals(keyS)) {
                                        fm.frame = getArrayS(next.getText());
                                    } else if (OFFSET.equals(keyS)) {
                                        fm.offset = getArrayF(next.getText());
                                    } else if (ROTATED.equals(keyS)) {
                                        if ("true".equals(next.getName())) {
                                            fm.rotated = true;
                                        } else if ("false".equals(next.getName())) {
                                            fm.rotated = false;
                                        }
                                    } else if (SOURCECOLORRECT.equals(keyS)) {
                                        fm.sourceColorRect = getArrayS(next.getText());
                                    } else if (SOURCESIZE.equals(keyS)) {
                                        fm.sourceSize = getArrayI(next.getText());
                                    }

                                }
                            }
                        }
                    }
                }
                IsFrames = false;
                continue;
            } else if (IsMetadata) {
                if (DICT.equals(element.getName())) {
                    //处理当前动画信息
                    for (Iterator<?> itc = element.elementIterator(); itc.hasNext(); ) {
                        Element cc = (Element) itc.next();
                        Element next = (Element) itc.next();
                        if (FORMAT.equals(cc.getText())) {
                            this.format = Integer.valueOf(next.getText());
                        } else if (REALTEXTUREFILENAME.equals(cc.getText())) {
                            this.realTextureFileName = next.getText();
                        } else if (SIZE.equals(cc.getText())) {
                            this.size = getArrayI(next.getText());
                        } else if (SMARTUPDATE.equals(cc.getText())) {
                            this.smartupdate = next.getText();
                        } else if (TEXTUREFILENAME.equals(cc.getText())) {
                            this.textureFileName = next.getText();
                        }
                    }
                }
                IsMetadata = false;
                continue;
            }

            if (KEY.equals(element.getName())) {
                if (FRAMES.equals(element.getText())) {
                    IsFrames = true;
                } else if (METADATA.equals(element.getText())) {
                    IsMetadata = true;
                }
            }

        }

        System.out.println(root.getName());
    }


    /**
     * ｛34，34｝   =  int[] a = new int[]{34,34};
     * 根据字符串获得数组对象
     *
     * @param toStr
     * @return
     */
    public static int[] getArrayI(String toStr) {
        String[] str = toStr.substring(1, toStr.length() - 1).split(",");
        int[] r = new int[str.length];

        for (int i = 0; i < str.length; i++) {
            r[i] = Integer.valueOf(str[i]);
        }
        return r;
    }

    /**
     * ｛34，34｝   =  int[] a = new int[]{34,34};
     * 根据字符串获得数组对象
     *
     * @param toStr
     * @return
     */
    public static float[] getArrayF(String toStr) {
        String[] str = toStr.substring(1, toStr.length() - 1).split(",");
        float[] r = new float[str.length];

        for (int i = 0; i < str.length; i++) {
            r[i] = Float.valueOf(str[i]);
        }
        return r;
    }

    /**
     * 根据字符串返回2维数组 {{830,366},{102,162}}
     *
     * @param toStr
     * @return
     */
    public static int[][] getArrayS(String toStr) {
        String[] str = toStr.substring(2, toStr.length() - 2).split("\\},\\{");
        int[][] r = new int[str.length][];
        for (int i = 0; i < str.length; i++) {
            String[] s1 = str[i].split(",");
            r[i] = new int[s1.length];
            for (int k = 0; k < s1.length; k++) {
                r[i][k] = Integer.valueOf(s1[k]);
            }
        }
        return r;
    }


    public String lastKey;

    /**
     * 帧信息
     */
    //public List<Frame> frames = new ArrayList<Frame>();
    public HashMap<String,Frame>  frames = new HashMap<String,Frame>();
    /**
     * 例：
     * <key>format</key>
     * <integer>2</integer>
     */
    public int format;
    /**
     * 例：
     * <key>realTextureFileName</key>
     * <string>1003_role.png</string>
     */
    public String realTextureFileName;

    /**
     * 例：
     * <key>size</key>
     * <string>{1024,2048}</string>
     */
    public int[] size;

    /**
     * 例：
     * <key>smart update</key>
     * <string>$TexturePacker:SmartUpdate:80b524f806ac49025ed256bea7a83b86:1/1$</string>
     */
    public String smartupdate;

    /**
     * 例：
     * <key>textureFileName</key>
     * <string>1003_role.png</string>
     */
    public String textureFileName;
}
/*
<plist version="1.0">	
   <dict>
    <key>frames</key>
    <dict>
        <key>1003_role/0000</key>
        <dict>
            <key>frame</key>
            <string>{{830,366},{102,162}}</string>
            <key>offset</key>
            <string>{-10,-38}</string>
            <key>rotated</key>
            <true/>
            <key>sourceColorRect</key>
            <string>{{419,277},{102,162}}</string>
            <key>sourceSize</key>
            <string>{960,640}</string>
        </dict>
     </dict>
     <key>metadata</key>
      <dict>
            <key>format</key>
            <integer>2</integer>
            <key>realTextureFileName</key>
            <string>1003_role.png</string>
            <key>size</key>
            <string>{1024,2048}</string>
            <key>smartupdate</key>
            <string>$TexturePacker:SmartUpdate:80b524f806ac49025ed256bea7a83b86:1/1$</string>
            <key>textureFileName</key>
            <string>1003_role.png</string>
      </dict>
   </dict>       
<plist/>
*/