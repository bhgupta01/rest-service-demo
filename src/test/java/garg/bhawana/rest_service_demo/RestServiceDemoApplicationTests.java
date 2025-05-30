package garg.bhawana.rest_service_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class RestServiceDemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getTask() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks").param("description", "dummy task in test"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]['description']").value("dummy task in test"));
	}

	@Test
	void getTaskInvalidPath() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/task").param("description", "dummy task in test"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void getTaskMissingRequiredParam() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
