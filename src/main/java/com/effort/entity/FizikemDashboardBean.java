package com.effort.entity;



import java.util.List;

public class FizikemDashboardBean {
	
	private long sno;
	private String headQuarter;
	private Long empId;
	private String nameOfSE;
	private long routeId;
	private String routeName;
	private long noOfOutlets;
	private long existingVisitedOutlets;
	private int strikeRate;
	private long productiveCalls;
	private int productiveCallStrikeRate;
	private double productiveOrderAmt;
	private int newCustCount;
	private double newCustOrderVal;
	private double totalOrderVal;
	private String punchInTime;
	private String punchOutTime;
	private long avgTimeInOutlet;
	private String newCustIds;
	private String customerInRoutes;
	private String date;
	
	
	private String stockDistributorName;
	private long pobWithStocks;
	private long pobWithOutStocks;
	private long totalPob;
	private long stockPickUpValue;
	private long stockSoldValue;
	
	
	private long orderTotalValue;
	private String formIds;
	
	private List<Long> stockPickupFormIdsList;
	private List<Long> stockSoldFormIdsList;
	private List<Long> pobWithStocksFormIdsList;
	private List<Long> pobWithOutStocksFormIdsList;
	
	public long getSno() {
		return sno;
	}
	public void setSno(long sno) {
		this.sno = sno;
	}
	public String getHeadQuarter() {
		return headQuarter;
	}
	public void setHeadQuarter(String headQuarter) {
		this.headQuarter = headQuarter;
	}
	public String getNameOfSE() {
		return nameOfSE;
	}
	public void setNameOfSE(String nameOfSE) {
		this.nameOfSE = nameOfSE;
	}
	public long getRouteId() {
		return routeId;
	}
	public void setRouteId(long routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public long getNoOfOutlets() {
		return noOfOutlets;
	}
	public void setNoOfOutlets(long noOfOutlets) {
		this.noOfOutlets = noOfOutlets;
	}
	public long getExistingVisitedOutlets() {
		return existingVisitedOutlets;
	}
	public void setExistingVisitedOutlets(long existingVisitedOutlets) {
		this.existingVisitedOutlets = existingVisitedOutlets;
	}
	public int getStrikeRate() {
		double calculate = ((double)getExistingVisitedOutlets() * 100)/((double)getNoOfOutlets());
		if(!Double.isInfinite(calculate))
			return (int)Math.round(calculate);
		else
			return 0;
	}
	public void setStrikeRate(int strikeRate) {
		this.strikeRate = strikeRate;
	}
	public long getProductiveCalls() {
		return productiveCalls;
	}
	public void setProductiveCalls(long productiveCalls) {
		this.productiveCalls = productiveCalls;
	}
	public int getProductiveCallStrikeRate() {		
		double calculate = ((double)getProductiveCalls() * 100)/((double)getNoOfOutlets());
		if(!Double.isInfinite(calculate))
			return (int)Math.round(calculate);
		else
			return 0;
	}
	public void setProductiveCallStrikeRate(int productiveCallStrikeRate) {
		this.productiveCallStrikeRate = productiveCallStrikeRate;
	}
	public int getNewCustCount() {
		return newCustCount;
	}
	public void setNewCustCount(int newCustCount) {
		this.newCustCount = newCustCount;
	}
	public String getPunchInTime() {
		return punchInTime;
	}
	public void setPunchInTime(String punchInTime) {
		this.punchInTime = punchInTime;
	}
	public String getPunchOutTime() {
		return punchOutTime;
	}
	public void setPunchOutTime(String punchOutTime) {
		this.punchOutTime = punchOutTime;
	}
	public long getAvgTimeInOutlet() {
		return avgTimeInOutlet;
	}
	public void setAvgTimeInOutlet(long avgTimeInOutlet) {
		this.avgTimeInOutlet = avgTimeInOutlet;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public double getProductiveOrderAmt() {
		return productiveOrderAmt;
	}
	public void setProductiveOrderAmt(double productiveOrderAmt) {
		this.productiveOrderAmt = productiveOrderAmt;
	}
	public double getNewCustOrderVal() {
		return newCustOrderVal;
	}
	public void setNewCustOrderVal(double newCustOrderVal) {
		this.newCustOrderVal = newCustOrderVal;
	}
	public double getTotalOrderVal() {
		return totalOrderVal;
	}
	public void setTotalOrderVal(double totalOrderVal) {
		this.totalOrderVal = totalOrderVal;
	}
	public String getNewCustIds() {
		return newCustIds;
	}
	public void setNewCustIds(String newCustIds) {
		this.newCustIds = newCustIds;
	}
	public String getCustomerInRoutes() {
		return customerInRoutes;
	}
	public void setCustomerInRoutes(String customerInRoutes) {
		this.customerInRoutes = customerInRoutes;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStockDistributorName() {
		return stockDistributorName;
	}
	public void setStockDistributorName(String stockDistributorName) {
		this.stockDistributorName = stockDistributorName;
	}
	public long getPobWithStocks() {
		return pobWithStocks;
	}
	public void setPobWithStocks(long pobWithStocks) {
		this.pobWithStocks = pobWithStocks;
	}
	public long getPobWithOutStocks() {
		return pobWithOutStocks;
	}
	public void setPobWithOutStocks(long pobWithOutStocks) {
		this.pobWithOutStocks = pobWithOutStocks;
	}
	public long getTotalPob() {
		return totalPob;
	}
	public void setTotalPob(long totalPob) {
		this.totalPob = totalPob;
	}
	public long getStockPickUpValue() {
		return stockPickUpValue;
	}
	public void setStockPickUpValue(long stockPickUpValue) {
		this.stockPickUpValue = stockPickUpValue;
	}
	public long getStockSoldValue() {
		return stockSoldValue;
	}
	public void setStockSoldValue(long stockSoldValue) {
		this.stockSoldValue = stockSoldValue;
	}
	public long getOrderTotalValue() {
		return orderTotalValue;
	}
	public void setOrderTotalValue(long orderTotalValue) {
		this.orderTotalValue = orderTotalValue;
	}
	public String getFormIds() {
		return formIds;
	}
	public void setFormIds(String formIds) {
		this.formIds = formIds;
	}
	public List<Long> getStockPickupFormIdsList() {
		return stockPickupFormIdsList;
	}
	public void setStockPickupFormIdsList(List<Long> stockPickupFormIdsList) {
		this.stockPickupFormIdsList = stockPickupFormIdsList;
	}
	public List<Long> getStockSoldFormIdsList() {
		return stockSoldFormIdsList;
	}
	public void setStockSoldFormIdsList(List<Long> stockSoldFormIdsList) {
		this.stockSoldFormIdsList = stockSoldFormIdsList;
	}
	public List<Long> getPobWithStocksFormIdsList() {
		return pobWithStocksFormIdsList;
	}
	public void setPobWithStocksFormIdsList(List<Long> pobWithStocksFormIdsList) {
		this.pobWithStocksFormIdsList = pobWithStocksFormIdsList;
	}
	public List<Long> getPobWithOutStocksFormIdsList() {
		return pobWithOutStocksFormIdsList;
	}
	public void setPobWithOutStocksFormIdsList(
			List<Long> pobWithOutStocksFormIdsList) {
		this.pobWithOutStocksFormIdsList = pobWithOutStocksFormIdsList;
	}
	
	
	
}
