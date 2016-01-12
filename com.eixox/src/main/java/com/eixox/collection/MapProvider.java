package com.eixox.collection;

public interface MapProvider<G, T> {
	T provide(G obj);
}
