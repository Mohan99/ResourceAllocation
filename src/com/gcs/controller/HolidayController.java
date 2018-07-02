package com.gcs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Holidaycalender;
import com.gcs.db.businessDao.State;
import com.gcs.dbDao.CategoryDao;
import com.gcs.dbDao.HolidayDao;
import com.gcs.requestDao.CategoryRequest;
import com.gcs.requestDao.HolidaycalenderRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Holiday")
public class HolidayController extends BaseController {

	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;

	@Autowired
	@Qualifier("holidayDao")
	private HolidayDao holidayDao;

	@Autowired
	private SessionData sessionobj;

	@RequestMapping(value = "/createHolidays", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			HolidaycalenderRequest calenderRequest = new HolidaycalenderRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("calenderRequest", calenderRequest);

			modelObj = new ModelAndView("createHoliday", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/searchHolidays", method = RequestMethod.GET)
	public ModelAndView employeeList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Holidaycalender> calenderList = holidayDao.getHolidaysList();
			modelObj = new ModelAndView("searchHolidays", "calenderList", calenderList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "holidayBulkUpload", method = RequestMethod.GET)
	public String employeeBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "holidayBulkUpload";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createOrupdateHoliday", method = RequestMethod.GET)
	public String createOrupdateCategory(@ModelAttribute HolidaycalenderRequest calenderRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Holidaycalender calender = new Holidaycalender();
			if (calenderRequest.getId() != 0) {
				calender.setId(calenderRequest.getId());
			}
			calender.setDate(calenderRequest.getDate());
			if (calenderRequest.getReason() != null)
				calender.setReason(calenderRequest.getReason());
			calender.setType(calenderRequest.getType());
			holidayDao.saveOrUpdate(calender);
			return "redirect:/Holiday/searchHolidays";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {
				List<Holidaycalender> holidayList = holidayDao.getHolidaysList();
				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				String fileName = file.getOriginalFilename();
				FileInputStream fileStream = new FileInputStream(file1);

				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				XSSFSheet ws = workbook.getSheetAt(0);
				ws.setForceFormulaRecalculation(true);
				DataFormatter df = new DataFormatter();

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int date = -1, reason = -1, type = -1;

				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {

					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("date".equalsIgnoreCase(cellValue)) {
						date = j;
					} else if ("reason".equalsIgnoreCase(cellValue)) {
						reason = j;
					} else if ("type".equalsIgnoreCase(cellValue)) {
						type = j;
					}
				}

				if (date == -1 || reason == -1 || type == -1) {
					try {
						throw new Exception("Could not find header indexes\nemployeeId : ");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				for (int i = 1; i < rowNum; i++) {
					XSSFRow row = ws.getRow(i);
					int calFlag = 1;
					Holidaycalender calender = new Holidaycalender();
					Date hlDate = null;
					String hlDateStr = df.formatCellValue(row.getCell(date));
					hlDate = new SimpleDateFormat("dd-MM-yyyy").parse(hlDateStr);
					for (Holidaycalender cal : holidayList) {
						calFlag=cal.getDate().compareTo(hlDate);
						if(calFlag==0)
							break;
					}
					if (calFlag!=0) {
						calender.setDate(hlDate);
						calender.setReason(cellToString(row.getCell(reason)));
						String typeStr = cellToString(row.getCell(type));
						if (typeStr.equalsIgnoreCase("GCS"))
							calender.setType(1);
						else if (typeStr.equalsIgnoreCase("WCM"))
							calender.setType(2);
						else if (typeStr.equalsIgnoreCase("ALL"))
							calender.setType(3);

						holidayDao.saveOrUpdate(calender);

						fileStream.close();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "redirect:/Holiday/searchHolidays";
		} else
			return "redirect:/login";
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		type = cell.getCellType();

		switch (type) {

		case XSSFCell.CELL_TYPE_NUMERIC:
			result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();

			break;
		case XSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
		}

		return result.toString();
	}

}
