package code.chat.service;

import code.chat.Repo.FoodRepo;
import code.chat.domain.Food;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService implements GraphQLQueryResolver {

    private final FoodRepo foodRepo;

    public FoodService(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    public List<Food> getAllFood() {
        return foodRepo.findAll();
    };

}
