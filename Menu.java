import java.util.List;
import java.util.Scanner;

public class Menu {
	private String title;
	private List<String> options;
	private static final Scanner scanner = new Scanner(System.in);

	public Menu(List<String> options) {
		this("Menu", options);
	}

	public Menu(String title, List<String> options) {
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException("O menu precisa ter pelo menos uma opção.");
		}

		if (title == null || title.trim().isEmpty()) {
			this.title = "Menu";
		} else {
			this.title = title;
		}

		this.options = options;
	}

	public int getSelection() {
		int op = 0;

		while (op == 0) {
			System.out.println(title + "\n");

			int i = 1;
			for (String option : options) {
				System.out.println(i++ + " - " + option);
			}

			System.out.println("Informe a opcao desejada: ");
			String str = scanner.nextLine();

			try {
				op = Integer.parseInt(str.trim());
			} catch (NumberFormatException e) {
				System.out.println("Entrada invalida! Digite apenas numeros.");
				op = 0;
				continue;
			}

			if (op < 1 || op >= i) {
				System.out.println("Opcao errada!");
				op = 0;
			}
		}

		return op;
	}

	public static String readLine() {
		return scanner.nextLine();
	}
}