package util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static void saveAndOpenFile(XWPFDocument document, File file) {
        try (FileOutputStream out = new FileOutputStream(file)){
            document.write(out);
            out.close();
            java.awt.Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
