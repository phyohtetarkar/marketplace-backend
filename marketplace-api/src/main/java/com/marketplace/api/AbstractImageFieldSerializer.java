package com.marketplace.api;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public abstract class AbstractImageFieldSerializer extends JsonSerializer<String> {

	protected final String baseUrl;

	protected final String dir;

	protected AbstractImageFieldSerializer(String dir) {
		this.dir = dir;
		this.baseUrl = ApplicationContextProvider.getContext().getEnvironment()
				.resolvePlaceholders("${app.image.base-url}");
	}

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (StringUtils.startsWithIgnoreCase(value, "http")) {
			gen.writeString(value);
		} else if (StringUtils.hasText(value)) {
			gen.writeString(String.format("%s/%s/%s", baseUrl, dir, value));
		}
	}

	public static class BannerImageSerializer extends AbstractImageFieldSerializer {
		public BannerImageSerializer() {
			super("banner");
		}
	}

	public static class CategoryImageSerializer extends AbstractImageFieldSerializer {
		public CategoryImageSerializer() {
			super("category");
		}
	}

	public static class ProductImageSerializer extends AbstractImageFieldSerializer {
		public ProductImageSerializer() {
			super("product");
		}
	}

	public static class ShopImageSerializer extends AbstractImageFieldSerializer {
		public ShopImageSerializer() {
			super("shop");
		}
	}

	public static class UserImageSerializer extends AbstractImageFieldSerializer {
		public UserImageSerializer() {
			super("user");
		}
	}

	public static class OrderImageSerializer extends AbstractImageFieldSerializer {
		public OrderImageSerializer() {
			super("order");
		}
	}

	public static class SiteAssetsImageSerializer extends AbstractImageFieldSerializer {
		public SiteAssetsImageSerializer() {
			super("site");
		}
	}
}
