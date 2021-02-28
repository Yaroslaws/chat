package code.chat.service;

import code.chat.Repo.FoodRepo;
import code.chat.domain.Food;
import code.chat.domain.Message;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepo foodRepo;

    public FoodService(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    public List<Food> getFood() {
        return foodRepo.findAll();
    };

}
