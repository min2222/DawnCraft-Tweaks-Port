package com.afunproject.dawncraft.invasion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InvasionRegistry {

	private static final Map<InvasionKey, InvasionEntry> ENTRIES = Maps.newHashMap();

	public static void register(InvasionEntry entry) {
		ENTRIES.put(InvasionKey.create(entry.name.toUpperCase(Locale.US), entry.name), entry);
	}

	public static InvasionEntry getInvasion(String name) {
		return getInvasion(InvasionKey.getKey(name));
	}

	public static List<InvasionEntry> getInvasions() {
		List<InvasionEntry> invasions = Lists.newArrayList();
		for (InvasionEntry invasion : ENTRIES.values()) invasions.add(invasion.copy());
		return invasions;
	}

	public static List<String> getKeys() {
		return ENTRIES.keySet().stream().map(key->key.toString()).toList();
	}

	public static InvasionEntry getInvasion(InvasionKey key) {
		return ENTRIES.get(key);
	}

}
