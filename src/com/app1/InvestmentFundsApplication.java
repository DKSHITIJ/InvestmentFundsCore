package com.app1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.app1.db.InvestmentFundsDB;
import com.app1.domain.Fund;

public class InvestmentFundsApplication {
	public static final double NO_OF_YEARS = 20;
	public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#.###");
	
	public static void main(String[] args) {
		InvestmentFundsDB investmentFundsDB = new InvestmentFundsDB();
		List<Fund> funds = investmentFundsDB.addAndGetTotalFunds();
		calculateFunds(funds);
	}
	
	private static List<Fund> calculateFunds(List<Fund> funds) {
		double finalAmount = 0;
		double totalAmountInvestedPerMonth = 0;	
		double finalAmountWithEPF = 0;
		double totalAmountInvestedPerMonthWithEPF = 0;
		List<Fund> totalFunds = new ArrayList<Fund>();

		for (Fund f : funds) {
			updateTotalFundDetails(f);

			totalAmountInvestedPerMonthWithEPF = totalAmountInvestedPerMonthWithEPF
					+ f.getMonthlyFundAmount();
			finalAmountWithEPF = finalAmountWithEPF + f.getFundAmount();

			if (!f.getFundName().equals("Employee Provident Fund")) {
				totalAmountInvestedPerMonth = totalAmountInvestedPerMonth
						+ f.getMonthlyFundAmount();
				finalAmount = finalAmount + f.getFundAmount();
			}
		}

		Fund f1 = new Fund("Total without Employee Provident Fund",
				totalAmountInvestedPerMonth, finalAmount, NO_OF_YEARS);
		Fund f2 = new Fund("Total with Employee Provident Fund",
				totalAmountInvestedPerMonthWithEPF, finalAmountWithEPF,
				NO_OF_YEARS);
		updateTotalFundDetails(f1);
		updateTotalFundDetails(f2);
		totalFunds.add(f1);
		totalFunds.add(f2);

		return totalFunds;
	}

	private static void updateTotalFundDetails(Fund f) {
		StringBuilder fundString = new StringBuilder();
		fundString.append(f.getFundName() + "........\n");
		fundString.append("Amount invested every month : "
				+ NUMBER_FORMAT.format(f.getMonthlyFundAmount()) + "\n");
		fundString.append("Amount invested every year  : "
				+ NUMBER_FORMAT.format(f.getMonthlyFundAmount() * 12) + "\n");
		fundString.append("Total amount invested       : "
				+ NUMBER_FORMAT.format(f.getMonthlyFundAmount() * 12
						* f.getNoOfYears()) + "\n");
		fundString.append("Absolute Returns            : "
				+ NUMBER_FORMAT.format(f.getAbsoluteReturns()) + "\n");
		fundString.append("Final Amount                : "
				+ NUMBER_FORMAT.format(f.getFundAmount()) + "\n");
		fundString.append("Annualised Returns          : "
				+ NUMBER_FORMAT.format(f.getInterstRate()) + "\n");
		fundString
				.append("------------------------------------------------------");

		f.setTotalFundDetails(fundString.toString());
		System.out.println(f.getTotalFundDetails());
	}

}
