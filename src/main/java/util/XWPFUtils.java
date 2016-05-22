package util;

import javafx.collections.ObservableList;
import model.Card;
import org.apache.poi.xwpf.usermodel.*;

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

    public static void appendTableHeader(XWPFTable table, String[] headers) {
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText(headers[0]);
        headerRow.addNewTableCell().setText(headers[1]);
        headerRow.addNewTableCell().setText(headers[2]);
        headerRow.addNewTableCell().setText(headers[3]);
        headerRow.addNewTableCell().setText(headers[4]);
        headerRow.addNewTableCell().setText(headers[5]);
    }

    public static void appentTableBody(XWPFTable table, ObservableList<Card> items) {
        for (Card card : items) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(card.getAuto());
            tableRow.getCell(1).setText(card.getDateAdd());
            tableRow.getCell(2).setText(card.getDateDel());
            tableRow.getCell(3).setText(String.valueOf(card.getMilage()));
            tableRow.getCell(4).setText(card.getState());
            tableRow.getCell(5).setText(card.getReason());
        }
    }
}
