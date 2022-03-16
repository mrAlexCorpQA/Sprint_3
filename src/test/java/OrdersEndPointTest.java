import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersEndPointTest {

    Order samokatOrder;

    private final String[] samokatColor;

    public OrdersEndPointTest(String[] samokatColor) {
        this.samokatColor = samokatColor;
    }

    @Parameterized.Parameters
    public static Object[][] colorData() {
        return new Object[][]{
                {new String[]{"\"BLACK\""}},
                {new String[]{"\"GREY\""}},
                {new String[]{"\"BLACK, GREY\""}},
                {new String[]{""}},
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
        setUp();
        samokatOrder.setSamokatColor(samokatColor);
        Response newOrderResponse = samokatOrder.createNewOrderAPIRequest(samokatOrder.createFullTestDataForNewOrder());
        newOrderResponse.then().assertThat().body("track", notNullValue())
                .and().statusCode(201);
    }

}