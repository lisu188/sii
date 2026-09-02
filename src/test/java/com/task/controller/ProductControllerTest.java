package com.task.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void completeRestFlow() throws Exception {
    mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(3))
        .andExpect(jsonPath("$[0].productName").value("Laptop"));

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productId\":\"4\",\"productName\":\"Tablet\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/products/4"))
        .andExpect(jsonPath("$.productName").value("Tablet"));

    mockMvc.perform(get("/products/4"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productName").value("Tablet"));

    mockMvc.perform(put("/products/4")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productName\":\"Tablet Pro\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value("4"))
        .andExpect(jsonPath("$.productName").value("Tablet Pro"));

    mockMvc.perform(put("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"productId\":\"1\",\"productName\":\"Gaming Laptop\"},{\"productId\":\"5\",\"productName\":\"Monitor\"}]"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productId\":\"4\",\"productName\":\"Duplicate\"}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.title").value("Product already exists"));

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"productName\":\" \"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("Invalid request"));

    mockMvc.perform(delete("/products/2"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/products/2"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.title").value("Product not found"));
  }
}
