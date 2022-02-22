package telran.employees.net;

import telran.employees.dto.Employee;
import telran.employees.dto.ReturnCode;
import telran.employees.services.EmployeesMethods;
import telran.net.ApplProtocol;
import telran.net.dto.Request;
import telran.net.dto.Response;
import telran.net.dto.ResponseCode;
import java.util.*;
import static telran.employees.net.dto.ApiConstants.*;

import java.io.Serializable;

public class EmployeesProtocol implements ApplProtocol {
	
public EmployeesProtocol(EmployeesMethods employees) {
		this.employees = employees;
	}

private EmployeesMethods employees;

	@Override
	public Response getResponse(Request request) {
		switch(request.requestType) {
		case ADD_EMPLOYEE: return _employee_add(request.requestData);
		case GET_EMPLOYEES: return _get_all_employees(request.requestData);
		case REMOVE_EMPLOYEE: return _employee_remove(request.requestData);
		case UPDATE_SALARY: return _employee_salary_update(request.requestData);
		case UPDATE_DEPARTMENT: return _employee_department_update(request.requestData);
		case DISPLAY_EMPLOYEE: return _get_employee(request.requestData);
		case GET_EMPLOYEES_BY_AGE: return _get_employees_by_age(request.requestData);
		case GET_EMPLOYEES_BY_SALARY: return _get_employees_by_salary(request.requestData);
		case GET_EMPLOYEES_BY_DEPARTMENT: return _get_employees_by_department(request.requestData);
		case GET_EMPLOYEES_BY_DEPARTMENT_AND_SALARY: return _get_employees_by_department_and_salary(request.requestData);
		//TODO
		default: return new Response(ResponseCode.UNKNOWN_REQUEST,
				request.requestType + " not implemented");
		}
		
	}

	private Response _employee_add(Serializable requestData) {
		try {
			ReturnCode responseData = employees.addEmployee((Employee) requestData);
			return new Response(ResponseCode.OK, responseData);
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _get_all_employees(Serializable requestData) {
		try {
			List<Employee> responseData = new ArrayList<>();
			employees.getAllEmployees().forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable)responseData);
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _employee_remove(Serializable requestData) {
		try {
			return new Response(ResponseCode.OK, employees.removeEmployee((long)requestData));
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _employee_salary_update(Serializable requestData) {
		try {
				HashMap<Long, Integer> dataMap = (HashMap<Long, Integer>)requestData;
				long id = dataMap.keySet().iterator().next();
				return new Response(ResponseCode.OK, employees.updateSalary(id, dataMap.get(id)));
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _employee_department_update(Serializable requestData) {
		try {
			HashMap<Long, String> dataMap = (HashMap<Long, String>) requestData;
			long id = dataMap.keySet().iterator().next();
			String department = dataMap.get(id);
			return new Response(ResponseCode.OK, 
					employees.updateDepartment(id, department));
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _get_employee(Serializable requestData) {
		try {
			return new Response(ResponseCode.OK, employees.getEmployee((long)requestData));
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _get_employees_by_age(Serializable requestData) {
		try {
			int[] ageRange = (int[]) requestData;
			List<Employee> responseData = new LinkedList<>();
			employees.getEmployeesByAge(ageRange[0], ageRange[1]).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable)responseData);
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _get_employees_by_salary(Serializable requestData) {
		try {
			int[] salaryRange = (int[]) requestData;
			List<Employee> responseData = new LinkedList<>();
			employees.getEmployeesBySalary(salaryRange[0], salaryRange[1]).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable)responseData);
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _get_employees_by_department(Serializable requestData) {
		try {
			String department = (String) requestData;
			List<Employee> responseData = new LinkedList<>();
			employees.getEmployeesByDepartment((String)requestData).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable)responseData);
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

	private Response _get_employees_by_department_and_salary(Serializable requestData) {
		try {
			HashMap<String, Integer[]> depSalaryInterval = (HashMap<String, Integer[]>)requestData;
			String department = depSalaryInterval.keySet().iterator().next();
			Integer[] salaryinterval = (Integer[]) depSalaryInterval.get(department);
			List<Employee> responseData = new LinkedList<>();
			employees.getEmployeesByDepartmentAndSalary(
					department,
					salaryinterval[0],
					salaryinterval[1]).forEach(responseData::add);
			return new Response(ResponseCode.OK, (Serializable)responseData);
		} catch (Exception ex) {
			return new Response(ResponseCode.WRONG_REQUEST_DATA, ex.getMessage());
		}
	}

}
