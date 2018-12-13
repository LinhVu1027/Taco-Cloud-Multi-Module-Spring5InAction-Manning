package com.cloud.tacos.api;

import com.cloud.tacos.assembler.TacoResourceAssembler;
import com.cloud.tacos.data.TacoRepository;
import com.cloud.tacos.domain.Taco;
import com.cloud.tacos.resource.TacoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/design", produces="application/json")
@CrossOrigin(origins="*")
public class DesignTacoController {

    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {                 //<3>
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }

//    @GetMapping("/{id}")
//    public Taco tacoById(@PathVariable("id") Long id) {
//        Optional<Taco> optTaco = tacoRepo.findById(id);
//        if (optTaco.isPresent()) {
//            return optTaco.get();
//        }
//        return null;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>((Taco) null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

    @GetMapping("/recenths")
    public Resources<Resource<Taco>> recentTacoshs() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        Resources<Resource<Taco>> recentResources = Resources.wrap(tacos);

//        recentResources.add(
//                new Link("http://localhost:8080/design/recenths", "recents")
//        );

//        recentResources.add(
//                ControllerLinkBuilder.linkTo(DesignTacoController.class)
//                        .slash("recenths")
//                        .withRel("recents")
//        );

        recentResources.add(
                ControllerLinkBuilder
                        .linkTo(ControllerLinkBuilder.methodOn(DesignTacoController.class).recentTacoshs())
                        .withRel("recents")
        );

        return recentResources;
    }

    @GetMapping("/recenth")
    public Resources<TacoResource> recentTacosh() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
        Resources<TacoResource> recentResources = new Resources<>(tacoResources);

        recentResources.add(
                ControllerLinkBuilder
                        .linkTo(ControllerLinkBuilder.methodOn(DesignTacoController.class).recentTacosh())
                        .withRel("recents")
        );

        return recentResources;
    }

}

