package com.hms.api.serviceimpl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.api.dao.TransactionDao;
import com.hms.api.entity.TransactionDetails;
import com.hms.api.service.TransactionService;
import com.hms.api.utility.NumberToWords;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class TransactionServiceIMPL implements TransactionService {

	@Autowired
	private TransactionDao dao;

	@Override
	public int generateSalaryForUser(TransactionDetails transactionDetails) {
		String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
		transactionDetails.setTransactionId(id);
		return dao.generateSalaryForUser(transactionDetails);
	}

	@Override
	public String generateSalaryreportForUser(String username, int from, int to) {
		String path = null;
		Map<String, Object> map = null;
		try {
			InputStream stream = this.getClass().getResourceAsStream("/payslip.jrxml");
			JasperReport report = JasperCompileManager.compileReport(stream);
			List<TransactionDetails> transactionDetailsList = dao.getTransactionDetails(username, from, to);

			for (TransactionDetails details : transactionDetailsList) {
				map = new HashMap<>();
				map.put("basic", details.getBasic());
				map.put("leaves", details.getLeaves());
				map.put("prasentDays", details.getPrasentDays());
				map.put("noOfDays", details.getNoOfDays());
				Month month = Month.of(details.getMonth());

				map.put("month", month.toString());
				map.put("name", details.getName());
				map.put("designation", details.getDesignation());
				map.put("dearnessAllowance", details.getDearnessAllowance());
				map.put("houserentAllowance", details.getHouserentAllowance());
				map.put("conveyanceAllowance", details.getConveyanceAllowance());
				map.put("medicalAllowance", details.getMedicalAllowance());
				map.put("specialAllowance", details.getSpecialAllowance());
				map.put("totalPay", details.getTotalPay());

				map.put("pf", details.getPf());
				map.put("tds", details.getTds());
				map.put("epf", details.getEpf());
				map.put("deductionTotal", details.getDeductionTotal());
				map.put("totalPayForTheMonth", details.getTotalPayForTheMonth());
				map.put("professionalTax", details.getProfessionalTax());
				map.put("netSalary", details.getNetSalary());
				map.put("grossSalary", details.getGrossSalary());
				String amountInWord = NumberToWords.convert((long)details.getTotalPayForTheMonth());
				map.put("amountInWord", amountInWord);
				final JasperPrint print = JasperFillManager.fillReport(report, map, new JREmptyDataSource());

				path = System.getProperty("user.home") + "/Downloads/paySlip_" + month.toString() + ".pdf";

				JasperExportManager.exportReportToPdfFile(print, path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

}
