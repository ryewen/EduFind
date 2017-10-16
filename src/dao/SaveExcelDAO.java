package dao;

import java.io.OutputStream;
import java.util.List;

import teachnet.info.object.InfoObject;

public interface SaveExcelDAO {

	public void saveIntoExcel(List<InfoObject> lessons, OutputStream os, int year, int month, int date);
}
