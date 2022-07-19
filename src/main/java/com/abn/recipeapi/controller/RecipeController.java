package com.abn.recipeapi.controller;

import com.abn.recipeapi.entity.Recipe;
import com.abn.recipeapi.model.RecipeDto;
import com.abn.recipeapi.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private ModelMapper modelMapper;
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> search(@RequestParam Map<String,String> criteria) {
        List<RecipeDto> listDto = this.recipeService.search(criteria)
                .stream().map( recipe ->
                        modelMapper.map(recipe, RecipeDto.class)
                ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> findById(@PathVariable Long id) {
        RecipeDto dto = modelMapper.map(this.recipeService.findById(id), RecipeDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/")
    public ResponseEntity<RecipeDto> addRecipe(@RequestBody RecipeDto recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        RecipeDto dto = modelMapper.map(this.recipeService.save(recipe), RecipeDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        RecipeDto dto = modelMapper.map(this.recipeService.update(id, recipe), RecipeDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.recipeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
