package seb_dot_hajek_at_gmail_dot_com.dots.shared.colors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RGB(short red, short green, short blue) {

	private static final Pattern HEX_PATTERN = Pattern.compile(
	  "#?(?<red>[a-fA-F\\d]{2})"
	  + "(?<green>[a-fA-F\\d]{2})"
	  + "(?<blue>[a-fA-F\\d]{2})"
	);

	public static RGB fromHex(final String colorHex)
	  throws          IllegalArgumentException {
        final Matcher matcher = HEX_PATTERN.matcher(colorHex);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
              "Invalid hex color: " + colorHex
            );
        }
        final short red = (short) Integer.parseInt(matcher.group("red"), 16);
        final short green =
          (short) Integer.parseInt(matcher.group("green"), 16);
        final short blue = (short) Integer.parseInt(matcher.group("blue"), 16);
        return new RGB(red, green, blue);
	}

	public float luminance() {
		return (0.2126f * this.red) + (0.7152f * this.green)
		  + (0.0722f * this.blue);
	}

	public float luminanceNormalized() {
		return this.luminance() / 255.0f;
	}

	public String toHex() {
		return String.format("%02x%02x%02x", this.red, this.green, this.blue);
	}

	public String toHex(final String prefix) {
		return String.format("%s%s", prefix, this.toHex());
	}

	public float contrast(final RGB other) {
		final var l1 = this.luminanceNormalized();
		final var l2 = other.luminanceNormalized();
		return (Math.max(l1, l2) + 0.05f) / (Math.min(l1, l2) + 0.05f);
	}

	public float contrastNormalized(final RGB other) {
		return this.contrast(other) / 21.0f;
	}

	public int distance(final RGB other) {
		return Math.abs(
		  Math.abs(this.red - other.red())
		  + Math.abs(this.green - other.green())
		  + Math.abs(this.blue - other.blue())
		);
	}

	public float distanceNormalized(final RGB other) {
		return this.distance(other) / 765f;
	}
}
