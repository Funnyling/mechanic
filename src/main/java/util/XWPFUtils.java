package util;

import javafx.collections.ObservableList;
import model.DocumentDto;
import org.apache.poi.xwpf.usermodel.*;

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

    public static void appendTableBody(XWPFTable table, ObservableList<DocumentDto> items) {
        for (DocumentDto documentDto : items) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(documentDto.getAuto());
            tableRow.getCell(1).setText(documentDto.getDateAdd());
            tableRow.getCell(2).setText(documentDto.getDateDel());
            tableRow.getCell(3).setText(String.valueOf(documentDto.getMilage()));
            tableRow.getCell(4).setText(documentDto.getState());
            tableRow.getCell(5).setText(documentDto.getReason());
        }
    }
}
