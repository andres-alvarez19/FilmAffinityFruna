package ufro.dci.filmaffinityfruna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class FilmAffinityFrunaApplication {

	public static void main(String[] args) {

		SpringApplication.run(FilmAffinityFrunaApplication.class, args);

		String url = "jdbc:mysql://localhost:3306/wikipeliculas";
		String user = "root";
		String password = "agusbenja29";

		try (Connection connection = DriverManager.getConnection(url, user, password);
			 Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery("SELECT FROM Actor");

			while (resultSet.next()) {
				String nombre = resultSet.getString("nombre");
				String fechaNacimiento = resultSet.getString("fecha_nacimiento");
				String nacionalidad = resultSet.getString("nacionalidad");
				String urlsRedesSociales = resultSet.getString("urls_redes_sociales");
				String filmografia = resultSet.getString("filmografia");

				System.out.println("Nombre: " + nombre);
				System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
				System.out.println("Nacionalidad: " + nacionalidad);
				System.out.println("Redes Sociales: " + urlsRedesSociales);
				System.out.println("Filmografía: " + filmografia);
				System.out.println("------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
