package project.entity;

/**
 * Product nesnelerinin isim ve tüketilme süresi tutulur 
 * Drink ve Food class'larının parent class'ı
 */
public class Product {

	private String name;
	private long consumeTime;

	public Product(String name, long consumeTime) {
		super();
		this.name = name;
		this.consumeTime = consumeTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(long consumeTime) {
		this.consumeTime = consumeTime;
	}
}
