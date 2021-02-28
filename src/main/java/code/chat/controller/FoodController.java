package code.chat.controller;

import code.chat.Repo.MessageRepo;
import code.chat.domain.Food;
import code.chat.domain.Message;
import code.chat.service.FoodService;
import code.chat.service.MesService;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "graphql", produces = "application/json")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    @GraphQLQuery(name = "food", description = "Analysis history for signal")
    public List<Food> getFood() {
        return foodService.getFood();
    };

}
