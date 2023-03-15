package com.javatechie.spring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.spring.api.controller.PersonController;
import com.javatechie.spring.api.entity.Person;
import com.javatechie.spring.api.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private PersonService personService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@WithMockUser(username = "javatechie", roles = {"Visualizar Colaboradores"})
	@Test
	public void testSavePerson() throws Exception {
		Person person = new Person(0, "user8", "CIVIL");
		String jsonRequest = mapper.writeValueAsString(person);

		mockMvc.perform(post("/api/v1/persons")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@WithAnonymousUser
	@Test
	public void testSavePersonAnonymousUser() throws Exception {
		Person person = new Person(0, "user8", "CIVIL");
		String jsonRequest = mapper.writeValueAsString(person);

		MvcResult result = mockMvc
				.perform(post("/api/v1/persons")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

		assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());

	}

	@WithMockUser("/javatechie-1")
	@Test
	public void testGetPersons() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/persons"))
				.andExpect(status().isOk())
				.andReturn();

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
}
