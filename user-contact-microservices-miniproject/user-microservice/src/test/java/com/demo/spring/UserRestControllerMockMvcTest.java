package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.demo.spring.entity.User;
import com.demo.spring.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserRestControllerMockMvcTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	UserRepository userRepo;

	@Test
	public void testSaveUserSuccess() throws Exception {
		User user = new User(200, "Ramesh");
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user);

		when(userRepo.existsById(200)).thenReturn(false);

		mvc.perform(post("/user/save").content(userJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User saved"));
	}
	
	@Test
	public void testSaveUserFailure() throws Exception {
		User user = new User(10, "Ramesh");
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user);

		when(userRepo.existsById(10)).thenReturn(true);

		mvc.perform(post("/user/save").content(userJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User already exists.."));
	}
	

	@Test
	public void testFindUserSuccess() throws Exception {
		when(userRepo.findById(10)).thenReturn(Optional.of(new User(10, "Ramesh")));

		mvc.perform(get("/user/10")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.userName").value("Ramesh"));
		
	}
	
	@Test
	public void testFindUserFailure() throws Exception {
		when(userRepo.findById(1300)).thenReturn(Optional.empty());

		mvc.perform(get("/user/1300")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User not found.."));
		
	}
	
	@Test
    public void testUserList() throws Exception {
        List<User> list = new ArrayList<>();
        list.add(new User(10, "Ramesh"));
        when(userRepo.findAll()).thenReturn(list);
        mvc.perform(get("/user/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
	
	
	@Test
	public void testDeleteSuccess() throws Exception {
		when(userRepo.existsById(30)).thenReturn(true);
		mvc.perform(delete("/user/delete/30").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User Deleted"));
	}
	
	
	@Test
	public void testDeleteFailure() throws Exception {

		when(userRepo.existsById(1400)).thenReturn(false);
		mvc.perform(delete("/user/delete/1400").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("User not found.."));
	}
	
	@Test
	public void testupdateUserSuccess() throws Exception {
		User user = new User(10,"Rajesh");
		ObjectMapper mapper = new ObjectMapper();
		String userJson=mapper.writeValueAsString(user);
		
		when(userRepo.existsById(10)).thenReturn(true);
		
		mvc.perform(put("/user/update").content(userJson).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("user Updated.."));
		
	}
	
	@Test
	public void testupdateUserFailure() throws Exception {
		User user = new User(10,"Rajesh");
		ObjectMapper mapper = new ObjectMapper();
		String userJson=mapper.writeValueAsString(user);
		
		when(userRepo.existsById(10)).thenReturn(false);
		
		mvc.perform(put("/user/update").content(userJson).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.status").value("User not found.."));
		
	}
	
	
}
