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

import com.demo.spring.entity.Contact;
import com.demo.spring.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContactRestControllerMockMvcTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	ContactRepository contactRepo;

	@Test
	public void testSaveSuccess() throws Exception {
		Contact contact = new Contact(105, "Home", "Hyd", "55160", "Ram@gmail.com", 20);
		ObjectMapper mapper = new ObjectMapper();
		String contactJson = mapper.writeValueAsString(contact);

		when(contactRepo.existsById(105)).thenReturn(false);

		mvc.perform(post("/contact/save").content(contactJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Contact saved"));
	}

	@Test
	public void testSaveFailure() throws Exception {
		Contact contact = new Contact(106, "Home", "Blore", "55165", "Giri@gmail.com", 10);
		ObjectMapper mapper = new ObjectMapper();
		String contactJson = mapper.writeValueAsString(contact);

		when(contactRepo.existsById(106)).thenReturn(true);

		mvc.perform(post("/contact/save").content(contactJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Contact already exists..."));
	}

	@Test
	public void testList() throws Exception {
		List<Contact> list = new ArrayList<>();
		list.add(new Contact(105, "Home", "Hyd", "55160", "Ram@gmail.com", 20));
		when(contactRepo.findAll()).thenReturn(list);
		mvc.perform(get("/contact/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

	}

	@Test
	public void testupdateContactSuccess() throws Exception {
		Contact contact = new Contact(105, "Home", "Hyd", "55160", "Ram@gmail.com", 20);
		ObjectMapper mapper = new ObjectMapper();
		String contactJson = mapper.writeValueAsString(contact);

		when(contactRepo.existsById(105)).thenReturn(true);

		mvc.perform(put("/contact/update").content(contactJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Contact Updated.."));

	}

	@Test
	public void testupdateContactFailure() throws Exception {
		Contact contact = new Contact(105, "Home", "Hyd", "55160", "Ram@gmail.com", 20);
		ObjectMapper mapper = new ObjectMapper();
		String contactJson = mapper.writeValueAsString(contact);

		when(contactRepo.findById(105)).thenReturn(Optional.of(contact));

		mvc.perform(put("/contact/update").content(contactJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Contact doesnt exists.."));

	}

	@Test
	public void testListUserId() throws Exception {
		List<Contact> list = new ArrayList<>();
		list.add(new Contact(105, "Home", "Hyd", "55160", "Ram@gmail.com", 20));
		when(contactRepo.findAll()).thenReturn(list);
		mvc.perform(get("/contact/list/20")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

	}

	@Test
	public void testDeleteSuccess() throws Exception {
		when(contactRepo.existsById(103)).thenReturn(true);
		mvc.perform(delete("/contact/delete/103").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Contact Deleted"));
	}

	@Test
	public void testDeleteFailure() throws Exception {
		when(contactRepo.existsById(1060)).thenReturn(false);
		mvc.perform(delete("/contact/delete/1060").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Contact doesnt exists.."));
	}

	@Test
	public void testDeleteAll() throws Exception {
		when(contactRepo.deleteAllById(10)).thenReturn(10);
		mvc.perform(delete("/contact/deleteallcontact/10").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Contacts deleted of userId:10"));

	}

	@Test
	public void testfindByTagAndId() throws Exception {
		when(contactRepo.findContactByTagAndId("Home", 20))
				.thenReturn((new Contact(105, "Home", "Hyd", "55160", "Ram@gmail.com", 20)));

		mvc.perform(get("/contact/Home/20")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.city").value("Hyd"));
	}

}
