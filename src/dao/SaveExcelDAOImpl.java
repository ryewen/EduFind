package dao;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import teachnet.info.object.lesson.Lesson;
import teachnet.info.object.lesson.LessonDetailConverter;
import teachnet.info.object.InfoObject;

@Component
public class SaveExcelDAOImpl implements SaveExcelDAO {

	@Autowired
	private LessonDetailConverter lessonConverter;
	
	@Override
	public void saveIntoExcel(List<InfoObject> lessons, OutputStream os, int year, int month, int date) {
		try {
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet ws = wwb.createSheet("lesson", 0);
			WritableCellFormat format = new WritableCellFormat(new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD));
			ws.addCell(new Label(0, 0, "Subject", format));		// name
			ws.addCell(new Label(1, 0, "Start Date", format));	// 05/30/2020
			ws.addCell(new Label(2, 0, "Start Time", format));	// 10:00 AM
			ws.addCell(new Label(3, 0, "End Date", format));	// 05/30/2020
			ws.addCell(new Label(4, 0, "End Time", format));	// 1:00 PM
			ws.addCell(new Label(5, 0, "All Day Event", format));	//	False
			ws.addCell(new Label(6, 0, "Description", format));	//	50 个多选题和 2 个问答题
			ws.addCell(new Label(7, 0, "Location", format));	//	“北京大学，逸夫楼 209 室”
			ws.addCell(new Label(8, 0, "Private", format));		// True
			Iterator<InfoObject> it1 = lessons.iterator();
			int i = 1;
			while(it1.hasNext()) {
				Iterator<Lesson> it2 = lessonConverter.convertDetail((Lesson) it1.next(), year, month, date).iterator();
				while(it2.hasNext()) {
					Lesson lesson = it2.next();
					ws.addCell(new Label(0, i, lesson.getShortName(), format));
					ws.addCell(new Label(1, i, lesson.getDate(), format));
					ws.addCell(new Label(2, i, lesson.getStartTime(), format));
					ws.addCell(new Label(3, i, lesson.getDate(), format));
					ws.addCell(new Label(4, i, lesson.getEndTime(), format));
					ws.addCell(new Label(5, i, "FALSE", format));
					ws.addCell(new Label(6, i, "课程", format));
					ws.addCell(new Label(7, i, lesson.getLocation(), format));
					ws.addCell(new Label(8, i, "TRUE", format));
					i ++;
				}
			}
			wwb.write();
			wwb.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
