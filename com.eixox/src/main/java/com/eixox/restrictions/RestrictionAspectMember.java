package com.eixox.restrictions;

import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;

public class RestrictionAspectMember extends AbstractAspectMember {

	public RestrictionAspectMember(AspectMember member, RestrictionList restrictions) {
		super(member);
		this._Restrictions = restrictions;
	}

	private final RestrictionList _Restrictions;

	public final RestrictionList getRestrictions() {
		return this._Restrictions;
	}
	
	public final boolean validate(Object entity){
		return this._Restrictions.validate(super.getValue(entity));
	}
	
	public final String getRestrictionMessageFor(Object entity){
		return this._Restrictions.getRestrictionMessageFor(super.getValue(entity));
	}
	
	public final void assertValid(Object entity) throws RestrictionException{
		this._Restrictions.assertValid(super.getValue(entity));
	}
}
