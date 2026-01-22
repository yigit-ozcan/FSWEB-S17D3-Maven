package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    public KangarooController(){
        this.kangaroos = new HashMap<>();
    }

    @GetMapping
    public Collection<Kangaroo> getAll() {
        return kangaroos.values();
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.get(id);

        if (kangaroo == null) {
            throw new ZooException(
                    "Kangaroo not found with id: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        return kangaroo;
    }

    @PostMapping
    public Kangaroo addKangaroo(@RequestBody Kangaroo kangaroo) {

        if (kangaroo.getId() == 0 ||
                kangaroo.getName() == null ||
                kangaroo.getGender() == null) {

            throw new ZooException(
                    "Invalid kangaroo data",
                    HttpStatus.BAD_REQUEST
            );
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }


    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable int id, @RequestBody Kangaroo kangaroo) {
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo deleteKangaroo(@PathVariable int id) {
        return kangaroos.remove(id);
    }
}
