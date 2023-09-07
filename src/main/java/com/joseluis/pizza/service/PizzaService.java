package com.joseluis.pizza.service;

import com.joseluis.pizza.persitence.entity.PizzaEntity;
import com.joseluis.pizza.persitence.repository.PizzaPagSortRepository;
import com.joseluis.pizza.persitence.repository.PizzaRepository;
import com.joseluis.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {
    //private final JdbcTemplate jdbcTemplate;  Nos ayuda a poner un query
    // o podemos utilizar Repository

    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public Page<PizzaEntity> getAll(int page, int element){
        Pageable pageRequest = PageRequest.of(page,element);
        // JdbcTemplate (con querys) --> return this.jdbcTemplate.query("SELECT * FROM pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
        return this.pizzaPagSortRepository.findAll(pageRequest);
    }

    //ES un metodo que utiliza un Query Method
    public Page<PizzaEntity> getAvailable(int page, int element, String sortBy, String sortDirecction){
        //this.pizzaRepository.countByVeganTrue();
        //return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirecction),sortBy);
        Pageable pageRequest = PageRequest.of(page,element, sort);
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }
    //ES un metodo que utiliza un Query Method
    public PizzaEntity getByName(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(()-> new RuntimeException("La pizza no existe"));
    }
    public List<PizzaEntity> getWith(String ingredinet){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredinet);
    }
    public List<PizzaEntity> getNotWith(String ingredinet){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredinet);
    }
    public List<PizzaEntity> getCheapest(int price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }
    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
    }

    public void delete(int idPIzza){
        this.pizzaRepository.deleteById(idPIzza);
    }

    @Transactional //ACID
    public void updatePrice(UpdatePizzaPriceDto dto){
        this.pizzaRepository.updatePrice(dto);
    }


    public boolean exists(int idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }

}
