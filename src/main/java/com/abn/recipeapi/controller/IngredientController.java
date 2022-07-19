package com.abn.recipeapi.controller;

import com.abn.recipeapi.entity.Ingredient;
import com.abn.recipeapi.model.IngredientDto;
import com.abn.recipeapi.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private ModelMapper modelMapper;
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> findById(@PathVariable Long id) {
        IngredientDto dto = modelMapper.map(this.ingredientService.findById(id), IngredientDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/")
    public ResponseEntity<List<IngredientDto>> findAll() {
        List<IngredientDto> listDto = this.ingredientService.findAll()
                .stream().map( ingredient ->
                    modelMapper.map(ingredient, IngredientDto.class)
                ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @PostMapping("/")
    public ResponseEntity<IngredientDto> create(@RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        IngredientDto dto = modelMapper.map(this.ingredientService.save(ingredient), IngredientDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> updateRecipe(@PathVariable Long id, @RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        IngredientDto dto = modelMapper.map(this.ingredientService.update(id, ingredient), IngredientDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.ingredientService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
