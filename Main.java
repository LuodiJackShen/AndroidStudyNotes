import java.io.*;

/**
 * by shenmingliang1
 * 2018.12.17 11:43.
 */

class Main {

    private void findErrFile(String path) {
        File file = new File(path);
        String[] list = file.list();
        if (list == null) {
            System.out.println("=========================路径为空" + path);
            return;
        }
        int length = list.length;
        System.out.println("file.size=" + length);
        for (int i = 0; i < length; i++) {
            String onePath = list[i];
            File oneFile = new File(path, onePath);
            FileInputStream fis = null;
            FileReader fr = null;
            char[] b = new char[1];
            try {
                //                System.out.print("寻找当前文件="+onePath);
                fr = new FileReader(oneFile);
                fr.read(b, 0, 1);
                //                System.out.println("the first c of the file = "+b[0]);
                if (b[0] == 0xfeff) {
                    System.out.println("=========================当前文件出现异常字符， file=" + onePath);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //            System.out.println("the first c of the file = "+b[0]);
            if (b[0] == 0xfeff) {

            }
        }
    }

    public static void main(String[] args) {
        new Main().findErrFile("D:\\as_workspace\\mrd-android-innersite-as\\MrdAndroidInnerSite\\src\\main\\res\\layout");
    }
}
