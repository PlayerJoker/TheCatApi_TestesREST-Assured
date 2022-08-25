import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class TesteQa{
    String id;
    String idvotacao;

    // before class 1:17:10 https://www.youtube.com/watch?v=FqfIhHNxOoI

    @Test
    public void americanas() {
                 /* Dado */given()
                /* Quando */.when().get("https://cep-v2-americanas-npf.b2w.io/cep/73035094")
                /* Entao */.then().log().all(); //.log() e .all() para retornar os valores da API
    }

    @Test
    public void cadastroapi() {
        given()
                .contentType("application/json")
                .body("{\"email\": \"fabioaugusto.almeida@icloud.com\", \"appDescription\": \"testandoapi\"}")
                .when()
                .post("https://api.thecatapi.com/v1/user/passwordlesssignup")
                .then()
                .log().all()
                .statusCode(400);

    }

  /*  @Test
    public void cadastroapiObrigatorio() {
        given()
                .contentType("application/json")
                .body("{\"appDescription\": \"testandoapi\"}")
                .when()
                .post("https://api.thecatapi.com/v1/user/passwordlesssignup")
                .then()
                .log().all()
                .body("message", containsString("\"email\" is required"))
                .statusCode(400);

    } */

    @Test
    public void efetuarVotacao() {
        given()
                .contentType("application/json")
                .body("{\"image_id\": \"3D-hT3sGc\", \"value\": true, \"sub_id\": \"demo-5141c3\"}")
                .header("x-api-key","live_CYVq1q0xnq8MrKygJEeSXNgfprk19q2EiKxEIK1IC8U3pcT2iNEgYjuVSxQRm3DR")
                .when()
                .post("https://api.thecatapi.com/v1/votes")
                .then()
                .statusCode(201)
                .body("message",is("SUCCESS"))
                .log().all();
    }

    @Test
    public void pegarVotacao() {
                given()
                .header("x-api-key","live_CYVq1q0xnq8MrKygJEeSXNgfprk19q2EiKxEIK1IC8U3pcT2iNEgYjuVSxQRm3DR")
                .when()
                .get("https://api.thecatapi.com/v1/votes")
                .then()
                .statusCode(200)
                .log().all();

    }


    @Test
    public void pegar_Votacao2() {
        Response response =
                given()
                    .header("x-api-key","live_CYVq1q0xnq8MrKygJEeSXNgfprk19q2EiKxEIK1IC8U3pcT2iNEgYjuVSxQRm3DR")
                .when()
                    .get("https://api.thecatapi.com/v1/votes");
                response.then()
                       //verificar o conteudo de um valor especifico .body("image_id", hasItems("b","8pb"))
                    .statusCode(200)
                    .log().all();

                //id é uma variavel global
                 id = response.jsonPath().getString("id");
                System.out.println("ID RETORNADO =>" + id);


                String id0 = response.jsonPath().getString("id[0]");
                System.out.println("ID 0 =>" + id0);
                System.out.println(id0);

    }

    @Test
    public void pegarVotacaoDel() {
        Response response =
                given()
                    .header("x-api-key","live_CYVq1q0xnq8MrKygJEeSXNgfprk19q2EiKxEIK1IC8U3pcT2iNEgYjuVSxQRm3DR")
                .when()
                    .get("https://api.thecatapi.com/v1/votes");
                response.then()
                    .log().all()
                    .body("image_id", hasItems("3D-hT3sGc"))
                    .statusCode(200);



                idvotacao = response.jsonPath().getString("id[1]");
                System.out.println("ID RETORNADO =>" + idvotacao);


    }

    @Test
    public void deletarVotacao() {

        efetuarVotacao();
        pegarVotacaoDel();

                given()
                        .header("x-api-key","live_CYVq1q0xnq8MrKygJEeSXNgfprk19q2EiKxEIK1IC8U3pcT2iNEgYjuVSxQRm3DR")
                        .contentType("application/json")
                        .pathParam("vote_id", idvotacao)
                .when()
                        .delete("https://api.thecatapi.com/v1/votes/{vote_id}")
                .then()
                        .statusCode(200).log().all();
    }
}
