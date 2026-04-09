package edu.eci.dosw;

import edu.eci.dosw.DOSW_Library.DoswLibraryApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = DoswLibraryApplication.class)
@ActiveProfiles("test")
class DoswLibraryApplicationTests {

	@Test
	void contextLoads() {
	}
}