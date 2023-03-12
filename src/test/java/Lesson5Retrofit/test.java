package Lesson5Retrofit;

import com.github.javafaker.Faker;
import lesson5.api.CategoryService;
import lesson5.api.ProductService;
import lesson5.dto.GetCategoryResponse;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class test {
           static ProductService productService;
        Product product = null;
        Faker faker = new Faker();
        int id;

        @BeforeAll
        static void beforeAll() {
            productService = RetrofitUtils.getRetrofit()
                    .create(ProductService.class);
        }

        @BeforeEach
        void setUp() {
            product = new Product()
                    .withTitle(faker.food().ingredient())
                    .withCategoryTitle("Food")
                    .withPrice((int) (Math.random() * 10000));
        }

        @Test
        void createProductInFoodCategoryTest() throws IOException {
            Response<Product> response = productService.createProduct(product)
                    .execute();
            id =  response.body().getId();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }

        @SneakyThrows
        @AfterEach
        void tearDown() {
            Response<ResponseBody> response = productService.deleteProduct(id).execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }


    static CategoryService categoryService;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));


    }
    }


