import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderList {

    //URL адрес endpoint для получения списка заказов
    String orderListEndpointURL = "https://qa-scooter.praktikum-services.ru/api/v1/orders";

    //Служебный метод для отправки запроса на получение списка заказов.
    @Step("Create new order list request sending")
    public Response orderListAPIRequest() {
        Response OrderListResponse = given()
                .when()
                .get(orderListEndpointURL);
        return OrderListResponse;
    }

}