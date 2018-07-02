package com.gcs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
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
import org.springframework.web.servlet.ModelAndView;

import com.gcs.bean.EmpBenchBean;
import com.gcs.bean.ResourceReportBean;
import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.EmployeeSkillSet;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.Timesheet;
import com.gcs.dbDao.CategoryDao;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ProjectDao;
import com.gcs.dbDao.ResourceDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.EmpReportDataRequest;
import com.gcs.requestDao.ResourceRequest;
import com.gcs.requestDao.TimesheetRequest;
import com.gcs.responseDao.EmployeeResponseReport;
import com.gcs.responseDao.Response;

import antlr.ParserSharedInputState;

@Controller
@RequestMapping("Reports")
public class ReportsController extends BaseController {
	@Autowired
	@Qualifier("resourceDao")
	private ResourceDao resourceDao;
	@Autowired
	//@Qualifier("usersDao")
	private UsersDao usersDao;
	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("projectDao")
	private ProjectDao projectDao;
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;
	@Autowired
	private SessionData sessionobj;

	private List<Employee> empActiveList;
	private List<Resourceallocations> resCmpList;
	private List<Projects> projCmpList;
	private ReportsController controller;

	public Projects getProjectObj(List<Projects> projList, int projId) {
		Projects projObj = null;
		for (Projects projects : projList) {
			if (projId == projects.getProjectId()) {
				projObj = new Projects();
				projObj.setProjectId(projects.getProjectId());
				projObj.setProjectName(projects.getProjectName());
				projObj.setStartDate(projects.getStartDate());
				projObj.setEndDate(projects.getEndDate());
				projObj.setStatus(projects.isStatus());
			}
		}
		return projObj;
	}

	
	
