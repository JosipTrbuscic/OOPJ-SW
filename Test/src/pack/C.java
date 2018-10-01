package pack;

import java.io.Closeable;
import java.io.IOException;

public class C implements Closeable {
	private Integer i = null;

	public C() {
	}

	public C(Integer i) {
		this.i = i;
	}

	@Override
	public void close() throws IOException {
		System.out.println("close " + this.i);
		throw new IOException("IO exception raised" + this.i);
	}

	public void m(C other) {
		System.out.println("m: " + this.i / other.i);
	}

	public static void main(String[] args) {
		C c1 = new C(1);
		try (C c2 = new C(2)) {
			try (C c0 = new C(12); C c4 = new C(4)) {
				c1.m(c2);
				c1.m(c0);
//				c2.m(new C());
//				c0.m(c4);
			} catch (NullPointerException e ) {
				System.out.println("null pointer xception");
			}catch(IOException e2) {
				System.out.println(e2.getMessage());
//				System.out.println("Caught io exception");
			}
		}catch (Exception e) {
			System.out.println("exception");
		} finally {
			System.out.println("finally");
		}
		System.out.println("end");
	}
}