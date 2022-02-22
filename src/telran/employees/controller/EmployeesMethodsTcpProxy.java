package telran.employees.controller;

import telran.employees.dto.Employee;
import telran.employees.dto.ReturnCode;
import telran.employees.services.EmployeesMethods;
import telran.net.Sender;
import telran.net.TcpSender;

import static telran.employees.net.dto.ApiConstants.*;

import java.util.HashMap;
public class EmployeesMethodsTcpProxy implements EmployeesMethods {
private Sender sender;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeesMethodsTcpProxy(Sender sender) {
		this.sender = sender;
	}

	@Override
	public ReturnCode addEmployee(Employee empl) {
		
		return sender.send(ADD_EMPLOYEE, empl);
	}

	@Override
	public ReturnCode removeEmployee(long id) {
		return sender.send(REMOVE_EMPLOYEE, id);
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		
		return sender.send(GET_EMPLOYEES, "");
	}

	@Override
	public Employee getEmployee(long id) {
		return sender.send(DISPLAY_EMPLOYEE, id);
	}

	@Override
	public Iterable<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		int[] ageInterval = {ageFrom, ageTo};
		return sender.send(GET_EMPLOYEES_BY_AGE, ageInterval);
	}

	@Override
	public Iterable<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		int[] salaryInterval = {salaryFrom, salaryTo};
		return sender.send(GET_EMPLOYEES_BY_SALARY, salaryInterval);
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartment(String department) {
		return sender.send(GET_EMPLOYEES_BY_DEPARTMENT, department);
	}

	@Override
	public Iterable<Employee> getEmployeesByDepartmentAndSalary(String department, int salaryFrom, int salaryTo) {
		HashMap<String, Integer[]> employeesBySalaryInDepInfo = new HashMap<>();
		Integer[] salaryInterval = {salaryFrom, salaryTo};
		employeesBySalaryInDepInfo.put(department, salaryInterval);
		return sender.send(GET_EMPLOYEES_BY_DEPARTMENT_AND_SALARY, employeesBySalaryInDepInfo);
	}

	@Override
	public ReturnCode updateSalary(long id, int newSalary) {
		HashMap<Long,Integer> salaryUpdateInfo = new HashMap<>();
		salaryUpdateInfo.put(id, newSalary);
		return sender.send(UPDATE_SALARY, salaryUpdateInfo);
	}

	@Override
	public ReturnCode updateDepartment(long id, String newDepartment) {
		HashMap<Long,String> departmentUpdateInfo = new HashMap<>();
		departmentUpdateInfo.put(id, newDepartment);
		return sender.send(UPDATE_DEPARTMENT, departmentUpdateInfo);
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
//		System.out.println("EmployeesMethodsTcpProxy::save()");
		if(sender instanceof TcpSender) {
			TcpSender sr = (TcpSender)sender;
			try {
				sr.close();
			} catch(Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

}
