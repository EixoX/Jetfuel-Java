package com.eixox.data.entities;

import java.util.ArrayList;

import com.eixox.data.ColumnType;
import com.eixox.data.DataInsert;
import com.eixox.data.Storage;

public class EntityInsert {

	public final EntityAspect aspect;
	public final Storage storage;
	private final DataInsert insert;
	private final ArrayList<EntityAspectMember> members;

	public EntityInsert(EntityAspect aspect, Storage storage) {
		this.aspect = aspect;
		this.storage = storage;
		this.insert = storage.insert(aspect.tableName);
		this.members = new ArrayList<EntityAspectMember>(aspect.getCount());
		for (EntityAspectMember member : aspect) {
			if (member.columntType != ColumnType.IDENTITY)
				if (!member.isReadOnly()) {
					this.members.add(member);
					this.insert.cols.add(member.columnName);
				}
		}
	}

	public final void reset() {
		this.insert.clear();
	}

	public final void add(Object entity) {
		Object[] values = new Object[members.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = members.get(i).getValue(entity);
		}
		this.insert.add(values);
	}

	public final long execute() {
		return this.insert.execute();
	}

	public final Object executeAndScopeIdentity() {
		return this.insert.executeAndScopeIdentity();
	}
}
