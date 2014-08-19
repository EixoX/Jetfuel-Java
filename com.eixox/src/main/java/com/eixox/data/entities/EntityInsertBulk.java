package com.eixox.data.entities;

public abstract class EntityInsertBulk<T> {

	public final EntityAspect aspect;
	
	public EntityInsertBulk(EntityAspect aspect){
		this.aspect = aspect;
	}
	
	public abstract void add(T entity);
	public abstract int execute();
}
