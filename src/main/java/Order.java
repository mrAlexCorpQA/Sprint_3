import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Order {

    //URL адрес endpoint для создания нового заказа
    String createNewOrderEndpointURL = "https://qa-scooter.praktikum-services.ru/api/v1/orders";

    //--Параметры для заказа--
    //Введите имя
    String clientFirstName = "Петр";
    //Введите фамилию
    String clientLastName = "Петров";
    //Введите адрес доставки
    String clientAdress = "г.Москва, ул. Усачева, 19а, кв 25";
    //Введите станцию метро
    String clientStationName = "4";
    //Введите телефон клиента
    String clientPhoneNumber = "89525224415";
    //Введите количество дней аренды (любое положительное число)
    int rentTime = 3;
    //Введите любое положительное число, которое будет прибавлено к дате доставки (по умолчанию дата доставки = текущий день). Если дата заказа должна быть текущей - введите "0"
    int numberDaysToPlus = 0;
    //Служебные данные для получения текущей даты и расчета новой даты доставки ("Когда привезти самокат")
    Calendar today = Calendar.getInstance();
    String samokatDeliveryDate = samokatDeliveryDateCounting(today, numberDaysToPlus);
    //Введите комментарий для курьера
    String clientCommentText = "Код домофона 15к5552";
    //Цвет самоката (добавить/изменить новые цвета можно в тесте OrdersEndPointTest (используется параметризация)).
    List<String> samokatColor;

    public void setSamokatColor(List<String> samokatColor) {
        this.samokatColor = samokatColor;
    }

    //Метод увеличивает текущую дату на Х дней (используется для метода заполнения даты deliveryDate)
    public String samokatDeliveryDateCounting(Calendar today, int numberDaysToPlus) {
        today.add(Calendar.DAY_OF_MONTH, numberDaysToPlus);
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String orderDate = dateformat.format(today.getTime());
        return orderDate;
    }

    //Служебный метод для создания полного набора данных для создания нового заказа
    @Step("Create full test data for new order request")
    public String createFullTestDataForNewOrder() {
        String prepareOrderDataFull = "{\"firstName\":\"" + clientFirstName + "\","
                + "\"lastName\":\"" + clientLastName + "\","
                + "\"address\":\"" + clientAdress + "\","
                + "\"metroStation\":\"" + clientStationName + "\","
                + "\"phone\":\"" + clientPhoneNumber + "\","
                + "\"rentTime\":" + rentTime + ","
                + "\"deliveryDate\":\"" + samokatDeliveryDate + "\","
                + "\"comment\":\"" + clientCommentText + "\","
                + "\"color\":" + Arrays.toString(samokatColor.toArray()) + "}";
        return prepareOrderDataFull;
    }

    //Служебный метод для отправки запроса на создание нового заказа.
    //В качестве аргумента принимает один из тестовых методов по подготовке тестовых данных prepareOrderData
    @Step("Create new order request sending")
    public Response createNewOrderAPIRequest(String regDataVariant) {
        Response newOrderResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(regDataVariant)
                .when()
                .post(createNewOrderEndpointURL);
        return newOrderResponse;
    }

}