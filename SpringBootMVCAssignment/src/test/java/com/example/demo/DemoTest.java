package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.controller.EmployeeController;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.EmployeeEntity;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble;

@WebMvcTest(EmployeeController.class)
@ActiveProfiles("test")
public class DemoTest {
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	EmployeeService employeeService;

	@Test
	public void get_allEmployees_returnsOkWithListOfEmployees() throws Exception {

		List<EmployeeEntity> employeeList = new ArrayList<>();
		EmployeeEntity employee1= new EmployeeEntity(1, "Pankaja",123456789);
		EmployeeEntity employee2 = new EmployeeEntity(2, "Radha",987654321);
		employeeList.add(employee1);
		employeeList.add(employee2);

		Mockito.when(employeeService.getAllEmployees()).thenReturn(employeeList);

		mockMvc.perform(MockMvcRequestBuilders.get("/demo/employees").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].name", is("Pankaja")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Radha")));
	}

	@Test
	public void post_createsNewEmployee_andReturnsObjWith201() throws Exception {
		EmployeeEntity employee= new EmployeeEntity(1, "Pankaja",123456789);

		Mockito.when(employeeService.createEmployee(Mockito.any(EmployeeEntity.class))).thenReturn(employee);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/demo/employees/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(employee));

		mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(employee)));
	}


	@Test
	public void put_updatesAndReturnsUpdatedObjWith202() throws Exception {
		EmployeeEntity employee = new EmployeeEntity(1,"Pankaja",123456789);

		Mockito.when(employeeService.updateEmployee(1,employee)).thenReturn(employee);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/demo/employees/update/1", employee).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(this.mapper.writeValueAsBytes(employee));

		mockMvc.perform(builder).andExpect(status().isAccepted()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(employee)));
	}

	@Test
	public void delete_delete_Returns204Status() throws Exception {
		int id=1;

		EmployeeService serviceSpy = Mockito.spy(employeeService);
		Mockito.doNothing().when(serviceSpy).deleteEmployee(id);

		mockMvc.perform(MockMvcRequestBuilders.delete("/demo/employees/delete/1")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(employeeService, times(1)).deleteEmployee(id);
	}

	@Test
	public void get_employeeById_Returns200Status() throws Exception {
		Optional<EmployeeEntity> employee = Optional.ofNullable(new EmployeeEntity(1,"Pankaja",123456789));

		Mockito.when(employeeService.getEmployeeById(1)).thenReturn(employee);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/demo/employees/1").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pankaja"));
	}


}
