package com.joseluis.pizza.web.controller;

import com.joseluis.pizza.persitence.entity.PizzaEntity;
import com.joseluis.pizza.service.PizzaService;
import com.joseluis.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "8") int element){
        return ResponseEntity.ok(this.pizzaService.getAll(page,element));
    }


    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> get(@PathVariable  Integer idPizza){
        //return ResponseEntity.ok(this.pizzaService.get(idPizza));
        return  this.pizzaService.exists(idPizza) ?
                ResponseEntity.ok(this.pizzaService.get(idPizza))
                : ResponseEntity.notFound().build();
    }
    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getAvailable(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8")int element,
                                                          @RequestParam(defaultValue = "price")String sortBy,
                                                          @RequestParam(defaultValue = "ASC") String sortDirecction
    ){
        return  ResponseEntity.ok(this.pizzaService.getAvailable(page,element,sortBy,sortDirecction));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name){
        return  ResponseEntity.ok(this.pizzaService.getByName(name));
    }
    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient){
        return  ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }
    @GetMapping("/nowith/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getNotWith(@PathVariable String ingredient){
        return  ResponseEntity.ok(this.pizzaService.getNotWith(ingredient));
    }
    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable int price){
        return  ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }

    @PostMapping
    public ResponseEntity<?> Add(@RequestBody PizzaEntity pizza){

        if (pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }

        // HTTP CODE: 400
        return ResponseEntity.status(HttpStatus.CONFLICT).body("La Pizza ya Existe!");
        //return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody PizzaEntity t){
        try {
            if (t.getIdPizza() != null || this.pizzaService.exists(t.getIdPizza())) {
                return ResponseEntity.ok(this.pizzaService.save(t));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Pizza no Existe!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<?> delete(@PathVariable int idPizza){
        try {
            if (this.pizzaService.exists(idPizza)) {
                this.pizzaService.delete(idPizza);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La Pizza no Existe!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/price")
    public ResponseEntity<?> updatePrice(@RequestBody UpdatePizzaPriceDto dto){
        if(this.pizzaService.exists(dto.getPizzaId())){
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
