package util;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Created by Елена on 22.05.2016.
 */
public class XWPFUtils {

    public static void appendNewRun(XWPFParagraph paragraph, String text, int fontSize, boolean bold,
                              boolean needUnderline, boolean needBreak) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setBold(bold);
        run.setText(text);
        if (needUnderline) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
        if (needBreak) {
            run.addBreak();
        }
    }
}
