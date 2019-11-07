package game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class SettingsTest {
	@Test
	public void testSettings() {
		
		//getRandomName()
		int len = Settings.PLAYER_NAME.size();
		ArrayList<String> liste = new ArrayList<String>(Settings.PLAYER_NAME);
		
		for(int i = 0;i<len;i++) {
			String nom = Settings.getRandomName();
			assertEquals(false, Settings.PLAYER_NAME.contains(nom));
			assertEquals(true,liste.contains(nom));
		}
		assertEquals(0,Settings.PLAYER_NAME.size());
		
		//getRandomIA()
		assertEquals(true,Settings.IA_list[0] == Settings.getRandomIA());
		assertEquals(true,Settings.IA_list.length == 1);
		assertEquals(false,null == Settings.getRandomIA());
		
		
		
		
	}
}
