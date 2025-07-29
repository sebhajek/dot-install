package seb_dot_hajek_at_gmail_dot_com.dots.shared.colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public record Theme(
  String          schemeName,
  RGB             foreground,
  RGB             background,
  RGB             highlightForeground,
  RGB             highlightBackground,
  RGB             cursorForeground,
  RGB             cursorBackground,
  Map<Short, RGB> ansi
) {
	public Theme {
		if (schemeName == null || schemeName.strip().isEmpty()) {
			throw new InvalidThemeException(
			  "Scheme name cannot be empty or whitespace-only"
			);
		}

		ansi = new HashMap<>(ansi);

		final List<Short> invalidIndices =
		  ansi.keySet().stream().filter(i -> i < 0 || i > 15).toList();
		if (!invalidIndices.isEmpty()) {
			throw new InvalidThemeException(
			  "Invalid ANSI indices: " + invalidIndices
			  + ". Must be between 0 and 15."
			);
		}
	}

	public static class InvalidThemeException extends IllegalStateException {
		public InvalidThemeException(final String message) { super(message); }
	}

	public boolean isLight() {
		return background.luminance() > foreground.luminance();
	}

	public static String csvHeader() {
		final List<String> headers = new ArrayList<>(
		  List.of("name", "fg", "bg", "hl-fg", "hl-bg", "cur-fg", "cur-bg")
		);
		IntStream.range(0, 16).forEach(i -> headers.add("ansi-" + i));
		return "#" + String.join(",", headers);
	}

	public String toCSV() {
		final List<String> parts = new ArrayList<>(List.of(
		  schemeName,
		  foreground.toHex(),
		  background.toHex(),
		  highlightForeground.toHex(),
		  highlightBackground.toHex(),
		  cursorForeground.toHex(),
		  cursorBackground.toHex()
		));
		for (short i = 0; i < 16; i++) {
			final RGB color = ansi.get(i);
			parts.add(color != null ? color.toHex() : "000000");
		}
		return String.join(",", parts);
	}

	public Theme normalize() {
		return normalizeName().normalizeColor();
	}

	private Theme normalizeName() {
		return new Theme(
		  schemeName.toLowerCase()
		    .replace("(", " ")
		    .replace(")", " ")
		    .strip()
		    .replaceAll("[ _]+", "-")
		    .replaceAll("-+", "-")
		    .replace("+", "-plus-")
		    .replaceAll("^-+|-+$", ""),
		  foreground,
		  background,
		  highlightForeground,
		  highlightBackground,
		  cursorForeground,
		  cursorBackground,
		  ansi
		);
	}

	private Theme normalizeColor() {
		final Map<Short, RGB> normalizedAnsi = new HashMap<>(ansi);

		if (isLight()) {
			maybeSwap(normalizedAnsi, (short) 0, (short) 7);
			maybeSwap(normalizedAnsi, (short) 8, (short) 15);
		} else {
			maybeSwap(normalizedAnsi, (short) 0, (short) 7, true);
			maybeSwap(normalizedAnsi, (short) 8, (short) 15, true);
		}

		for (short i = 0; i < 8; i++) {
			final short normal = i;
			final short bright = (short) (i + 8);
			if (normalizedAnsi.containsKey(normal)
			    && normalizedAnsi.containsKey(bright)) {
				final RGB normalColor = normalizedAnsi.get(normal);
				final RGB brightColor = normalizedAnsi.get(bright);
				if (normalColor.distanceNormalized(background)
				    < brightColor.distanceNormalized(background)) {
					normalizedAnsi.put(normal, brightColor);
					normalizedAnsi.put(bright, normalColor);
				}
			}
		}

		return new Theme(
		  schemeName,
		  foreground,
		  background,
		  highlightForeground,
		  highlightBackground,
		  cursorForeground,
		  cursorBackground,
		  normalizedAnsi
		);
	}

	private void maybeSwap(
	  final Map<Short, RGB> map, final short a, final short b
	) {
		maybeSwap(map, a, b, false);
	}

	private void maybeSwap(
	  final Map<Short, RGB> map,
	  final short           a,
	  final short           b,
	  final boolean         reverse
	) {
		if (map.containsKey(a) && map.containsKey(b)) {
			final RGB colorA = map.get(a);
			final RGB colorB = map.get(b);
			if ((reverse && colorA.luminance() > colorB.luminance())
			    || (!reverse && colorA.luminance() < colorB.luminance())) {
				map.put(a, colorB);
				map.put(b, colorA);
			}
		}
	}
}
