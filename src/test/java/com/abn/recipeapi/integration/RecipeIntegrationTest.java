package com.abn.recipeapi.integration;

import com.abn.recipeapi.enums.MeasureType;
import com.abn.recipeapi.exception.EntityNotFoundException;
import com.abn.recipeapi.model.IngredientDto;
import com.abn.recipeapi.model.RecipeDto;
import com.abn.recipeapi.model.RecipeIngredientDto;
import com.abn.recipeapi.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Base64Utils;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecipeIntegrationTest extends PostgresTestConfiguration{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    private final String basicAuthInfo;

    public RecipeIntegrationTest(
            @Value("${spring.security.user.name}") String username,
            @Value("${spring.security.user.password}") String password
    ) {
        this.basicAuthInfo = "Basic " + Base64Utils.encodeToString((username + ":" +password).getBytes());
    }

    private RecipeDto saveRecipe(RecipeDto dto) throws Exception {
        String recipe = mapper.writeValueAsString(dto);
        ResultActions result = mockMvc.perform(post("/recipes/")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo)
                        .contentType("application/json")
                        .content(recipe))
                .andExpect(status().isCreated());

        return mapper.readValue(result.andReturn().getResponse().getContentAsString(), RecipeDto.class);
    }

    @Test
    void contextLoads() {
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenARecipe_whenAddRecipe_thenReturnSuccess() throws Exception {
        String recipe = mapper.writeValueAsString(TestUtil.createRecipeVegetarian());

        mockMvc.perform(post("/recipes/")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo)
                        .contentType("application/json")
                        .content(recipe))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("boiled egg"))
                .andExpect(jsonPath("$.ingredients.length()").value(2));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenAAbsentIngredient_whenAddRecipe_thenReturnNotFound() throws Exception {
        String recipe = mapper.writeValueAsString(TestUtil.createRecipeWithInvalidIngredient());

        mockMvc.perform(post("/recipes/")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo)
                        .contentType("application/json")
                        .content(recipe))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals(
                        "Ingredient with ID: 100 not found.",
                        result.getResolvedException().getMessage())
                );
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenARecipe_whenUpdateRecipe_thenReturnSuccess() throws Exception {
        RecipeDto savedRecipe = saveRecipe(TestUtil.createRecipeToUpdate());

        savedRecipe.setTitle("Ribs with potatoes and pineapple with Mushroom");

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setId(11L);
        RecipeIngredientDto ri1 = new RecipeIngredientDto();
        ri1.setAmount(4);
        ri1.setIngredient(ingredient1);
        ri1.setMeasureType(MeasureType.UNIT.name());
        savedRecipe.setRecipeIngredients(List.of(ri1));

        mockMvc.perform(put("/recipes/" + savedRecipe.getId())
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(savedRecipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Ribs with potatoes and pineapple with Mushroom"))
                .andExpect(jsonPath("$.ingredients.length()").value(1));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenAAbsentIngredient_whenUpdateRecipe_thenNotFound() throws Exception {
        RecipeDto savedRecipe = saveRecipe(TestUtil.createRecipeToUpdate());

        savedRecipe.setTitle("Ribs with potatoes and pineapple with Mushroom");

        IngredientDto ingredient1 = new IngredientDto();
        ingredient1.setId(100L);
        RecipeIngredientDto ri1 = new RecipeIngredientDto();
        ri1.setAmount(4);
        ri1.setIngredient(ingredient1);
        ri1.setMeasureType(MeasureType.UNIT.name());
        savedRecipe.setRecipeIngredients(List.of(ri1));

        mockMvc.perform(put("/recipes/" + savedRecipe.getId())
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(savedRecipe)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals(
                        "Ingredient with ID: 100 not found.",
                        result.getResolvedException().getMessage())
                );
    }

    @Test
    public void givenAAbsentRecipe_whenUpdateRecipe_thenNotFound() throws Exception {
        mockMvc.perform(put("/recipes/100")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(new RecipeDto())))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals(
                        "Recipe with ID: 100 not found.",
                        result.getResolvedException().getMessage())
                );
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenARecipe_whenDeleteRecipe_thenReturnSuccess() throws Exception {
        RecipeDto savedRecipe = saveRecipe(TestUtil.createRecipeToUpdate());

        mockMvc.perform(delete("/recipes/" + savedRecipe.getId())
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenAAbsentRecipe_whenDeleteRecipe_thenNotFound() throws Exception {
        mockMvc.perform(delete("/recipes/100")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals(
                        "Recipe with ID: 100 not found.",
                        result.getResolvedException().getMessage())
                );
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenAValidID_whenFindById_thenReturnSuccess() throws Exception {
        RecipeDto savedRecipe = saveRecipe(TestUtil.createRecipeToUpdate());

        mockMvc.perform(get("/recipes/" + savedRecipe.getId())
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Ribs with potatoes and pineapple"))
                .andExpect(jsonPath("$.ingredients.length()").value(3));
    }

    @Test
    public void givenAAbsentId_whenFindById_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/recipes/100")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals(
                        "Recipe with ID: 100 not found.",
                        result.getResolvedException().getMessage())
                );
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenCriteriaNonVegetarianIngredientOut_whenSearchByCriteria_thenReturnSuccess() throws Exception {
        saveRecipe(TestUtil.createRecipeNonVegetarian());
        saveRecipe(TestUtil.createRecipeVegetarian());
        saveRecipe(TestUtil.createRecipeNonVegetarian2());

        ResultActions result = mockMvc.perform(
                get("/recipes/search?type=Non-Vegetarian&ingredientOut=Salt")
                        .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0]['ingredients'][0]['ingredient']['name']")
                        .value("Potato"))
                .andExpect(jsonPath("$.[0]['ingredients'][1]['ingredient']['name']")
                        .value("Ribs"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenCriteriaVegetarianIngredientIn_whenSearchByCriteria_thenReturnSuccess() throws Exception {
        saveRecipe(TestUtil.createRecipeNonVegetarian());
        saveRecipe(TestUtil.createRecipeVegetarian());
        saveRecipe(TestUtil.createRecipeNonVegetarian2());

        ResultActions result = mockMvc.perform(
                get("/recipes/search?type=Vegetarian&ingredientIn=Salt")
                                .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0]['ingredients'][0]['ingredient']['name']")
                        .value("Salt"))
                .andExpect(jsonPath("$.[0]['ingredients'][1]['ingredient']['name']")
                        .value("Egg"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenCriteriaIngredientOutIngredientIn_whenSearchByCriteria_thenReturnSuccess() throws Exception {
        saveRecipe(TestUtil.createRecipeNonVegetarian());
        saveRecipe(TestUtil.createRecipeVegetarian());
        saveRecipe(TestUtil.createRecipeNonVegetarian2());

        ResultActions result = mockMvc.perform(
                get("/recipes/search?ingredientOut=Chicken,Potato&ingredientIn=Salt")
                                .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0]['ingredients'][0]['ingredient']['name']")
                        .value("Salt"))
                .andExpect(jsonPath("$.[0]['ingredients'][1]['ingredient']['name']")
                        .value("Egg"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenCriteriaNonVegetarianIngredientIn_whenSearchByCriteria_thenReturnSuccess() throws Exception {
        saveRecipe(TestUtil.createRecipeNonVegetarian());
        saveRecipe(TestUtil.createRecipeVegetarian());
        saveRecipe(TestUtil.createRecipeNonVegetarian2());

        ResultActions result = mockMvc.perform(
                get("/recipes/search?instruction=stove&ingredientIn=Salt")
                                .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0]['ingredients'][0]['ingredient']['name']")
                        .value("Salt"))
                .andExpect(jsonPath("$.[0]['ingredients'][1]['ingredient']['name']")
                        .value("Egg"));
    }

    @Test
    @Sql("/scripts/init.sql")
    public void givenAllCriteria_whenSearchByCriteria_thenReturnSuccess() throws Exception {
        saveRecipe(TestUtil.createRecipeNonVegetarian());
        saveRecipe(TestUtil.createRecipeVegetarian());
        saveRecipe(TestUtil.createRecipeNonVegetarian2());

        ResultActions result = mockMvc.perform(
                get("/recipes/search?type=Non-Vegetarian&instruction=owen&serving=2" +
                        "&ingredientIn=Salt,Potato&ingredientOut=Egg")
                                .header(HttpHeaders.AUTHORIZATION, basicAuthInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0]['serving']").value("2"))
                .andExpect(jsonPath("$.[0]['ingredients'][0]['ingredient']['name']")
                        .value("Salt"))
                .andExpect(jsonPath("$.[0]['ingredients'][1]['ingredient']['name']")
                        .value("Chicken"));
    }
}
