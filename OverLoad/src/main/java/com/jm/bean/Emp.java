package com.jm.bean;

import java.io.Serializable;

public class Emp implements Serializable {
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private Integer id; //用户id
		
		private String jobNum; //工号
		
		private String empName; //用户名称
		
		private String passWord; //密码
	
		private Integer isEmp; //是否在用（1是、0否）
		
		private String stationName; //站点外键
		
		private String salt;//盐值
		
		private String name;//角色名

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSalt() {
			return salt;
		}

		public void setSalt(String salt) {
			this.salt = salt;
		}

		public Emp() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Emp(Integer id, String jobNum, String empName,
				String passWord, Integer isEmp,
				String stationName) {
			super();
			this.id = id;
			this.jobNum = jobNum;
			this.empName = empName;
			this.passWord = passWord;
			this.isEmp = isEmp;
			this.stationName = stationName;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getJobNum() {
			return jobNum;
		}

		public void setJobNum(String jobNum) {
			this.jobNum = jobNum;
		}

		public String getEmpName() {
			return empName;
		}

		public void setEmpName(String empName) {
			this.empName = empName;
		}

		public String getPassWord() {
			return passWord;
		}

		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}


		public Integer getIsEmp() {
			return isEmp;
		}

		public void setIsEmp(Integer isEmp) {
			this.isEmp = isEmp;
		}

	
		public String getStationName() {
			return stationName;
		}

		public void setStationName(String stationName) {
			this.stationName = stationName;
		}

		
		
		
		

    
}
