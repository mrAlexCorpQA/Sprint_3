import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListEndpointTest {

    OrderList orderList;

    @Before
    public void setUp() {
        orderList = new OrderList();
    }

    //Проверка получения нового списка заказов
    @Test
    @DisplayName("Get order list")
    @Description("Get order list test for /api/v1/orders endpoint")
    public void getOrderListAPITest() {
        setUp();
        Response orderListResponse = orderList.orderListAPIRequest();
        orderListResponse.then().assertThat().body("orders", notNullValue())
                .and().statusCode(200);
    }

}