	@RequestMapping(value = "getAllEmpReport", method = RequestMethod.GET)
	public String allEmployeeReport(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<ResourceReportBean> resourceList = resourceDao.getEmployeeReport();
			List<EmployeeSkillSet> empSkillList = categoryDao.getEmpSkillSet();
			System.out.println("resourcelist===" + resourceList.size());

			try {
				empActiveList = empDao.getActiveEmployeeList();
				//empSkillList = 
				resCmpList = resourceDao.getResources();
				Employee employee = null;
				EmployeeSkillSet empSkill = null;
				Resourceallocations resource = null;
				ResourceReportBean bean = null;
				for (Employee emp : empActiveList) {
					boolean found = false;
					for (Resourceallocations res : resCmpList) {
						if (emp.getEmpId() == res.getEmployeeId()) {
							found = true;

						}
					}
					
					
					if (found == false  && emp.getEmployeeCategory().getIsCategory().equals("Y")) {
						employee = new Employee();
						bean = new ResourceReportBean();
						
						resource = new Resourceallocations();
						
						empSkill = new EmployeeSkillSet();
						
						employee.setEmpId(emp.getEmpId());
						employee.setEmployeeId(emp.getEmployeeId());
						employee.setEmployeeName(emp.getEmployeeName());
						employee.setEmployeeDesg(emp.getEmployeeDesg());
						//employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
						employee.setEmployeeCategory(emp.getEmployeeCategory());
						employee.setEmployeeCity(emp.getEmployeeCity());
						employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
						employee.setReportingTo(emp.getReportingTo());

						resource.setProjectFromStr(null);

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
						if (emp.getJoinDate() != null)
							employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
						bean.setEmp(emp);
						bean.setResource(resource);
						bean.setStatus("Bench");
						resourceList.add(bean);
						System.out.println("resourceList=====" +resourceList.size());
						
					}

					model.put("resourceList", resourceList);
					model.put("empSkillList", empSkillList);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "employeeReport";
		} else
			return "redirect:/login";

	}

	
	@RequestMapping(value = "getAllResorcesUnderManager", method = RequestMethod.GET)
	public String resorcesListUndermanager(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<ResourceReportBean> resourceList = resourceDao
					.getBenchEmployeesUnderManager(sessionobj.getEmpObj().getEmpId());
			try {
				empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());
				resCmpList = resourceDao.getResources();
				Employee employee = null;
				Resourceallocations resource = null;
				
				ResourceReportBean bean = null;
				for (Employee emp : empActiveList) {
					//boolean found = false;
					for (Resourceallocations res : resCmpList) {
						if (emp.getEmpId() == res.getEmployeeId()) {
							//found = true;
							employee = new Employee();
							bean = new ResourceReportBean();
							resource = new Resourceallocations();
							employee.setEmpId(emp.getEmpId());
							employee.setEmployeeId(emp.getEmployeeId());
							employee.setEmployeeName(emp.getEmployeeName());
							employee.setEmployeeDesg(emp.getEmployeeDesg());
							//employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
							
							employee.setEmployeeCategory(emp.getEmployeeCategory());
							employee.setEmployeeCity(emp.getEmployeeCity());
							employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
							employee.setBenchStatus(emp.isBenchStatus());
							employee.setWorkplace(emp.getWorkplace());

							resource.setProjectFromStr(null);

							SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
							if (emp.getJoinDate() != null)
								employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
							//bean.setEmp(emp);
							//bean.setResource(resource);
							//bean.setStatus("Bench");
							resourceList.add(bean);

						}
					}
				
				}
				model.put("resourceList", resourceList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "employeeSetBench";
		} else
			return "redirect:/login";

	}

	
	@RequestMapping(value = "getBenchEmployees", method = RequestMethod.GET)
	 public String benchEmployeesReport(ModelMap model) {
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   List<ResourceReportBean> resourceList = resourceDao.getBenchEmployeesReport();

	   try {
	    empActiveList = empDao.getActiveEmployeeList();
	    resCmpList = resourceDao.getResources();
	    Employee employee = null;
	    Resourceallocations resource = null;
	    ResourceReportBean bean = null;
	    for (Employee emp : empActiveList) {
	     boolean found = false;
	     for (Resourceallocations res : resCmpList) {
	      if (emp.getEmpId() == res.getEmployeeId()) {
	    	  
	       found = true;

	      }
	     }
	    // System.out.println("Category====" + emp.getEmployeeCategory().getIsCategory());
	     if (found == false && emp.getEmployeeCategory().getIsCategory().equals("Y")) {
	      employee = new Employee();
	      bean = new ResourceReportBean();
	      resource = new Resourceallocations();
	      employee.setEmpId(emp.getEmpId());
	      employee.setEmployeeId(emp.getEmployeeId());
	      employee.setEmployeeName(emp.getEmployeeName());
	      employee.setEmployeeDesg(emp.getEmployeeDesg());
	      employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
	      employee.setEmployeeCategory(emp.getEmployeeCategory());
	      employee.setEmployeeCity(emp.getEmployeeCity());
	      employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
	      employee.setReportingTo(emp.getReportingTo());

	      resource.setProjectFromStr(null);

	      SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
	      if(employee.getJoinDateStr() != null)

	      employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
	      bean.setEmp(emp);
	      bean.setResource(resource);
	      bean.setStatus("Bench");
	      resourceList.add(bean);
	     }
	   

	     model.put("resourceList", resourceList);
	    }
	   } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	   return "benchEmployeesReport";
	  } else
	   return "redirect:/login";

	 }
	@RequestMapping(value = "exportBenchEmployees", method = RequestMethod.GET)
	 public String exportBenchEmployeesReport(ModelMap model, HttpServletResponse response) {
	  ModelAndView modelObj = null;
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   List<ResourceReportBean> resourceList = resourceDao.getBenchEmployeesReport();
	   controller = new ReportsController();
	   try {
	    Employee employee = null;
	    ResourceReportBean bean = null;
	    Resourceallocations resource = null;
	    for (Employee emp : empActiveList) {
	     boolean found = false;
	     for (Resourceallocations res : resCmpList) {
	      if (emp.getEmpId() == res.getEmployeeId()) {
	       found = true;
	      }
	     }

	     if (found == false && emp.getEmployeeCategory().getIsCategory().equals("Y")) {
	      employee = new Employee();
	      resource = new Resourceallocations();
	      bean = new ResourceReportBean();
	      employee.setEmpId(emp.getEmpId());
	      employee.setEmployeeId(emp.getEmployeeId());
	      employee.setEmployeeName(emp.getEmployeeName());
	      employee.setEmployeeDesg(emp.getEmployeeDesg());
	      employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
	      employee.setEmployeeCategory(emp.getEmployeeCategory());
	      employee.setEmployeeCity(emp.getEmployeeCity());
	      employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
	      employee.setReportingTo(emp.getReportingTo());
	      // employee.setJoinDate(emp.getJoinDate());
	      SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
	      if(emp.getJoinDate()!= null){
	      employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
	      }
	      resource.setProjectFrom(null);
	      bean.setResource(resource);
	      bean.setEmp(emp);
	      bean.setStatus("Bench");
	      resourceList.add(bean);
	     }
	    }
	    controller.doExport(resourceList, response);
	    model.put("resourceList", resourceList);
	   } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	   return "employeeReport";
	  } else
	   return "redirect:/login";

	 }
	
	public void doEmpTotalBenchPeriodExport(List<EmpBenchBean> resourceList, HttpServletResponse response)
			throws Exception {
		if (resourceList != null && !resourceList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();

			HSSFSheet sheet = workBook.createSheet("All Employee Bench Report");

			for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 8);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			// style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 8);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("Name");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("Bench Days");
			ch3.setCellStyle(style2);

			sheet.setFitToPage(true);

			short rowNo = 4;
			for (EmpBenchBean details : resourceList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(details.getEmployeeId());
				row.createCell((short) 1).setCellValue(details.getEmployeeName());
				if (details.getBenchPeriod() == -1)
					row.createCell((short) 2).setCellValue("Still Bench");
				else
					row.createCell((short) 2).setCellValue(details.getBenchPeriod());

				rowNo++;
			}

			String file = "EmpsTotalBenchPeriod.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}


	@RequestMapping(value = "exportEmployeesReport", method = RequestMethod.GET)
	public String exportEmployeeReport(ModelMap model, HttpServletResponse response) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<ResourceReportBean> resourceList = resourceDao.getEmployeeReport();
			controller = new ReportsController();
			try {
				/*
				 * if (empActiveList == null) empActiveList = empDao.getActiveEmployeeList(); if
				 * (resCmpList == null) resCmpList = resourceDao.getResources();
				 */
				Employee employee = null;
				ResourceReportBean bean = null;
				Resourceallocations resource = null;
				for (Employee emp : empActiveList) {
					boolean found = false;
					for (Resourceallocations res : resCmpList) {
						if (emp.getEmpId() == res.getEmployeeId()) {
							found = true;
						}
					}

					if (found == false) {
						employee = new Employee();
						resource = new Resourceallocations();
						bean = new ResourceReportBean();
						employee.setEmpId(emp.getEmpId());
						employee.setEmployeeId(emp.getEmployeeId());
						employee.setEmployeeName(emp.getEmployeeName());
						employee.setEmployeeDesg(emp.getEmployeeDesg());
						employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
						employee.setEmployeeCategory(emp.getEmployeeCategory());
						employee.setEmployeeCity(emp.getEmployeeCity());
						employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());
						// employee.setJoinDate(emp.getJoinDate());
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
						if(emp.getJoinDate()!=null){
						employee.setJoinDateStr(formatter.format(emp.getJoinDate()));
						}
						resource.setProjectFrom(null);
						bean.setResource(resource);
						bean.setEmp(emp);
						bean.setStatus("Bench");
						resourceList.add(bean);
					}
				}
				controller.doExport(resourceList, response);
				model.put("resourceList", resourceList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "employeeReport";
		} else
			return "redirect:/login";

	}


	@RequestMapping(value = "/empBenchSearch", method = RequestMethod.GET)
	public String empBenchSearch(ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		List<Employee> empList = null;
		ResourceRequest resReq = new ResourceRequest();
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.getResourcesList();
			empList = empDao.getActiveEmployeeList();
			model.put("resourceList", resourceList);
			model.put("empList", empList);
			model.put("resReq", resReq);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return "empBenchReport";
	}

	public Employee getEmployeeObj(int empId, List<Employee> empList) {
		Employee emp = null;
		for (Employee employee : empList) {
			if (employee.getEmpId() == empId) {
				emp = new Employee();
				emp.setEmployeeId(employee.getEmployeeId());
				emp.setEmployeeName(employee.getEmployeeName());
			}
		}
		return emp;

	}

	@RequestMapping(value = "/empBenchPeriod", method = RequestMethod.GET)
	public String empBenchPeriodReport(ModelMap model, @RequestParam int empId, @RequestParam Date startDate,
			@RequestParam Date endDate) throws ParseException {
		List<Resourceallocations> resList = resourceDao.getEmpBenchPeriodReport(empId, startDate, endDate);
		Date todate = null;
		Date fromdate = null;
		Date currDate = new Date();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		String strDate = formatter.format(date);
		int i = 0;
		EmpBenchBean bean = null;
		List<EmpBenchBean> benchList = new ArrayList<EmpBenchBean>();
		Employee emp = null;
		empActiveList = empDao.getActiveEmployeeList();
		emp = getEmployeeObj(empId, empActiveList);
		for (Resourceallocations myList : resList) {
			if (myList.getProjectTo() != null) {
				todate = myList.getProjectTo();
			} else
				todate = currDate;
			i++;
			if (todate == null) {
				break;
			} else {
				if (i < resList.size()) {
					Resourceallocations res = resList.get(i);
					fromdate = res.getProjectFrom();
					if (todate != null) {
						long diff = fromdate.getTime() - todate.getTime();
						if (diff > 0) {
							bean = new EmpBenchBean();

							bean.setEmployeeId(emp.getEmployeeId());
							bean.setEmployeeName(emp.getEmployeeName());
							bean.setFromDate(todate);
							bean.setToDate(fromdate);
							bean.setBenchPeriod(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
							benchList.add(bean);

						}

					}
				}

				if (i == resList.size() && (todate != null || todate.before(currDate))) {
					Resourceallocations res = resList.get(i - 1);
					fromdate = res.getProjectFrom();
					long diff = currDate.getTime() - todate.getTime();
					if (diff > 0) {
						bean = new EmpBenchBean();

						bean.setEmployeeId(emp.getEmployeeId());
						bean.setEmployeeName(emp.getEmployeeName());
						bean.setFromDate(todate);
						SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
						String CurrDate = s.format(currDate);
						// sbean.setToDate(currDate.getDate());

						bean.setBenchPeriod(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
						benchList.add(bean);
					}
				}

			}
		}
		model.put("benchList", benchList);
		return "empBenchPeriodReport";

	}

	@RequestMapping(value = "/allEmpBenchPeriod", method = RequestMethod.GET)
	public String allEmpBenchPeriodReport(ModelMap model, HttpServletRequest req) throws ParseException {
		DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate = formatter1.parse(req.getParameter("startdate"));
		Date endDate = formatter1.parse(req.getParameter("enddate"));
		Date todate = null;
		Date fromdate = null;
		Date tempDate = null;
		Date currDate = new Date();

		List<EmpBenchBean> benchList = new ArrayList<>();
		EmpBenchBean bean = null;
		List<Employee> empActiveList = empDao.getActiveEmployeeList();

		String empId = null;
		String empName = null;

		List<Resourceallocations> resList = resourceDao.getAllEmpBenchPeriodReport(startDate, endDate);
		for (Employee employee : empActiveList) {
			boolean empFlag = false;
			int i = 0;
			int temp = 0;
			boolean tempFlag = true;
			for (Resourceallocations myList : resList) {
				i++;
				if (employee.getEmpId() == myList.getEmployeeId() && employee.getEmployeeCategory().getIsCategory().equals("Y")) {
					empId = employee.getEmployeeId();
					empName = employee.getEmployeeName();
					if (myList.getProjectTo() != null && tempFlag) {
						todate = myList.getProjectTo();
					} else if (tempFlag)
						todate = currDate;

					if (todate == null) {
						break;
					} else {

						if (i < resList.size()) {
							Resourceallocations res = resList.get(i);
							fromdate = res.getProjectFrom();
							tempDate = res.getProjectTo();
							if (tempDate != null && todate.after(tempDate)) {
								tempFlag = false;
							}
							if (tempFlag) {
								long diff = fromdate.getTime() - todate.getTime();
								if (diff > 0 && employee.getEmployeeCategory().getIsCategory().equals("Y")) {
									bean = new EmpBenchBean();
									bean.setEmployeeId(empId);
									bean.setEmployeeName(empName);
									bean.setFromDate(todate);
									bean.setToDate(fromdate);
									bean.setBenchPeriod(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
									benchList.add(bean);
								}

							}

						}
					}

				} else {
					if (temp != 0 && empFlag == false && employee.getEmployeeCategory().getIsCategory().equals("Y") && (todate == null || todate.before(currDate))) {
						Resourceallocations res = resList.get(i - 1);
						fromdate = res.getProjectFrom();
						tempDate = res.getProjectTo();
						if (tempDate != null && todate.after(tempDate)) {
							tempFlag = false;
						}
						if (tempFlag) {
							long diff = currDate.getTime() - todate.getTime();
							if (diff > 0) {
								bean = new EmpBenchBean();
								bean.setEmployeeId(empId);
								bean.setEmployeeName(empName);
								bean.setFromDate(todate);
								SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
								String CurrDate = s.format(currDate);
								// bean.setToDate(currDate);
								bean.setBenchPeriod(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
								benchList.add(bean);
							}
						}
					}
					temp = 1;
					empFlag = true;
				}
				
				if(todate != null){

				if (i == resList.size()  && employee.getEmployeeCategory().getIsCategory().equals("Y") && (todate != null || todate.before(currDate))) {
					Resourceallocations res = resList.get(i - 1);
					fromdate = res.getProjectFrom();
					if(todate != null){
					long diff = currDate.getTime() - todate.getTime();
					if (diff > 0) {
						bean = new EmpBenchBean();
						bean.setEmployeeId(employee.getEmployeeId());
						bean.setEmployeeName(employee.getEmployeeName());
						bean.setFromDate(todate);
						SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
						String CurrDate = s.format(currDate);

						bean.setBenchPeriod(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
						benchList.add(bean);
					}
					}
					}
				}
			}
		}

		model.put("benchList", benchList);
		return "allEmpBenchReport";
	}

	@RequestMapping(value = "/empsTotalBenchPeriod", method = RequestMethod.GET)
	public String allEmpsBenchPeriod(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "fetchEmpsBenchPeriod";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}

	}

	@RequestMapping(value = "/allEmpsTotalBenchPeriod", method = RequestMethod.GET)
	public String searchAllEmpsBenchPeriod(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "fetchAllEmpReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}

	}

	@RequestMapping(value = "/getEmpsTotalBenchPeriod", method = RequestMethod.POST)
	public String getTotalEmpsBenchPeriod(ModelMap model, HttpServletRequest req) throws ParseException {
		DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate = formatter1.parse(req.getParameter("startdate"));
		Date endDate = formatter1.parse(req.getParameter("enddate"));
		Date todate = null;
		Date fromdate = null;
		Date currDate = new Date();

		// get the join date all employees
		List<EmpBenchBean> benchList = new ArrayList<>();
		EmpBenchBean bean = null;
		List<Employee> empActiveList = empDao.getActiveEmployeeList();
		List<EmpBenchBean> listBean = resourceDao.getEmpsTotalBenchPeriod(startDate, endDate);

		for (Employee employee : empActiveList) {
			int i = 0;
			long days = 0;
			List<Resourceallocations> resList = resourceDao.getEmpBenchPeriodReport(employee.getEmpId(), startDate,
					endDate);
			int intI = 0;
			if (resList.size() > 0 && employee.getEmployeeCategory().getIsCategory().equals("Y")) {
				boolean found = true;

				bean = new EmpBenchBean();
				bean.setEmployeeId(employee.getEmployeeId());
				bean.setEmployeeName(employee.getEmployeeName());
				Date empJoinDate = employee.getJoinDate();
				for (Resourceallocations myList : resList) {
					if(empJoinDate!= null){
					if (startDate.before(empJoinDate))
						startDate = empJoinDate;
					}
					long diff = 0;
					if (startDate.before(myList.getProjectFrom()) && i == 0) {
						if (myList.getEmployeeId() == 1)
							diff = myList.getProjectFrom().getTime() - startDate.getTime();
						if (diff > 0) {
							days = days + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						}
					}
					

					i++;
					if (myList.getProjectTo() != null) {
						if (startDate.before(myList.getProjectTo()))
							todate = myList.getProjectTo();
						if (i < resList.size()) {
							Resourceallocations res = resList.get(i);
							fromdate = res.getProjectFrom();
							if(todate != null){
							diff = fromdate.getTime() - todate.getTime();
							}
							if (diff > 0) {
								days = days + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
							}

						}
						
						if (i == resList.size() && (todate != null || todate.before(currDate))) {
							Resourceallocations res = resList.get(i - 1);
							fromdate = res.getProjectFrom();
							diff = currDate.getTime() - todate.getTime();
							if (diff > 0) {
								days = days + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
							}
						}
					} else {
						found = false;
						bean.setBenchPeriod(days);
						benchList.add(bean);
						break;
					}
				}

				bean.setBenchPeriod(days);
				if (found)
					benchList.add(bean);
				intI++;
			} else if (employee.getEmployeeCategory().getIsCategory().equals("Y")) {
				bean = new EmpBenchBean();
				bean.setEmployeeId(employee.getEmployeeId());
				bean.setEmployeeName(employee.getEmployeeName());
				bean.setBenchPeriod(-1);
				benchList.add(bean);
			}

		}
		model.put("benchList", benchList);
		model.put("startdate", req.getParameter("startdate"));
		model.put("enddate", req.getParameter("enddate"));
		return "employeesTotalBenchPeriodReport";

	}

	@RequestMapping(value = "/exportEmployeesTotalBenchPeriod/{startdate}/{enddate}", method = RequestMethod.GET)
	public String exportTotalEmpsBenchPeriod(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startdate, @PathVariable String enddate) throws ParseException {
		DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate = formatter1.parse(startdate);
		Date endDate = formatter1.parse(enddate);
		Date todate = null;
		Date fromdate = null;
		Date currDate = new Date();

		List<EmpBenchBean> benchList = new ArrayList<>();
		EmpBenchBean bean = null;
		List<Employee> empActiveList = empDao.getActiveEmployeeList();
		List<EmpBenchBean> listBean = resourceDao.getEmpsTotalBenchPeriod(startDate, endDate);

		for (Employee employee : empActiveList) {
			int i = 0;
			long days = 0;
			List<Resourceallocations> resList = resourceDao.getEmpBenchPeriodReport(employee.getEmpId(), startDate,
					endDate);
			if (resList.size() > 0) {
				boolean found = true;
				bean = new EmpBenchBean();
				bean.setEmployeeId(employee.getEmployeeId());
				bean.setEmployeeName(employee.getEmployeeName());
				for (Resourceallocations myList : resList) {

					long diff = 0;
					if (startDate.before(myList.getProjectFrom()) && i == 0) {
						diff = myList.getProjectFrom().getTime() - startDate.getTime();
						if (diff > 0) {
							days = days + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						}
					}

					i++;
					if (myList.getProjectTo() != null) {
						if (startDate.before(myList.getProjectTo()))
							todate = myList.getProjectTo();
						if (i < resList.size()) {
							Resourceallocations res = resList.get(i);
							fromdate = res.getProjectFrom();
							diff = fromdate.getTime() - todate.getTime();
							if (diff > 0) {
								days = days + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
							}

						}
						if (i == resList.size() && (todate != null || todate.before(currDate))) {
							Resourceallocations res = resList.get(i - 1);
							fromdate = res.getProjectFrom();
							diff = currDate.getTime() - todate.getTime();
							if (diff > 0) {
								days = days + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
							}
						}
					} else {
						found = false;
						bean.setBenchPeriod(days);
						benchList.add(bean);
						break;
					}
				}

				bean.setBenchPeriod(days);
				if (found)
					benchList.add(bean);
			} else {
				bean = new EmpBenchBean();
				bean.setEmployeeId(employee.getEmployeeId());
				bean.setEmployeeName(employee.getEmployeeName());
				bean.setBenchPeriod(-1);
				benchList.add(bean);
			}

		}
		try {
			doEmpTotalBenchPeriodExport(benchList, response);
			model.put("benchList", benchList);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "employeesTotalBenchPeriodReport";
	}

	private void downloadFile(final String fileName, HttpServletResponse response) {
		final File f = new File(fileName);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "max-age=0");
		FileInputStream fin = null;
		ServletOutputStream os = null;
		try {
			fin = new FileInputStream(f);
			final int size = 1024;
			response.setContentLength(fin.available());
			final byte[] buffer = new byte[size];
			os = response.getOutputStream();
			int length = 0;
			while ((length = fin.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fin != null)
					fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	

public void doExport(List<ResourceReportBean> resourceList, HttpServletResponse response) throws Exception {
		if (resourceList != null && !resourceList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();
			HSSFSheet sheet = workBook.createSheet("Technical Team");

			for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
				sheet.autoSizeColumn((short) (columnPosition));
			}

			sheet.setHorizontallyCenter(true);
			sheet.setFitToPage(true);
			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 15);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 13);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// HSSFCell ch3 =headingRow.createCell(0);

			// HSSFRow headingRow = sheet.createRow(3);
			// HSSFCell ch3 = headingRow.createCell(9);
			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("Name");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("Designation");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("Service Category");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("Primary Assignment");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("Other Assignments");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("Specialization");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 7);
			ch3.setCellValue("Mobile No");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 8);
			ch3.setCellValue("Location");
			ch3.setCellStyle(style2);
			/*ch3 = headingRow.createCell((short) 9);
			ch3.setCellValue("Date");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 10);
			ch3.setCellValue("Join Date");
			ch3.setCellStyle(style2);*/
			ch3 = headingRow.createCell((short) 9);
			ch3.setCellValue("Reporting To");
			ch3.setCellStyle(style2);

			short rowNo = 4;

			for (ResourceReportBean details : resourceList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellStyle(style);
				row.createCell((short) 0).setCellValue(details.getEmp().getEmployeeId());
				row.createCell((short) 1).setCellStyle(style);
				row.createCell((short) 1).setCellValue(details.getEmp().getEmployeeName());
				row.createCell((short) 2).setCellStyle(style);
				row.createCell((short) 2).setCellValue(details.getEmp().getEmployeeDesg());
				row.createCell((short) 3).setCellStyle(style);
				if(details.getEmp().getEmployeeCategory()!=null)
				row.createCell((short) 3).setCellValue(details.getEmp().getEmployeeCategory().getCategoryName());
				row.createCell((short) 4).setCellStyle(style);
				if (details.getStatus() == "_" || details.getStatus().equalsIgnoreCase("Bench")) {
					row.createCell((short) 4).setCellValue("Bench");
				} else {
					row.createCell((short) 4).setCellValue(details.getPrimaryProjects());
				}

				row.createCell((short) 5).setCellStyle(style);
				if (details.getStatus() == "_" || details.getStatus().equalsIgnoreCase("Bench")) {
					row.createCell((short) 5).setCellValue("");
				} else {
					row.createCell((short) 5).setCellValue(details.getSecondaryProjects());
				}

				row.createCell((short) 6).setCellStyle(style);
				row.createCell((short) 6).setCellValue(details.getEmp().getEmployeeSpecialization());
				row.createCell((short) 7).setCellStyle(style);
				row.createCell((short) 7).setCellValue(details.getEmp().getEmployeeMobilenumber());
				
				row.createCell((short) 8).setCellStyle(style);
				if(details.getEmp().getEmployeeCity()!= null)
				row.createCell((short) 8).setCellValue(details.getEmp().getEmployeeCity().getName());
				
				row.createCell((short) 9).setCellStyle(style);
				
				if(details.getPrimaryProjects() == null && details.getEmp().getReportingTo()!= null){
				row.createCell((short) 9).setCellValue(details.getEmp().getReportingTo().getEmployeeName());
				}else{
					System.out.println(details.getReportingTo());
					row.createCell((short) 9).setCellValue(details.getReportingTo());
				}
					
				
				
				
				
				rowNo++;
			}

			String file = "EmployeeResourceReport.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}

	
	@RequestMapping(value = "/timesheetReport", method = RequestMethod.GET)
	public String timesheetReport(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "timesheetReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}

	}

	@RequestMapping(value = "/getTimeSheetDetails", method = RequestMethod.POST)
	public String getTimeSheetDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

			Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
			startDate = new java.sql.Date(startDate.getTime());

			Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
			endDate = new java.sql.Date(endDate.getTime());

			List<Timesheet> timeSheetDtls = resourceDao.getTimeSheet(startDate, endDate);
			List<Timesheet> weeklyList = new ArrayList<>();
			Timesheet sheet = null;
			int estUtil = 0, actulUti = 0;
			for (Timesheet timesheet : timeSheetDtls) {
				sheet = new Timesheet();
				estUtil = (timesheet.getEstimatedEfforts() * 100) / timesheet.getNumberEfforts();
				actulUti = (timesheet.getTimesheetEfforts() * 100) / timesheet.getNumberEfforts();
				sheet = timesheet;
				sheet.setEstimatedUtilization(estUtil);
				sheet.setActualUtilization(actulUti);
				weeklyList.add(sheet);
			}

			Timesheet sheet1 = null;
			empActiveList = empDao.getActiveEmployeeList();
			for (Employee empList : empActiveList) {
				boolean found = true;
				for (Timesheet sheetList : weeklyList) {
					if (empList.getEmpId() == sheetList.getEmpId().getEmpId())
						found = false;
				}
				if (found) {
					if (weeklyList.size() != 0) {
						sheet1 = new Timesheet();
						sheet1.setEmpId(empList);
						sheet1.setFromDateStr(weeklyList.get(0).getFromDateStr());
						sheet1.setToDateStr(weeklyList.get(0).getToDateStr());
						sheet1.setActualUtilization(0);
						sheet1.setEstimatedEfforts(0);
						sheet1.setEstimatedUtilization(0);
						sheet1.setTimesheetEfforts(0);
						sheet1.setBillableEfforts(0);
						sheet1.setSumHours(0);
						sheet1.setNumberEfforts(0);
						weeklyList.add(sheet1);
					}
				}
			}
			model.put("weeklyList", weeklyList);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			return "timeSheetDetailsWeeklyReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/managerTimesheetReport", method = RequestMethod.GET)
	public String managerTimesheetReport(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "managerTimesheetReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}
		
	}

	@RequestMapping(value = "/getManagerTimeSheetDetails", method = RequestMethod.POST)
	public String getManagerTimeSheetDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			int managerId = sessionobj.getEmpObj().getEmpId();
			DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

			Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
			startDate = new java.sql.Date(startDate.getTime());

			Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
			endDate = new java.sql.Date(endDate.getTime());

			List<Timesheet> timeSheetDtls = resourceDao.getManagerTimeSheet(startDate, endDate, managerId);
			empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());
			
			
			List<Timesheet> weeklyList = new ArrayList<>();
			Timesheet sheet = null;
			int estUtil = 0, actulUti = 0;
			for (Timesheet timesheet : timeSheetDtls) {
				sheet = new Timesheet();
				actulUti = (timesheet.getSumHours()  * 100) /( timesheet.getNumberEfforts() -timesheet.getLeaveHours() );
				sheet = timesheet;
				sheet.setNumberEfforts(timesheet.getNumberEfforts() -timesheet.getLeaveHours());
				sheet.setActualUtilization(actulUti);
				weeklyList.add(sheet);
			}

			Timesheet sheet1 = null;

			for (Employee empList : empActiveList) {
				boolean found = true;
				for (Timesheet sheetList : weeklyList) {
					
					if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
						found = false;
				}
				
				if (found) {
					if (weeklyList.size() != 0) {
						sheet1 = new Timesheet();
						sheet1.setEmpId(empList);
						sheet1.setFromDateStr(weeklyList.get(0).getFromDateStr());
						sheet1.setToDateStr(weeklyList.get(0).getToDateStr());
						/*
						 * sheet1.setActualUtilization(0); sheet1.setEstimatedEfforts(0);
						 * sheet1.setEstimatedUtilization(0);
						 */
						sheet1.setTimesheetEfforts(0);
						sheet1.setBillableEfforts(0);
						sheet1.setSumHours(0);
						sheet1.setNumberEfforts(40);
						weeklyList.add(sheet1);
					}
				}
			}
			model.put("weeklyList", weeklyList);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			return "timeSheetDetailsWeeklyReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}

	/*@RequestMapping(value = "/exportGetTimeSheetWeeklyDetails/{startdate}/{enddate}", method = RequestMethod.GET)
	public String getTimeSheetWeeklyReport(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startdate, @PathVariable String enddate) throws ParseException {
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = (Date) formatter1.parse(startdate);
		Date endDate = (Date) formatter1.parse(enddate);
		int managerId = sessionobj.getEmpObj().getEmpId();

		startDate = new java.sql.Date(startDate.getTime());
		endDate = new java.sql.Date(endDate.getTime());
		System.out.println("startDate==" + startDate + ",endDate=" + endDate);

		List<Timesheet> timeSheetDtls = resourceDao.getManagerTimeSheet(startDate, endDate, managerId);
		empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());

		List<Timesheet> weeklyList = new ArrayList<>();
		Timesheet sheet = null;
		int estUtil = 0, actulUti = 0;
		for (Timesheet timesheet : timeSheetDtls) {
			sheet = new Timesheet();
			actulUti = (timesheet.getTimesheetEfforts() * 100) / ( timesheet.getNumberEfforts() -timesheet.getLeaveHours() );
			sheet = timesheet;
			sheet.setNumberEfforts(timesheet.getNumberEfforts() -timesheet.getLeaveHours());
			sheet.setActualUtilization(actulUti);
			weeklyList.add(sheet);

		}

		Timesheet sheet1 = null;

		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : weeklyList) {
				if (empList.getEmpId() == sheetList.getEmpId().getEmpId())
					found = false;
			}
			if (found) {
				if (weeklyList.size() != 0) {
					sheet1 = new Timesheet();
					sheet1.setEmpId(empList);
					sheet1.setFromDateStr(weeklyList.get(0).getFromDateStr());
					sheet1.setToDateStr(weeklyList.get(0).getToDateStr());
					
					 * sheet1.setActualUtilization(0); sheet1.setEstimatedEfforts(0);
					 * sheet1.setEstimatedUtilization(0);
					 
					sheet1.setTimesheetEfforts(0);
					sheet1.setNumberEfforts(40);
					weeklyList.add(sheet1);
				}
			}
		}

		try {
			doEmpTotalBenchPeriodWeeklyExport(weeklyList, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("weeklyList", weeklyList);

		return "timeSheetDetailsWeeklyReport";
	}

	public void doEmpTotalBenchPeriodWeeklyExport(List<Timesheet> weeklyList, HttpServletResponse response)
			throws Exception {
		System.out.println("Came into doEmpTotalBenchPeriodWeeklyExport==" + weeklyList.size());
		if (weeklyList != null && !weeklyList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();

			HSSFSheet sheet = workBook.createSheet("All Employee Weekly Report");

			for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 8);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			// style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 8);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("EMPLOYEE ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("EMPLOYEE NAME");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("FROM DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("TO DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("TOTAL EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("ACTUAL EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("AUTUAL UTILIZATION");
			ch3.setCellStyle(style2);

			sheet.setFitToPage(true);

			short rowNo = 4;
			for (Timesheet details : weeklyList) {

				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				row.createCell((short) 2).setCellValue(details.getFromDateStr());
				row.createCell((short) 3).setCellValue(details.getToDateStr());
				row.createCell((short) 4).setCellValue(details.getNumberEfforts());
				row.createCell((short) 5).setCellValue(details.getTimesheetEfforts());
				row.createCell((short) 6).setCellValue(details.getActualUtilization());
				rowNo++;
			}

			String file = "EmpsTotalWeeklyReport.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}*/
	
	
	
	@RequestMapping(value = "/exportGetTimeSheetWeeklyDetails/{startdate}/{enddate}", method = RequestMethod.GET)
	public String getTimeSheetWeeklyReport(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startdate, @PathVariable String enddate) throws ParseException {
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = (Date) formatter1.parse(startdate);
		Date endDate = (Date) formatter1.parse(enddate);
		int managerId = sessionobj.getEmpObj().getEmpId();
				

		startDate = new java.sql.Date(startDate.getTime());
		endDate = new java.sql.Date(endDate.getTime());
		
		
		

		List<Timesheet> timeSheetDtls = resourceDao.getManagerTimeSheet(startDate, endDate, managerId);
		empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());

		List<Timesheet> weeklyList = new ArrayList<>();
		Timesheet sheet = null;
		int estUtil = 0, actulUti = 0;
		for (Timesheet timesheet : timeSheetDtls) {
			sheet = new Timesheet();
			actulUti = (timesheet.getSumHours()  * 100) /( timesheet.getNumberEfforts() -timesheet.getLeaveHours() );
			sheet = timesheet;
			sheet.setNumberEfforts(timesheet.getNumberEfforts() -timesheet.getLeaveHours());
			sheet.setActualUtilization(actulUti);
			weeklyList.add(sheet);
		}

		Timesheet sheet1 = null;

		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : weeklyList) {
				
				if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
					found = false;
			}
			
			if (found) {
				if (weeklyList.size() != 0) {
					sheet1 = new Timesheet();
					sheet1.setEmpId(empList);
					sheet1.setFromDateStr(weeklyList.get(0).getFromDateStr());
					sheet1.setToDateStr(weeklyList.get(0).getToDateStr());
					/*
					 * sheet1.setActualUtilization(0); sheet1.setEstimatedEfforts(0);
					 * sheet1.setEstimatedUtilization(0);
					 */
					sheet1.setTimesheetEfforts(0);
					sheet1.setBillableEfforts(0);
					sheet1.setSumHours(0);
					sheet1.setNumberEfforts(40);
					weeklyList.add(sheet1);
				}
			}
		}

		try {
			doEmpManagerWeeklyExport(weeklyList, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("weeklyList", weeklyList);

		return "timeSheetDetailsWeeklyReport";
	}

	public void doEmpManagerWeeklyExport(List<Timesheet> weeklyList, HttpServletResponse response)
			throws Exception {
		if (weeklyList != null && !weeklyList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();

			HSSFSheet sheet = workBook.createSheet("All Employee Weekly Report");

			for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 8);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			// style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 8);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("EMPLOYEE ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("EMPLOYEE NAME");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("FROM DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("TO DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("TOTAL EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("NON-BILLABLE EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("BILLABLE EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 7);
			ch3.setCellValue("EMPLOYEE LOGGED EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 8);
			ch3.setCellValue("AUTUAL UTILIZATION");
			ch3.setCellStyle(style2);

			sheet.setFitToPage(true);

			short rowNo = 4;
			for (Timesheet details : weeklyList) {

				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				row.createCell((short) 2).setCellValue(details.getFromDateStr());
				row.createCell((short) 3).setCellValue(details.getToDateStr());
				row.createCell((short) 4).setCellValue(details.getNumberEfforts());
				row.createCell((short) 5).setCellValue(details.getTimesheetEfforts());
				row.createCell((short) 6).setCellValue(details.getBillableEfforts());
				row.createCell((short) 7).setCellValue(details.getSumHours());
				row.createCell((short) 8).setCellValue(details.getActualUtilization());
				rowNo++;
			}

			String file = "EmpsTotalWeeklyReport.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}

	@RequestMapping(value = "/monthlyReport", method = RequestMethod.GET)
	public String timesheetMonthlyReport(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "timesheetMonthlyReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}

	}

	@RequestMapping(value = "/managerMonthlyReport", method = RequestMethod.GET)
	public String managerMonthlyReport(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "managerTimesheetMonthlyReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}

	}
	
	

	/*@RequestMapping(value = "/exportTimeSheetMonthlyDetails/{startDate}", method = RequestMethod.GET)
	public String ExportTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startDate) throws ParseException {

		int managerId = sessionobj.getEmpObj().getEmpId();
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		Date sDate = (Date) formatter.parse(startDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(sDate);
		String formatedDate = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.YEAR);
		System.out.println("formatedDate : " + formatedDate);

		String date[] = formatedDate.split("-");
		int month = Integer.parseInt(date[0]);
		int year = Integer.parseInt(date[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		Date actualDate = calendar.getTime();
		List<Timesheet> timeSheetDtls = resourceDao.getManagerMonthlyTimeSheet(month, year, managerId);
		int empId = 0;
		boolean empFlag = false;
		Date fromDate = null;
		Date toDate = null;

		int totalNoEfts = 0, totalActEfts = 0;

		int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int totalWorkingDays = 0, totalEfforts = 0;
		int day1 = actualDate.getDay();
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
		int day2 = calendar.getTime().getDay();
		if (day2 == 6 || day2 == 0) {
			day2 = 5;
		}

		if (day1 == 6 || day1 == 0) {
			day1 = 1;
		}

		int estimatedEfforts = 0, actualEstEfrts = 0;
		int utilizedEfforts = 0, actualUtiEfts = 0;
		int estUtil = 0, actulUtil = 0;
		int x = 0, x1 = 0, x2 = 0;
		int y = 0, y1 = 0, y2 = 0;

		int cmp = 0;
		int i = 0;
		Timesheet sheet = null;
		List<Timesheet> monthlyList = new ArrayList<>();
		for (Timesheet timesheet : timeSheetDtls) {
			i++;

			empId = timesheet.getEmpId().getEmpId();
			fromDate = timesheet.getFromDate();
			toDate = timesheet.getToDate();
			actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
			actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();

			if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				empFlag = true;
			}
			if (i == timeSheetDtls.size()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				empFlag = true;
			}
			if (empFlag) {
				if (i == 1) {
					x = timeSheetDtls.get(0).getNumberEfforts();
					x1 = timeSheetDtls.get(0).getEstimatedEfforts();
					x2 = timeSheetDtls.get(0).getTimesheetEfforts();

				} else {
					x = timeSheetDtls.get(i - 1).getNumberEfforts();
					x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
					x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();

				}
				sheet = new Timesheet();
				int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
				if (tDay1 == 0) {
					tDay1 = 1;
				}
				if (tDay2 == 6) {
					tDay2 = 5;
				}

				totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
				totalEfforts = totalWorkingDays * 8;
				estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y / 8)) * (5 - day2);
				utilizedEfforts = actualUtiEfts - (x2 / (x / 8)) * (day1 - 1) - (y2 / (y / 8)) * (5 - day2);
				estUtil = (estimatedEfforts * 100) / totalEfforts;
				actulUtil = (utilizedEfforts * 100) / totalEfforts;
				sheet.setEmpId(timesheet.getEmpId());
				sheet.setFromDate(actualDate);
				sheet.setNumberEfforts(totalEfforts);
				sheet.setEstimatedEfforts(estimatedEfforts);
				sheet.setTimesheetEfforts(utilizedEfforts);
				sheet.setEstimatedUtilization(estUtil);
				sheet.setActualUtilization(actulUtil);
				monthlyList.add(sheet);
				
				 * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
				 * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
				 * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
				 
				actualEstEfrts = 0;
				actualUtiEfts = 0;
				empFlag = false;
			}
		}
		Timesheet sheet1 = null;
		empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());
		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : monthlyList) {
				if (empList.getEmpId() == sheetList.getEmpId().getEmpId())
					found = false;
			}
			if (found) {
				sheet1 = new Timesheet();
				sheet1.setEmpId(empList);
				sheet1.setFromDate(actualDate);
				sheet1.setActualUtilization(0);
				sheet1.setEstimatedEfforts(0);
				sheet1.setEstimatedUtilization(0);
				sheet1.setTimesheetEfforts(0);
				sheet1.setNumberEfforts(0);
				monthlyList.add(sheet1);
			}
		}
		try {
			doExportMonthlyReport(monthlyList, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("monthlySheet", monthlyList);
		return "timeSheetDetailsMonthlyReport";
	}

	public void doExportMonthlyReport(List<Timesheet> monthlyList, HttpServletResponse response) throws Exception {
		if (monthlyList != null && !monthlyList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();
			HSSFSheet sheet = workBook.createSheet("Technical Team");

			for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
				sheet.autoSizeColumn((short) (columnPosition));
			}

			sheet.setHorizontallyCenter(true);
			sheet.setFitToPage(true);
			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 15);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 13);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// HSSFCell ch3 =headingRow.createCell(0);

			// HSSFRow headingRow = sheet.createRow(3);
			// HSSFCell ch3 = headingRow.createCell(9);
			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("Employee ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("Employee Name");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("Month");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("Total Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("Actual Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("Actual Utilization");
			ch3.setCellStyle(style2);
			short rowNo = 4;

			for (Timesheet details : monthlyList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellStyle(style);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellStyle(style);
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				row.createCell((short) 2).setCellStyle(style);
				row.createCell((short) 2).setCellValue(details.getFromDate().getMonth());
				row.createCell((short) 3).setCellStyle(style);
				row.createCell((short) 3).setCellValue(details.getNumberEfforts());

				row.createCell((short) 4).setCellStyle(style);
				row.createCell((short) 4).setCellValue(details.getTimesheetEfforts());

				row.createCell((short) 5).setCellStyle(style);
				row.createCell((short) 5).setCellValue(details.getActualUtilization());
				rowNo++;
			}

			String file = "EmployeeMonthlyReport.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}
*/
	
	@RequestMapping(value = "/exportTimeSheetMonthlyDetails/{startDate}", method = RequestMethod.GET)
	public String ExportTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startDate) throws ParseException {
		int managerId = sessionobj.getEmpObj().getEmpId();
		DateFormat formatter1 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				Date sDate = (Date) formatter1.parse(startDate);
		//DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
		//String startDate = req.getParameter("month");
		//String date[] = startDate.split("/");

				Calendar cal = Calendar.getInstance();
				cal.setTime(sDate);
				String formatedDate = (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.YEAR);
				String date[] = formatedDate.split("-");
				
		int month = Integer.parseInt(date[0]);
		int year = Integer.parseInt(date[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month );
		calendar.set(Calendar.YEAR, year);
		Date actualDate = calendar.getTime();
		List<Timesheet> timeSheetDtls = resourceDao.getManagerMonthlyTimeSheet(month, year, managerId);
		
		int empId = 0;
		boolean empFlag = false;
		Date fromDate = null;
		Date toDate = null;

		int totalNoEfts = 0, totalActEfts = 0;

		int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int totalWorkingDays = 0, totalEfforts = 0;
		int day1 = actualDate.getDay();
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
		int day2 = calendar.getTime().getDay();
		if (day2 == 6 || day2 == 0) {
			day2 = 5;
		}

		if (day1 == 6 || day1 == 0) {
			day1 = 1;
		}

		int estimatedEfforts = 0, actualEstEfrts = 0;
		int utilizedEfforts = 0, actualUtiEfts = 0;
		int billableEts = 0 , sumEfts = 0;
		int estUtil = 0, actulUtil = 0;
		int x = 0, x1 = 0, x2 = 0 , x3 = 0,x4 = 0;
		int y = 0, y1 = 0, y2 = 0 , y3 = 0, y4 = 0;

		int cmp = 0;
		int i = 0;
		Timesheet sheet = null;
		List<Timesheet> monthlyList = new ArrayList<>();
		for (Timesheet timesheet : timeSheetDtls) {
			i++;

			if(timesheet.getEmpId() != null){
			empId = timesheet.getEmpId().getEmpId();
			fromDate = timesheet.getFromDate();
			toDate = timesheet.getToDate();
			// actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
			actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();
			billableEts = timesheet.getBillableEfforts();
			sumEfts = timesheet.getSumHours();

			if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				// y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				y4 = timeSheetDtls.get(i - 1).getSumHours();
				empFlag = true;
			}
			if (i == timeSheetDtls.size()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				// y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				y4 = timeSheetDtls.get(i - 1).getSumHours();
				empFlag = true;
			}
			if (empFlag) {
				if (i == 1) {
					x = timeSheetDtls.get(0).getNumberEfforts();
					// x1 = timeSheetDtls.get(0).getEstimatedEfforts();
					x2 = timeSheetDtls.get(0).getTimesheetEfforts();
					x3 = timeSheetDtls.get(0).getBillableEfforts();
					x4 = timeSheetDtls.get(0).getSumHours();

				} else {
					x = timeSheetDtls.get(i - 1).getNumberEfforts();
					// x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
					x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
					x3 = timeSheetDtls.get(i - 1).getBillableEfforts();

				}
				sheet = new Timesheet();
				int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
				if (tDay1 == 0) {
					tDay1 = 1;
				}
				if (tDay2 == 6) {
					tDay2 = 5;
				}

				totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
				totalEfforts = totalWorkingDays * 8;
				// estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
				// 8)) * (5 - day2);
				utilizedEfforts = sumEfts;// - (x2 / (x / 8)) * (day1 - 1) - (y2 / (y / 8)) * (5 - day2);
				// estUtil = (estimatedEfforts * 100) / totalEfforts;
				actulUtil = (utilizedEfforts * 100) / totalEfforts;
				sheet.setEmpId(timesheet.getEmpId());
				sheet.setFromDate(actualDate);
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
				
				sheet.setFromDateStr(formatter.format(actualDate));
				sheet.setNumberEfforts(totalEfforts);
				// sheet.setEstimatedEfforts(estimatedEfforts);
				sheet.setTimesheetEfforts(actualUtiEfts);
				sheet.setBillableEfforts(billableEts);
				sheet.setSumHours(utilizedEfforts);
				// sheet.setEstimatedUtilization(estUtil);
				sheet.setActualUtilization(actulUtil);
				monthlyList.add(sheet);
				/*
				 * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
				 * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
				 * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
				 */
				actualEstEfrts = 0;
				actualUtiEfts = 0;
				empFlag = false;
			}
		}
		}
		
		Timesheet sheet1 = null;
		//empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpUsersObj().getEmpId().getEmpId())
		empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());
		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : monthlyList) {
				if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
					found = false;
				
			}
			
			if (found) {
				sheet1 = new Timesheet();
				sheet1.setEmpId(empList);
				sheet1.setFromDate(actualDate);
				sheet1.setActualUtilization(0);
				sheet1.setEstimatedEfforts(0);
				sheet1.setEstimatedUtilization(0);
				sheet1.setTimesheetEfforts(0);
				sheet1.setBillableEfforts(0);
				sheet1.setNumberEfforts(0);
				monthlyList.add(sheet1);
			}
		}
	
	try {
			doExportMonthlyReport(monthlyList, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("monthlySheet", monthlyList);
		return "timeSheetDetailsMonthlyReport";
	}

	public void doExportMonthlyReport(List<Timesheet> monthlyList, HttpServletResponse response) throws Exception {
		if (monthlyList != null && !monthlyList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();
			HSSFSheet sheet = workBook.createSheet("Technical Team");

			for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
				sheet.autoSizeColumn((short) (columnPosition));
			}

			sheet.setHorizontallyCenter(true);
			sheet.setFitToPage(true);
			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 15);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 13);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// HSSFCell ch3 =headingRow.createCell(0);

			// HSSFRow headingRow = sheet.createRow(3);
			// HSSFCell ch3 = headingRow.createCell(9);
			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("Employee ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("Employee Name");
			ch3.setCellStyle(style2);
			SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("Month");
			ch3.setCellStyle(style2);
			/*ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("Total Efforts");
			ch3.setCellStyle(style2);*/
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("Non-Billable Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("Billable Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("Employee Logged Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("Actual Utilization");
			ch3.setCellStyle(style2);
			short rowNo = 4;

			for (Timesheet details : monthlyList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellStyle(style);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellStyle(style);
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				
				row.createCell((short) 2).setCellStyle(style);
				//SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
				row.createCell((short) 2).setCellValue(details.getFromDate().getMonth());
				/*row.createCell((short) 3).setCellStyle(style);
				row.createCell((short) 3).setCellValue(details.getNumberEfforts());*/

				row.createCell((short) 3).setCellStyle(style);
				row.createCell((short) 3).setCellValue(details.getTimesheetEfforts());
				
				row.createCell((short) 4).setCellStyle(style);
				row.createCell((short) 4).setCellValue(details.getBillableEfforts());
				
				row.createCell((short) 5).setCellStyle(style);
				row.createCell((short) 5).setCellValue(details.getSumHours());

				row.createCell((short) 6).setCellStyle(style);
				row.createCell((short) 6).setCellValue(details.getActualUtilization());
				rowNo++;
			}

			String file = "EmployeeMonthlyReport.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}
	@RequestMapping(value = "/getTimeSheetMonthlyDetails", method = RequestMethod.POST)
	public String getTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req) throws ParseException {

		DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
		String startDate = req.getParameter("month");
		String date[] = startDate.split("/");
		int month = Integer.parseInt(date[0]);
		int year = Integer.parseInt(date[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		Date actualDate = calendar.getTime();
		List<Timesheet> timeSheetDtls = resourceDao.getMonthlyTimeSheet(month, year);
		int empId = 0;
		boolean empFlag = false;
		Date fromDate = null;
		Date toDate = null;

		int totalNoEfts = 0, totalActEfts = 0;

		int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int totalWorkingDays = 0, totalEfforts = 0;
		int day1 = actualDate.getDay();
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
		int day2 = calendar.getTime().getDay();
		if (day2 == 6 || day2 == 0) {
			day2 = 5;
		}

		if (day1 == 6 || day1 == 0) {
			day1 = 1;
		}

		int estimatedEfforts = 0, actualEstEfrts = 0;
		int billableEts = 0 , sumEfts = 0;
		int utilizedEfforts = 0, actualUtiEfts = 0;
		int estUtil = 0, actulUtil = 0;
		int x = 0, x1 = 0, x2 = 0 , x3 = 0 , x4 = 0;
		int y = 0, y1 = 0, y2 = 0 , y3 = 0 , y4 = 0;

		int cmp = 0;
		int i = 0;
		Timesheet sheet = null;
		List<Timesheet> monthlyList = new ArrayList<>();
		for (Timesheet timesheet : timeSheetDtls) {
			i++;

			empId = timesheet.getEmpId().getEmpId();
			fromDate = timesheet.getFromDate();
			toDate = timesheet.getToDate();
			actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
			actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();

			if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				y3 = timeSheetDtls.get(i - 1).getSumHours();
				empFlag = true;
			}
			if (i == timeSheetDtls.size()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				empFlag = true;
			}
			if (empFlag) {
				if (i == 1) {
					x = timeSheetDtls.get(0).getNumberEfforts();
					x1 = timeSheetDtls.get(0).getEstimatedEfforts();
					x2 = timeSheetDtls.get(0).getTimesheetEfforts();
					x3 = timeSheetDtls.get(0).getBillableEfforts();
					

				} else {
					x = timeSheetDtls.get(i - 1).getNumberEfforts();
					x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
					x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
					x3 = timeSheetDtls.get(i - 1).getBillableEfforts();

				}
				sheet = new Timesheet();
				int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
				if (tDay1 == 0) {
					tDay1 = 1;
				}
				if (tDay2 == 6) {
					tDay2 = 5;
				}

				totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
				totalEfforts = totalWorkingDays * 8;
				estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y / 8)) * (5 - day2);
				utilizedEfforts = actualUtiEfts - (x2 / (x / 8)) * (day1 - 1) - (y2 / (y / 8)) * (5 - day2);
				estUtil = (estimatedEfforts * 100) / totalEfforts;
				actulUtil = (utilizedEfforts * 100) / totalEfforts;
				sheet.setEmpId(timesheet.getEmpId());
				sheet.setFromDate(actualDate);
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
				sheet.setFromDateStr(formatter.format(actualDate));
				sheet.setNumberEfforts(totalEfforts);
				sheet.setEstimatedEfforts(estimatedEfforts);
				sheet.setTimesheetEfforts(utilizedEfforts);
				sheet.setEstimatedUtilization(estUtil);
				sheet.setActualUtilization(actulUtil);
				monthlyList.add(sheet);
				/*
				 * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
				 * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
				 * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
				 */
				actualEstEfrts = 0;
				actualUtiEfts = 0;
				empFlag = false;
			}
		}
		Timesheet sheet1 = null;
		
		/*empActiveList = empDao.getActiveEmployeeList();
		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : monthlyList) {
				
				if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
					found = false;
			}*/
		
	empActiveList = empDao.getActiveEmployeeList();
		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : monthlyList) {
				if (empList.getEmpId() == sheetList.getEmpId().getEmpId())
					found = false;
			}
			if (found) {
				sheet1 = new Timesheet();
				sheet1.setEmpId(empList);
				sheet1.setFromDate(actualDate);
				sheet1.setActualUtilization(0);
				sheet1.setEstimatedEfforts(0);
				sheet1.setEstimatedUtilization(0);
				sheet1.setTimesheetEfforts(0);
				sheet1.setBillableEfforts(0);
				sheet1.setSumHours(0);
				sheet1.setNumberEfforts(0);
				//System.out.println(timeSheetDtls.get(0));
				//sheet1.setNumberEfforts(timeSheetDtls.get(0).getNumberEfforts());
				monthlyList.add(sheet1);
			}
		}
		model.put("monthlySheet", monthlyList);
		model.put("startDate", actualDate);
		return "timeSheetDetailsMonthlyReport";
	}

	/*@RequestMapping(value = "/getManagerTimeSheetMonthlyDetails", method = RequestMethod.POST)
	public String getManagerTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req) throws ParseException {

		int managerId = sessionobj.getEmpObj().getEmpId();
		DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
		String startDate = req.getParameter("month");
		String date[] = startDate.split("/");
		System.out.println("getTimeSheetMonthly-------");
		int month = Integer.parseInt(date[0]);
		int year = Integer.parseInt(date[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		Date actualDate = calendar.getTime();
		List<Timesheet> timeSheetDtls = resourceDao.getManagerMonthlyTimeSheet(month, year, managerId);
		int empId = 0;
		boolean empFlag = false;
		Date fromDate = null;
		Date toDate = null;

		int totalNoEfts = 0, totalActEfts = 0;

		int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int totalWorkingDays = 0, totalEfforts = 0;
		int day1 = actualDate.getDay();
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
		int day2 = calendar.getTime().getDay();
		if (day2 == 6 || day2 == 0) {
			day2 = 5;
		}

		if (day1 == 6 || day1 == 0) {
			day1 = 1;
		}

		int estimatedEfforts = 0, actualEstEfrts = 0;
		int utilizedEfforts = 0, actualUtiEfts = 0;
		int billableEts = 0 , sumEfts = 0;
		int estUtil = 0, actulUtil = 0;
		int x = 0, x1 = 0, x2 = 0 , x3 = 0,x4 = 0;
		int y = 0, y1 = 0, y2 = 0 , y3 = 0, y4 = 0;

		int cmp = 0;
		int i = 0;
		Timesheet sheet = null;
		List<Timesheet> monthlyList = new ArrayList<>();
		for (Timesheet timesheet : timeSheetDtls) {
			i++;

			empId = timesheet.getEmpId().getEmpId();
			fromDate = timesheet.getFromDate();
			toDate = timesheet.getToDate();
			// actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
			actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();
			
			billableEts = billableEts + timesheet.getBillableEfforts();

			if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				// y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				System.out.println("non billable hours ====" + y2);
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				System.out.println("billable ====" + y3);
				y4 = timeSheetDtls.get(i - 1).getSumHours();
				System.out.println("sum  ====" + y4);
				empFlag = true;
			}
			if (i == timeSheetDtls.size()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				// y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				y4 = timeSheetDtls.get(i - 1).getSumHours();
				System.out.println("sum hours ====" + y4);
				empFlag = true;
			}
			if (empFlag) {
				if (i == 1) {
					x = timeSheetDtls.get(0).getNumberEfforts();
					// x1 = timeSheetDtls.get(0).getEstimatedEfforts();
					x2 = timeSheetDtls.get(0).getTimesheetEfforts();
					x3 = timeSheetDtls.get(0).getBillableEfforts();
					System.out.println("billable ====" + x3);
					x4 = timeSheetDtls.get(0).getSumHours();

				} else {
					x = timeSheetDtls.get(i - 1).getNumberEfforts();
					// x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
					x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
					x3 = timeSheetDtls.get(i - 1).getBillableEfforts();

				}
				sheet = new Timesheet();
				int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
				if (tDay1 == 0) {
					tDay1 = 1;
				}
				if (tDay2 == 6) {
					tDay2 = 5;
				}

				totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
				totalEfforts = totalWorkingDays * 8;
				// estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
				// 8)) * (5 - day2);
				utilizedEfforts = actualUtiEfts + billableEts;// - (x2 / (x / 8)) * (day1 - 1) - (y2 / (y / 8)) * (5 - day2);
				System.out.println("actualUtiEfts==="+actualUtiEfts);
				System.out.println("billableEts==="+billableEts);
				System.out.println("utilizedEfforts==="+utilizedEfforts);
				// estUtil = (estimatedEfforts * 100) / totalEfforts;
				actulUtil = (utilizedEfforts * 100) / totalEfforts;
				sheet.setEmpId(timesheet.getEmpId());
				sheet.setFromDate(actualDate);
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
				sheet.setFromDateStr(formatter.format(actualDate));
				sheet.setNumberEfforts(totalEfforts);
				// sheet.setEstimatedEfforts(estimatedEfforts);
				sheet.setTimesheetEfforts(utilizedEfforts);
				// sheet.setEstimatedUtilization(estUtil);
				sheet.setActualUtilization(actulUtil);
				monthlyList.add(sheet);
				
				 * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
				 * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
				 * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
				 
				actualEstEfrts = 0;
				actualUtiEfts = 0;
				empFlag = false;
			}
		}
		
		Timesheet sheet1 = null;
		empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());
		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : monthlyList) {
				if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
					found = false;
				
			}
			
			if (found) {
				sheet1 = new Timesheet();
				sheet1.setEmpId(empList);
				sheet1.setFromDate(actualDate);
				sheet1.setActualUtilization(0);
				sheet1.setEstimatedEfforts(0);
				sheet1.setEstimatedUtilization(0);
				sheet1.setTimesheetEfforts(0);
				sheet1.setNumberEfforts(0);
				monthlyList.add(sheet1);
			}
		}
		model.put("monthlySheet", monthlyList);
		model.put("startDate", actualDate);
		return "timeSheetDetailsMonthlyReport";
	}*/
	
	@RequestMapping(value = "/getManagerTimeSheetMonthlyDetails", method = RequestMethod.POST)
	 public String getManagerTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req) throws ParseException {

		int managerId = sessionobj.getEmpObj().getEmpId();
	  DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
	  String startDate = req.getParameter("month");
	  String date[] = startDate.split("/");
	  
	  int month = Integer.parseInt(date[0]);
	  int year = Integer.parseInt(date[1]);
	  Calendar calendar = Calendar.getInstance();
	  calendar.clear();
	  calendar.set(Calendar.MONTH, month - 1);
	  calendar.set(Calendar.YEAR, year);
	  Date actualDate = calendar.getTime();
	  List<Timesheet> timeSheetDtls = resourceDao.getManagerMonthlyTimeSheet(month, year, managerId);
	  
	  int empId = 0;
	  boolean empFlag = false;
	  Date fromDate = null;
	  Date toDate = null;

	  int totalNoEfts = 0, totalActEfts = 0;

	  int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	  int totalWorkingDays = 0, totalEfforts = 0;
	  int day1 = actualDate.getDay();
	  calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
	  int day2 = calendar.getTime().getDay();
	  if (day2 == 6 || day2 == 0) {
	   day2 = 5;
	  }

	  if (day1 == 6 || day1 == 0) {
	   day1 = 1;
	  }

	  int estimatedEfforts = 0, actualEstEfrts = 0;
	  int utilizedEfforts = 0, actualUtiEfts = 0;
	  int billableEts = 0 , sumEfts = 0;
	  int estUtil = 0, actulUtil = 0;
	  int x = 0, x1 = 0, x2 = 0 , x3 = 0,x4 = 0;
	  int y = 0, y1 = 0, y2 = 0 , y3 = 0, y4 = 0;

	  int cmp = 0;
	  int i = 0;
	  Timesheet sheet = null;
	  List<Timesheet> monthlyList = new ArrayList<>();
	  for (Timesheet timesheet : timeSheetDtls) {
	   i++;

	   if(timesheet.getEmpId() != null){
	   empId = timesheet.getEmpId().getEmpId();
	   fromDate = timesheet.getFromDate();
	   toDate = timesheet.getToDate();
	   // actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
	   actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();
	   billableEts = timesheet.getBillableEfforts();
	   sumEfts = timesheet.getSumHours();

	   if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
	    y4 = timeSheetDtls.get(i - 1).getSumHours();
	    empFlag = true;
	   }
	   if (i == timeSheetDtls.size()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
	    y4 = timeSheetDtls.get(i - 1).getSumHours();
	    empFlag = true;
	   }
	   if (empFlag) {
	    if (i == 1) {
	     x = timeSheetDtls.get(0).getNumberEfforts();
	     // x1 = timeSheetDtls.get(0).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(0).getTimesheetEfforts();
	     x3 = timeSheetDtls.get(0).getBillableEfforts();
	     x4 = timeSheetDtls.get(0).getSumHours();

	    } else {
	     x = timeSheetDtls.get(i - 1).getNumberEfforts();
	     // x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	     x3 = timeSheetDtls.get(i - 1).getBillableEfforts();

	    }
	    sheet = new Timesheet();
	    int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
	    if (tDay1 == 0) {
	     tDay1 = 1;
	    }
	    if (tDay2 == 6) {
	     tDay2 = 5;
	    }

	    totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
	    totalEfforts = totalWorkingDays * 8;
	    // estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
	    // 8)) * (5 - day2);
	    utilizedEfforts = sumEfts;// - (x2 / (x / 8))  (day1 - 1) - (y2 / (y / 8))  (5 - day2);
	    // estUtil = (estimatedEfforts * 100) / totalEfforts;
	    actulUtil = (utilizedEfforts * 100) / totalEfforts;
	    sheet.setEmpId(timesheet.getEmpId());
	    sheet.setFromDate(actualDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
	    sheet.setFromDateStr(formatter.format(actualDate));
	    sheet.setNumberEfforts(totalEfforts);
	    // sheet.setEstimatedEfforts(estimatedEfforts);
	    sheet.setTimesheetEfforts(actualUtiEfts);
	    sheet.setBillableEfforts(billableEts);
	    sheet.setSumHours(utilizedEfforts);
	    // sheet.setEstimatedUtilization(estUtil);
	    sheet.setActualUtilization(actulUtil);
	    monthlyList.add(sheet);
	    /*
	     * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
	     * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
	     * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
	     */
	    actualEstEfrts = 0;
	    actualUtiEfts = 0;
	    empFlag = false;
	   }
	  }
	  }
	  
	  Timesheet sheet1 = null;
	 // empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpUsersObj().getEmpId().getEmpId());
	  empActiveList = empDao.getEmployeesUnderManagers(sessionobj.getEmpObj().getEmpId());
	  for (Employee empList : empActiveList) {
	   boolean found = true;
	   for (Timesheet sheetList : monthlyList) {
	    if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
	     found = false;
	    
	   }
	   
	   if (found) {
	    sheet1 = new Timesheet();
	    sheet1.setEmpId(empList);
	    sheet1.setFromDate(actualDate);
	    sheet1.setActualUtilization(0);
	    sheet1.setEstimatedEfforts(0);
	    sheet1.setEstimatedUtilization(0);
	    sheet1.setTimesheetEfforts(0);
	    sheet1.setBillableEfforts(0);
	    sheet1.setNumberEfforts(0);
	    monthlyList.add(sheet1);
	   }
	  }
	  model.put("monthlySheet", monthlyList);
	  model.put("startDate", actualDate);
	  return "timeSheetDetailsMonthlyReport";
	 }
	
	@RequestMapping(value = "/getSubmittedTimesheets", method = RequestMethod.POST)
	public String getSubmittedTimesheets(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

			Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
			startDate = new java.sql.Date(startDate.getTime());

			Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
			endDate = new java.sql.Date(endDate.getTime());

			List<Timesheet> timeSheetDtlsSubmitted = resourceDao.getManagerTimeSheetsSubmitted(startDate, endDate);
			
			model.put("timeSheetDtlsSubmitted", timeSheetDtlsSubmitted);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			return "timeSheetDetailsSubmittedReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}
	
	/*@RequestMapping(value = "/getPmoResourcesList", method = RequestMethod.GET)
	public String getPmoResourcesList(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			

			List<Timesheet> timeSheetDtlsSubmitted = resourceDao.getPmoManagerResourcesList();
			
			model.put("timeSheetDtlsSubmitted", timeSheetDtlsSubmitted);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			return "pmoResourcesList";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}
	*/
	@RequestMapping(value = "/exportGetSubmittedTimesheets/{startdate}/{enddate}", method = RequestMethod.GET)
	public String exportGetSubmittedTimesheets(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startdate, @PathVariable String enddate)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

			Date startDate = (Date) formatter1.parse(startdate);
			Date endDate = (Date) formatter1.parse(enddate);
			startDate = new java.sql.Date(startDate.getTime());
			endDate = new java.sql.Date(endDate.getTime());
			List<Timesheet> timeSheetDtlsSubmitted = resourceDao.getManagerTimeSheetsSubmitted(startDate, endDate);
			
			try {
				doExportGetSubmittedTimesheets(timeSheetDtlsSubmitted, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			model.put("timeSheetDtlsSubmitted", timeSheetDtlsSubmitted);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			return "timeSheetDetailsSubmittedReport"; 
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}
	
	public void doExportGetSubmittedTimesheets(List<Timesheet> timeSheetDtlsSubmitted, HttpServletResponse response) throws Exception {
		if (timeSheetDtlsSubmitted != null && !timeSheetDtlsSubmitted.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();
			HSSFSheet sheet = workBook.createSheet("Technical Team");

			for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
				sheet.autoSizeColumn((short) (columnPosition));
			}
			
			
			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);
			
			//Timesheet timeSheetDtlsSubmitted1 = null;
			sheet.setHorizontallyCenter(true);
			sheet.setFitToPage(true);
			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 15);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 13);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// HSSFCell ch3 =headingRow.createCell(0);

			// HSSFRow headingRow = sheet.createRow(3);
			// HSSFCell ch3 = headingRow.createCell(9);
			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("EmployeeId");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("EmployeeName");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("EmployeeMobilenumber");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("EmailId");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("Total Employess");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("Submitted Timesheets");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("Not Submitted Timesheets");
			
			sheet.setFitToPage(true);
			

			short rowNo = 4;

			for (Timesheet details : timeSheetDtlsSubmitted) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellStyle(style);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellStyle(style);
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				
				row.createCell((short) 2).setCellStyle(style);
				row.createCell((short) 2).setCellValue(details.getEmpId().getEmployeeMobilenumber());
				row.createCell((short) 3).setCellStyle(style);
				row.createCell((short) 3).setCellValue(details.getEmpId().getEmailId());
				row.createCell((short) 4).setCellStyle(style);
				row.createCell((short) 4).setCellValue(details.getTotalCount());
				row.createCell((short) 5).setCellStyle(style);
				row.createCell((short) 5).setCellValue(details.getSubmittedCount());
				if(details.getEmpId().getEmployeeName().equals("Raghavendra Maram"))
				row.createCell((short) 6).setCellStyle(style);
				row.createCell((short) 6).setCellValue(details.getBalanceCount());
				rowNo++;
			}

			String file = "PMOExport.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}
	
	
	@RequestMapping(value = "/getPmoResourcesList", method = RequestMethod.GET)
	public String getPmoResourcesList(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			

			List<Timesheet> timeSheetDtlsSubmitted = resourceDao.getPmoManagerResourcesList();
			
			model.put("timeSheetDtlsSubmitted", timeSheetDtlsSubmitted);
			/*model.put("startDate", startDate);
			model.put("endDate", endDate);*/
			return "pmoResourcesList";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/exportGetPmoResourcesList", method = RequestMethod.GET)
	public String exportGetPmoResourcesList(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			

			List<Timesheet> timeSheetDtlsSubmitted = resourceDao.getPmoManagerResourcesList();
			try {
				doExportGetPmoResourcesList(timeSheetDtlsSubmitted, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			model.put("timeSheetDtlsSubmitted", timeSheetDtlsSubmitted);
			/*model.put("startDate", startDate);
			model.put("endDate", endDate);*/
			return "pmoResourcesList";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}
	
	public void doExportGetPmoResourcesList(List<Timesheet> timeSheetDtlsSubmitted, HttpServletResponse response) throws Exception {
		if (timeSheetDtlsSubmitted != null && !timeSheetDtlsSubmitted.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();
			HSSFSheet sheet = workBook.createSheet("Technical Team");

			for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
				sheet.autoSizeColumn((short) (columnPosition));
			}
			
			
			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);
			
			//Timesheet timeSheetDtlsSubmitted1 = null;
			sheet.setHorizontallyCenter(true);
			sheet.setFitToPage(true);
			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 15);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 13);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// HSSFCell ch3 =headingRow.createCell(0);

			// HSSFRow headingRow = sheet.createRow(3);
			// HSSFCell ch3 = headingRow.createCell(9);
			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("EmployeeId");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("EmployeeName");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("EmployeeMobilenumber");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("EmailId");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("Total Employess");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("Engaged Resources");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("Bench Resources");
			
			sheet.setFitToPage(true);
			

			short rowNo = 4;

			for (Timesheet details : timeSheetDtlsSubmitted) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellStyle(style);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellStyle(style);
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				
				row.createCell((short) 2).setCellStyle(style);
				row.createCell((short) 2).setCellValue(details.getEmpId().getEmployeeMobilenumber());
				row.createCell((short) 3).setCellStyle(style);
				row.createCell((short) 3).setCellValue(details.getEmpId().getEmailId());
				row.createCell((short) 4).setCellStyle(style);
				row.createCell((short) 4).setCellValue(details.getTotalCount());
				row.createCell((short) 5).setCellStyle(style);
				row.createCell((short) 5).setCellValue(details.getResCount());
				
				row.createCell((short) 6).setCellStyle(style);
				row.createCell((short) 6).setCellValue(details.getBenchCount());
				rowNo++;
			}

			String file = "EmpResourceList.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}
	
	
