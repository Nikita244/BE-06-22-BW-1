package it.gruppo6.app;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import java.util.Random;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;


public class Decorazioni {
	//colori
	static AnsiFormat titolo = new AnsiFormat(TEXT_COLOR(208), BOLD());
	public static Attribute ORANGE_TEXT = TEXT_COLOR(208);
	
	public static void titolo() {
		System.out.println(colorize("\n\n          __  __ _______     _______ _____             _____ _____   ____  _____ _______ _____ \r\n    /\\   |  \\/  |__   __|   |__   __|  __ \\     /\\    / ____|  __ \\ / __ \\|  __ \\__   __|_   _|\r\n   /  \\  | \\  / |  | |         | |  | |__) |   /  \\  | (___ | |__) | |  | | |__) | | |    | |  \r\n  / /\\ \\ | |\\/| |  | |         | |  |  _  /   / /\\ \\  \\___ \\|  ___/| |  | |  _  /  | |    | |  \r\n / ____ \\| |  | |  | |         | |  | | \\ \\  / ____ \\ ____) | |    | |__| | | \\ \\  | |   _| |_ \r\n/_/    \\_\\_|  |_|  |_|         |_|  |_|  \\_\\/_/    \\_\\_____/|_|     \\____/|_|  \\_\\ |_|  |_____|\n\n", titolo));
	}
	
	//random generatore
	public static Attribute getRandomColor() {
	    Attribute[] arrayColori = { BRIGHT_YELLOW_TEXT(), BRIGHT_CYAN_TEXT(), BRIGHT_MAGENTA_TEXT(), BRIGHT_GREEN_TEXT() };
	    Random r = new Random();
	    int i = r.nextInt(arrayColori.length);
	    return arrayColori[i];
	}
}
