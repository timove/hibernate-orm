/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2012, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.metamodel.internal.source.annotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.metamodel.internal.source.annotations.attribute.Column;
import org.hibernate.metamodel.internal.source.annotations.attribute.PluralAssociationAttribute;
import org.hibernate.metamodel.spi.relational.ForeignKey;
import org.hibernate.metamodel.spi.relational.Value;
import org.hibernate.metamodel.spi.source.PluralAttributeKeySource;
import org.hibernate.metamodel.spi.source.RelationalValueSource;

/**
 * @author Hardy Ferentschik
 * @author Strong Liu <stliu@hibernate.org>
 */
public class PluralAttributeKeySourceImpl implements PluralAttributeKeySource {
	private final PluralAssociationAttribute attribute;
	private final ForeignKey.ReferentialAction deleteAction;

	public PluralAttributeKeySourceImpl(PluralAssociationAttribute attribute) {
		this.attribute = attribute;
		this.deleteAction = attribute.getOnDeleteAction() == OnDeleteAction.CASCADE
				? ForeignKey.ReferentialAction.CASCADE : ForeignKey.ReferentialAction.NO_ACTION;
	}

	@Override
	public List<RelationalValueSource> getValueSources() {
		List<RelationalValueSource> valueSources = new ArrayList<RelationalValueSource>();
		if ( !attribute.getColumnValues().isEmpty() ) {
			for ( Column columnValues : attribute.getColumnValues() ) {
				valueSources.add( new ColumnSourceImpl( attribute, null, columnValues ) );
			}
		}
		return valueSources;
	}

	@Override
	public ForeignKey.ReferentialAction getOnDeleteAction() {
		return deleteAction;
	}

	@Override
	public boolean areValuesIncludedInInsertByDefault() {
		return true;
	}

	@Override
	public boolean areValuesIncludedInUpdateByDefault() {
		return true;
	}

	@Override
	public boolean areValuesNullableByDefault() {
		return true;
	}

	@Override
	public String getExplicitForeignKeyName() {
		return null;
	}

	@Override
	public JoinColumnResolutionDelegate getForeignKeyTargetColumnResolutionDelegate() {
		return null; //new JoinColumnResolutionDelegateImpl();
	}

	@Override
	public List<RelationalValueSource> relationalValueSources() {
		// TODO
		return Collections.emptyList();
	}

	public static class JoinColumnResolutionDelegateImpl implements JoinColumnResolutionDelegate {
		@Override
		public List<Value> getJoinColumns(JoinColumnResolutionContext context) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public String getReferencedAttributeName() {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	}
}