/*	@RequestMapping(value = "/accountsTimeSheetReport", method = RequestMethod.GET)
	public String accountsTimeSheetReport(ModelMap model,@ModelAttribute TimesheetRequest timesheetReq) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> usersEmpList = usersDao.getListEmpUsers();
			model.put("usersEmpList", usersEmpList);
			model.put("TimesheetRequest", timesheetReq);
			
			return "accountsTimeSheetReport";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/getAccountsTimeSheetDetails", method = RequestMethod.POST)
	public String getAccountsTimeSheetDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
			Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
			startDate = new java.sql.Date(startDate.getTime());
			Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
			endDate = new java.sql.Date(endDate.getTime());
			List<Employee> usersEmpList = usersDao.getListEmpUsers();
			List<Timesheet> timeSheetDtls = resourceDao.getAccountsTimeSheet(startDate, endDate);

			model.put("weeklyList", timeSheetDtls);
			
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			model.put("usersEmpList", usersEmpList);
			
			
			return "accountsUtilizationReport";
			
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}
	
	
	
	@RequestMapping(value = "/getAccountsReportingManagerList", method = RequestMethod.POST)
	public String getReportingManagerList(ModelMap model, HttpServletRequest req, HttpServletResponse response,@RequestParam int id)
			
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
			Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
			startDate = new java.sql.Date(startDate.getTime());
			Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
			endDate = new java.sql.Date(endDate.getTime());
			//String reporting=(req.getParameter("id"));
			//int reportId=Integer.parseInt(reporting);
			List<Employee> empList = empDao.getActiveEmployeeList();
			List<Employee> usersEmpList = usersDao.getListEmpUsers();
			List<Timesheet> timeSheetDtls = resourceDao.getAccountsReportingManagerList(startDate, endDate,id);
			

TimesheetRequest sheetReq = new TimesheetRequest();

model.put("sheetReq", sheetReq);
			model.put("timeSheetDtls", timeSheetDtls);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			//model.put("TimesheetRequest", timesheetReq);
			model.put("usersEmpList", usersEmpList);
			
			return "accountsReportingManager";
			
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}
	*/
	
