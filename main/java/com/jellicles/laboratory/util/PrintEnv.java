public class PrintEnv {
	public static void main(String...args) {
		String autopath = System.getenv("autopath");
		System.out.println(autopath);
	}
}
