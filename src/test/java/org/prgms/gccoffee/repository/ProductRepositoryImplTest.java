package org.prgms.gccoffee.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgms.gccoffee.model.Category;
import org.prgms.gccoffee.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProductRepositoryImplTest {

	static EmbeddedMysql embeddedMysql;

	@BeforeAll
	static void setUp() {
		final MysqldConfig config = aMysqldConfig(v8_0_11)
			.withCharset(Charset.UTF8)
			.withPort(2215)
			.withUser("test", "test1234!")
			.withTimeZone("Asia/Seoul")
			.build();

		embeddedMysql = anEmbeddedMysql(config)
			.addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
			.start();

	}

	@AfterAll
	static void cleanUp() {
		embeddedMysql.stop();
	}

	private static final Product newProduct = new Product(UUID.randomUUID(), "new-product",
		Category.COFFEE_BEAN_PACKAGE,
		1000L);

	@Autowired
	ProductRepository productRepository;

	@DisplayName("상품을 추가할 수 있다.")
	@Test
	@Order(1)
	void test_insert() {
		productRepository.insert(newProduct);
		final List<Product> all = productRepository.findAll();
		assertThat(all.isEmpty(), is(false));
	}

	@DisplayName("상품을 이름으로 조회할 수 있다.")
	@Test
	@Order(2)
	void testFindByName() {
		final Optional<Product> product = productRepository.findByName(newProduct.getProductName());
		assertThat(product.isEmpty(), is(false));
	}

	@DisplayName("상품을 id으로 조회할 수 있다.")
	@Test
	@Order(3)
	void testIfndById() {
		final Optional<Product> product = productRepository.findById(newProduct.getProductId());
		assertThat(product.isEmpty(), is(false));
	}

	@DisplayName("상품을 category으로 조회할 수 있다.")
	@Test
	@Order(4)
	void testFindByCategory() {
		final List<Product> product = productRepository.findByCategory(newProduct.getCategory());
		assertThat(product.isEmpty(), is(false));
	}

	@DisplayName("T상품을 수정할 수 있다")
	@Test
	@Order(5)
	void testUpdate() {
		newProduct.setProductName("updated_product");
		productRepository.update(newProduct);

		final Optional<Product> product = productRepository.findById(newProduct.getProductId());
		assertThat(product.isEmpty(), is(false));
		assertThat(product.get(), samePropertyValuesAs(newProduct));
	}

	@DisplayName("T상품을 완전 삭제한다.")
	@Test
	@Order(6)
	void testDeleteAll() {
		productRepository.deleteAll();
		final List<Product> all = productRepository.findAll();
		assertThat(all.isEmpty(), is(true));
	}
}
