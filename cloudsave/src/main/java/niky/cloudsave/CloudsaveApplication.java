package niky.cloudsave;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudsaveApplication {
	public static void main(String[] args) {
		SpringApplication.run(CloudsaveApplication.class, args);
	}

	/**
	 * Filerna sparas i en MultipartFile som använder ett UserObject för att kontrollera vem som skickar in filen.
	 * Det skickas sedan till FileService där vi bygger filen med konstruktorn. Med inbyggda metoder lagras datan
	 *  i bytes[] arrayen.
	 *
	 *  Sedan sparar vi filen i databasen med fileRepository.save(fileDB);
	 *
	 * @ManyToOne annotation på User user för att en user kan ha flera filer. (I FileDB.java filen).
	 */
}


