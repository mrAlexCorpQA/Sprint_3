import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersEndPointTest {

    Order samokatOrder;

    private final List<String> samokatColor;

    public OrdersEndPointTest(List<String> samokatColor) {
        this.samokatColor = samokatColor;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] colorData() {
        return new Object[][]{
                {List.of("\"BLACK\"")},
                {List.of("\"GREY\"")},
                {List.of("\"BLACK, GREY\"")},
                {List.of("")},
        };
    }

    @Before
    public void setUp() {
        samokatOrder = new Order();
    }

    //Проверка создания нового заказа с разными параметрами цвета
    @Test
    @DisplayName("Create new order")
    @Description("New order creation test for /api/v1/orders")
    public void createNewOrderAPITestWithParameters() {
        samokatOrder.setSamokatColor(samokatColor);
        Response newOrderResponse = samokatOrder.createNewOrderAPIRequest(samokatOrder.createFullTestDataForNewOrder());
        newOrderResponse.then().assertThat().body("track", notNullValue())
                .and().statusCode(201);
    }

}