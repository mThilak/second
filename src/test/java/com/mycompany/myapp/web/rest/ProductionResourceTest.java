package com.mycompany.myapp.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Production;
import com.mycompany.myapp.repository.ProductionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductionResource REST controller.
 *
 * @see ProductionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ProductionResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_YEAR_ESTABLISHED = 0;
    private static final Integer UPDATED_YEAR_ESTABLISHED = 1;
    

    @Inject
    private ProductionRepository productionRepository;

    private MockMvc restProductionMockMvc;

    private Production production;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductionResource productionResource = new ProductionResource();
        ReflectionTestUtils.setField(productionResource, "productionRepository", productionRepository);
        this.restProductionMockMvc = MockMvcBuilders.standaloneSetup(productionResource).build();
    }

    @Before
    public void initTest() {
        production = new Production();
        production.setName(DEFAULT_NAME);
        production.setYear_established(DEFAULT_YEAR_ESTABLISHED);
    }

    @Test
    @Transactional
    public void createProduction() throws Exception {
        // Validate the database is empty
        assertThat(productionRepository.findAll()).hasSize(0);

        // Create the Production
        restProductionMockMvc.perform(post("/app/rest/productions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(production)))
                .andExpect(status().isOk());

        // Validate the Production in the database
        List<Production> productions = productionRepository.findAll();
        assertThat(productions).hasSize(1);
        Production testProduction = productions.iterator().next();
        assertThat(testProduction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduction.getYear_established()).isEqualTo(DEFAULT_YEAR_ESTABLISHED);
    }

    @Test
    @Transactional
    public void getAllProductions() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productions
        restProductionMockMvc.perform(get("/app/rest/productions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(production.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].year_established").value(DEFAULT_YEAR_ESTABLISHED));
    }

    @Test
    @Transactional
    public void getProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get the production
        restProductionMockMvc.perform(get("/app/rest/productions/{id}", production.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(production.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.year_established").value(DEFAULT_YEAR_ESTABLISHED));
    }

    @Test
    @Transactional
    public void getNonExistingProduction() throws Exception {
        // Get the production
        restProductionMockMvc.perform(get("/app/rest/productions/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Update the production
        production.setName(UPDATED_NAME);
        production.setYear_established(UPDATED_YEAR_ESTABLISHED);
        restProductionMockMvc.perform(post("/app/rest/productions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(production)))
                .andExpect(status().isOk());

        // Validate the Production in the database
        List<Production> productions = productionRepository.findAll();
        assertThat(productions).hasSize(1);
        Production testProduction = productions.iterator().next();
        assertThat(testProduction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduction.getYear_established()).isEqualTo(UPDATED_YEAR_ESTABLISHED);;
    }

    @Test
    @Transactional
    public void deleteProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get the production
        restProductionMockMvc.perform(delete("/app/rest/productions/{id}", production.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Production> productions = productionRepository.findAll();
        assertThat(productions).hasSize(0);
    }
}
