package com.zuzhi.order.web.rest;

import com.zuzhi.order.OrderApp;

import com.zuzhi.order.domain.UserMenuRel;
import com.zuzhi.order.repository.UserMenuRelRepository;
import com.zuzhi.order.service.UserMenuRelService;
import com.zuzhi.order.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.zuzhi.order.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserMenuRelResource REST controller.
 *
 * @see UserMenuRelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApp.class)
public class UserMenuRelResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_MENU_ID = 1L;
    private static final Long UPDATED_MENU_ID = 2L;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MENU_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MENU_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_GMT_CREATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GMT_CREATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_GMT_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GMT_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserMenuRelRepository userMenuRelRepository;

    @Autowired
    private UserMenuRelService userMenuRelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMenuRelMockMvc;

    private UserMenuRel userMenuRel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserMenuRelResource userMenuRelResource = new UserMenuRelResource(userMenuRelService);
        this.restUserMenuRelMockMvc = MockMvcBuilders.standaloneSetup(userMenuRelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMenuRel createEntity(EntityManager em) {
        UserMenuRel userMenuRel = new UserMenuRel()
            .userId(DEFAULT_USER_ID)
            .menuId(DEFAULT_MENU_ID)
            .userName(DEFAULT_USER_NAME)
            .menuName(DEFAULT_MENU_NAME)
            .gmtCreate(DEFAULT_GMT_CREATE)
            .gmtModified(DEFAULT_GMT_MODIFIED);
        return userMenuRel;
    }

    @Before
    public void initTest() {
        userMenuRel = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserMenuRel() throws Exception {
        int databaseSizeBeforeCreate = userMenuRelRepository.findAll().size();

        // Create the UserMenuRel
        restUserMenuRelMockMvc.perform(post("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMenuRel)))
            .andExpect(status().isCreated());

        // Validate the UserMenuRel in the database
        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeCreate + 1);
        UserMenuRel testUserMenuRel = userMenuRelList.get(userMenuRelList.size() - 1);
        assertThat(testUserMenuRel.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserMenuRel.getMenuId()).isEqualTo(DEFAULT_MENU_ID);
        assertThat(testUserMenuRel.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserMenuRel.getMenuName()).isEqualTo(DEFAULT_MENU_NAME);
        assertThat(testUserMenuRel.getGmtCreate()).isEqualTo(DEFAULT_GMT_CREATE);
        assertThat(testUserMenuRel.getGmtModified()).isEqualTo(DEFAULT_GMT_MODIFIED);
    }

    @Test
    @Transactional
    public void createUserMenuRelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userMenuRelRepository.findAll().size();

        // Create the UserMenuRel with an existing ID
        userMenuRel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMenuRelMockMvc.perform(post("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMenuRel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userMenuRelRepository.findAll().size();
        // set the field null
        userMenuRel.setUserId(null);

        // Create the UserMenuRel, which fails.

        restUserMenuRelMockMvc.perform(post("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMenuRel)))
            .andExpect(status().isBadRequest());

        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMenuIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userMenuRelRepository.findAll().size();
        // set the field null
        userMenuRel.setMenuId(null);

        // Create the UserMenuRel, which fails.

        restUserMenuRelMockMvc.perform(post("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMenuRel)))
            .andExpect(status().isBadRequest());

        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGmtCreateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userMenuRelRepository.findAll().size();
        // set the field null
        userMenuRel.setGmtCreate(null);

        // Create the UserMenuRel, which fails.

        restUserMenuRelMockMvc.perform(post("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMenuRel)))
            .andExpect(status().isBadRequest());

        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGmtModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userMenuRelRepository.findAll().size();
        // set the field null
        userMenuRel.setGmtModified(null);

        // Create the UserMenuRel, which fails.

        restUserMenuRelMockMvc.perform(post("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMenuRel)))
            .andExpect(status().isBadRequest());

        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserMenuRels() throws Exception {
        // Initialize the database
        userMenuRelRepository.saveAndFlush(userMenuRel);

        // Get all the userMenuRelList
        restUserMenuRelMockMvc.perform(get("/api/user-menu-rels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMenuRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].menuId").value(hasItem(DEFAULT_MENU_ID.intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].menuName").value(hasItem(DEFAULT_MENU_NAME.toString())))
            .andExpect(jsonPath("$.[*].gmtCreate").value(hasItem(sameInstant(DEFAULT_GMT_CREATE))))
            .andExpect(jsonPath("$.[*].gmtModified").value(hasItem(sameInstant(DEFAULT_GMT_MODIFIED))));
    }

    @Test
    @Transactional
    public void getUserMenuRel() throws Exception {
        // Initialize the database
        userMenuRelRepository.saveAndFlush(userMenuRel);

        // Get the userMenuRel
        restUserMenuRelMockMvc.perform(get("/api/user-menu-rels/{id}", userMenuRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userMenuRel.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.menuId").value(DEFAULT_MENU_ID.intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.menuName").value(DEFAULT_MENU_NAME.toString()))
            .andExpect(jsonPath("$.gmtCreate").value(sameInstant(DEFAULT_GMT_CREATE)))
            .andExpect(jsonPath("$.gmtModified").value(sameInstant(DEFAULT_GMT_MODIFIED)));
    }

    @Test
    @Transactional
    public void getNonExistingUserMenuRel() throws Exception {
        // Get the userMenuRel
        restUserMenuRelMockMvc.perform(get("/api/user-menu-rels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserMenuRel() throws Exception {
        // Initialize the database
        userMenuRelService.save(userMenuRel);

        int databaseSizeBeforeUpdate = userMenuRelRepository.findAll().size();

        // Update the userMenuRel
        UserMenuRel updatedUserMenuRel = userMenuRelRepository.findOne(userMenuRel.getId());
        updatedUserMenuRel
            .userId(UPDATED_USER_ID)
            .menuId(UPDATED_MENU_ID)
            .userName(UPDATED_USER_NAME)
            .menuName(UPDATED_MENU_NAME)
            .gmtCreate(UPDATED_GMT_CREATE)
            .gmtModified(UPDATED_GMT_MODIFIED);

        restUserMenuRelMockMvc.perform(put("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserMenuRel)))
            .andExpect(status().isOk());

        // Validate the UserMenuRel in the database
        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeUpdate);
        UserMenuRel testUserMenuRel = userMenuRelList.get(userMenuRelList.size() - 1);
        assertThat(testUserMenuRel.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserMenuRel.getMenuId()).isEqualTo(UPDATED_MENU_ID);
        assertThat(testUserMenuRel.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserMenuRel.getMenuName()).isEqualTo(UPDATED_MENU_NAME);
        assertThat(testUserMenuRel.getGmtCreate()).isEqualTo(UPDATED_GMT_CREATE);
        assertThat(testUserMenuRel.getGmtModified()).isEqualTo(UPDATED_GMT_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingUserMenuRel() throws Exception {
        int databaseSizeBeforeUpdate = userMenuRelRepository.findAll().size();

        // Create the UserMenuRel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserMenuRelMockMvc.perform(put("/api/user-menu-rels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMenuRel)))
            .andExpect(status().isCreated());

        // Validate the UserMenuRel in the database
        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserMenuRel() throws Exception {
        // Initialize the database
        userMenuRelService.save(userMenuRel);

        int databaseSizeBeforeDelete = userMenuRelRepository.findAll().size();

        // Get the userMenuRel
        restUserMenuRelMockMvc.perform(delete("/api/user-menu-rels/{id}", userMenuRel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserMenuRel> userMenuRelList = userMenuRelRepository.findAll();
        assertThat(userMenuRelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMenuRel.class);
        UserMenuRel userMenuRel1 = new UserMenuRel();
        userMenuRel1.setId(1L);
        UserMenuRel userMenuRel2 = new UserMenuRel();
        userMenuRel2.setId(userMenuRel1.getId());
        assertThat(userMenuRel1).isEqualTo(userMenuRel2);
        userMenuRel2.setId(2L);
        assertThat(userMenuRel1).isNotEqualTo(userMenuRel2);
        userMenuRel1.setId(null);
        assertThat(userMenuRel1).isNotEqualTo(userMenuRel2);
    }
}