/*	@RequestMapping(value = "/getAccountsProjectsList", method = RequestMethod.POST)
	public String getAccountsProjectsList(ModelMap model, HttpServletRequest req, HttpServletResponse response)
			
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
			Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
			startDate = new java.sql.Date(startDate.getTime());
			Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
			endDate = new java.sql.Date(endDate.getTime());
			
			List<Employee> empList = empDao.getActiveEmployeeList();
			
			List<Timesheet> timeSheetDtls = resourceDao.getAccountsTimeSheet(startDate, endDate);
			List<Employee> usersEmpList = usersDao.getListEmpUsers();
			List<Projects> projectList = projectDao.getProjects();

			model.put("timeSheetDtls", timeSheetDtls);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			model.put("usersEmpList", usersEmpList);
			
			model.put("projectList", projectList);
			return "accountsProjectsUtilizationReport";
			
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			return "redirect:/login";
		}
	}*/
			

	
	/*@RequestMapping(value = "/getAllEmpTimeSheetMonthlyDetails", method = RequestMethod.POST)
	 public String getAllEmpTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req) throws ParseException {

	  DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
	  String startDate = req.getParameter("month");
	  System.out.println("getTimeSheetMonthly-------");
	  String date[] = startDate.split("/");
	  int month = Integer.parseInt(date[0]);
	  int year = Integer.parseInt(date[1]);
	  Calendar calendar = Calendar.getInstance();
	  calendar.clear();
	  calendar.set(Calendar.MONTH, month - 1);
	  calendar.set(Calendar.YEAR, year);
	  Date actualDate = calendar.getTime();
	  List<Timesheet> timeSheetDtls = resourceDao.getMonthlyTimeSheet(month, year);
	  int empId = 0;
	  boolean empFlag = false;
	  Date fromDate = null;
	  Date toDate = null;

	  int totalNoEfts = 0, totalActEfts = 0;

	  int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	  int totalWorkingDays = 0, totalEfforts = 0;
	  int day1 = actualDate.getDay();
	  calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
	  int day2 = calendar.getTime().getDay();
	  if (day2 == 6 || day2 == 0) {
	   day2 = 5;
	  }

	  if (day1 == 6 || day1 == 0) {
	   day1 = 1;
	  }

	  int estimatedEfforts = 0, actualEstEfrts = 0;
	  int utilizedEfforts = 0, actualUtiEfts = 0;
	  int billableEts = 0 , sumEfts = 0;
	  int estUtil = 0, actulUtil = 0;
	  int x = 0, x1 = 0, x2 = 0 , x3 = 0,x4 = 0;
	  int y = 0, y1 = 0, y2 = 0 , y3 = 0, y4 = 0;

	  int cmp = 0;
	  int i = 0;
	  Timesheet sheet = null;
	  List<Timesheet> monthlyList = new ArrayList<>();
	  for (Timesheet timesheet : timeSheetDtls) {
	   i++;

	   if(timesheet.getEmpId() != null){
	   empId = timesheet.getEmpId().getEmpId();
	   fromDate = timesheet.getFromDate();
	   toDate = timesheet.getToDate();
	   System.out.println("timesheet list ==="  +timesheet);
	   // actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
	   actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();
	   billableEts = timesheet.getBillableEfforts();
	   sumEfts = timesheet.getSumHours();
	   System.out.println("sum hours===="  + sumEfts);

	   if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    System.out.println("non billable hours ====" + y2);
	    y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
	    System.out.println("billable ====" + y3);
	    y4 = timeSheetDtls.get(i - 1).getSumHours();
	    System.out.println("sum  ====" + y4);
	    empFlag = true;
	   }
	   if (i == timeSheetDtls.size()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
	    y4 = timeSheetDtls.get(i - 1).getSumHours();
	    System.out.println("sum hours ====" + y4);
	    empFlag = true;
	   }
	   if (empFlag) {
	    if (i == 1) {
	     x = timeSheetDtls.get(0).getNumberEfforts();
	     // x1 = timeSheetDtls.get(0).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(0).getTimesheetEfforts();
	     x3 = timeSheetDtls.get(0).getBillableEfforts();
	     x4 = timeSheetDtls.get(0).getSumHours();

	    } else {
	     x = timeSheetDtls.get(i - 1).getNumberEfforts();
	     // x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	     x3 = timeSheetDtls.get(i - 1).getBillableEfforts();

	    }
	    sheet = new Timesheet();
	    int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
	    if (tDay1 == 0) {
	     tDay1 = 1;
	    }
	    if (tDay2 == 6) {
	     tDay2 = 5;
	    }

	    totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
	    totalEfforts = totalWorkingDays * 8;
	    // estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
	    // 8)) * (5 - day2);
	    utilizedEfforts = sumEfts;// - (x2 / (x / 8))  (day1 - 1) - (y2 / (y / 8))  (5 - day2);
	    System.out.println("actualUtiEfts==="+actualUtiEfts);
	    System.out.println("billableEts==="+billableEts);
	    System.out.println("utilizedEfforts==="+utilizedEfforts);
	    // estUtil = (estimatedEfforts * 100) / totalEfforts;
	    actulUtil = (utilizedEfforts * 100) / totalEfforts;
	    sheet.setEmpId(timesheet.getEmpId());
	    sheet.setFromDate(actualDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
	    sheet.setFromDateStr(formatter.format(actualDate));
	    sheet.setNumberEfforts(totalEfforts);
	    // sheet.setEstimatedEfforts(estimatedEfforts);
	    sheet.setTimesheetEfforts(actualUtiEfts);
	    sheet.setBillableEfforts(billableEts);
	    sheet.setSumHours(utilizedEfforts);
	    // sheet.setEstimatedUtilization(estUtil);
	    sheet.setActualUtilization(actulUtil);
	    monthlyList.add(sheet);
	    
	     * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
	     * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
	     * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
	     
	    actualEstEfrts = 0;
	    actualUtiEfts = 0;
	    empFlag = false;
	   }
	  }
	  }
	  Timesheet sheet1 = null;
	  
	  
	  
	 empActiveList = empDao.getActiveEmployeeList();
	  for (Employee empList : empActiveList) {
	   boolean found = true;
	   for (Timesheet sheetList : monthlyList) {
	    if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
	     found = false;
	   }
	   if (found) {
	    sheet1 = new Timesheet();
	    sheet1.setEmpId(empList);
	    sheet1.setFromDate(actualDate);
	    sheet1.setActualUtilization(0);
	    sheet1.setEstimatedEfforts(0);
	    sheet1.setEstimatedUtilization(0);
	    sheet1.setTimesheetEfforts(0);
	    sheet1.setBillableEfforts(0);
	    sheet1.setSumHours(0);
	    sheet1.setNumberEfforts(timeSheetDtls.get(0).getNumberEfforts());
	    monthlyList.add(sheet1);
	   }
	  }
	  model.put("monthlySheet", monthlyList);
	  model.put("startDate", actualDate);
	  return "allEmpTimeSheetDetailsMonthlyReport";
	 }*/
	
	@RequestMapping(value = "/empTimesheetReport", method = RequestMethod.GET)
	 public String empTimesheetReport(ModelMap model) {
	  ModelAndView modelObj = null;
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   return "empTimesheetReport";
	  } else {
	   Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
	   modelObj = getLogoutView(resp);
	   return "redirect:/login";
	  }
	 }
	
	 @RequestMapping(value = "/getAllEmpTimeSheetDetails", method = RequestMethod.POST)
	 public String getAllEmpTimeSheetDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response)
	   throws ParseException {
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

	   Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
	   startDate = new java.sql.Date(startDate.getTime());

	   Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
	   endDate = new java.sql.Date(endDate.getTime());

	   List<Timesheet> timeSheetDtls = resourceDao.getTimeSheet(startDate, endDate);
	   List<Timesheet> weeklyList = new ArrayList<>();
	   Timesheet sheet = null;
	   int estUtil = 0, actulUti = 0;
	   for (Timesheet timesheet : timeSheetDtls) {
	    sheet = new Timesheet();
	    estUtil = (timesheet.getEstimatedEfforts() * 100) / timesheet.getNumberEfforts();
	    actulUti = (timesheet.getTimesheetEfforts() * 100) / timesheet.getNumberEfforts();
	    sheet = timesheet;
	    sheet.setEstimatedUtilization(estUtil);
	    sheet.setActualUtilization(actulUti);
	    weeklyList.add(sheet);
	   }

	   Timesheet sheet1 = null;
	   empActiveList = empDao.getActiveEmployeeList();
	   for (Employee empList : empActiveList) {
	    boolean found = true;
	    for (Timesheet sheetList : weeklyList) {
	     if (empList.getEmpId() == sheetList.getEmpId().getEmpId())
	      found = false;
	    }
	    if (found) {
	     if (weeklyList.size() != 0) {
	      sheet1 = new Timesheet();
	      sheet1.setEmpId(empList);
	      sheet1.setFromDateStr(weeklyList.get(0).getFromDateStr());
	      sheet1.setToDateStr(weeklyList.get(0).getToDateStr());
	      sheet1.setActualUtilization(0);
	      sheet1.setEstimatedEfforts(0);
	      sheet1.setEstimatedUtilization(0);
	      sheet1.setTimesheetEfforts(0);
	      sheet1.setBillableEfforts(0);
	      sheet1.setSumHours(0);
	      sheet1.setNumberEfforts(0);
	      weeklyList.add(sheet1);
	     }
	    }
	   }
	   model.put("weeklyList", weeklyList);
	   model.put("startDate", startDate);
	   model.put("endDate", endDate);
	   return "allEmpTimeSheetDetailsWeeklyReport";
	  } else {
	   Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
	   return "redirect:/login";
	  }
	 }
	 
	 
	 @RequestMapping(value = "/getAllEmpWeeklyTimeSheetDetails", method = RequestMethod.POST)
	 public String getAllEmpWeeklyTimeSheetDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response)
	   throws ParseException {
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

	   Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
	   startDate = new java.sql.Date(startDate.getTime());

	   Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
	   endDate = new java.sql.Date(endDate.getTime());

	   List<Timesheet> timeSheetDtls = resourceDao.getTimeSheet(startDate, endDate);
	   List<Timesheet> weeklyList = new ArrayList<>();
	   Timesheet sheet = null;
	   int estUtil = 0, actulUti = 0;
	   int sumHours =0;
	   for (Timesheet timesheet : timeSheetDtls) {
	    sheet = new Timesheet();
	    estUtil = (timesheet.getEstimatedEfforts() * 100) / timesheet.getNumberEfforts();
	    sumHours =(timesheet.getTimesheetEfforts() + timesheet.getBillableEfforts());
	    actulUti = (sumHours * 100) / timesheet.getNumberEfforts();
	    
	    sheet = timesheet;
	    sheet.setEstimatedUtilization(estUtil);
	    sheet.setActualUtilization(actulUti);
	    sheet.setSumHours(sumHours);
	    weeklyList.add(sheet);
	   }

	   Timesheet sheet1 = null;
	   empActiveList = empDao.getActiveEmployeeList();
	   for (Employee empList : empActiveList) {
	    boolean found = true;
	    for (Timesheet sheetList : weeklyList) {
	     if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
	      found = false;
	    }
	    if (found) {
	     if (weeklyList.size() != 0) {
	      sheet1 = new Timesheet();
	      sheet1.setEmpId(empList);
	      sheet1.setFromDateStr(weeklyList.get(0).getFromDateStr());
	      sheet1.setToDateStr(weeklyList.get(0).getToDateStr());
	      sheet1.setActualUtilization(0);
	      sheet1.setEstimatedEfforts(0);
	      sheet1.setEstimatedUtilization(0);
	      sheet1.setTimesheetEfforts(0);
	      sheet1.setBillableEfforts(0);
	      sheet1.setSumHours(0);
	      sheet1.setNumberEfforts(0);
	      weeklyList.add(sheet1);
	     }
	    }
	   }
	   model.put("weeklyList", weeklyList);
	   model.put("startDate", startDate);
	   model.put("endDate", endDate);
	   return "allEmpTimeSheetDetailsWeeklyReport";
	  } else {
	   Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
	   return "redirect:/login";
	  }
	 }
	 
	 
	 @RequestMapping(value = "/getAllEmpTimeSheetMonthlyDetails", method = RequestMethod.POST)
	 public String getAllEmpTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req) throws ParseException {

	  DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
	  String startDate = req.getParameter("month");
	  String date[] = startDate.split("/");
	  int month = Integer.parseInt(date[0]);
	  int year = Integer.parseInt(date[1]);
	  Calendar calendar = Calendar.getInstance();
	  calendar.clear();
	  calendar.set(Calendar.MONTH, month - 1);
	  calendar.set(Calendar.YEAR, year);
	  Date actualDate = calendar.getTime();
	  List<Timesheet> timeSheetDtls = resourceDao.getMonthlyTimeSheet(month, year);
	  int empId = 0;
	  boolean empFlag = false;
	  Date fromDate = null;
	  Date toDate = null;

	  int totalNoEfts = 0, totalActEfts = 0;

	  int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	  int totalWorkingDays = 0, totalEfforts = 0;
	  int day1 = actualDate.getDay();
	  calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
	  int day2 = calendar.getTime().getDay();
	  if (day2 == 6 || day2 == 0) {
	   day2 = 5;
	  }

	  if (day1 == 6 || day1 == 0) {
	   day1 = 1;
	  }

	  int estimatedEfforts = 0, actualEstEfrts = 0;
	  int utilizedEfforts = 0, actualUtiEfts = 0;
	  int billableEts = 0 , sumEfts = 0;
	  int estUtil = 0, actulUtil = 0;
	  int x = 0, x1 = 0, x2 = 0 , x3 = 0,x4 = 0;
	  int y = 0, y1 = 0, y2 = 0 , y3 = 0, y4 = 0;

	  int cmp = 0;
	  int i = 0;
	  Timesheet sheet = null;
	  List<Timesheet> monthlyList = new ArrayList<>();
	  for (Timesheet timesheet : timeSheetDtls) {
	   i++;

	   if(timesheet.getEmpId() != null){
	   empId = timesheet.getEmpId().getEmpId();
	   fromDate = timesheet.getFromDate();
	   toDate = timesheet.getToDate();
	   // actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
	   //actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();
	   actualUtiEfts = timesheet.getTimesheetEfforts();
	   billableEts = timesheet.getBillableEfforts();
	   sumEfts = (timesheet.getTimesheetEfforts() + timesheet.getBillableEfforts());

	   if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    //System.out.println("non billable hours ====" + y2);
	    y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
	    //System.out.println("billable ====" + y3);
	    y4 = timeSheetDtls.get(i - 1).getSumHours();
	    //System.out.println("sum  ====" + y4);
	    empFlag = true;
	   }
	   if (i == timeSheetDtls.size()) {
	    y = timeSheetDtls.get(i - 1).getNumberEfforts();
	    // y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	    y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	    y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
	    y4 = timeSheetDtls.get(i - 1).getSumHours();
	    //System.out.println("sum hours ====" + y4);
	    empFlag = true;
	   }
	   if (empFlag) {
	    if (i == 1) {
	     x = timeSheetDtls.get(0).getNumberEfforts();
	     // x1 = timeSheetDtls.get(0).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(0).getTimesheetEfforts();
	     x3 = timeSheetDtls.get(0).getBillableEfforts();
	     x4 = timeSheetDtls.get(0).getSumHours();

	    } else {
	     x = timeSheetDtls.get(i - 1).getNumberEfforts();
	     // x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
	     x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
	     x3 = timeSheetDtls.get(i - 1).getBillableEfforts();

	    }
	    sheet = new Timesheet();
	    int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
	    if (tDay1 == 0) {
	     tDay1 = 1;
	    }
	    if (tDay2 == 6) {
	     tDay2 = 5;
	    }

	    totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
	    totalEfforts = totalWorkingDays * 8;
	    // estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
	    // 8)) * (5 - day2);
	    utilizedEfforts = sumEfts;// - (x2 / (x / 8))  (day1 - 1) - (y2 / (y / 8))  (5 - day2);
	    // estUtil = (estimatedEfforts * 100) / totalEfforts;
	    actulUtil = (utilizedEfforts * 100) / totalEfforts;
	    sheet.setEmpId(timesheet.getEmpId());
	    sheet.setFromDate(actualDate);
	    SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
	    sheet.setFromDateStr(formatter.format(actualDate));
	    sheet.setNumberEfforts(totalEfforts);
	    // sheet.setEstimatedEfforts(estimatedEfforts);
	    sheet.setTimesheetEfforts(actualUtiEfts);
	    sheet.setBillableEfforts(billableEts);
	    sheet.setSumHours(utilizedEfforts);
	    // sheet.setEstimatedUtilization(estUtil);
	    sheet.setActualUtilization(actulUtil);
	    monthlyList.add(sheet);
	    /*
	     * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
	     * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
	     * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
	     */
	    actualEstEfrts = 0;
	    actualUtiEfts = 0;
	    empFlag = false;
	   }
	  }
	  }
	  Timesheet sheet1 = null;
	  
	  
	  
	 empActiveList = empDao.getActiveEmployeeList();
	  for (Employee empList : empActiveList) {
	   boolean found = true;
	   for (Timesheet sheetList : monthlyList) {
	    if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
	     found = false;
	   }
	   if (found) {
	    sheet1 = new Timesheet();
	    sheet1.setEmpId(empList);
	    sheet1.setFromDate(actualDate);
	    sheet1.setActualUtilization(0);
	    sheet1.setEstimatedEfforts(0);
	    sheet1.setEstimatedUtilization(0);
	    sheet1.setTimesheetEfforts(0);
	    sheet1.setBillableEfforts(0);
	    sheet1.setSumHours(0);
	    sheet1.setNumberEfforts(0);
	    //sheet1.setNumberEfforts(timeSheetDtls.get(0).getNumberEfforts());
	    monthlyList.add(sheet1);
	   }
	  }
	  model.put("monthlySheet", monthlyList);
	  model.put("startDate", actualDate);
	  return "allEmpTimeSheetDetailsMonthlyReport";
	 }
	 

	 @RequestMapping(value = "/getPmoMonthlySubmittedTimesheets", method = RequestMethod.POST)
	 public String getPmoMonthlySubmittedTimesheets(ModelMap model, HttpServletRequest req) throws ParseException {

	  //int managerId = sessionobj.getEmpUsersObj().getEmpId().getEmpId();
	  DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
	  String startDate = req.getParameter("month");
	  System.out.println("start Date: "+startDate);
	  String date[] = startDate.split("/");
	  
	  int month = Integer.parseInt(date[0]);
	  int year = Integer.parseInt(date[1]);
	  Calendar calendar = Calendar.getInstance();
	  calendar.clear();
	  calendar.set(Calendar.MONTH, month - 1);
	  calendar.set(Calendar.YEAR, year);
	  Date actualDate = calendar.getTime();
	 
	  List<Timesheet>  timeSheetDtls = resourceDao.getManagerMonthlyTimeSheetsSubmitted(month, year);
	  
	  System.out.println("timeSheetDtls-------"+timeSheetDtls.size());
	  
	  
	 model.put("timeSheetDtls", timeSheetDtls);
	 model.put("startDate", startDate);
	 //model.put("endDate", endDate);
	  return "timeSheetDetailsPmoMonthlyReport";
	 }
	
	 
	 
	 
	 
	
	
	 
	 @RequestMapping(value = "/getAccountsTimeSheetDetails", method = RequestMethod.POST)
	 public String getAccountsTimeSheetDetails(ModelMap model,@ModelAttribute TimesheetRequest timesheet, HttpServletRequest req, HttpServletResponse response)

	   throws ParseException {
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
	   Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
	   startDate = new java.sql.Date(startDate.getTime());
	   Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
	   endDate = new java.sql.Date(endDate.getTime());
	   System.out.println("startDate="+startDate+",endDate="+endDate+",EmpId="+timesheet.getEmpId().getEmpId());
	   List<Employee> usersEmpList = usersDao.getListEmpUsers();
	   
	   
	  int id =timesheet.getEmpId().getEmpId();
	   List<Timesheet> timeSheetDtls = resourceDao.getAccountsTimeSheet(startDate, endDate,id);

	   System.out.println("tsheet list size : " + timeSheetDtls.size());
	   model.put("weeklyList", timeSheetDtls);
	   
	   model.put("startDate", startDate);
	   model.put("endDate", endDate);
	   model.put("empList", usersEmpList);
	   model.put("id", id);

	   return "accountsUtilizationReport";

	  } else {
	   Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
	   return "redirect:/login";
	  }
	 }
	@RequestMapping(value = "/getAccountsProjectsList", method = RequestMethod.POST)
	 public String getAccountsProjectsList(ModelMap model,@ModelAttribute TimesheetRequest timesheet, HttpServletRequest req, HttpServletResponse response)

	   throws ParseException {
	  if (sessionobj != null && sessionobj.getIsValidLogin()) {
	   DateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
	   Date startDate = (Date) formatter1.parse(req.getParameter("startdate"));
	   startDate = new java.sql.Date(startDate.getTime());
	   Date endDate = (Date) formatter1.parse(req.getParameter("enddate"));
	   endDate = new java.sql.Date(endDate.getTime());
	   System.out.println("startDate="+startDate+",endDate="+endDate+",EmpId="+timesheet.getProjectId().getProjectId());
	   List<Employee> usersEmpList = usersDao.getListEmpUsers();
	   
	   List<Projects> projList = projectDao.getActiveProjects();
	  int id=timesheet.getProjectId().getProjectId();
	   List<Timesheet> timeSheetDtls = resourceDao.getProjectwiseTimeSheet(startDate, endDate,id);

	   System.out.println("tsheet list size : " + timeSheetDtls.size());
	   model.put("weeklyList", timeSheetDtls);
	   model.put("projList", projList);
	   model.put("startDate", startDate);
	   model.put("endDate", endDate);
	   model.put("usersEmpList", usersEmpList);
	   model.put("id", id);

	   return "accountsProjectsUtilizationReport";

	  } else {
	   Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
	   return "redirect:/login";
	  }
	 }
	 
	
	@RequestMapping(value = "/exportGetAllTimeSheetWeeklyDetails/{startdate}/{enddate}", method = RequestMethod.GET)
	public String getAllTimeSheetWeeklyReport(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startdate, @PathVariable String enddate) throws ParseException {
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = (Date) formatter1.parse(startdate);
		Date endDate = (Date) formatter1.parse(enddate);
		//int managerId = sessionobj.getEmpUsersObj().getEmpId().getEmpId();

		startDate = new java.sql.Date(startDate.getTime());
		endDate = new java.sql.Date(endDate.getTime());
		System.out.println("startDate==" + startDate + ",endDate=" + endDate);
		
		List<Timesheet> timeSheetDtls = resourceDao.getTimeSheet(startDate, endDate);
		List<Timesheet> weeklyList = new ArrayList<>();
		Timesheet sheet = null;
		int estUtil = 0, actulUti = 0;
		for (Timesheet timesheet : timeSheetDtls) {
			sheet = new Timesheet();
			estUtil = (timesheet.getEstimatedEfforts() * 100) / timesheet.getNumberEfforts();
			actulUti = (timesheet.getTimesheetEfforts() * 100) / timesheet.getNumberEfforts();
			sheet = timesheet;
			sheet.setEstimatedUtilization(estUtil);
			sheet.setActualUtilization(actulUti);
			weeklyList.add(sheet);
		}

		Timesheet sheet1 = null;
		empActiveList = empDao.getActiveEmployeeList();
		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : weeklyList) {
				if (empList.getEmpId() == sheetList.getEmpId().getEmpId())
					found = false;
			}
			if (found) {
				if (weeklyList.size() != 0) {
					sheet1 = new Timesheet();
					sheet1.setEmpId(empList);
					sheet1.setFromDateStr(weeklyList.get(0).getFromDateStr());
					sheet1.setToDateStr(weeklyList.get(0).getToDateStr());
					sheet1.setActualUtilization(0);
					sheet1.setEstimatedEfforts(0);
					sheet1.setEstimatedUtilization(0);
					sheet1.setTimesheetEfforts(0);
					sheet1.setBillableEfforts(0);
					sheet1.setSumHours(0);
					sheet1.setNumberEfforts(0);
					weeklyList.add(sheet1);
				}
			}
		}
		try {
			doAllEmpTotalBenchPeriodWeeklyExport(weeklyList, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("weeklyList", weeklyList);
		model.put("startDate", startDate);
		model.put("endDate", endDate);
		return "allEmpTimeSheetDetailsWeeklyReport";
		
	}

	public void doAllEmpTotalBenchPeriodWeeklyExport(List<Timesheet> weeklyList, HttpServletResponse response)
			throws Exception {
		System.out.println("Came into doEmpTotalBenchPeriodWeeklyExport==" + weeklyList.size());
		if (weeklyList != null && !weeklyList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();

			HSSFSheet sheet = workBook.createSheet("All Employee Weekly Report");

			for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 8);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			// style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 8);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("EMPLOYEE ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("EMPLOYEE NAME");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("FROM DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("TO DATE");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("TOTAL EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("NON-BILLABLE EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("BILLABLE EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 7);
			ch3.setCellValue("EMPLOYEE LOGGED EFFORTS");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 8);
			ch3.setCellValue("AUTUAL UTILIZATION");
			ch3.setCellStyle(style2);

			sheet.setFitToPage(true);

			short rowNo = 4;
			for (Timesheet details : weeklyList) {

				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				System.out.println("details.getEmpId().getEmployeeName()="+ details.getEmpId().getEmployeeName());
				row.createCell((short) 2).setCellValue(details.getFromDateStr());
				row.createCell((short) 3).setCellValue(details.getToDateStr());
				row.createCell((short) 4).setCellValue(details.getNumberEfforts());
				row.createCell((short) 5).setCellValue(details.getTimesheetEfforts());
				row.createCell((short) 6).setCellValue(details.getBillableEfforts());
				System.out.println("details.getBillableEfforts()===" +details.getBillableEfforts());
				row.createCell((short) 7).setCellValue(details.getSumHours());
				row.createCell((short) 8).setCellValue(details.getActualUtilization());
				rowNo++;
			}

			String file = "EmpsTotalWeeklyReport.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}
	
	@RequestMapping(value = "/exportAllEmpTimeSheetMonthlyDetails/{startDate}", method = RequestMethod.GET)
	public String ExportAllEmpTimeSheetMonthlyDetails(ModelMap model, HttpServletRequest req, HttpServletResponse response,
			@PathVariable String startDate) throws ParseException {
		//int managerId = sessionobj.getEmpUsersObj().getEmpId().getEmpId();
		DateFormat formatter1 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				Date sDate = (Date) formatter1.parse(startDate);
		//DateFormat formatter1 = new SimpleDateFormat("MMM-yyyy");
		//String startDate = req.getParameter("month");
		//String date[] = startDate.split("/");

				Calendar cal = Calendar.getInstance();
				cal.setTime(sDate);
				String formatedDate = (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.YEAR);
				String date[] = formatedDate.split("-");
				System.out.println("formatedDate : " + formatedDate);
				
		int month = Integer.parseInt(date[0]);
		int year = Integer.parseInt(date[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month );
		calendar.set(Calendar.YEAR, year);
		Date actualDate = calendar.getTime();
		List<Timesheet> timeSheetDtls = resourceDao.getMonthlyTimeSheet(month, year);
		int empId = 0;
		boolean empFlag = false;
		Date fromDate = null;
		Date toDate = null;

		int totalNoEfts = 0, totalActEfts = 0;

		int totalWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int totalWorkingDays = 0, totalEfforts = 0;
		int day1 = actualDate.getDay();
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE) - 1);
		int day2 = calendar.getTime().getDay();
		if (day2 == 6 || day2 == 0) {
			day2 = 5;
		}

		if (day1 == 6 || day1 == 0) {
			day1 = 1;
		}

		int estimatedEfforts = 0, actualEstEfrts = 0;
		int utilizedEfforts = 0, actualUtiEfts = 0;
		int billableEts = 0 , sumEfts = 0;
		int estUtil = 0, actulUtil = 0;
		int x = 0, x1 = 0, x2 = 0 , x3 = 0,x4 = 0;
		int y = 0, y1 = 0, y2 = 0 , y3 = 0, y4 = 0;

		int cmp = 0;
		int i = 0;
		Timesheet sheet = null;
		List<Timesheet> monthlyList = new ArrayList<>();
		for (Timesheet timesheet : timeSheetDtls) {
			i++;

			if(timesheet.getEmpId() != null){
			empId = timesheet.getEmpId().getEmpId();
			fromDate = timesheet.getFromDate();
			toDate = timesheet.getToDate();
			System.out.println("timesheet list ==="  +timesheet);
			// actualEstEfrts = actualEstEfrts + timesheet.getEstimatedEfforts();
			actualUtiEfts = actualUtiEfts + timesheet.getTimesheetEfforts();
			billableEts = timesheet.getBillableEfforts();
			sumEfts = timesheet.getSumHours();
			System.out.println("sum hours===="  + sumEfts);

			if (i < timeSheetDtls.size() && empId != timeSheetDtls.get(i).getEmpId().getEmpId()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				// y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				System.out.println("non billable hours ====" + y2);
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				System.out.println("billable ====" + y3);
				y4 = timeSheetDtls.get(i - 1).getSumHours();
				System.out.println("sum  ====" + y4);
				empFlag = true;
			}
			if (i == timeSheetDtls.size()) {
				y = timeSheetDtls.get(i - 1).getNumberEfforts();
				// y1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
				y2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
				y3 = timeSheetDtls.get(i - 1).getBillableEfforts();
				y4 = timeSheetDtls.get(i - 1).getSumHours();
				System.out.println("sum hours ====" + y4);
				empFlag = true;
			}
			if (empFlag) {
				if (i == 1) {
					x = timeSheetDtls.get(0).getNumberEfforts();
					// x1 = timeSheetDtls.get(0).getEstimatedEfforts();
					x2 = timeSheetDtls.get(0).getTimesheetEfforts();
					x3 = timeSheetDtls.get(0).getBillableEfforts();
					x4 = timeSheetDtls.get(0).getSumHours();

				} else {
					x = timeSheetDtls.get(i - 1).getNumberEfforts();
					// x1 = timeSheetDtls.get(i - 1).getEstimatedEfforts();
					x2 = timeSheetDtls.get(i - 1).getTimesheetEfforts();
					x3 = timeSheetDtls.get(i - 1).getBillableEfforts();

				}
				sheet = new Timesheet();
				int tDay1 = actualDate.getDay(), tDay2 = calendar.getTime().getDay();
				if (tDay1 == 0) {
					tDay1 = 1;
				}
				if (tDay2 == 6) {
					tDay2 = 5;
				}

				totalWorkingDays = (totalWeeks * 5) - (tDay1 - 1) - (5 - tDay2);
				totalEfforts = totalWorkingDays * 8;
				// estimatedEfforts = actualEstEfrts - (x1 / (x / 8)) * (day1 - 1) - (y1 / (y /
				// 8)) * (5 - day2);
				utilizedEfforts = sumEfts;// - (x2 / (x / 8)) * (day1 - 1) - (y2 / (y / 8)) * (5 - day2);
				System.out.println("actualUtiEfts==="+actualUtiEfts);
				System.out.println("billableEts==="+billableEts);
				System.out.println("utilizedEfforts==="+utilizedEfforts);
				// estUtil = (estimatedEfforts * 100) / totalEfforts;
				actulUtil = (utilizedEfforts * 100) / totalEfforts;
				sheet.setEmpId(timesheet.getEmpId());
				sheet.setFromDate(actualDate);
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM-yyyy");
				sheet.setFromDateStr(formatter.format(actualDate));
				sheet.setNumberEfforts(totalEfforts);
				// sheet.setEstimatedEfforts(estimatedEfforts);
				sheet.setTimesheetEfforts(actualUtiEfts);
				sheet.setBillableEfforts(billableEts);
				sheet.setSumHours(utilizedEfforts);
				// sheet.setEstimatedUtilization(estUtil);
				sheet.setActualUtilization(actulUtil);
				monthlyList.add(sheet);
				/*
				 * System.out.println("totalEfforts=" + totalEfforts + ",estimatedEfforts=" +
				 * estimatedEfforts + ",utilizedEfforts=" + utilizedEfforts);
				 * System.out.println("estUtil=" + estUtil + ",actulUtil=" + actulUtil);
				 */
				actualEstEfrts = 0;
				actualUtiEfts = 0;
				empFlag = false;
			}
		}
		}
		Timesheet sheet1 = null;
		
		
		
	empActiveList = empDao.getActiveEmployeeList();
		for (Employee empList : empActiveList) {
			boolean found = true;
			for (Timesheet sheetList : monthlyList) {
				if (empList.getEmpId().equals(sheetList.getEmpId().getEmpId()))
					found = false;
			}
			if (found) {
				sheet1 = new Timesheet();
				sheet1.setEmpId(empList);
				sheet1.setFromDate(actualDate);
				sheet1.setActualUtilization(0);
				sheet1.setEstimatedEfforts(0);
				sheet1.setEstimatedUtilization(0);
				sheet1.setTimesheetEfforts(0);
				sheet1.setBillableEfforts(0);
				sheet1.setSumHours(0);
				sheet1.setNumberEfforts(timeSheetDtls.get(0).getNumberEfforts());
				monthlyList.add(sheet1);
			}
		}
		
	try {
			doExportAllEmpMonthlyReport(monthlyList, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	model.put("monthlySheet", monthlyList);
	model.put("startDate", actualDate);
	return "allEmpTimeSheetDetailsMonthlyReport";
	}

	public void doExportAllEmpMonthlyReport(List<Timesheet> monthlyList, HttpServletResponse response) throws Exception {
		if (monthlyList != null && !monthlyList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			// HSSFSheet sheet = workBook.createSheet();
			HSSFSheet sheet = workBook.createSheet("Technical Team");

			for (int columnPosition = 0; columnPosition < 5; columnPosition++) {
				sheet.autoSizeColumn((short) (columnPosition));
			}

			sheet.setHorizontallyCenter(true);
			sheet.setFitToPage(true);
			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 15);
			style1.setFont(font);
			ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

			HSSFRow row2 = sheet.createRow(2);
			HSSFCell ch2 = row2.createCell(0);
			ch2.setCellStyle(style1);
			ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 13);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// HSSFCell ch3 =headingRow.createCell(0);

			// HSSFRow headingRow = sheet.createRow(3);
			// HSSFCell ch3 = headingRow.createCell(9);
			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("Employee ID");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("Employee Name");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("Month");
			ch3.setCellStyle(style2);
			/*ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("Total Efforts");
			ch3.setCellStyle(style2);*/
			ch3 = headingRow.createCell((short) 3);
			ch3.setCellValue("Non-Billable Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 4);
			ch3.setCellValue("Billable Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 5);
			ch3.setCellValue("Employee Logged Efforts");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 6);
			ch3.setCellValue("Actual Utilization");
			ch3.setCellStyle(style2);
			short rowNo = 4;

			for (Timesheet details : monthlyList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellStyle(style);
				row.createCell((short) 0).setCellValue(details.getEmpId().getEmployeeId());
				row.createCell((short) 1).setCellStyle(style);
				row.createCell((short) 1).setCellValue(details.getEmpId().getEmployeeName());
				System.out.println("details.getEmpId().getEmployeeName()== "+details.getEmpId().getEmployeeName());
				row.createCell((short) 2).setCellStyle(style);
				row.createCell((short) 2).setCellValue(details.getFromDate().getMonth());
				/*row.createCell((short) 3).setCellStyle(style);
				row.createCell((short) 3).setCellValue(details.getNumberEfforts());*/

				row.createCell((short) 3).setCellStyle(style);
				row.createCell((short) 3).setCellValue(details.getTimesheetEfforts());
				
				row.createCell((short) 4).setCellStyle(style);
				row.createCell((short) 4).setCellValue(details.getBillableEfforts());
				System.out.println("details.getBillableEfforts()" +details.getBillableEfforts());
				
				row.createCell((short) 5).setCellStyle(style);
				row.createCell((short) 5).setCellValue(details.getSumHours());

				row.createCell((short) 6).setCellStyle(style);
				row.createCell((short) 6).setCellValue(details.getActualUtilization());
				rowNo++;
			}

			String file = "AllEmployeeMonthlyReport.xls";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}
	
	@RequestMapping(value = "/accountsTimeSheetReport", method = RequestMethod.GET)
    public String accountsTimeSheetReport(ModelMap model, @ModelAttribute TimesheetRequest timesheetReq) {
     ModelAndView modelObj = null;
     if (sessionobj != null && sessionobj.getIsValidLogin()) {
      List<Employee> usersEmpList = usersDao.getListEmpUsers();
      List<Projects> projList = projectDao.getActiveProjects();
      model.put("usersEmpList", usersEmpList);
      model.put("TimesheetRequest", timesheetReq);
      model.put("projList", projList);

      return "accountsTimeSheetReport";
     } else {
      Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
      modelObj = getLogoutView(resp);

      return "redirect:/login";
     }
    }
 @RequestMapping(value = "getReportingManagerList", method = RequestMethod.POST)
    public ModelAndView getReportingManagerList(ModelMap model, HttpServletRequest req) {
     ModelAndView modelObj = null;
     if (sessionobj != null && sessionobj.getIsValidLogin()) {
      
      String startDate = req.getParameter("startdate");
      System.out.println("startDate " + startDate );
      String endDate = req.getParameter("enddate");
      System.out.println("enddate " + endDate);
      List<Employee> usersEmpList = usersDao.getListEmpUsers();
      List<Projects> projList = projectDao.getActiveProjects();    
      TimesheetRequest timeSheetReq=new TimesheetRequest();

      model.put("usersEmpList", usersEmpList);
      model.put("projList", projList);
       //model.put("weeklyList", timeSheetDtls);
      model.put("TimesheetRequest", timeSheetReq);
      model.put("startDate", startDate);
      model.put("endDate", endDate);
      // model.put("id", id);
      sessionobj.setStartDateStr(startDate);
      sessionobj.setEndDateStr(endDate);
      Map<String, Object> mapModel = new HashMap<String, Object>();
      modelObj = new ModelAndView("accountsUtilizationReport", mapModel);
     } else {
      Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
      modelObj = getLogoutView(resp);
     }
     return modelObj;
    }
    
    @RequestMapping(value = "getReportingManagerList/{empId}")
    public String getReportingManagerList(@PathVariable Integer empId, HttpServletRequest request,TimesheetRequest timeSheetReq,
      HttpServletResponse response, ModelMap model) throws IOException {
     ModelAndView modelObj = null;
     if (sessionobj != null && sessionobj.getIsValidLogin()) {
      Date fromDate=null; Date toDate=null;
      List<Employee> usersEmpList = usersDao.getListEmpUsers();

      List<Projects> projList = projectDao.getActiveProjects();
      //int empUserId = sessionobj.getEmpObj().getEmpId();
      SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
      
      SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
      
      try {
       fromDate = format1.parse(sessionobj.getStartDateStr());
       toDate=format1.parse(sessionobj.getEndDateStr());
        
      } catch (ParseException e) {
       
      }
      System.out.println("Start date = "+fromDate+", Session Start date = "+sessionobj.getStartDateStr());
      System.out.println("end date = "+toDate);
      System.out.println("format2.format(fromDate) = "+format2.format(fromDate));
      System.out.println(" format2.format(toDate) = "+ format2.format(toDate));
      // int id =timeSheetReq.getEmpId().getEmpId();
         List<Timesheet> timeSheetList = resourceDao.getAccountsTimeSheet(format2.format(fromDate), format2.format(toDate), empId);
      //List<Timesheet> timeSheetList = resourceDao.getProjec(format2.format(fromDate), format2.format(toDate), empId);
      for(Timesheet tSheet:timeSheetList)
      {
      System.out.println("tSheet=== "+tSheet );
      
      }
      System.out.println(timeSheetList.size() );
      model.put("usersEmpList", usersEmpList);
      model.put("projList", projList);
      model.put("timeSheetList", timeSheetList);
      model.put("TimesheetRequest", timeSheetReq);
      model.put("empId", empId);
      //model.put("startDate", startDate);
      //model.put("endDate", endDate);
      
      Map<String, Object> mapModel = new HashMap<String, Object>();
      modelObj = new ModelAndView("accountsUtilizationReport", mapModel);
      
     
      
      return "accountsUtilizationReport";
     }
     return "login";
    }
@RequestMapping(value = "exportGetReportingManagerList/{empId}/{fromDate}/{toDate}")
    public String exportGetReportingManagerList(@PathVariable Integer empId, HttpServletRequest request,TimesheetRequest timeSheetReq,
      HttpServletResponse response, ModelMap model) throws IOException {
     ModelAndView modelObj = null;
     if (sessionobj != null && sessionobj.getIsValidLogin()) {
      Date fromDate=null; Date toDate=null;
      List<Employee> usersEmpList = usersDao.getListEmpUsers();

      List<Projects> projList = projectDao.getActiveProjects();
      //int empUserId = sessionobj.getEmpObj().getEmpId();
      SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
      
      SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
      
      try {
       fromDate = format1.parse(sessionobj.getStartDateStr());
       toDate=format1.parse(sessionobj.getEndDateStr());
        
      } catch (ParseException e) {
       
      }
      
      // int id =timeSheetReq.getEmpId().getEmpId();
         List<Timesheet> timeSheetList = resourceDao.getAccountsTimeSheet(format2.format(fromDate), format2.format(toDate), empId);
      //List<Timesheet> timeSheetList = resourceDao.getProjec(format2.format(fromDate), format2.format(toDate), empId);
      for(Timesheet tSheet:timeSheetList)
      {
      System.out.println("tSheet=== "+tSheet );
      
      }
      System.out.println(timeSheetList.size() );
      
      try {
       doExportGetReportingManagerList(timeSheetList, response);
      } catch (Exception e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
      }
      model.put("usersEmpList", usersEmpList);
      model.put("projList", projList);
      model.put("timeSheetList", timeSheetList);
      model.put("TimesheetRequest", timeSheetReq);
      model.put("empId", empId);
      //model.put("startDate", startDate);
      //model.put("endDate", endDate);
      
      Map<String, Object> mapModel = new HashMap<String, Object>();
      modelObj = new ModelAndView("accountsUtilizationReport", mapModel);
      
     
      
      return "accountsUtilizationReport";
     }
     return "login";
    }
    
public void doExportGetReportingManagerList(List<Timesheet> timeSheetList, HttpServletResponse response)
	      throws Exception {
	     if (timeSheetList != null && !timeSheetList.isEmpty()) {
	      HSSFWorkbook workBook = new HSSFWorkbook();
	      // HSSFSheet sheet = workBook.createSheet();

	      HSSFSheet sheet = workBook.createSheet("All Employee Timesheet Report");

	      for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
	       sheet.autoSizeColumn(columnIndex);
	      }

	      sheet.setColumnWidth(0, 10000);
	      sheet.setColumnWidth(1, 10000);
	      sheet.setColumnWidth(2, 10000);
	      sheet.setHorizontallyCenter(true);
	      sheet.setVerticallyCenter(true);
	      sheet.autoSizeColumn(0);
	      sheet.autoSizeColumn(1);
	      sheet.autoSizeColumn(2);
	      PrintSetup ps = sheet.getPrintSetup();

	      sheet.setAutobreaks(true);

	      ps.setFitHeight((short) 1);
	      ps.setFitWidth((short) 1);

	      Font font = workBook.createFont();
	      font.setFontHeightInPoints((short) 11);
	      font.setFontName("Calibri");
	      font.setColor(IndexedColors.BLACK.getIndex());
	      font.setBold(true);

	      HSSFCellStyle style = workBook.createCellStyle();
	      style.setAlignment(CellStyle.ALIGN_CENTER);
	      style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	      style.setWrapText(true);
	      style.setFont(font);

	      HSSFRow row0 = sheet.createRow(0);
	      HSSFCell ch0 = row0.createCell(0);
	      style.setFillForegroundColor(IndexedColors.RED.getIndex());

	      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
	      ch0.setCellStyle(style);

	      HSSFRow row1 = sheet.createRow(1);
	      HSSFCell ch1 = row1.createCell(0);
	      HSSFCellStyle style1 = workBook.createCellStyle();
	      style1.setAlignment(CellStyle.ALIGN_CENTER);
	      style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	      font.setFontHeightInPoints((short) 8);
	      style1.setFont(font);
	      ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
	      ch1.setCellStyle(style1);
	      sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

	      HSSFRow row2 = sheet.createRow(2);
	      HSSFCell ch2 = row2.createCell(0);
	      ch2.setCellStyle(style1);
	      ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
	      sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
	      style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	      // style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
	      HSSFPalette palette = workBook.getCustomPalette();
	      palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
	      palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

	      style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	      HSSFCellStyle style2 = workBook.createCellStyle();
	      style2.setAlignment(CellStyle.ALIGN_CENTER);
	      style2.setWrapText(true);
	      style2.setFont(font);
	      HSSFRow headingRow = sheet.createRow(3);
	      font.setFontHeightInPoints((short) 8);
	      style2.setFont(font);
	      style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	      HSSFCell ch3 = headingRow.createCell((short) 0);
	      ch3.setCellValue("EMPLOYEE ID");
	      ch3.setCellStyle(style2);
	      ch3 = headingRow.createCell((short) 1);
	      ch3.setCellValue("EMPLOYEE NAME");
	      ch3.setCellStyle(style2);
	      /*ch3 = headingRow.createCell((short) 2);
	      ch3.setCellValue("PROJECTS");
	      ch3.setCellStyle(style2);*/
	      ch3 = headingRow.createCell((short) 2);
	      ch3.setCellValue("PROJECTS");
	      ch3.setCellStyle(style2);
	      ch3 = headingRow.createCell((short) 3);
	      ch3.setCellValue("FROM DATE");
	      ch3.setCellStyle(style2);
	      ch3 = headingRow.createCell((short) 4);
	      ch3.setCellValue("TO DATE");
	      ch3.setCellStyle(style2);
	      ch3 = headingRow.createCell((short) 5);
	      ch3.setCellValue("BILLABLE EFFORTS");
	      ch3.setCellStyle(style2);
	      ch3 = headingRow.createCell((short) 6);
	      ch3.setCellValue("NON-BILABLE EFFORTS");
	      ch3.setCellStyle(style2);

	      sheet.setFitToPage(true);

	      short rowNo = 6;
	      for (Timesheet details : timeSheetList) {
	       SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
	       details.setFromDateStr(format1.format(details.getFromDate()));
	       details.setToDateStr(format1.format(details.getToDate()));
	       HSSFRow row = sheet.createRow(rowNo);
	       row.createCell((short) 0).setCellValue(details.getEmployeeId());
	       row.createCell((short) 1).setCellValue(details.getEmployeeName());
	       row.createCell((short) 2).setCellValue(details.getBillableProjects());
	       
	       //row.createCell((short) 2).setCellValue(details.getProjectId().getProjectName());
	       row.createCell((short) 3).setCellValue(details.getFromDateStr());
	       row.createCell((short) 4).setCellValue(details.getToDateStr());
	       row.createCell((short) 5).setCellValue(details.getBillableEfforts());
	       row.createCell((short) 6).setCellValue(details.getTimesheetEfforts());
	       
	       rowNo++;
	      }

	      String file = "EmployeeTimesheetReport.xls";
	      try {
	       FileOutputStream fos = new FileOutputStream(file);
	       workBook.write(fos);
	       fos.flush();
	       fos.close();
	       downloadFile(file, response);

	      } catch (FileNotFoundException e) {
	       e.printStackTrace();
	      } catch (IOException e) {
	       e.printStackTrace();
	      } catch (Exception e) {
	       e.printStackTrace();
	      } finally {
	       response.getOutputStream().flush();
	       response.getOutputStream().close();
	      }
	     }
	    }
@RequestMapping(value = "/projectwiseTimeSheetReport", method = RequestMethod.GET)
    public String projectwiseTimeSheetReport(ModelMap model, @ModelAttribute TimesheetRequest timesheetReq) {
     ModelAndView modelObj = null;
     if (sessionobj != null && sessionobj.getIsValidLogin()) {
      List<Employee> usersEmpList = usersDao.getListEmpUsers();
      List<Projects> projList = projectDao.getActiveProjects();
      model.put("usersEmpList", usersEmpList);
      model.put("TimesheetRequest", timesheetReq);
      model.put("projList", projList);
      return "projectwiseTimeSheet";
     } else {
      Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
      modelObj = getLogoutView(resp);

      return "redirect:/login";
     }
    }
      
      
      @RequestMapping(value = "getProjectsList", method = RequestMethod.POST)
      public ModelAndView getProjectsList(ModelMap model, HttpServletRequest req) {
       ModelAndView modelObj = null;
       if (sessionobj != null && sessionobj.getIsValidLogin()) {
        //int empUserId = sessionobj.getEmpObj().getEmpId();
        //int empUserId = sessionobj.getEmpUsersObj().getEmpId().getEmpId();
        String startDate = req.getParameter("startdate");
        System.out.println("startDate " + startDate );
        String endDate = req.getParameter("enddate");
        System.out.println("enddate " + endDate);
        //int totalEfforts = Integer.parseInt(req.getParameter("noOfEfforts"));
       //System.out.println("startDate=" + startDate + ",endDate" + endDate);
        List<Employee> usersEmpList = usersDao.getListEmpUsers();

        List<Projects> projList = projectDao.getActiveProjects();    
        TimesheetRequest timeSheetReq=new TimesheetRequest();

        model.put("usersEmpList", usersEmpList);
        model.put("projList", projList);
        model.put("TimesheetRequest", timeSheetReq);
        model.put("startDate", startDate);
        model.put("endDate", endDate);
        sessionobj.setStartDateStr(startDate);
        sessionobj.setEndDateStr(endDate);
        Map<String, Object> mapModel = new HashMap<String, Object>();
        modelObj = new ModelAndView("accountsProjectsUtilizationReport", mapModel);
       } else {
        Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
        modelObj = getLogoutView(resp);
       }
       return modelObj;
      }
      
      @RequestMapping(value = "getProjectsList/{projectId}")
      public String getProjectsList(@PathVariable Integer projectId, HttpServletRequest request,TimesheetRequest timeSheetReq,
        HttpServletResponse response, ModelMap model) throws IOException {
       ModelAndView modelObj = null;
       if (sessionobj != null && sessionobj.getIsValidLogin()) {
        Date fromDate=null; Date toDate=null;
        List<Employee> usersEmpList = usersDao.getListEmpUsers();

        List<Projects> projList = projectDao.getActiveProjects();
        //int empUserId = sessionobj.getEmpObj().getEmpId();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
        
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
         fromDate = format1.parse(sessionobj.getStartDateStr());
         toDate=format1.parse(sessionobj.getEndDateStr());
          
        } catch (ParseException e) {
         
        }
        System.out.println("Start date = "+fromDate+", Session Start date = "+sessionobj.getStartDateStr());
        System.out.println("end date = "+toDate);
        System.out.println("format2.format(fromDate) = "+format2.format(fromDate));
        System.out.println(" format2.format(toDate) = "+ format2.format(toDate));
        
        List<Timesheet> timeSheetList = resourceDao.getProjectwiseTimeSheet(format2.format(fromDate), format2.format(toDate), projectId);
        for(Timesheet tSheet:timeSheetList)
        {
        System.out.println("tSheet=== "+tSheet );
        
        }
        System.out.println(timeSheetList.size() );
        model.put("usersEmpList", usersEmpList);
        model.put("projList", projList);
        model.put("timeSheetList", timeSheetList);
        model.put("TimesheetRequest", timeSheetReq);
        model.put("projectId", projectId);
        /*model.put("startDate", startDate);
        model.put("endDate", endDate);*/
        
        Map<String, Object> mapModel = new HashMap<String, Object>();
        modelObj = new ModelAndView("accountsProjectsUtilizationReport", mapModel);
        
       
        
        return "accountsProjectsUtilizationReport";
       }
       return "login";
      }
  @RequestMapping(value = "exportProjectsList/{projectId}/{fromDate}/{toDate}")
     public String exportProjectsList(@PathVariable Integer projectId, HttpServletRequest request,TimesheetRequest timeSheetReq,
       HttpServletResponse response, ModelMap model) throws IOException {
      ModelAndView modelObj = null;
      
      if (sessionobj != null && sessionobj.getIsValidLogin()) {
       Date fromDate=null; Date toDate=null;
       List<Employee> usersEmpList = usersDao.getListEmpUsers();

       List<Projects> projList = projectDao.getActiveProjects();
       //int empUserId = sessionobj.getEmpObj().getEmpId();
       SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
       
       SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
       
       try {
        fromDate = format1.parse(sessionobj.getStartDateStr());
        toDate=format1.parse(sessionobj.getEndDateStr());
         
       } catch (ParseException e) {
        
       }
       System.out.println("Start date = "+fromDate+", Session Start date = "+sessionobj.getStartDateStr());
       System.out.println("end date = "+toDate);
       System.out.println("format2.format(fromDate) = "+format2.format(fromDate));
       System.out.println(" format2.format(toDate) = "+ format2.format(toDate));
       
       List<Timesheet> timeSheetList = resourceDao.getProjectwiseTimeSheet(format2.format(fromDate), format2.format(toDate), projectId);
       for(Timesheet tSheet:timeSheetList)
       {
       System.out.println("tSheet=== "+tSheet );
       
       }
       System.out.println(timeSheetList.size() );
       try {
        doExportProjectsList(timeSheetList, response);
       } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }
       model.put("usersEmpList", usersEmpList);
       model.put("projList", projList);
       model.put("timeSheetList", timeSheetList);
       model.put("TimesheetRequest", timeSheetReq);
       model.put("projectId", projectId);
       /*model.put("startDate", startDate);
       model.put("endDate", endDate);*/
       
       Map<String, Object> mapModel = new HashMap<String, Object>();
       modelObj = new ModelAndView("accountsProjectsUtilizationReport", mapModel);
       
      
       
       return "accountsProjectsUtilizationReport";
      }
      return "login";
     }
     
     public void doExportProjectsList(List<Timesheet> timeSheetList, HttpServletResponse response)
       throws Exception {
      System.out.println("Came into doEmpTotalBenchPeriodWeeklyExport==" + timeSheetList.size());
      if (timeSheetList != null && !timeSheetList.isEmpty()) {
       HSSFWorkbook workBook = new HSSFWorkbook();
       // HSSFSheet sheet = workBook.createSheet();

       HSSFSheet sheet = workBook.createSheet("All Employee Projects Report");

       for (int columnIndex = 0; columnIndex < 10; columnIndex++) {
        sheet.autoSizeColumn(columnIndex);
       }

       sheet.setColumnWidth(0, 10000);
       sheet.setColumnWidth(1, 10000);
       sheet.setColumnWidth(2, 10000);
       sheet.setHorizontallyCenter(true);
       sheet.setVerticallyCenter(true);
       sheet.autoSizeColumn(0);
       sheet.autoSizeColumn(1);
       sheet.autoSizeColumn(2);
       PrintSetup ps = sheet.getPrintSetup();

       sheet.setAutobreaks(true);

       ps.setFitHeight((short) 1);
       ps.setFitWidth((short) 1);

       Font font = workBook.createFont();
       font.setFontHeightInPoints((short) 11);
       font.setFontName("Calibri");
       font.setColor(IndexedColors.BLACK.getIndex());
       font.setBold(true);

       HSSFCellStyle style = workBook.createCellStyle();
       style.setAlignment(CellStyle.ALIGN_CENTER);
       style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       style.setWrapText(true);
       style.setFont(font);

       HSSFRow row0 = sheet.createRow(0);
       HSSFCell ch0 = row0.createCell(0);
       style.setFillForegroundColor(IndexedColors.RED.getIndex());

       style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
       sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
       ch0.setCellStyle(style);

       HSSFRow row1 = sheet.createRow(1);
       HSSFCell ch1 = row1.createCell(0);
       HSSFCellStyle style1 = workBook.createCellStyle();
       style1.setAlignment(CellStyle.ALIGN_CENTER);
       style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       font.setFontHeightInPoints((short) 8);
       style1.setFont(font);
       ch1.setCellValue("Gemini Consulting and Services India Pvt Ltd");
       ch1.setCellStyle(style1);
       sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

       HSSFRow row2 = sheet.createRow(2);
       HSSFCell ch2 = row2.createCell(0);
       ch2.setCellStyle(style1);
       ch2.setCellValue("Resource Assignment Sheet (Technical Team)");
       sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
       style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
       // style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
       HSSFPalette palette = workBook.getCustomPalette();
       palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
       palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

       style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

       HSSFCellStyle style2 = workBook.createCellStyle();
       style2.setAlignment(CellStyle.ALIGN_CENTER);
       style2.setWrapText(true);
       style2.setFont(font);
       HSSFRow headingRow = sheet.createRow(3);
       font.setFontHeightInPoints((short) 8);
       style2.setFont(font);
       style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
       style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

       HSSFCell ch3 = headingRow.createCell((short) 0);
       ch3.setCellValue("EMPLOYEE ID");
       ch3.setCellStyle(style2);
       ch3 = headingRow.createCell((short) 1);
       ch3.setCellValue("EMPLOYEE NAME");
       ch3.setCellStyle(style2);
       /*ch3 = headingRow.createCell((short) 2);
       ch3.setCellValue("PROJECTS");
       ch3.setCellStyle(style2);*/
       ch3 = headingRow.createCell((short) 2);
       ch3.setCellValue("FROM DATE");
       ch3.setCellStyle(style2);
       ch3 = headingRow.createCell((short) 3);
       ch3.setCellValue("TO DATE");
       ch3.setCellStyle(style2);
       ch3 = headingRow.createCell((short) 4);
       ch3.setCellValue("BILLABLE EFFORTS");
       ch3.setCellStyle(style2);
       ch3 = headingRow.createCell((short) 5);
       ch3.setCellValue("NON-BILABLE EFFORTS");
       ch3.setCellStyle(style2);

       sheet.setFitToPage(true);

       short rowNo = 5;
       for (Timesheet details : timeSheetList) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
        details.setFromDateStr(format1.format(details.getFromDate()));
        details.setToDateStr(format1.format(details.getToDate()));
        HSSFRow row = sheet.createRow(rowNo);
        row.createCell((short) 0).setCellValue(details.getEmployeeId());
        row.createCell((short) 1).setCellValue(details.getEmployeeName());
        //row.createCell((short) 2).setCellValue(details.getProjectId().getProjectName());
        row.createCell((short) 2).setCellValue(details.getFromDateStr());
        row.createCell((short) 3).setCellValue(details.getToDateStr());
        row.createCell((short) 4).setCellValue(details.getBillableEfforts());
        row.createCell((short) 5).setCellValue(details.getTimesheetEfforts());
        
        rowNo++;
       }

       String file = "EmployeeProjectsReportList.xls";
       try {
    	      FileOutputStream fos = new FileOutputStream(file);
    	      workBook.write(fos);
    	      fos.flush();
    	      fos.close();
    	      downloadFile(file, response);

    	     } catch (FileNotFoundException e) {
    	      e.printStackTrace();
    	     } catch (IOException e) {
    	      e.printStackTrace();
    	     } catch (Exception e) {
    	      e.printStackTrace();
    	     } finally {
    	      response.getOutputStream().flush();
    	      response.getOutputStream().close();
    	     }
    	    }
    	   }



}
