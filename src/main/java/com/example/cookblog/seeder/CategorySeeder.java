package com.example.cookblog.seeder;

import com.example.cookblog.model.Category;
import com.example.cookblog.repository.CategoryRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
class CategorySeeder implements Seeder {

    private final CategoryRepository categoryRepository;
    private final Faker faker;

    @Override
    public void seed(int objectsToSeed) {
        final Set<Category> categories = new HashSet<>();
        while (categories.size() < objectsToSeed) {
            final var category = createCategory();
            if (!categories.contains(category)) {
                categoryRepository.save(category);
                categories.add(category);
            }
        }
    }

    private Category createCategory() {
        return Category.builder()
                .name(faker.lorem().word())
                .build();
    }

}
