package code.chat.service;

import code.chat.Repo.FoodRepo;
import code.chat.domain.Food;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FoodService implements GraphQLQueryResolver {

    @Autowired
    private  FoodRepo foodRepo;

    public Food getFood(Long id) {
        return foodRepo.getOne(id);
    };

}
