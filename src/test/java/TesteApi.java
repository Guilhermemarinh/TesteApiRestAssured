import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class TesteApi {

    String vote_id;

    @Test
    public void  cadastro() {
        String url= "https://api.thecatapi.com/v1/user/passwordlesssignup";
        String bodymessage="{\"email\": \"guilhermel_ima@hotmail.com\",\"appDescription\": \"teste api\"}";

        Response response = given().contentType("application/json").body(bodymessage).
                when().post(url);

                response.then().body("message",containsString("SUCCESS")).statusCode(200);

                System.out.println("Retorno =>" + response.body().asString());
    }

    @Test
    public void  votos() {
        String url= "https://api.thecatapi.com/v1/votes/";

        Response response = given().contentType("application/json").body("{\"image_id\": \"rzPxlNge1\", \"value\": \"true\", \"sub_id\": \"demo-cbd3aa\"}").when().post(url);

        response.then().body("message",containsString("SUCCESS")).statusCode(200);

        System.out.println("Retorno =>" + response.body().asString());
        String id = response.jsonPath().getString("id");
        vote_id = id;
        System.out.println("ID =>" + id);
    }
    
    @Test
    public void  deletavotacao() {
        //erro
        votos();
        deletaVoto();
    }

    private void deletaVoto() {
        String url= "https://api.thecatapi.com/v1/votes/{vote_id}";
        Response response = given().contentType("application/json").header("x-api-key","e7f3c42a-7986-4bd8-a955-efaf081e334a").pathParam("vote_id", vote_id).when().delete(url);

        System.out.println("Retorno =>" + response.body().asString());

        response.then().body("message",containsString("SUCCESS")).statusCode(200);

    }

    @Test
    public void  favoritar() {

        favorita();
        desfavorita();
    }

    private void favorita() {
        String url= "https://api.thecatapi.com/v1/favourites";
        Response response =
                given()
                .contentType("application/json")
                .header("x-api-key","e7f3c42a-7986-4bd8-a955-efaf081e334a")
                .body("{\"image_id\": \"9ccXTANkb\"}").when().post(url);
                String id = response.jsonPath().getString("id");
                vote_id = id;

        System.out.println("Retorno Favorita =>" + response.body().asString());
        response.then().body("message",containsString("SUCCESS")).statusCode(200);

    }
    private void desfavorita() {
        String url= "https://api.thecatapi.com/v1/favourites/{favourite_id}";
        Response response =
                given().contentType("application/json")
                        .header("x-api-key","e7f3c42a-7986-4bd8-a955-efaf081e334a")
                        .pathParam("favourite_id", vote_id)
                        .when().delete(url);

        System.out.println("Retorno Desfavorita =>" + response.body().asString());
        response.then().body("message",containsString("SUCCESS")).statusCode(200);


    }
}
